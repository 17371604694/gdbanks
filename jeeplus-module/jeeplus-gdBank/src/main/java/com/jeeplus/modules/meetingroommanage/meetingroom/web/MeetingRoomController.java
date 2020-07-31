/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroom.web;

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
import com.jeeplus.modules.meetingroommanage.meetingroom.entity.MeetingRoom;
import com.jeeplus.modules.meetingroommanage.meetingroom.service.MeetingRoomService;

/**
 * 会议室Controller
 * @author cheny
 * @version 2019-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/meetingroom/meetingroom/meetingRoom")
public class MeetingRoomController extends BaseController {

	@Autowired
	private MeetingRoomService meetingRoomService;
	
	@ModelAttribute
	public MeetingRoom get(@RequestParam(required=false) String id) {
		MeetingRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = meetingRoomService.get(id);
		}
		if (entity == null){
			entity = new MeetingRoom();
		}
		return entity;
	}
	
	/**
	 * 会议室列表页面
	 */
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:list")
	@RequestMapping(value = {"list", ""})
	public String list(MeetingRoom meetingRoom, Model model) {
		model.addAttribute("meetingRoom", meetingRoom);
		return "modules/meetingroommanage/meetingroom/meetingRoomList";
	}
	
		/**
	 * 会议室列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MeetingRoom meetingRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MeetingRoom> page = meetingRoomService.findPage(new Page<MeetingRoom>(request, response), meetingRoom); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议室表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"meetingroom:meetingroom:meetingRoom:view","meetingroom:meetingroom:meetingRoom:add","meetingroom:meetingroom:meetingRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, MeetingRoom meetingRoom, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("meetingRoom", meetingRoom);
		return "modules/meetingroommanage/meetingroom/meetingRoomForm";
	}

	/**
	 * 保存会议室
	 */
	@ResponseBody
	@RequiresPermissions(value={"meetingroom:meetingroom:meetingRoom:add","meetingroom:meetingroom:meetingRoom:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(MeetingRoom meetingRoom, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		try{
			MeetingRoom m = meetingRoomService.findRoomByName(meetingRoom.getRoomName());
			if(m==null){//如果不存在，则执行保存或更新
				String errMsg = beanValidator(meetingRoom);
				if (StringUtils.isNotBlank(errMsg)){
					j.setSuccess(false);
					j.setMsg(errMsg);
					return j;
				}
				//新增或编辑表单保存
				meetingRoomService.save(meetingRoom);//保存
				j.setSuccess(true);
				j.setMsg("保存会议室成功");
			}else {
				j.setSuccess(false);
				j.setMsg("保存会议室失败，会议室名称重复，请重新输入！");
			}
		}catch (Exception e){
			e.printStackTrace();
		}


		return j;
	}

	
	/**
	 * 批量删除会议室成功
	 */
	@ResponseBody
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			meetingRoomService.delete(meetingRoomService.get(id));
		}
		j.setMsg("删除会议室成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(MeetingRoom meetingRoom, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议室管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MeetingRoom> page = meetingRoomService.findPage(new Page<MeetingRoom>(request, response, -1), meetingRoom);
    		new ExportExcel("会议室管理", MeetingRoom.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议室管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MeetingRoom> list = ei.getDataList(MeetingRoom.class);
			for (MeetingRoom meetingRoom : list){
				try{
					meetingRoomService.save(meetingRoom);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议室管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条会议室管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入会议室管理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入会议室管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("meetingroom:meetingroom:meetingRoom:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议室管理数据导入模板.xlsx";
    		List<MeetingRoom> list = Lists.newArrayList(); 
    		new ExportExcel("会议室管理数据", MeetingRoom.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


	/**
	 *查询所有会议室（返回会议查询表格左侧列）
	 */
	@ResponseBody
	@RequestMapping(value = "dataAll")
	public List<MeetingRoom> dataALL(MeetingRoom meetingRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<MeetingRoom> allRoom = meetingRoomService.findAllRoom();
		return allRoom;
	}

}