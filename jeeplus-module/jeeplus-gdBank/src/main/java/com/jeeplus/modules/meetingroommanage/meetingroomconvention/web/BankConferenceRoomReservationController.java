/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroomconvention.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.utils.GetBetweenDate;
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
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.service.BankConferenceRoomReservationService;

/**
 * 会议管理Controller
 * @author cheny
 * @version 2019-11-19
 */
@Controller
@RequestMapping(value = "${adminPath}/meetingroommanage/meetingroomconvention/bankConferenceRoomReservation")
public class BankConferenceRoomReservationController extends BaseController {

	@Autowired
	private BankConferenceRoomReservationService bankConferenceRoomReservationService;
	
	@ModelAttribute
	public BankConferenceRoomReservation get(@RequestParam(required=false) String id) {
		BankConferenceRoomReservation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankConferenceRoomReservationService.get(id);
		}
		if (entity == null){
			entity = new BankConferenceRoomReservation();
		}
		return entity;
	}
	
	/**
	 * 会议室预定列表页面
	 */
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
		model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
		return "modules/meetingroommanage/meetingroomconvention/bankConferenceRoomReservationList";
	}

	/**
	 * 会议室预定待开列表页面
	 */
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:list")
	@RequestMapping(value = {"listNotOpen"})
	public String listNotOpen(BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
		model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
		return "modules/meetingroommanage/meetingroomconvention/notOpenList";
	}

	/**
	 * 会议室预定待开列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:list")
	@RequestMapping(value = "notOpenData")
	public Map<String, Object> notOpenData(BankConferenceRoomReservation bankConferenceRoomReservation, HttpServletRequest request, HttpServletResponse response, Model model) {
		//如果登录用户不是超管和会议室管理员，只显示自己的会议
		List<String> roleIds = bankConferenceRoomReservationService.findrole(UserUtils.getUser().getId());
		boolean result = false;
		if(roleIds!=null&&!roleIds.contains("83464c364a1247189932649bd838c4ab")){//不是会议室管理员
			result = true;
		}
		if(!UserUtils.getUser().getId().equalsIgnoreCase("1")&&result){//不是超管且不是会议室管理员
			String loginOfficeId = UserUtils.getUser().getOffice().getId();
			bankConferenceRoomReservation.setHostDept(loginOfficeId);
		}
		Date d = new Date();
		bankConferenceRoomReservation.setBeginTime(d);
		Page<BankConferenceRoomReservation> page = bankConferenceRoomReservationService.findPage(new Page<BankConferenceRoomReservation>(request, response), bankConferenceRoomReservation);
		return getBootstrapData(page);
	}
	
		/**
	 * 会议室预定列表数据
	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankConferenceRoomReservation bankConferenceRoomReservation, HttpServletRequest request, HttpServletResponse response, Model model) {
		//如果登录用户不是超管和会议室管理员，只显示自己的会议
		List<String> roleIds = bankConferenceRoomReservationService.findrole(UserUtils.getUser().getId());
		boolean result = false;
		if(roleIds!=null&&!roleIds.contains("83464c364a1247189932649bd838c4ab")){//不是会议室管理员
			result = true;
		}
		if(!UserUtils.getUser().getId().equalsIgnoreCase("1")&&result){//不是超管且不是会议室管理员
//			String loginOfficeId = UserUtils.getUser().getOffice().getId();
			String loginId = UserUtils.getUser().getId();//登陆用户id
//			bankConferenceRoomReservation.setHostDept(loginOfficeId);
			bankConferenceRoomReservation.setConferencePresonId(loginId);
		}
		Page<BankConferenceRoomReservation> page = bankConferenceRoomReservationService.findPage(new Page<BankConferenceRoomReservation>(request, response), bankConferenceRoomReservation);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑会议室预定表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:view","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:add","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
		return "modules/meetingroommanage/meetingroomconvention/bankConferenceRoomReservationForm";
	}

	/**
	 * 保存会议室预定
	 */
	@ResponseBody
	@RequiresPermissions(value={"meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:add","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankConferenceRoomReservation bankConferenceRoomReservation, Model model) throws Exception{
		AjaxJson j = new AjaxJson();

		try{
			/**
			 * 后台hibernate-validation插件校验
			 */
			if(bankConferenceRoomReservationService.isAppointment(bankConferenceRoomReservation)){
				String errMsg = beanValidator(bankConferenceRoomReservation);
				if (StringUtils.isNotBlank(errMsg)){
					j.setSuccess(false);
					j.setMsg(errMsg);
					return j;
				}
				//新增或编辑表单保存
				//将传入的的日期转为，字符串
				SimpleDateFormat sdfSave = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date beginDate = bankConferenceRoomReservation.getBeginTime();
				Date endDate = bankConferenceRoomReservation.getEndTime();
				String beginStr = sdf.format(beginDate);
				String endStr = sdf.format(endDate);
				//获取两个时间段内的所有日期，日期可跨年
				List<String> list = GetBetweenDate.getBetweenDate(beginStr,endStr);
				List<String> listEnd = new ArrayList<>();
				List<String> listBegin = new ArrayList<>();
				if(list.size()==1){
					bankConferenceRoomReservationService.save(bankConferenceRoomReservation);//保存
					j.setSuccess(true);
					j.setMsg("保存会议室预定成功");
				}else {//循环保存
					String beginAtThe = " 08:00:00";//默认的开始时分
					String endAtThe = " 22:00:00";//默认的结束时分
					for(String date:list){
						listEnd.add(date+endAtThe);
						listBegin.add(date+beginAtThe);
					}
					for(int i = 0;i<list.size();i++){
						if(i==0){//第一条
							bankConferenceRoomReservation.setEndTime(sdfSave.parse(listEnd.get(i)));
							bankConferenceRoomReservationService.save(bankConferenceRoomReservation);//保存
							bankConferenceRoomReservation.setId("");
						}else if(i==list.size()-1){//最后一条
							bankConferenceRoomReservation.setBeginTime(sdfSave.parse(listBegin.get(i)));
							bankConferenceRoomReservation.setEndTime(endDate);
							bankConferenceRoomReservationService.save(bankConferenceRoomReservation);//保存
							bankConferenceRoomReservation.setId("");
						}else {
							bankConferenceRoomReservation.setBeginTime(sdfSave.parse(listBegin.get(i)));
							bankConferenceRoomReservation.setEndTime(sdfSave.parse(listEnd.get(i)));
							bankConferenceRoomReservationService.save(bankConferenceRoomReservation);//保存
							bankConferenceRoomReservation.setId("");
						}
					}
				}
			}else {
				j.setSuccess(false);
				j.setMsg("预定失败，该会议室已占用！");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return j;
	}

	
	/**
	 *批量取消会议室预定
	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankConferenceRoomReservationService.delete(bankConferenceRoomReservationService.get(id));
		}
		j.setMsg("取消会议室预定成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankConferenceRoomReservation bankConferenceRoomReservation, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议室预定"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankConferenceRoomReservation> page = bankConferenceRoomReservationService.findPage(new Page<BankConferenceRoomReservation>(request, response, -1), bankConferenceRoomReservation);
    		new ExportExcel("会议室预定", BankConferenceRoomReservation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出会议室预定记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankConferenceRoomReservation> list = ei.getDataList(BankConferenceRoomReservation.class);
			for (BankConferenceRoomReservation bankConferenceRoomReservation : list){
				try{
					bankConferenceRoomReservationService.save(bankConferenceRoomReservation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会议室预定记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条会议室预定记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入会议室预定失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入会议室预定成功数据模板
	 */
	@ResponseBody
	@RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "会议室预定数据导入模板.xlsx";
    		List<BankConferenceRoomReservation> list = Lists.newArrayList(); 
    		new ExportExcel("会议室预定数据", BankConferenceRoomReservation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

	/**
	 * 新建会议室预定表单
	 * params:
	 *
	 */
	@RequiresPermissions(value={"meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:add"},logical=Logical.OR)
	@RequestMapping(value = "Directly/{mode}")
	public String formDirectly(@PathVariable String mode, BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
		return "modules/meetingroommanage/meetingroomconvention/bankConferenceRoomReservationFormDirectly";
	}


	/**
	 * 查看会议室是否被预定
	 */
	@ResponseBody
	@RequiresPermissions(value={"meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:add","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit"},logical=Logical.OR)
	@RequestMapping(value = "isAppointment")
	public AjaxJson isAppointment(BankConferenceRoomReservation bankConferenceRoomReservation, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		if(bankConferenceRoomReservation.getBeginTime()!=null||bankConferenceRoomReservation.getEndTime()!=null){
			if(bankConferenceRoomReservationService.isAppointment(bankConferenceRoomReservation)){
				j.setSuccess(false);
				j.setMsg("请重新选择时间，当前时间已被占用！");
			}
		}

		return j;
	}
}