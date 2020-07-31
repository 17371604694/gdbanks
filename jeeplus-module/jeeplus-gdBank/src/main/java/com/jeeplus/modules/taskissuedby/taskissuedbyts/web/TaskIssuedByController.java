/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskissuedby.taskissuedbyts.web;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.sys.mapper.UserMapper;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.mapper.TaskIssuedByMapper;
import org.apache.http.entity.ContentType;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.entity.TaskIssuedBy;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.service.TaskIssuedByService;

/**
 * 任务下发Controller
 * @author chenl
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "${adminPath}/taskissuedby/taskissuedbyts/taskIssuedBy")
public class TaskIssuedByController extends BaseController {

	@Autowired
	private TaskIssuedByService taskIssuedByService;

	@Autowired
	private TaskIssuedByMapper tm;

	@Autowired
	private UserMapper userMapper;

	@ModelAttribute
	public TaskIssuedBy get(@RequestParam(required=false) String id) {
		TaskIssuedBy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskIssuedByService.get(id);
		}
		if (entity == null){
			entity = new TaskIssuedBy();
		}
		return entity;
	}
	
	/**
	 * 任务下发列表页面
	 */
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:list")
	@RequestMapping(value = {"list", ""})
	public String list(TaskIssuedBy taskIssuedBy, Model model) {
		model.addAttribute("taskIssuedBy", taskIssuedBy);
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByList";
	}

	/**
	 * 任务列表页面 ---执行
 	 */
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:list")
	@RequestMapping(value = {"listCheck"})
	public String listCheck(TaskIssuedBy taskIssuedBy, Model model) {
		model.addAttribute("taskIssuedBy", taskIssuedBy);
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByListCheck";
	}

	/**
	 * 任务列表页面 ---统计
	 */
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:list")
	@RequestMapping(value = {"listStatistics"})
	public String listStatistics(TaskIssuedBy taskIssuedBy, Model model) {
		model.addAttribute("taskIssuedBy", taskIssuedBy);
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByListStatistics";
	}

	
		/**
	 * 任务下发列表数据
	 */
	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TaskIssuedBy taskIssuedBy, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<TaskIssuedBy> page = taskIssuedByService.findPage(new Page<TaskIssuedBy>(request, response), taskIssuedBy);
		return getBootstrapData(page);
	}

	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:list")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(TaskIssuedBy taskIssuedBy, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<TaskIssuedBy> page = taskIssuedByService.findPage2(new Page<TaskIssuedBy>(request, response), taskIssuedBy);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑任务下发表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"taskissuedby:taskissuedbyts:taskIssuedBy:view","taskissuedby:taskissuedbyts:taskIssuedBy:add","taskissuedby:taskissuedbyts:taskIssuedBy:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TaskIssuedBy taskIssuedBy, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("taskIssuedBy", taskIssuedBy);
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByForm";
	}

	/**
	 * 新建任务下发表单页面
	 * params:
	 *
	 */
	@RequestMapping(value = "Directly/{mode}")
	public String formDirectly(@PathVariable String mode, TaskIssuedBy taskIssuedBy, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("taskIssuedBy", taskIssuedBy);
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByDirectlyForm";
	}

	//任务处理页面
//	@RequiresPermissions(value={"taskissuedby:taskissuedbyts:taskIssuedBy:add","taskissuedby:taskissuedbyts:taskIssuedBy:edit"},logical=Logical.OR)
	@RequestMapping(value = "opencl")
	public String opencl(@PathVariable(required = false) String mode, TaskIssuedBy taskIssuedBy, Model model,String id) {
		model.addAttribute("mode", mode);

		model.addAttribute("taskIssuedBy", taskIssuedByService.get(id));
		return "modules/taskissuedby/taskissuedbyts/taskIssuedByFormcl";
	}


	/**
	 * 保存任务下发
	 */
	@ResponseBody
	@RequiresPermissions(value={"taskissuedby:taskissuedbyts:taskIssuedBy:add","taskissuedby:taskissuedbyts:taskIssuedBy:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TaskIssuedBy taskIssuedBy, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(taskIssuedBy);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		try {
			System.err.println(taskIssuedBy.toString());
			String ids = taskIssuedBy.getExecutorId();
			String idArray[] =ids.split(",");
			//新增或编辑表单保存
			for(int i= 0 ;i<idArray.length;i++){
				taskIssuedBy.setExecutorId(idArray[i]);
				taskIssuedBy.setExecutorName(userMapper.get(idArray[i]).getName());
				taskIssuedByService.save(taskIssuedBy);//保存
				taskIssuedBy.setId("");
				taskIssuedBy.setExecutorName("");
			}
			int num = 0;
			num = idArray.length;
			j.setSuccess(true);
			j.setMsg("保存任务下发"+num+"条成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 处理下发任务
	 */
	@ResponseBody
	@RequestMapping(value = "dispose")
	public AjaxJson dispose(TaskIssuedBy taskIssuedBy, Model model) {
		AjaxJson j = new AjaxJson();
		String errMsg = beanValidator(taskIssuedBy);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		System.out.println(taskIssuedBy.toString());
		taskIssuedBy.setState(1);
		taskIssuedBy.setEndTime(new Date());
		taskIssuedByService.save(taskIssuedBy);//保存
		j.setSuccess(true);
		j.setMsg("处理成功");
		return j;
	}
	
	/**
	 * 批量删除任务下发
	 */
	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			taskIssuedByService.delete(taskIssuedByService.get(id));
		}
		j.setMsg("删除任务下发成功");
		return j;
	}





	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TaskIssuedBy taskIssuedBy, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "任务下发"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TaskIssuedBy> page = taskIssuedByService.findPage(new Page<TaskIssuedBy>(request, response, -1), taskIssuedBy);
    		new ExportExcel("任务下发", TaskIssuedBy.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出任务下发记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TaskIssuedBy> list = ei.getDataList(TaskIssuedBy.class);
			for (TaskIssuedBy taskIssuedBy : list){
				try{
					taskIssuedByService.save(taskIssuedBy);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条任务下发记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条任务下发记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入任务下发失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入任务下发数据模板
	 */
	@ResponseBody
	@RequiresPermissions("taskissuedby:taskissuedbyts:taskIssuedBy:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "任务下发数据导入模板.xlsx";
    		List<TaskIssuedBy> list = Lists.newArrayList(); 
    		new ExportExcel("任务下发数据", TaskIssuedBy.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


}