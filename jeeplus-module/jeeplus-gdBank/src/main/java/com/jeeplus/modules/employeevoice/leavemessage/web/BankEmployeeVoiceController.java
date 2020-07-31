/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.employeevoice.leavemessage.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.Role;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.RoleMapper;
import com.jeeplus.modules.sys.utils.UserUtils;
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
import com.jeeplus.modules.employeevoice.leavemessage.entity.BankEmployeeVoice;
import com.jeeplus.modules.employeevoice.leavemessage.service.BankEmployeeVoiceService;

/**
 * 员工心声Controller
 * @author cheny
 * @version 2019-12-18
 */
@Controller
@RequestMapping(value = "${adminPath}/employeevoice/leavemessage/bankEmployeeVoice")
public class BankEmployeeVoiceController extends BaseController {

	@Autowired
	private BankEmployeeVoiceService bankEmployeeVoiceService;
	@Autowired
	private RoleMapper roleMapper;
	
	@ModelAttribute
	public BankEmployeeVoice get(@RequestParam(required=false) String id) {
		BankEmployeeVoice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankEmployeeVoiceService.get(id);
		}
		if (entity == null){
			entity = new BankEmployeeVoice();
		}
		return entity;
	}
	
	/**
	 * 员工心声列表页面
	 */
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceList";
	}

	/**
	 * 员工心声回复列表页面
	 */
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:list")
	@RequestMapping(value = {"replylist"})
	public String replylist(BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceReplyList";
	}
	
		/**
	 * 员工心声列表数据
	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankEmployeeVoice bankEmployeeVoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		String id = UserUtils.getUser().getId();
		bankEmployeeVoice.setLeaveMessageUserId(id);
		Page<BankEmployeeVoice> page = bankEmployeeVoiceService.findPage(new Page<BankEmployeeVoice>(request, response), bankEmployeeVoice);
		return getBootstrapData(page);
	}

	/**
	 * 回复列表数据
	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:list")
	@RequestMapping(value = "replydata")
	public Map<String, Object> replydata(BankEmployeeVoice bankEmployeeVoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		String uid = UserUtils.getUser().getId();
		if(!"1".equals(uid)){
			String oid = UserUtils.getUser().getOffice().getId();
			bankEmployeeVoice.setReplyDept(oid);
		}
		Page<BankEmployeeVoice> page = bankEmployeeVoiceService.findPage(new Page<BankEmployeeVoice>(request, response), bankEmployeeVoice);
		return getBootstrapData(page);
	}

	/**
	 * 新建员工心声表单页面
	 * params:
	 *
	 */
	@RequiresPermissions(value={"employeevoice:leavemessage:bankEmployeeVoice:add"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceForm";
	}

	/**
	 * 查看员工心声详情页面
	 * @param mode
	 * @param bankEmployeeVoice
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value={"employeevoice:leavemessage:bankEmployeeVoice:view"},logical=Logical.OR)
	@RequestMapping(value = "form2/{mode}")
	public String form2(@PathVariable String mode, BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceViewForm";
	}

	/**
	 * 查询所有回复，根据id
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getReplyData")
	public List<BankEmployeeVoice> getReplyData(String id) {
		BankEmployeeVoice bankEmployeeVoice = bankEmployeeVoiceService.get(id);
		List<BankEmployeeVoice> list = bankEmployeeVoiceService.getReply(bankEmployeeVoice);
		return list;
	}

	//回复页面
	@RequestMapping(value = "form1/{mode}")
	public String form1(@PathVariable String mode, BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceReplyForm";
	}

	/*员工心声首页发表页面*/
	@RequestMapping(value = "formweb/{mode}")
	public String formweb(@PathVariable String mode, BankEmployeeVoice bankEmployeeVoice, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankEmployeeVoice", bankEmployeeVoice);
		return "modules/employeevoice/leavemessage/bankEmployeeVoiceWebForm";
	}


	/**
	 * 保存员工心声
	 */
	@ResponseBody
	@RequiresPermissions(value={"employeevoice:leavemessage:bankEmployeeVoice:add","employeevoice:leavemessage:bankEmployeeVoice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankEmployeeVoice bankEmployeeVoice, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		//新增或编辑表单保存
		try{
			if(bankEmployeeVoice.getId()==null||bankEmployeeVoice.getId().equalsIgnoreCase("")){
				bankEmployeeVoice.setIsReply("否");
				bankEmployeeVoiceService.save(bankEmployeeVoice);//保存
				j.setSuccess(true);
				j.setMsg("留言成功");
			}else {
				BankEmployeeVoice bankEmployeeVoice1 = bankEmployeeVoiceService.get(bankEmployeeVoice.getId());
				List<BankEmployeeVoice> list = bankEmployeeVoiceService.getReplyList(bankEmployeeVoice1);
				if(list.size()>0){
					bankEmployeeVoice.setId(list.get(0).getId());
					bankEmployeeVoiceService.updataReply(bankEmployeeVoice);//更新回复

				}else {
					bankEmployeeVoiceService.saveReply(bankEmployeeVoice);//保存回复
				}

				bankEmployeeVoice.setIsReply("是");
				bankEmployeeVoiceService.updateisReply(bankEmployeeVoice);
				j.setSuccess(true);
				j.setMsg("回复成功！");
			}
		}catch (Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("操作失败！请稍后重试");
		}
		return j;
	}


	/**
	 * 批量删除员工心声
	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		try{
			for(String id : idArray){
				bankEmployeeVoiceService.delete(bankEmployeeVoiceService.get(id));
			}
			j.setSuccess(true);
			j.setMsg("删除员工心声成功");
		}catch (Exception e){
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除员工心声失败！请稍后重试！");
		}
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankEmployeeVoice bankEmployeeVoice, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工心声"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankEmployeeVoice> page = bankEmployeeVoiceService.findPage(new Page<BankEmployeeVoice>(request, response, -1), bankEmployeeVoice);
    		new ExportExcel("员工心声", BankEmployeeVoice.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工心声记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankEmployeeVoice> list = ei.getDataList(BankEmployeeVoice.class);
			for (BankEmployeeVoice bankEmployeeVoice : list){
				try{
					bankEmployeeVoiceService.save(bankEmployeeVoice);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工心声记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工心声记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工心声失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工心声数据模板
	 */
	@ResponseBody
	@RequiresPermissions("employeevoice:leavemessage:bankEmployeeVoice:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工心声数据导入模板.xlsx";
    		List<BankEmployeeVoice> list = Lists.newArrayList(); 
    		new ExportExcel("员工心声数据", BankEmployeeVoice.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}