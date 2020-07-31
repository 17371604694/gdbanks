/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.programatcontent.programatcont.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.programatcontent.programatcont.entity.BankMnue;
import com.jeeplus.modules.programatcontent.programatcont.entity.BankOpenItem;
import com.jeeplus.modules.programatcontent.programatcont.entity.DistributeContent;
import com.jeeplus.modules.programatcontent.programatcont.entity.StatisticsDistribute;
import com.jeeplus.modules.programatcontent.programatcont.mapper.DistributeContentMapper;
import com.jeeplus.modules.programatcontent.programatcont.service.DistributeContentService;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.verifiertrue.verifiertrues.entity.BankVerifierTrue;
import com.jeeplus.modules.verifiertrue.verifiertrues.mapper.BankVerifierTrueMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 发稿内容Controller
 * @author chenl
 * @version 2019-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/programatcontent/programatcont/distributeContent")
public class DistributeContentController extends BaseController {

	@Autowired
	private DistributeContentService distributeContentService;
	@Autowired
	private DistributeContentMapper distributeContentMapper;
	@Autowired
	private BankVerifierTrueMapper bankVerifierTrueMapper;
	@Autowired
	private MenuMapper menuMapper;

	@ModelAttribute
	public DistributeContent get(@RequestParam(required=false) String id) {
		DistributeContent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = distributeContentService.get(id);
		}
		if (entity == null){
			entity = new DistributeContent();
		}
		return entity;
	}
	
	/**
	 * 栏目发稿列表页面
	 */
	@RequiresPermissions("programatcontent:programatcont:distributeContent:list")
	@RequestMapping(value = {"list"})
	public String list(DistributeContent distributeContent, Model model,String name,HttpServletRequest request) {
		//根据传入的name查询到栏目id
		if (!"all".equals(name)){
//			BankMnue byName = distributeContentMapper.findByName(name);
			//byName.getId();
			//改版:name既是id
			distributeContent.setProgramatIdName(name);
		}
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentList";
	}


	
		/**
	 * 栏目列表数据
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		//加入筛选条件
		String psnameId = request.getParameter("psnameId");
		if (!"all".equals(psnameId)){
			distributeContent.setProgramatId(psnameId);
		}
		//登录用户只能查看到自己的发稿
		String currentId = UserUtils.getUser().getId();//当前用户id
		if(!"1".equals(currentId)&&!"c6d9c07543f64a21b4454c23c8dedde2".equals(currentId)){//如果是admin或者admingd显示所有
			User u = new User();
			u.setId(currentId);
			distributeContent.setCreateBy(u);
			distributeContent.setLoginid(currentId);
		}else {
			distributeContent.setCreateBy(new User());
		}
		Page<DistributeContent> page = distributeContentService.findPage(new Page<DistributeContent>(request, response), distributeContent);

        return getBootstrapData(page);
	}


	/**
	 * 返回 副栏目可选显示设置
	 */
	@RequestMapping(value = {"listShow"})
	public String listShow(BankOpenItem bankOpenItem, Model model, String name, HttpServletRequest request) {

		model.addAttribute("bankOpenItem", bankOpenItem);
		return "modules/programatcontent/programatcont/bankOpenItemList";
	}
	/**
	 * 返回副栏目下拉选项
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:list")
	@RequestMapping(value = "dataOpenItem")
	public Map<String, Object> dataOpenItem(BankOpenItem bankOpenItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankOpenItem> page=new Page<>();
        int allOpenItemCount = distributeContentMapper.findAllOpenItemCount();
        page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
        page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
        bankOpenItem.setPageNo((page.getPageNo()-1)*page.getPageSize());
        bankOpenItem.setPageSize(page.getPageSize());
		page.setCount(allOpenItemCount);
		page.setList(distributeContentMapper.findOpenItemAll(bankOpenItem));
        return getBootstrapData(page);
	}

	/**
	 * 返回主栏目下拉选项
	 */
	@ResponseBody
	@RequestMapping(value = "selectPrograma")
	public Map<String, Object> selectPrograma(BankOpenItem bankOpenItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankOpenItem> page=new Page<>();
		int allOpenItemCount = distributeContentMapper.findAllOpenItemCount();
		page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
		page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		bankOpenItem.setPageNo((page.getPageNo()-1)*page.getPageSize());
		bankOpenItem.setPageSize(page.getPageSize());
		page.setCount(allOpenItemCount);
		page.setList(distributeContentMapper.findOpenItemAll(bankOpenItem));
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑 对应的发稿表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"programatcontent:programatcont:distributeContent:view","programatcontent:programatcont:distributeContent:add","programatcontent:programatcont:distributeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, DistributeContent distributeContent, Model model,HttpServletRequest request) {
		String psnameId=null;
		String name = UserUtils.getUser().getName();
		try{
			if("view".equals(mode)){
				Integer onclickNum  = distributeContent.getOnclickNum();
				if(onclickNum==null){
					onclickNum = 0;
					onclickNum++;
					distributeContent.setOnclickNum(onclickNum);
				}else {
					onclickNum++;
					distributeContent.setOnclickNum(onclickNum);
				}
				Integer result = distributeContentMapper.updateOnclickNum(distributeContent);
			}
			if("add".equals(mode)){
				distributeContent.setAuthor(name);
			}
			model.addAttribute("mode", mode);
			model.addAttribute("distributeContent", distributeContent);
			psnameId = request.getParameter("psnameId");
			BankMnue byId = distributeContentMapper.findById(psnameId);
			if(byId!=null){
				model.addAttribute("distributeContentName", byId.getName());
				model.addAttribute("distributeContentId", byId.getId());
			}

		}catch (Exception e){
			e.printStackTrace();
		}

		if(psnameId!=null && psnameId.equals("941b99f20a7b497ab3162ffc2178a72e")){//返回会议通知的表单
			return "modules/programatcontent/programatcont/distributeContentFormMeeting";
		}

		if (psnameId!=null && psnameId.equals("80e5812e3f6e4b6bbbb45fa82e81e954")){//返回图文新闻的表单
			return "modules/programatcontent/programatcont/distributeContentFormVideo";
		}
		return "modules/programatcontent/programatcont/distributeContentForm";
	}

	/**
	 * 待审核列表页面
	 */
	@RequestMapping(value = {"allList"})
	public String allList(DistributeContent distributeContent, Model model,String name,HttpServletRequest request) {
		//根据传入的name查询到栏目id
		if (!"all".equals(name)){
			BankMnue byName = distributeContentMapper.findByName(name);
			distributeContent.setProgramatIdName(byName.getId());
		}
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentListAll";
	}

	/**
	 * 待审核发稿数据
	 */
	@ResponseBody
	@RequestMapping(value = "allData")
	public Map<String, Object> allData(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		//只显示待审核
		distributeContent.setStateparentid(6);
		//登录用户只能查看到当前用户所属部门的发稿
		String currentId = UserUtils.getUser().getId();//当前用户id
		if(!"1".equals(currentId)&&!"c6d9c07543f64a21b4454c23c8dedde2".equals(currentId)){//如果是admin或者admingd显示所有
			User u = new User();
			u.setId(currentId);
			distributeContent.setCreateBy(u);
			distributeContent.setLoginid(currentId);
		}else {
			distributeContent.setCreateBy(new User());
		}
		Page<DistributeContent> page = distributeContentService.findallPage(new Page<DistributeContent>(request, response), distributeContent);
		return getBootstrapData(page);
	}

	/**
	 * 全部稿件列表页面
	 */
	@RequestMapping(value = {"allListData"})
	public String allListData(DistributeContent distributeContent, Model model,String name,HttpServletRequest request) {
		//根据传入的name查询到栏目id
		if (!"all".equals(name)){
			BankMnue byName = distributeContentMapper.findByName(name);
			distributeContent.setProgramatIdName(byName.getId());
		}
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentListAllData";
	}

	/**
	 *全部稿件数据
	 */
	@ResponseBody
	@RequestMapping(value = "allDatalist")
	public Map<String, Object> allDatalist(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(distributeContent.getProgramatId()!=null&&!"".equals(distributeContent.getProgramatId())){//收索按钮查看数据时
			String name  = distributeContent.getProgramatId();
			List<Menu> menu = menuMapper.getByName(name);
			if (menu!=null&&menu.size()!=0){
				if(menu.size()==1){//如果只查询到一个栏目
					distributeContent.setProgramatId(menu.get(0).getId());
				}else {//如果查询到多个栏目
					for(Menu menu1 :menu){
						if("10b93924e01f4ae48aba7949da22eaf1".equals(menu1.getParentId())){//父栏目是首页新闻的菜单
							distributeContent.setProgramatId(menu1.getId());
						}
					}
				}

			}
		}

		String currentId = UserUtils.getUser().getId();//当前用户id
		if(!"1".equals(currentId)&&!"c6d9c07543f64a21b4454c23c8dedde2".equals(currentId)){//如果是admin或者admingd显示所有
			User u = new User();
			u.setId(currentId);
			distributeContent.setCreateBy(u);
			distributeContent.setLoginid(currentId);
		}else {
			distributeContent.setCreateBy(new User());
		}

		Page<DistributeContent> page = distributeContentService.findPage(new Page<DistributeContent>(request, response), distributeContent);
		return getBootstrapData(page);
	}

	/**
	 * 已审核列表页面
	 */
	@RequestMapping(value = {"allListNoAudit"})
	public String allListNoAudit(DistributeContent distributeContent, Model model,String name,HttpServletRequest request) {
		//根据传入的name查询到栏目id
		if (!"all".equals(name)){
			BankMnue byName = distributeContentMapper.findByName(name);
			distributeContent.setProgramatIdName(byName.getId());
		}
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentListAllNoAudit";
	}

	/**
	 * 已审核发稿数据
	 */
	@ResponseBody
	@RequestMapping(value = "allDataNoAudit")
	public Map<String, Object> allDataNoAudit(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(distributeContent.getProgramatId()!=null&&!"".equals(distributeContent.getProgramatId())){//收索按钮查看数据时
			String name  = distributeContent.getProgramatId();
			List<Menu> menu = menuMapper.getByName(name);
			if (menu!=null&&menu.size()!=0){
				if(menu.size()==1){//如果只查询到一个栏目
					distributeContent.setProgramatId(menu.get(0).getId());
				}else {//如果查询到多个栏目
					for(Menu menu1 :menu){
						if("10b93924e01f4ae48aba7949da22eaf1".equals(menu1.getParentId())){//父栏目是首页新闻的菜单
							distributeContent.setProgramatId(menu1.getId());
						}
					}
				}

			}
		}
		//只已显示审核
		distributeContent.setStateparentid(6);//Stateparentid、Statepid  不为空的时候，会查询已经审核结束的稿件
		distributeContent.setStatepid(2);
		//登录用户只能查看到自己的发稿
		String currentId = UserUtils.getUser().getId();//当前用户id
		if(!"1".equals(currentId)&&!"c6d9c07543f64a21b4454c23c8dedde2".equals(currentId)){//如果是admin或者admingd显示所有
			User u = new User();
			u.setId(currentId);
			distributeContent.setCreateBy(u);
			distributeContent.setLoginid(currentId);
		}else {
			distributeContent.setCreateBy(new User());
		}
		Page<DistributeContent> page = distributeContentService.findPage(new Page<DistributeContent>(request, response), distributeContent,"noaudit");
		return getBootstrapData(page);
	}


	@RequiresPermissions(value={"programatcontent:programatcont:distributeContent:view","programatcontent:programatcont:distributeContent:add","programatcontent:programatcont:distributeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "formAll")
	public String formAll( DistributeContent distributeContent, Model model) {
		model.addAttribute("distributeContent", distributeContent);
		String psnameId = distributeContent.getProgramatId();
		BankMnue byId = distributeContentMapper.findById(psnameId);
		if(byId!=null){
			model.addAttribute("distributeContentName", byId.getName());
			model.addAttribute("distributeContentId", byId.getId());
		}

		if(psnameId!=null && psnameId.equals("941b99f20a7b497ab3162ffc2178a72e")){//返回会议通知的表单
			return "modules/programatcontent/programatcont/distributeContentFormMeeting";
		}

		if (psnameId!=null && psnameId.equals("80e5812e3f6e4b6bbbb45fa82e81e954")){
			return "modules/programatcontent/programatcont/distributeContentFormVideoEdit";
		}
		return "modules/programatcontent/programatcont/distributeContentFormAll";
	}

	/**
	 * 保存发稿
	 */
	@ResponseBody
	@RequiresPermissions(value={"programatcontent:programatcont:distributeContent:add","programatcontent:programatcont:distributeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(DistributeContent distributeContent, Model model) throws Exception{
		DistributeContent oldD = distributeContent;
		String pId = distributeContent.getProgramatId();
		if(pId.contains(",")){
			String savePid   = pId.substring(0,pId.lastIndexOf(","));
			distributeContent.setProgramatId(savePid);
		}
		String id = UUID.randomUUID().toString();
		AjaxJson j = new AjaxJson();
		try{
			/**
			 * 后台hibernate-validation插件校验
			 */
			String errMsg = beanValidator(distributeContent);
			if (StringUtils.isNotBlank(errMsg)){
				j.setSuccess(false);
				j.setMsg(errMsg);
				return j;
			}
			//流程如下:
			//1,单位审核人审核(必审核)
			//2,栏目审核人审核(可有可无)
			//3.副栏目审核人审核(可以可无可多个)
            if (UserUtils.getUser().getLoginName().equals("admingd")||UserUtils.getUser().getLoginName().equals("admin")){//超级管理员不需要审核---直接通过 待 admingd
				distributeContent.setStatepid(2); //状态 默认1,等待审核,2,审核通过,3不通过
				distributeContent.setStateparentid(6); //每执行通过一步加1
				//新增或编辑表单保存
//				distributeContent.setOfficeName(distributeContentMapper.findOfficeId(distributeContent.getAuthor()));
//				distributeContent.setOfficeId(distributeContentMapper.findOffice(distributeContent.getAuthor()));
				distributeContent.setOfficeName(UserUtils.getUser().getOffice().getName());
				distributeContent.setOfficeId(UserUtils.getUser().getOffice().getId());
				distributeContent.setOnclickNum(0);
				if(oldD.getId()==null||"".equals(oldD.getId())){//为空就是新纪录
					distributeContent.setId(id);//设置主键
					distributeContentService.saveDistributeContent(distributeContent);//保存发稿（需要提前设置主键）
					distributeContentService.saveContent(distributeContent);//保存正文
				}else {//更新
					distributeContentService.updataDistributeContent(distributeContent);//updata
					distributeContentService.updataContent(distributeContent);//updata
				}
				j.setSuccess(true);
				j.setMsg("保存发稿成功");
				return j;
			}
			//判断该栏目是否有审核人
			BankVerifierTrue byProgramatID = distributeContentMapper.findByProgramatID(distributeContent.getProgramatId()); //得到栏目审核人id
			boolean lanmu1  = false;
			if(byProgramatID!=null){
				lanmu1=(null != byProgramatID.getVerifyPerson() && !"".equals(byProgramatID.getVerifyPerson())); //是否有审核人
			}else {
				lanmu1 = false;
			}

			boolean lanmu2=(null!=distributeContent.getProgramatParentid() && !distributeContent.getProgramatParentid().equals("")); //副栏目是否有值,如果有根据值查询是否设置审核人
			if (lanmu1==false && lanmu2==false){
				distributeContent.setStepSum(1); //总共需要的步数 1步部门审核人
			}
			if (lanmu1==false && lanmu2==true){
                //设置副栏目审核人
				String[] split = distributeContent.getProgramatParentid().split(",");
				if (split.length<=0){
					distributeContent.setStepSum(1); //副栏目没有设置
				}else {
					List<String> persont = new ArrayList<>();
					for (int i = 0; i <split.length ; i++) {
						BankVerifierTrue byProgramatID2 = distributeContentMapper.findByProgramatID(split[i]); //得到副栏目审核人id
						if (null != byProgramatID2.getVerifyPerson() && !"".equals(byProgramatID2.getVerifyPerson())){
							persont.add(byProgramatID2.getVerifyPerson());
						}
					}
					if (persont.size()<=0){  //选择了副栏目,但对应的副栏目没有设置审核人
						distributeContent.setStepSum(1);
					}else{
						distributeContent.setStepSum(2);//选择了副栏目,对应的副栏目有设置审核人,但是栏目审核人没有设置审核人
					}
					distributeContent.setRemarks(StringUtils.join(persont,","));
				}
			}
			if (lanmu1==true && lanmu2==false){
				distributeContent.setStepSum(2);
				//设置栏目审核人
				distributeContent.setReservec(byProgramatID.getVerifyPerson());
			}
			if (lanmu1==true && lanmu2==true){
				//设置栏目审核人
				distributeContent.setReservec(byProgramatID.getVerifyPerson());
				//设置副栏目审核人
				String[] split = distributeContent.getProgramatParentid().split(",");
				if (split.length<=0){
					distributeContent.setStepSum(2); //副没有设置审核人
				}else {
					List<String> persont = new ArrayList<>();
					for (int i = 0; i <split.length ; i++) {
						BankVerifierTrue byProgramatID2 = distributeContentMapper.findByProgramatID(split[i]); //得到副栏目审核人id
				        if (null != byProgramatID2.getVerifyPerson() && !"".equals(byProgramatID2.getVerifyPerson())){
							persont.add(byProgramatID2.getVerifyPerson());
						}
					}
					if (persont.size()<=0){  //选择了副栏目,但对应的副栏目没有设置审核人
						distributeContent.setStepSum(2);
					}else{
						distributeContent.setStepSum(3);
					}
					distributeContent.setRemarks(StringUtils.join(persont,","));
				}
			}

			//新增或编辑表单保存
			//设置部门名称，id
//			distributeContent.setOfficeName(distributeContentMapper.findOfficeId(distributeContent.getAuthor()));
//			distributeContent.setOfficeId(distributeContentMapper.findOffice(distributeContent.getAuthor()));
			distributeContent.setOfficeName(UserUtils.getUser().getOffice().getName());
			distributeContent.setOfficeId(UserUtils.getUser().getOffice().getId());
			distributeContent.setOnclickNum(0);
			if(distributeContent.getId()!=null&&!"".equals(distributeContent.getId())){//如果是修改
				if(distributeContent.getStateparentid()>distributeContent.getStepSum()){//如果当前步奏，大于修改之后总步骤
					distributeContent.setStateparentid(6);//则发稿通过
				}else if(distributeContent.getStateparentid()==distributeContent.getStepSum()){
					//修改之后总步骤与当前步骤相等，且当前步骤为第二步
					if(distributeContent.getStateparentid()==2&&distributeContent.getRemarks()!=null){
						//如果修改后副栏目审核人和修改前副栏目审核人有相同的人，则继续审核
						String [] stringoldD  = oldD.getRemarks().split(",");
						String [] stringnewD  = distributeContent.getRemarks().split(",");
						boolean fal = false;
						for (String s :stringoldD){
							for(String ss :stringnewD){
								if(ss.equals(s)){
									fal = true;
								}
							}
						}
						if(fal){//有相同
							//不用修改当前步骤数也不用修改remarks
						}else {
							distributeContent.setStateparentid(6);//发稿通过
						}
					}

					//修改之后总步骤与当前步骤相等，且总步骤为3步
					if(distributeContent.getStateparentid()==3){
						//不用修改当前步骤数也不用修改remarks
					}
				}
			}else {//新纪录
				distributeContent.setStatepid(1); //状态 默认1,等待审核,2,审核通过,3不通过
				distributeContent.setStateparentid(1); //当前步奏6为结束
			}
			if(oldD.getId()==null||"".equals(oldD.getId())){//为空就是新纪录
				distributeContent.setId(id);//设置主键
				distributeContentService.saveDistributeContent(distributeContent);//保存发稿（需要提前设置主键）
				distributeContentService.saveContent(distributeContent);//保存正文
			}else {//更新
				distributeContentService.updataDistributeContent(distributeContent);//updata
				distributeContentService.updataContent(distributeContent);//updata
			}
			j.setSuccess(true);
			j.setMsg("保存发稿成功");
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("保存发稿失败，请联系管理员！");
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 统计发稿数列表页面
	 * params:
	 *
	 */
	@RequiresPermissions(value={"programatcontent:programatcont:distributeContent:list"},logical=Logical.OR)
	@RequestMapping(value = "statistical")
	public String statisticalDistribute( DistributeContent distributeContent, Model model) {
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/statisticaldistribute/statisticalDistributeList";
	}

	/**
	 * 返回发稿统计列表数据
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:list")
	@RequestMapping(value = "statisticalDistributeData")
	public Map<String, Object> statisticalDistributeData(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DistributeContent> page = null;
		try {
			page = distributeContentService.findStatisticalPage(new Page<DistributeContent>(request, response), distributeContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getBootstrapData(page);
	}

	/**
	 * 批量删除发稿
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			distributeContentService.delete(distributeContentService.get(id));//删除发稿记录
			distributeContentService.deleteContentText(id);//删除发稿正文
			distributeContentMapper.deleteBankdVer(id);
		}
		j.setMsg("删除发稿成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发稿"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DistributeContent> page = distributeContentService.findPage(new Page<DistributeContent>(request, response, -1), distributeContent);
    		new ExportExcel("发稿", DistributeContent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出发稿记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DistributeContent> list = ei.getDataList(DistributeContent.class);
			for (DistributeContent distributeContent : list){
				try{
					distributeContentService.save(distributeContent);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，发稿 "+failureNum+" 条操作成功记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条操作成功记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入发稿失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入操作成功数据模板
	 */
	@ResponseBody
	@RequiresPermissions("programatcontent:programatcont:distributeContent:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "发稿数据导入模板.xlsx";
    		List<DistributeContent> list = Lists.newArrayList(); 
    		new ExportExcel("发稿数据", DistributeContent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


    /**
	 * 首页新闻下的所有栏目数据
	 * */
	@ResponseBody
	@RequestMapping(value = "treeData1")
	public List<Map<String, Object>> treeData1(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BankMnue> list=distributeContentMapper.findBMAll();
		for (int i = 0; i <list.size() ; i++) {
			BankMnue e=list.get(i);
            Map<String,Object> map= Maps.newHashMap();
			map.put("name",e.getName());
			map.put("id", e.getId());
			map.put("text", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 首页新闻下的所有栏目数据 ---该方法用户同步栏目
	 * */
	@ResponseBody
	@RequestMapping(value = "treeDataSiznes")
	public AjaxJson treeDataSiznes(HttpServletResponse response) {
        AjaxJson jy = new AjaxJson();
		List<BankMnue> list=distributeContentMapper.findBMAll();
		BankOpenItem bankOpenItem=new BankOpenItem();
		bankOpenItem.setPageSize(999999999);
		bankOpenItem.setPageNo(0);
        List<BankOpenItem> openItemAll = distributeContentMapper.findOpenItemAll(bankOpenItem);
        List<BankOpenItem> blist=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            boolean fig=true;
            for (int j = 0; j <openItemAll.size() ; j++) {
                if(list.get(i).getId().equals(openItemAll.get(j).getColumnid())){
                    openItemAll.get(j).setName(list.get(i).getName());
					openItemAll.get(j).setSort(list.get(i).getSort());
                    blist.add(openItemAll.get(j));
                    fig=true;
                    break;
                }else {
                    fig=false;

                }
            }
            if (!fig){
                BankOpenItem bb=new BankOpenItem();
                bb.setId(IdGen.uuid());
                bb.setColumnid(list.get(i).getId());
                bb.setName(list.get(i).getName());
                bb.setType(1);
                bb.setSort(list.get(i).getSort());
                blist.add(bb);
            }

		}
        int i = distributeContentMapper.delBaknOpenItem();
        if(i>0){
            for (int j = 0; j <blist.size() ; j++) {
                distributeContentMapper.addBankOpenItem(blist.get(j));
            }
        }
        jy.setMsg("同步完成");
        jy.setSuccess(true);
        return jy;
	}
    /**
     * 首页新闻下的所有栏目数据 ---该方法用户同步栏目
     * */
    @ResponseBody
    @RequestMapping(value = "openitemup")
    public Object openitemup(BankOpenItem bankOpenItem,HttpServletResponse response) {

        int i = distributeContentMapper.updateBankOpenItem(bankOpenItem);
        return  i;


    }



	/**
	 * 树结构显示所有部门负责人信息
	 * */
	@ResponseBody
	@RequestMapping(value = "treeData2")
	public List<Map<String, Object>> treeData2(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BankMnue> list=distributeContentMapper.findDepartmentReviewerAll();
		for (int i = 0; i <list.size() ; i++) {
			if(UserUtils.getUser().getOffice().getId().equalsIgnoreCase(list.get(i).getOfficeId())){
				BankMnue e=list.get(i);
				Map<String,Object> map= Maps.newHashMap();
				map.put("name",e.getName());
				map.put("id", e.getId());
				map.put("text", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}




	/***
     *点击通过审核 或不通过
     * @param ids

     * @return
     */
    @ResponseBody
    @RequestMapping(value = "passgo")
    public Object passgo(String ids,Integer starte){
        String idArray[] =ids.split(",");
        boolean fig=true;
        for(String id : idArray){
			DistributeContent distributeContent = distributeContentService.get(id);
			int tsp=(distributeContent.getStateparentid());//当前审核环节
			if(distributeContent.getStepSum()==2){//如果审核步奏只有2步，需要判断第二步是栏目审核审核还是副栏目审核人审核
				BankVerifierTrue byProgramatID = distributeContentMapper.findByProgramatID(distributeContent.getProgramatId()); //得到栏目审核人id
				boolean lanmu1=(null != byProgramatID.getVerifyPerson() && !"".equals(byProgramatID.getVerifyPerson())); //是否有审核人
				if(lanmu1){//第二步为栏目审核人
					tsp++;
				}else {//第二步为副栏目审核人
					if(distributeContent.getStateparentid()==1){//当前为第1步
						tsp++;
					}else {//当前为第2步
						tsp = 2;//将当前步奏固定在第2步
						//所有的副栏目审核通过，才通过，通过一个删除一个
						String[] split = distributeContent.getRemarks().split(",");
						if (split.length>1){  //副栏目审核人二个以上时
							String sps="";
							User user = UserUtils.getUser();
							if(user.getId().equals("1")||user.getId().equals("")){//超级管理员
								sps = "";
							}else {
								for (int i = 0; i <split.length ; i++) {
									if (!user.getId().equals(split[i])){
										sps=sps+""+split[i]+",";
									}
								}
							}
							if(sps.length()<0||sps.equals("")||sps.equals(",")){//若全部删除了
								distributeContentMapper.updateDistributeContentState(id,2,6);
								return fig;
							}else {
								//对于副栏目多个审核人,每次通过一个从remarks里删除一个
								int i = distributeContentMapper.updateRemarksById(id, sps);
//								distributeContentMapper.updateDistributeContentState(id,2,tsp);
							}
						}else {//所有副栏目完毕之后
							tsp++;
						}
					}
				}
			}else if(distributeContent.getStepSum()==3){//审核步骤有3步时
				if(distributeContent.getStateparentid()==2){//若当前步骤为第二步
					tsp++;
				}else if(distributeContent.getStateparentid()==1){//若当前步骤为第1步
					tsp++;
				}else {//若当前步骤为第3步
					tsp = 3;//将当前步奏固定在第2步
					//所有的副栏目审核通过，才通过，通过一个删除一个
					String[] split = distributeContent.getRemarks().split(",");
					if (split.length>1){  //副栏目审核人二个以上时
						String sps="";
						User user = UserUtils.getUser();
						if(user.getId().equals("1")||user.getId().equals("")){//超级管理员
							sps = "";
						}else {
							for (int i = 0; i <split.length ; i++) {
								if (!user.getId().equals(split[i])){
									sps=sps+""+split[i]+",";
								}
							}
						}
						if(sps.length()<0||sps.equals("")||sps.equals(",")){//若全部删除了
							int i = distributeContentMapper.updateRemarksById(id, sps);
							distributeContentMapper.updateDistributeContentState(id,2,6);
							return fig;
						}else {
							//对于副栏目多个审核人,每次通过一个从remarks里删除一个
							int i = distributeContentMapper.updateRemarksById(id, sps);
//							distributeContentMapper.updateDistributeContentState(id,2,tsp);
						}
					}else {//所有副栏目完毕之后
						tsp++;
					}
				}
			}else {
				tsp++;
			}
			//审核通过
			if(starte==2){
				if (tsp>(distributeContent.getStepSum())){//如果当前环节步奏大于总共步骤
					distributeContentMapper.updateDistributeContentState(id,2,6); //环节已完成
				}else{
					distributeContentMapper.updateDistributeContentState(id,2,tsp);//环节未结束
				}
			}else if(starte==3){//审核不通过（直接更改状态为不通过，流程结束）
			  distributeContentMapper.updateDistributeContentState(id,3,6);
			}
        }
	    return fig;
    }

	/**
	 * 管理员稿件置顶管理列表页面
	 */
	@RequiresPermissions("programatcontent:programatcont:distributeContent:list")
	@RequestMapping(value = {"listtrue"})
	public String listtrue(DistributeContent distributeContent, Model model,String name,HttpServletRequest request) {
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentListtrue";
	}

	/**
	 * 返回置顶管理列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "listtrueData")
	public Map<String, Object> listtrueData(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DistributeContent> page = null;
		List list = new ArrayList<>();
		String loginId = UserUtils.getUser().getId();

		try {
			if(!"1".equalsIgnoreCase(loginId)&&!"c6d9c07543f64a21b4454c23c8dedde2".equalsIgnoreCase(loginId)){//不是admin和admingd 设置过滤条件
				List<BankVerifierTrue> bank = bankVerifierTrueMapper.findBankTrueByVerifyPerson(loginId);//当前登录人有哪些审核栏目
				for(int i =0 ;i<bank.size();i++){
					list.add("'"+bank.get(i).getColumnId()+"'");
				}
				if(list.size()>0){
					distributeContent.setProgramatIdList(list);
				}else {
					list.add("'null'");
					distributeContent.setProgramatIdList(list);
				}
			}
			//只显示审核完成的
			distributeContent.setStatepid(2);
			distributeContent.setStateparentid(6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		page = distributeContentService.findPage(new Page<DistributeContent>(request, response), distributeContent);
		return getBootstrapData(page);
	}

	/**
	 * 返回，稿件置顶列表的查看页面
	 * params:
	 *
	 */
	@RequiresPermissions(value={"programatcontent:programatcont:distributeContent:view","programatcontent:programatcont:distributeContent:add","programatcontent:programatcont:distributeContent:edit"},logical=Logical.OR)
	@RequestMapping(value = "listtrueDataform")
	public String listtrueDataform( DistributeContent distributeContent, Model model) {
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/distributeContentFormAll";
	}

	/*置顶页面*/
	@RequestMapping(value = "admintopform")
	public String admintopform(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("distributeContent", distributeContent);
		return "modules/programatcontent/programatcont/topForm";
	}

	/*置顶*/
	@ResponseBody
	@RequestMapping(value = "admintop")
	public AjaxJson admintop(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try{
//			distributeContent.setTagTitle("0");//更改状态为是
			distributeContentService.admintop(distributeContent);
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("置顶失败，请稍后重试！");
		}
		return ajaxJson;
	}

	/*重新发起*/
	@ResponseBody
	@RequestMapping(value = "again")
	public AjaxJson again(DistributeContent distributeContent, HttpServletRequest request, HttpServletResponse response, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		try{
			distributeContentService.again(distributeContent);
		}catch (Exception e){
			e.printStackTrace();
			ajaxJson.setSuccess(false);
			ajaxJson.setMsg("重新发起失败，请稍后重试！");
		}
		return ajaxJson;
	}

}