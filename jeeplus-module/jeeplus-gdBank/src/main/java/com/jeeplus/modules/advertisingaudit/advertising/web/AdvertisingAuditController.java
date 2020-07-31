/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.advertisingaudit.advertising.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.advertisingaudit.advertising.mapper.AdvertisingAuditMapper;

import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.mapper.OfficeMapper;
import com.jeeplus.modules.sys.utils.TimeUtils;
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
import com.jeeplus.modules.advertisingaudit.advertising.entity.AdvertisingAudit;
import com.jeeplus.modules.advertisingaudit.advertising.service.AdvertisingAuditService;

/**
 * 广告审核Controller
 * @author chenl
 * @version 2019-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/advertisingaudit/advertising/advertisingAudit")
public class AdvertisingAuditController extends BaseController {

	@Autowired
	private AdvertisingAuditService advertisingAuditService;
	@Autowired
	private AdvertisingAuditMapper as;
	@Autowired
	private OfficeMapper officeMapper;


	@ModelAttribute
	public AdvertisingAudit get(@RequestParam(required=false) String id) {
		AdvertisingAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = advertisingAuditService.get(id);
		}
		if (entity == null){
			entity = new AdvertisingAudit();
		}
		return entity;
	}
	
	/**
	 * 广告审核列表页面
	 */
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:list")
	@RequestMapping(value = {"list", ""})
	public String list(AdvertisingAudit advertisingAudit, Model model) {
		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			advertisingAudit.setApplicantPerson(UserUtils.getUser().getId());
		}

		model.addAttribute("advertisingAudit", advertisingAudit);
		return "modules/advertisingaudit/advertising/advertisingAuditList";
	}


	/**
	 * 广告审核列表页面 审核
	 */
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:list")
	@RequestMapping(value = {"listCheck"})
	public String listCheck(AdvertisingAudit advertisingAudit, Model model) {
		model.addAttribute("advertisingAudit", advertisingAudit);
		return "modules/advertisingaudit/advertising/advertisingAuditListCheck";
	}
	/**
	 * 广告审核列表页面 全部列表
	 */
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:list")
	@RequestMapping(value = {"listAll"})
	public String listAll(AdvertisingAudit advertisingAudit, Model model) {
		model.addAttribute("advertisingAudit", advertisingAudit);
		return "modules/advertisingaudit/advertising/advertisingAuditListAll";
	}

		/**
	 * 广告审核列表数据
	 */
	@ResponseBody
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AdvertisingAudit advertisingAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdvertisingAudit> page = advertisingAuditService.findPage(new Page<AdvertisingAudit>(request, response), advertisingAudit); 
		return getBootstrapData(page);
	}


	@ResponseBody
//	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:list")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(HttpServletRequest request, HttpServletResponse response, Model model,
									 Integer stateStep,
									 @RequestParam(defaultValue = "",required = false) String beginTime,
									 @RequestParam(defaultValue = "",required = false) String endTime,
									 @RequestParam(defaultValue = "",required = false) String applicantUnit,
									 @RequestParam(defaultValue = "",required = false)  Integer state
									 ) {
		Page<AdvertisingAudit> page=new Page<>();
		page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
		page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		String uid="";
		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			uid=UserUtils.getUser().getId();
		}
		List<AdvertisingAudit> listConAll = as.findListConAll(uid,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize(),stateStep,beginTime,endTime,applicantUnit,state);
        Integer listConAllCount = as.findListConAllCount(uid);
        page.setList(listConAll);
		page.setCount(listConAllCount==null?0:listConAllCount);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑广告审核表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"advertisingaudit:advertising:advertisingAudit:view","advertisingaudit:advertising:advertisingAudit:add","advertisingaudit:advertising:advertisingAudit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, AdvertisingAudit advertisingAudit, Model model) {
		model.addAttribute("mode", mode);
		if (mode.equals("add")){
			advertisingAudit.setApplicantPerson(UserUtils.getUser().getId());
			advertisingAudit.setApplicantPersonName(UserUtils.getUser().getName());
			Office office = UserUtils.getUser().getOffice();
			advertisingAudit.setApplicantUnit(office.getName());
		}
        if (mode.equals("edit") && advertisingAudit.getStateStep()==7){
            String remarks = advertisingAudit.getRemarks();
            String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"重新发起申请!";
            advertisingAudit.setRemarks(remarkSave);
            advertisingAudit.setStateStep(1);
            advertisingAudit.setState(1);
            advertisingAudit.setChoices(0);
        }
		model.addAttribute("advertisingAudit", advertisingAudit);
		return "modules/advertisingaudit/advertising/advertisingAuditForm";
	}

	/**
	 * 新建广告审核表单页面
	 * params:
	 *
	 */
	@RequestMapping(value = "Directly/{mode}")
	public String formDirectly(@PathVariable String mode,  AdvertisingAudit advertisingAudit, Model model) {
		Office office = UserUtils.getUser().getOffice();
		advertisingAudit.setApplicantUnit(office.getName());
		model.addAttribute("mode", mode);
		model.addAttribute("advertisingAudit", advertisingAudit);
		return "modules/advertisingaudit/advertising/advertisingDirectlyForm";
	}

	/**
	 * 保存广告审核
	 */
	@ResponseBody
	@RequiresPermissions(value={"advertisingaudit:advertising:advertisingAudit:add","advertisingaudit:advertising:advertisingAudit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(AdvertisingAudit advertisingAudit, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(advertisingAudit);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		/*advertisingAudit.setState(1);//设置审核状态为 待审核
		advertisingAudit.setStateStep(1);//设置审核步奏为第一步*/
		System.err.println(advertisingAudit.getAdvertisingCentent());
		advertisingAuditService.save(advertisingAudit);//保存
		j.setSuccess(true);
		j.setMsg("申请广告成功");
		return j;
	}

	
	/**
	 * 批量删除广告审核
	 */
	@ResponseBody
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			advertisingAuditService.delete(advertisingAuditService.get(id));
		}
		j.setMsg("删除广告审核成功");
		return j;
	}

	/**
	 * 审核操作通过(可批量操作)
	 *
	 * 	 * ids   id
	 * 	 * state   审核状态 1待审核 2 通过  3不通过 广告审核员移到第二步
	 * 	 * sta     区分是那个负责人审核的 1，单位负责人审核 2,相关部门(可选,可多选) 3,广告审核员  4,办公室负责人审核,5,行领导(可选) 6.完成 通过,7,驳回
	 *      * 根据确认 事项涉及多选的有几人 所有步骤加choices
	 *
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "approved")
	public AjaxJson approved(String ids,Model model,Integer state,Integer sta) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
				AdvertisingAudit advertisingAudit =  advertisingAuditService.get(id);

				//通过
				if (state==2){
					String remarks = advertisingAudit.getRemarks();
					String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"审核通过!";
					if(sta==3){
                        String department = advertisingAudit.getDepartment();
                        if (department.length()>0){
							System.out.println();
							String[] split = department.split(",");
							if(advertisingAudit.getChoices()==split.length){
								sta=sta+1; //多人审核完成
							}
						}else {
							sta=sta+1; //无跳过
						}
					}

					if (sta==5){ //行领导
						String officeSupervisorLeader = advertisingAudit.getSatrapPrincipal();
						if (officeSupervisorLeader.length()<=0){ //无跳过
							sta=sta+1;
						}
					}

					System.out.println("");

					as.updatePass(id,state,sta,remarkSave); //步骤1单位负责人审核
					j.setMsg("广告审核申请通过");
				}else if (state==3){ //不通过
					String remarks = advertisingAudit.getRemarks();
					String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"驳回  待重新发起!";
					sta = 7;
					as.updatePass(id,state,sta,remarkSave); //审核失败，不通过
					System.out.println("");
					j.setMsg("广告审核申请不通过");
				}


			}

		return j;
	}


    /**
     *根据id修该 Choices 多人的第几个人审核
     */
    @ResponseBody
    @RequestMapping(value = "setChoices")
    public Object setChoices(String id,Integer choices){

        return as.updataChoices(id,choices);
    }




	/**
	 * 审核操作不通过(可批量操作)
	 */
	@ResponseBody
	@RequestMapping(value = "approvedNo")
	public AjaxJson approvedNo(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		try{
			for(String id : idArray){
				AdvertisingAudit advertisingAudit =  advertisingAuditService.get(id);
				String r = advertisingAudit.getRemarks();
				if(advertisingAudit.getStateStep()==1){//如果是第一步，由单位负责人审核
					if(advertisingAudit.getUnitPrincipal().equalsIgnoreCase("")){//单位负责人空
						advertisingAudit.setStateStep(4);
						advertisingAudit.setState(3);
						advertisingAuditService.updataStateStepAndState(advertisingAudit);
					}else {
						advertisingAudit.setStateStep(4);
						advertisingAudit.setState(3);
						advertisingAudit.setRemarks(r+" "+TimeUtils.getTimeStr()+"--"+UserUtils.getUser().getName()+"："+"审核未通过!");
						advertisingAuditService.updataStateStepAndState(advertisingAudit);
					}

				}else if(advertisingAudit.getStateStep()==2){//如果是第二步且
					if(advertisingAudit.getSatrapPrincipal().equalsIgnoreCase("")){
						advertisingAudit.setStateStep(4);
						advertisingAudit.setState(3);
						advertisingAudit.setRemarks(r+" "+TimeUtils.getTimeStr()+"--"+UserUtils.getUser().getName()+"："+"审核未通过!");
						advertisingAuditService.updataStateStepAndState(advertisingAudit);
					}else {
						//行领导设置为空 ，由办公室负责人审核
						advertisingAudit.setStateStep(4);
						advertisingAudit.setState(3);
						advertisingAudit.setRemarks(r+" "+TimeUtils.getTimeStr()+"--"+UserUtils.getUser().getName()+"："+"审核未通过!");
						advertisingAuditService.updataStateStepAndState(advertisingAudit);
					}
				}else if(advertisingAudit.getStateStep()==3){//如果是第三步，行领导审核
					advertisingAudit.setStateStep(4);
					advertisingAudit.setState(3);
					advertisingAudit.setRemarks(r+" "+TimeUtils.getTimeStr()+"--"+UserUtils.getUser().getName()+"："+"审核未通过!");
					advertisingAuditService.updataStateStepAndState(advertisingAudit);
				}
			}

		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("系统出现异常请稍后重试！");
		}
		j.setSuccess(true);
		j.setMsg("审核成功！审批不通过！");
		return j;
	}


	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(AdvertisingAudit advertisingAudit, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "广告审核"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AdvertisingAudit> page = advertisingAuditService.findPage(new Page<AdvertisingAudit>(request, response, -1), advertisingAudit);
    		new ExportExcel("广告审核", AdvertisingAudit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出广告审核记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AdvertisingAudit> list = ei.getDataList(AdvertisingAudit.class);
			for (AdvertisingAudit advertisingAudit : list){
				try{
					advertisingAuditService.save(advertisingAudit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条广告审核记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条广告审核记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入广告审核失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入广告审核数据模板
	 */
	@ResponseBody
	@RequiresPermissions("advertisingaudit:advertising:advertisingAudit:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "广告审核数据导入模板.xlsx";
    		List<AdvertisingAudit> list = Lists.newArrayList(); 
    		new ExportExcel("广告审核数据", AdvertisingAudit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}