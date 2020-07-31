/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.entity.TaskDistributionBytsFit;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.mapper.TaskDistributionBytsFitMapper;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.service.TaskDistributionBytsFitService;
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
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity.TaskDistributionByts;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.service.TaskDistributionBytsService;

/**
 * 任务下发Controller
 * @author chenl
 * @version 2020-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/taskdistributionbyts/taskdistributionbyt/taskDistributionByts")
public class TaskDistributionBytsController extends BaseController {

	@Autowired
	private TaskDistributionBytsService taskDistributionBytsService;
    @Autowired
    private TaskDistributionBytsFitService taskDistributionBytsFitService;
    @Autowired
    private TaskDistributionBytsFitMapper tm;
    @Autowired
    private SystemService systemService;



    @ModelAttribute
	public TaskDistributionByts get(@RequestParam(required=false) String id) {
		TaskDistributionByts entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = taskDistributionBytsService.get(id);
		}
		if (entity == null){
			entity = new TaskDistributionByts();
		}
		return entity;
	}
	
	/**
     * 任务下发列表页面
     */
    @RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:list")
    @RequestMapping(value = {"list", ""})
    public String list(TaskDistributionByts taskDistributionByts, Model model) {
        model.addAttribute("taskDistributionByts", taskDistributionByts);
        return "modules/taskdistributionbyts/taskdistributionbyt/taskDistributionBytsList";
    }


    /**
     * 任务下发列表页面 统计页面
     */
    @RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:list")
    @RequestMapping(value = {"listTong"})
    public String listTong(TaskDistributionByts taskDistributionByts, Model model) {
        model.addAttribute("taskDistributionByts", taskDistributionByts);
        return "modules/taskdistributionbyts/taskdistributionbyt/taskDistributionBytsListTong";
    }


		/**
	 * 任务下发列表数据
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TaskDistributionByts taskDistributionByts, HttpServletRequest request, HttpServletResponse response, Model model) {
		String loginId = UserUtils.getUser().getId();
		if("1".equals(loginId)||"c6d9c07543f64a21b4454c23c8dedde2".equals(loginId)){

		}else {
			taskDistributionByts.setPublisherId(loginId);
		}
		Page<TaskDistributionByts> page = taskDistributionBytsService.findPage(new Page<TaskDistributionByts>(request, response), taskDistributionByts);
		return getBootstrapData(page);
	}

	/**
	 * 任务下发列表数据---统计要看全部
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:list")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(TaskDistributionByts taskDistributionByts, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TaskDistributionByts> page = taskDistributionBytsService.findPage(new Page<TaskDistributionByts>(request, response), taskDistributionByts);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑任务下发表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
//	@RequiresPermissions(value={"taskdistributionbyts:taskdistributionbyt:taskDistributionByts:view","taskdistributionbyts:taskdistributionbyt:taskDistributionByts:add","taskdistributionbyts:taskdistributionbyt:taskDistributionByts:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TaskDistributionByts taskDistributionByts, Model model) {
		model.addAttribute("mode", mode);
		if(mode.equals("add")){
			taskDistributionByts.setPublisherId(UserUtils.getUser().getId());
			taskDistributionByts.setPublisherName(UserUtils.getUser().getName());

		}
		model.addAttribute("taskDistributionByts", taskDistributionByts);
		return "modules/taskdistributionbyts/taskdistributionbyt/taskDistributionBytsForm";
	}

	/*首页下发任务表单*/
	@RequestMapping(value = "directlyform/{mode}")
	public String directlyform(@PathVariable String mode, TaskDistributionByts taskDistributionByts, Model model) {
		model.addAttribute("mode", mode);
		if(mode.equals("add")){
			taskDistributionByts.setPublisherId(UserUtils.getUser().getId());
			taskDistributionByts.setPublisherName(UserUtils.getUser().getName());
		}
		model.addAttribute("taskDistributionByts", taskDistributionByts);
		return "modules/taskdistributionbyts/taskdistributionbyt/taskDirectly";
	}

	/**
	 * 保存任务下发
	 */
	@ResponseBody
	@RequiresPermissions(value={"taskdistributionbyts:taskdistributionbyt:taskDistributionByts:add","taskdistributionbyts:taskdistributionbyt:taskDistributionByts:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TaskDistributionByts taskDistributionByts, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(taskDistributionByts);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		taskDistributionBytsService.save(taskDistributionByts);//保存
        List<TaskDistributionBytsFit> list = tm.findByTidAll(taskDistributionByts.getId());
        String taskIds = taskDistributionByts.getTaskIds();
        String[] split = taskIds.split(",");
        List<TaskDistributionBytsFit> lists=new ArrayList<>();
        if(list.size()>0){
            for (int i = 0; i <split.length ; i++) {   //已有添加
                boolean trg=true;
                for (int k = 0; k <list.size(); k++) {
                   if(split[i].equals(list.get(k).getExecutorId())){
                       list.get(k).setId("");
                       lists.add(list.get(k));
                       trg=true;
                       break;
                   }else {
                       trg=false;
                   }
                }

                if(!trg){ //没有添加
                    User user = systemService.getUser(split[i]);
                    TaskDistributionBytsFit ts=new TaskDistributionBytsFit();
                    ts.setTaskDistributionBytsId(taskDistributionByts.getId());
                    ts.setExecutorName(user.getName());
                    ts.setExecutorId(split[i]);
                    ts.setState(1);
                    ts.setRemarks(taskDistributionByts.getTaskName());
                    lists.add(ts);
                }


            }
            tm.delTaskByTid(taskDistributionByts.getId());

            System.out.println("===="+lists.size());
            for (int i = 0; i <lists.size() ; i++) {
                taskDistributionBytsFitService.save(lists.get(i));
            }

        }else {
            for (int i = 0; i <split.length ; i++) {
                User user = systemService.getUser(split[i]);
                TaskDistributionBytsFit ts=new TaskDistributionBytsFit();
                ts.setTaskDistributionBytsId(taskDistributionByts.getId());
                ts.setExecutorName(user.getName());
                ts.setExecutorId(split[i]);
                ts.setState(1);
                ts.setRemarks(taskDistributionByts.getTaskName());
                taskDistributionBytsFitService.save(ts);
            }
        }




        j.setSuccess(true);
		j.setMsg("保存任务下发成功");
		return j;
	}

	
	/**
	 * 批量删除任务下发
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			taskDistributionBytsService.delete(taskDistributionBytsService.get(id));
		}
		j.setMsg("删除任务下发成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TaskDistributionByts taskDistributionByts, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "任务下发"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TaskDistributionByts> page = taskDistributionBytsService.findPage(new Page<TaskDistributionByts>(request, response, -1), taskDistributionByts);
    		new ExportExcel("任务下发", TaskDistributionByts.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TaskDistributionByts> list = ei.getDataList(TaskDistributionByts.class);
			for (TaskDistributionByts taskDistributionByts : list){
				try{
					taskDistributionBytsService.save(taskDistributionByts);
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
	@RequiresPermissions("taskdistributionbyts:taskdistributionbyt:taskDistributionByts:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "任务下发数据导入模板.xlsx";
    		List<TaskDistributionByts> list = Lists.newArrayList(); 
    		new ExportExcel("任务下发数据", TaskDistributionByts.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}