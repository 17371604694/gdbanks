/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifierts.verifier.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Maps;
import com.jeeplus.modules.verifierts.verifier.entity.BankMenu;
import com.jeeplus.modules.verifierts.verifier.entity.BankUser;
import com.jeeplus.modules.verifierts.verifier.mapper.BankVerifierMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.verifierts.verifier.entity.BankVerifier;
import com.jeeplus.modules.verifierts.verifier.service.BankVerifierService;

/**
 * 添加审核人审核栏目Controller
 * @author chenl
 * @version 2019-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/verifierts/verifier/bankVerifier")
public class BankVerifierController extends BaseController {

	@Autowired
	private BankVerifierService bankVerifierService;
	@Autowired
	private BankVerifierMapper bankVerifierMapper;
	
	@ModelAttribute
	public BankVerifier get(@RequestParam(required=false) String id) {
		BankVerifier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankVerifierService.get(id);
		}
		if (entity == null){
			entity = new BankVerifier();
		}
		return entity;
	}
	
	/**
	 * 添加审核成功列表页面
	 */
	@RequiresPermissions("verifierts:verifier:bankVerifier:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankVerifier bankVerifier, Model model) {
		model.addAttribute("bankVerifier", bankVerifier);
		return "modules/verifierts/verifier/bankVerifierList";
	}
	
		/**
	 * 添加审核成功列表数据
	 */
	@ResponseBody
	@RequiresPermissions("verifierts:verifier:bankVerifier:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankVerifier bankVerifier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankVerifier> page = bankVerifierService.findPage(new Page<BankVerifier>(request, response), bankVerifier); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑添加审核成功表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"verifierts:verifier:bankVerifier:view","verifierts:verifier:bankVerifier:add","verifierts:verifier:bankVerifier:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankVerifier bankVerifier, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankVerifier", bankVerifier);
		return "modules/verifierts/verifier/bankVerifierForm";
	}

	/**
	 * 保存添加审核成功
	 */
	@ResponseBody
	@RequiresPermissions(value={"verifierts:verifier:bankVerifier:add","verifierts:verifier:bankVerifier:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankVerifier bankVerifier, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankVerifier);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankVerifierService.save(bankVerifier);//保存
		j.setSuccess(true);
		j.setMsg("保存添加审核成功成功");
		return j;
	}

	
	/**
	 * 批量删除添加审核成功
	 */
	@ResponseBody
	@RequiresPermissions("verifierts:verifier:bankVerifier:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankVerifierService.delete(bankVerifierService.get(id));
		}
		j.setMsg("删除添加审核成功成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("verifierts:verifier:bankVerifier:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankVerifier bankVerifier, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "添加审核成功"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankVerifier> page = bankVerifierService.findPage(new Page<BankVerifier>(request, response, -1), bankVerifier);
    		new ExportExcel("添加审核成功", BankVerifier.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出添加审核成功记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("verifierts:verifier:bankVerifier:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankVerifier> list = ei.getDataList(BankVerifier.class);
			for (BankVerifier bankVerifier : list){
				try{
					bankVerifierService.save(bankVerifier);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条添加审核成功记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条添加审核成功记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入添加审核成功失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入添加审核成功数据模板
	 */
	@ResponseBody
	@RequiresPermissions("verifierts:verifier:bankVerifier:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "添加审核成功数据导入模板.xlsx";
    		List<BankVerifier> list = Lists.newArrayList(); 
    		new ExportExcel("添加审核成功数据", BankVerifier.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

    /**
	 * 查询所有栏目
	 * */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData1")
	public List<Map<String, Object>> treeData1(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
//		List<Menu> list = bankVerifierMapper.findLanMuAll();
        //获取1级栏目
        List<BankMenu> listfist = bankVerifierMapper.findBankMenuFistAll();
        //获取所有二级栏目
		List<BankMenu> list = bankVerifierMapper.findBankMenuAll();

        System.out.println(list.size());
		//在这里排数据  http://localhost:8080/a/sys/office/treeData?type=2
		/*{
    "parent": "#",
    "isParent": true,
    "name": "光大银行",
    "id": "6179da158f7f4502a0bd9197b18f7b30",
    "state": {
      "opened": true
    },
    "text": "光大银行",
    "type": "1"
  },*/

		/*{
    "parent": "6179da158f7f4502a0bd9197b18f7b30",
    "name": "支行",
    "id": "33c1e889ac274c1393adc08c1318f930",
    "text": "支行",
    "type": "2"
  }*/

		List<String> idlist=new ArrayList<>();
		//添加一级栏目
        for (int i = 0; i <listfist.size() ; i++) {
            Map<String, Object> mapfist = Maps.newHashMap();
            mapfist.put("id", listfist.get(i).getId());
            mapfist.put("isParent", true);
            mapfist.put("parent","#");
            Map<String, Object> map1 = Maps.newHashMap();
            map1.put("opened",true);
            mapfist.put("state",map1);
            mapfist.put("name", listfist.get(i).getPname());
            mapfist.put("text", listfist.get(i).getPname());
            mapfist.put("type", "1");
            mapList.add(mapfist);
            idlist.add(listfist.get(i).getId());
        }
        //添加二级栏目
        for (int i=0; i<list.size(); i++){
            BankMenu e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            //判断该id下是否有下级
            //添加三级栏目
            List<BankMenu> extAll = bankVerifierMapper.findBankMenuExtByIdAll(e.getId());
            //有数据
            if (extAll.size()!=0){
                Map<String, Object> mapfist = Maps.newHashMap();
                mapfist.put("id", e.getId());
                mapfist.put("isParent", true);
                mapfist.put("parent",e.getParentId());
                Map<String, Object> map1 = Maps.newHashMap();
                map1.put("opened",true);
                mapfist.put("state",map1);
                mapfist.put("name", e.getPname());
                mapfist.put("text", e.getPname());
                mapfist.put("type", "3");
                mapList.add(mapfist);
                for (int j = 0; j <extAll.size() ; j++) {
                    Map<String, Object> map3 = Maps.newHashMap();
                    map3.put("parent", extAll.get(j).getParentId());
                    map3.put("name",extAll.get(j).getPname());
                    map3.put("id", extAll.get(j).getId());
                    map3.put("text", extAll.get(j).getPname());
                    map3.put("type", 4);
                    mapList.add(map3);
                }
                //二级栏目下没有三级栏目时
            }else {
                for (int j = 0; j <idlist.size() ; j++) {
                    if (idlist.get(j).equals(e.getParentId())){
                        map.put("parent", e.getParentId());
                        map.put("name",e.getPname());
                        map.put("id", e.getId());
                        map.put("text", e.getPname());
                        map.put("type", 2);
                        mapList.add(map);
                    }
                }
            }
        }
		return mapList;
	}
     /***
      * 查询是否重复
      */
	@ResponseBody
	@RequestMapping(value = "checkPrograma")
	public Object checkPrograma(String name){
        BankVerifier byParmName = bankVerifierMapper.findByParmName(name);
        System.out.println(byParmName!=null?false:true);
        return byParmName!=null?false:true;
	}

	//添加
    @ResponseBody
    @RequestMapping(value = "addUsers")
    public void addUsers(String uid,String uname,String uloginName){
        System.out.println(uid+"==="+uname+"=="+uloginName);
        int i = bankVerifierMapper.addUsers(new BankUser(uid, uname, uloginName));
        System.out.println(i);
    }

    @ResponseBody
    @RequestMapping(value = "delUsers")
    public void delUsers(String id){
        int i = bankVerifierMapper.delUsers(id);
        System.out.println(i);
    }
    @ResponseBody
    @RequestMapping(value = "delUsersAll")
    public void delUsersAll(){
        int i = bankVerifierMapper.delUsersAll();
        System.out.println(i);
    }
    @ResponseBody
    @RequestMapping(value = "finBankUserAll")
    public Object finBankUserAll(){
        List<BankUser> list = bankVerifierMapper.finBankUserAll();
        System.out.println(list.size());
        return list;
    }


}