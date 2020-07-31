/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity.TaskDistributionByts;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.service.TaskDistributionBytsService;
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
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.entity.TaskDistributionBytsFit;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.service.TaskDistributionBytsFitService;

/**
 * 执行人Controller
 * @author chenl
 * @version 2020-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFit")
public class TaskDistributionBytsFitController extends BaseController {

	@Autowired
	private TaskDistributionBytsFitService taskDistributionBytsFitService;
	@Autowired
	private TaskDistributionBytsService taskDistributionBytsService;
	
	@ModelAttribute
	public TaskDistributionBytsFit get(@RequestParam(required=false) String id) {
		TaskDistributionBytsFit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskDistributionBytsFitService.get(id);
		}
		if (entity == null){
			entity = new TaskDistributionBytsFit();
		}
		return entity;
	}
	
	/**
	 * 执行人列表页面
	 */
//	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:list")
	@RequestMapping(value = {"list", ""})
	public String list(TaskDistributionBytsFit taskDistributionBytsFit, Model model) {
		model.addAttribute("taskDistributionBytsFit", taskDistributionBytsFit);
		return "modules/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFitList";
	}


	/**
	 *
	 */
//	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:list")
	@RequestMapping(value = {"getList"})
	public String getList(TaskDistributionBytsFit taskDistributionBytsFit, Model model,String tid) {
		taskDistributionBytsFit.setTaskDistributionBytsId(tid);
		model.addAttribute("taskDistributionBytsFit", taskDistributionBytsFit);
		return "modules/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFitListList";
	}
	
		/**
	 * 执行人列表数据
	 */
	@ResponseBody
//	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TaskDistributionBytsFit taskDistributionBytsFit, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<TaskDistributionBytsFit> page = taskDistributionBytsFitService.findPage(new Page<TaskDistributionBytsFit>(request, response), taskDistributionBytsFit);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑执行人表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
//	@RequiresPermissions(value={"taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:view","taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:add","taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TaskDistributionBytsFit taskDistributionBytsFit, Model model) {
		model.addAttribute("mode", mode);
		if(mode.equals("edit")){
			taskDistributionBytsFit.setState(2);
		}
		model.addAttribute("taskDistributionBytsFit", taskDistributionBytsFit);
		return "modules/taskdistributionbyts/taskdistributionbytsfits/taskDistributionBytsFitForm";
	}

	/**
	 * 保存执行人
	 */
	@ResponseBody
//	@RequiresPermissions(value={"taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:add","taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TaskDistributionBytsFit taskDistributionBytsFit, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(taskDistributionBytsFit);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		Integer state = taskDistributionBytsFitService.get(taskDistributionBytsFit.getId()).getState();
		if(1==state){//该任务人第一次执行该条任务
			TaskDistributionByts ts=taskDistributionBytsService.get(taskDistributionBytsFit.getTaskDistributionBytsId());
			ts.setState(ts.getState()+1);
			String[] taskIds = ts.getTaskIds().split(",");
			if (taskIds.length==ts.getState()){
				ts.setState(-1); //全部执行完成
				ts.setEndEndTime(taskDistributionBytsFit.getEndExecutorTime());
			}
			taskDistributionBytsService.save(ts);
		}
		//新增或编辑表单保存
		taskDistributionBytsFitService.save(taskDistributionBytsFit);//保存

//		if(taskDistributionBytsFit.getState()==2){
//
//			TaskDistributionByts ts=taskDistributionBytsService.get(taskDistributionBytsFit.getTaskDistributionBytsId());
//
//            ts.setState(ts.getState()+1);
//
//            String[] taskIds = ts.getTaskIds().split(",");
//            if (taskIds.length==ts.getState()){
//                ts.setState(-1); //全部执行完成
//                ts.setEndEndTime(taskDistributionBytsFit.getEndExecutorTime());
//            }
//
//
//            taskDistributionBytsService.save(ts);
//
//
//		}


		j.setSuccess(true);
		j.setMsg("保存执行人成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "chuli")
	public AjaxJson chuli(TaskDistributionBytsFit taskDistributionBytsFit, Model model) throws Exception{
		AjaxJson j = new AjaxJson();


		taskDistributionBytsFit.setState(2);
		//新增或编辑表单保存
		taskDistributionBytsFitService.save(taskDistributionBytsFit);//保存


		j.setSuccess(true);
		j.setMsg("处理成功");
		return j;
	}

	//根据主表id,查询主表信息(主要是主表发起人id)
	@ResponseBody
	@RequestMapping(value = "getTasInfo")
	public AjaxJson getTasInfo(String id, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
        TaskDistributionByts taskDistributionByts = taskDistributionBytsService.get(id);
        LinkedHashMap<String, Object> objectObjectLinkedHashMap = new LinkedHashMap<>();
        objectObjectLinkedHashMap.put("taskDistributionByts",taskDistributionByts);
        j.setBody(objectObjectLinkedHashMap);
		j.setSuccess(true);
		j.setMsg("处理成功");
		return j;
	}



	
	/**
	 * 批量删除执行人
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			taskDistributionBytsFitService.delete(taskDistributionBytsFitService.get(id));
		}
		j.setMsg("删除执行人成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TaskDistributionBytsFit taskDistributionBytsFit, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "执行人"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TaskDistributionBytsFit> page = taskDistributionBytsFitService.findPage(new Page<TaskDistributionBytsFit>(request, response, -1), taskDistributionBytsFit);
    		new ExportExcel("执行人", TaskDistributionBytsFit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出执行人记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TaskDistributionBytsFit> list = ei.getDataList(TaskDistributionBytsFit.class);
			for (TaskDistributionBytsFit taskDistributionBytsFit : list){
				try{
					taskDistributionBytsFitService.save(taskDistributionBytsFit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条执行人记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条执行人记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入执行人失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入执行人数据模板
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbytsfits:taskDistributionBytsFit:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "执行人数据导入模板.xlsx";
    		List<TaskDistributionBytsFit> list = Lists.newArrayList(); 
    		new ExportExcel("执行人数据", TaskDistributionBytsFit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}