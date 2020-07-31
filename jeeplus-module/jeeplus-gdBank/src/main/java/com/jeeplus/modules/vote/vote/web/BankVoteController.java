/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.vote.vote.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.vote.vote.entity.BankVote;
import com.jeeplus.modules.vote.vote.service.BankVoteService;

/**
 * 投票Controller
 * @author cheny
 * @version 2020-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/vote/vote/bankVote")
public class BankVoteController extends BaseController {

	@Autowired
	private BankVoteService bankVoteService;
	
	@ModelAttribute
	public BankVote get(@RequestParam(required=false) String id) {
		BankVote entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankVoteService.get(id);
		}
		if (entity == null){
			entity = new BankVote();
		}
		return entity;
	}
	
	/**
	 * 投票主题列表页面
	 */
	@RequiresPermissions("vote:vote:bankVote:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankVote bankVote, Model model) {
		model.addAttribute("bankVote", bankVote);
		return "modules/vote/vote/bankVoteList";
	}
	
		/**
	 * 投票主题列表数据
	 */
	@ResponseBody
	@RequiresPermissions("vote:vote:bankVote:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankVote bankVote, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankVote> page = bankVoteService.findPage(new Page<BankVote>(request, response), bankVote); 
		return getBootstrapData(page);
	}

	/**
	 * 增加，编辑投票主题表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"vote:vote:bankVote:view","vote:vote:bankVote:add","vote:vote:bankVote:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankVote bankVote, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankVote", bankVote);
		return "modules/vote/vote/bankVoteForm";
	}



	/**
	 * 保存投票主题
	 */
	@ResponseBody
	@RequiresPermissions(value={"vote:vote:bankVote:add","vote:vote:bankVote:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankVote bankVote, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankVote);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankVoteService.save(bankVote);//保存
		j.setSuccess(true);
		j.setMsg("保存投票成功");
		return j;
	}

	
	/**
	 * 批量删除投票主题
	 */
	@ResponseBody
	@RequiresPermissions("vote:vote:bankVote:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankVoteService.delete(bankVoteService.get(id));
		}
		j.setMsg("删除投票成功");
		return j;
	}
	
//	/**
//	 * 导出excel文件
//	 */
//	@ResponseBody
//	@RequiresPermissions("vote:vote:bankVote:export")
//    @RequestMapping(value = "export")
//    public AjaxJson exportFile(BankVote bankVote, HttpServletRequest request, HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		try {
//            String fileName = "投票"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//            Page<BankVote> page = bankVoteService.findPage(new Page<BankVote>(request, response, -1), bankVote);
//    		new ExportExcel("投票", BankVote.class).setDataList(page.getList()).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg("导出投票记录失败！失败信息："+e.getMessage());
//		}
//			return j;
//    }
//
//	/**
//	 * 导入Excel数据
//
//	 */
//	@ResponseBody
//	@RequiresPermissions("vote:vote:bankVote:import")
//    @RequestMapping(value = "import")
//   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
//		AjaxJson j = new AjaxJson();
//		try {
//			int successNum = 0;
//			int failureNum = 0;
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<BankVote> list = ei.getDataList(BankVote.class);
//			for (BankVote bankVote : list){
//				try{
//					bankVoteService.save(bankVote);
//					successNum++;
//				}catch(ConstraintViolationException ex){
//					failureNum++;
//				}catch (Exception ex) {
//					failureNum++;
//				}
//			}
//			if (failureNum>0){
//				failureMsg.insert(0, "，失败 "+failureNum+" 条投票记录。");
//			}
//			j.setMsg( "已成功导入 "+successNum+" 条投票记录"+failureMsg);
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg("导入投票失败！失败信息："+e.getMessage());
//		}
//		return j;
//    }
//
//	/**
//	 * 下载导入投票数据模板
//	 */
//	@ResponseBody
//	@RequiresPermissions("vote:vote:bankVote:import")
//    @RequestMapping(value = "import/template")
//     public AjaxJson importFileTemplate(HttpServletResponse response) {
//		AjaxJson j = new AjaxJson();
//		try {
//            String fileName = "投票数据导入模板.xlsx";
//    		List<BankVote> list = Lists.newArrayList();
//    		new ExportExcel("投票数据", BankVote.class, 1).setDataList(list).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			j.setSuccess(false);
//			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
//		}
//		return j;
//    }

	/**
	 * 投票选项列表页面
	 * params:
	 */
	@RequiresPermissions(value={"vote:vote:bankVote:view"},logical=Logical.OR)
	@RequestMapping(value = "bankSelectList")
	public String bankSelectList( BankVote bankVote, Model model) {
		/*传入主题ID*/
		model.addAttribute("bankVote", bankVote);
		return "modules/vote/vote/bankSelectList";
	}

	/**
	 * 添加投票选项页面
	 * params:
	 */
	@RequiresPermissions(value={"vote:vote:bankVote:view"},logical=Logical.OR)
	@RequestMapping(value = "voteselect")
	public String viewlist( BankVote bankVote, Model model) {
		model.addAttribute("bankVote", bankVote);
		return "modules/vote/vote/voteselect";
	}

	/**
	 * 编辑投票选项页面
	 * params:
	 */
	@RequiresPermissions(value={"vote:vote:bankVote:view"},logical=Logical.OR)
	@RequestMapping(value = "editlist")
	public String editlist( BankVote bankVote, Model model) {
		BankVote bankVote1  =  bankVoteService.getSelectInfo(bankVote.getId());
		model.addAttribute("bankVote", bankVote1);
		return "modules/vote/vote/voteselectedit";
	}

	/**
	 * 投票主题列表数据
	 */
	@ResponseBody
	@RequiresPermissions("vote:vote:bankVote:list")
	@RequestMapping(value = "bankSelectListData")
	public Map<String, Object> bankSelectListData(BankVote bankVote, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankVote> page = bankVoteService.findPage1(new Page<BankVote>(request, response), bankVote);
		return getBootstrapData(page);
	}

	/**
	 * 保存投票选项
	 */
	@ResponseBody
//	@RequiresPermissions(value={"vote:vote:bankVote:add","vote:vote:bankVote:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveselect")
	public AjaxJson saveselect(BankVote bankVote, Model model) throws Exception{
		AjaxJson j = new AjaxJson();

		try{

			if(bankVote.getSelectId()==null||bankVote.getSelectId().length()<=0){
				//新增选项
				bankVote.setVoteNum(0);
				bankVoteService.saveselect(bankVote);//保存
				j.setSuccess(true);
				j.setMsg("保存选项成功");
			}else {//更新
				bankVoteService.updataselect(bankVote);//更新
			}

		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("文件名太长!");
			e.printStackTrace();
		}

		return j;
	}

	/**
	 * 批量删除选项
	 */
	@ResponseBody
	@RequiresPermissions("vote:vote:bankVote:del")
	@RequestMapping(value = "deleteselect")
	public AjaxJson deleteselect(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankVoteService.deleteselect(id);
		}
		j.setMsg("删除选项成功");
		return j;
	}

	/**
	 * 投票详情页面
	 * params:
	 */
	@RequiresPermissions(value={"vote:vote:bankVote:view"},logical=Logical.OR)
	@RequestMapping(value = "voteinfo")
	public String voteinfo( BankVote bankVote, Model model) {
		model.addAttribute("bankVote", bankVote);
		return "modules/vote/vote/voteinfo";
	}

	/**
	 * 投票详情页面数据
	 */
	@ResponseBody
	@RequiresPermissions(value={"vote:vote:bankVote:view"},logical=Logical.OR)
	@RequestMapping(value = "voteinfodata")
	public AjaxJson voteinfodata(BankVote bankVote, Model model) {
		AjaxJson j = new AjaxJson();
		try {
			bankVoteService.voteinfodata(bankVote);//查询主题的投票详情
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}


	/**
	 * 查询是否已经投票结束
	 */
	@ResponseBody
//	@RequiresPermissions(value={"vote:vote:bankVote:add","vote:vote:bankVote:edit"},logical=Logical.OR)
	@RequestMapping(value = "isvote")
	public AjaxJson isvote(BankVote bankVote, Model model) {

		AjaxJson j = new AjaxJson();;
		try {
//			bankVoteService.vote(bankVote);//保存
			j.setSuccess(true);
			j.setMsg("保存投票成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

}