/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifiertrue.verifiertrues.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.programatcontent.programatcont.entity.DistributeContent;
import com.jeeplus.modules.programatcontent.programatcont.mapper.DistributeContentMapper;
import com.jeeplus.modules.verifiertrue.verifiertrues.mapper.BankVerifierTrueMapper;
import com.jeeplus.modules.verifierts.verifier.entity.BankMenu;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.verifiertrue.verifiertrues.entity.BankVerifierTrue;
import com.jeeplus.modules.verifiertrue.verifiertrues.service.BankVerifierTrueService;

/**
 * 添加审核人审核栏目Controller
 * @author chenl
 * @version 2019-12-09
 */
@Controller
@RequestMapping(value = "${adminPath}/verifiertrue/verifiertrues/bankVerifierTrue")
public class BankVerifierTrueController extends BaseController {

	@Autowired
	private BankVerifierTrueService bankVerifierTrueService;
	@Autowired
	private BankVerifierTrueMapper bankVerifierTrueMapper;
	@Autowired
	private DistributeContentMapper distributeContentMapper;

	@ModelAttribute
	public BankVerifierTrue get(@RequestParam(required=false) String id) {
		BankVerifierTrue entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankVerifierTrueService.get(id);
		}
		if (entity == null){
			entity = new BankVerifierTrue();
		}
		return entity;
	}
	
	/**
	 * 审核栏目人树表页面
	 */
	@RequiresPermissions("verifiertrue:verifiertrues:bankVerifierTrue:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		treeData1();
		return "modules/verifiertrue/verifiertrues/bankVerifierTrueList";
	}

	/**
	 * 审核栏目人树表数据
	 */
	@RequiresPermissions("verifiertrue:verifiertrues:bankVerifierTrue:list")
	@ResponseBody
	@RequestMapping(value = "data")
	public List<BankVerifierTrue> data(BankVerifierTrue bankVerifierTrue) {

		return bankVerifierTrueService.findList(bankVerifierTrue);
	}

	/**
	 * 查看，增加，编辑审核栏目人表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequiresPermissions(value={"verifiertrue:verifiertrues:bankVerifierTrue:view","verifiertrue:verifiertrues:bankVerifierTrue:add","verifiertrue:verifiertrues:bankVerifierTrue:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankVerifierTrue bankVerifierTrue, Model model) {
		if (bankVerifierTrue.getParent()!=null && StringUtils.isNotBlank(bankVerifierTrue.getParent().getId())){
			bankVerifierTrue.setParent(bankVerifierTrueService.get(bankVerifierTrue.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(bankVerifierTrue.getId())){
				BankVerifierTrue bankVerifierTrueChild = new BankVerifierTrue();
				bankVerifierTrueChild.setParent(new BankVerifierTrue(bankVerifierTrue.getParent().getId()));
				List<BankVerifierTrue> list = bankVerifierTrueService.findList(bankVerifierTrue); 
				if (list.size() > 0){
					bankVerifierTrue.setSort(list.get(list.size()-1).getSort());
					if (bankVerifierTrue.getSort() != null){
						bankVerifierTrue.setSort(bankVerifierTrue.getSort() + 30);
					}
				}
			}
		}
		if (bankVerifierTrue.getSort() == null){
			bankVerifierTrue.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("bankVerifierTrue", bankVerifierTrue);
		return "modules/verifiertrue/verifiertrues/bankVerifierTrueForm";
	}

	/**
	 * 保存审核栏目人
	 */
	@ResponseBody
	@RequiresPermissions(value={"verifiertrue:verifiertrues:bankVerifierTrue:add","verifiertrue:verifiertrues:bankVerifierTrue:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankVerifierTrue bankVerifierTrue, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankVerifierTrue);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		System.out.println(bankVerifierTrue.toString());
        //获取该栏目原审核人,要在新设置前获得
        BankVerifierTrue bank = bankVerifierTrueService.get(bankVerifierTrue.getId());
		//新增或编辑表单保存
		bankVerifierTrueService.save(bankVerifierTrue);//保存
		//判断是否修改栏目审核人,如果是则修改发稿表对应审核人未审核的审核人设置为该审核人
        if (!(bank.getVerifyPerson()+"").equals((bankVerifierTrue.getVerifyPerson()+""))){
                //修改栏目的审核人
                int one = distributeContentMapper.updateVeriftyByID(bankVerifierTrue.getColumnId(),bankVerifierTrue.getVerifyPerson());
                //修改副栏目的审核人
                //所有稿件里包含该栏目的
                List<DistributeContent> dlist = distributeContentMapper.findDistrByProgramatParentid(bankVerifierTrue.getColumnId());
                //对比稿件副栏目审核人力是否包含原审核人,如果有就替换成当前审核人,如果没有就添加当前审核人
                for (int i = 0; i <dlist.size(); i++) {

                    if (dlist.get(i).getRemarks().contains(bank.getVerifyPerson())){
                       String newString = dlist.get(i).getRemarks().replace(bank.getVerifyPerson(),bankVerifierTrue.getVerifyPerson());
                       int e =distributeContentMapper.updateVeriftyByParentid(dlist.get(i).getId(),newString);
                        System.out.println(e>0?"替换成功":"替换失败");
                    }else{
                        String[] split = dlist.get(i).getRemarks().split(",");
                        String[] sp=new String[split.length+1];
                        for (int k = 0; k <split.length ; k++) {
                            sp[k]=split[k];
                        }
                        sp[sp.length-1]=bankVerifierTrue.getVerifyPerson();
                        int e =distributeContentMapper.updateVeriftyByParentid(dlist.get(i).getId(),StringUtils.join(sp,","));
                        System.out.println(e>0?"追加成功":"追加失败");
                    }
                }
        	}

		//查询该栏目下是否有子栏目
		List<BankMenu> bankMenuExtByIdAll = bankVerifierTrueMapper.findBankMenuExtByIdAll(bankVerifierTrue.getColumnId());
		System.out.println(bankMenuExtByIdAll.size());
		//如果有,同步子栏目审核人
		if (bankMenuExtByIdAll.size()>0){
			for (int i = 0; i <bankMenuExtByIdAll.size() ; i++) {
				bankVerifierTrueMapper.updateById(bankVerifierTrue.getVerifyPerson(),bankVerifierTrue.getRemarks(),bankMenuExtByIdAll.get(i).getId());
			}

		}
		j.setSuccess(true);
		j.put("bankVerifierTrue", bankVerifierTrue);
		j.setMsg("保存审核栏目人成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<BankVerifierTrue> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return bankVerifierTrueService.getChildren(parentId);
	}
	
	/**
	 * 删除审核栏目人
	 */
	@ResponseBody
	@RequiresPermissions("verifiertrue:verifiertrues:bankVerifierTrue:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BankVerifierTrue bankVerifierTrue) {
		AjaxJson j = new AjaxJson();
		bankVerifierTrueService.delete(bankVerifierTrue);
		j.setSuccess(true);
		j.setMsg("删除审核栏目人成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BankVerifierTrue> list = bankVerifierTrueService.findList(new BankVerifierTrue());
		for (int i=0; i<list.size(); i++){
			BankVerifierTrue e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				System.out.println();
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					System.out.println();
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}


	@RequestMapping(value = "updateVerifier")
	public String updateVerifier( BankVerifierTrue bankVerifierTrue, Model model) {
		System.out.println("dffffffffffffffffffffffffffffffffffffffffffff");
		System.out.println(bankVerifierTrue.getColumnName()+"========"+bankVerifierTrue.getColumnId()+"=="+bankVerifierTrue.getVerifyPerson());
		List<BankMenu> bankMenuExtByIdAll = bankVerifierTrueMapper.findBankMenuExtByIdAll(bankVerifierTrue.getColumnId());
		System.out.println(bankMenuExtByIdAll.size());
		System.out.println(bankMenuExtByIdAll.toArray());
		bankVerifierTrueMapper.updateById(bankVerifierTrue.getVerifyPerson(),bankVerifierTrue.getRemarks(),bankVerifierTrue.getColumnId());
		if (bankMenuExtByIdAll.size()>0){
			//System.out.println(bankVerifierTrue.getColumnName()+"=="+bankVerifierTrue.getColumnId());
			for (int i = 0; i <bankMenuExtByIdAll.size() ; i++) {
				bankVerifierTrueMapper.updateById(bankVerifierTrue.getVerifyPerson(),bankVerifierTrue.getRemarks(),bankMenuExtByIdAll.get(i).getId());
			}

		}

		//model.addAttribute("mode", mode);
		model.addAttribute("bankVerifierTrue", bankVerifierTrue);
		return "modules/verifiertrue/verifiertrues/bankVerifierTrueForm";
	}

	/**
	 * 查询所有栏目
	 * */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData1")
	public void treeData1() {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//获取1级栏目
		List<BankMenu> listfist = bankVerifierTrueMapper.findBankMenuFistAll();
		//添加一级栏目
		for (int i = 0; i <listfist.size() ; i++) {
            //添加1级
			BankVerifierTrue bb=new BankVerifierTrue();
			bb.setId(IdGen.uuid());
			bb.setColumnId(listfist.get(i).getId());
			bb.setColumnName(listfist.get(i).getPname());

			if (listfist.get(i).getId().equals("10b93924e01f4ae48aba7949da22eaf1")){
				bb.setSort(10);
			}else {
				bb.setSort(30);
			}
			bb.setDelFlag("0");
			BankVerifierTrue byClunmName = bankVerifierTrueMapper.findByClunmName(listfist.get(i).getId());
			if (null==byClunmName){
				bankVerifierTrueMapper.addBank(bb);
			}
			System.out.println(listfist.get(i).getId()+"==="+listfist.get(i).getPname());
            //添加二级
			List<BankMenu> bankMenuExtByIdAll = bankVerifierTrueMapper.findBankMenuExtByIdAll(listfist.get(i).getId());
//			List<BankVerifierTrue> allByPid = bankVerifierTrueMapper.findAllByPid(listfist.get(i).getId());
			if (bankMenuExtByIdAll.size()>0){

				for (int j = 0; j <bankMenuExtByIdAll.size() ; j++) {

					BankVerifierTrue bb2=new BankVerifierTrue();
					bb2.setId(IdGen.uuid());
					bb2.setColumnId(bankMenuExtByIdAll.get(j).getId());
					bb2.setColumnName(bankMenuExtByIdAll.get(j).getPname());
					bb2.setSort(30);
					bb2.setDelFlag("0");
					bb2.setParent(bb);
					System.out.println(bankMenuExtByIdAll.get(j).getId()+"==="+bankMenuExtByIdAll.get(j).getPname());
					if (null==bankVerifierTrueMapper.findByClunmName(bankMenuExtByIdAll.get(j).getId())){
						bankVerifierTrueMapper.addBank(bb2);
					}
                    //只有部室网栏 添加三级
					if (bankMenuExtByIdAll.get(j).getParentId().equals("8fbe0c55539c4151a90661af16f24be6")){
						List<BankMenu> bankMenuExtByIdAll1 = bankVerifierTrueMapper.findBankMenuExtByIdAll(bankMenuExtByIdAll.get(j).getId());
						if (bankMenuExtByIdAll1.size()>0) {
							for (int k = 0; k < bankMenuExtByIdAll1.size(); k++) {   //添加三级
								BankVerifierTrue bb3 = new BankVerifierTrue();
								bb3.setId(IdGen.uuid());
								bb3.setColumnId(bankMenuExtByIdAll1.get(k).getId());
								bb3.setColumnName(bankMenuExtByIdAll1.get(k).getPname());
								bb3.setSort(30);
								bb3.setDelFlag("0");
								bb3.setParent(bb2);
								if (null==bankVerifierTrueMapper.findByClunmName(bankMenuExtByIdAll1.get(k).getId())){
									bankVerifierTrueMapper.addBank(bb3);
								}

							}
						}

					}



				}


			}


		}

	}


}