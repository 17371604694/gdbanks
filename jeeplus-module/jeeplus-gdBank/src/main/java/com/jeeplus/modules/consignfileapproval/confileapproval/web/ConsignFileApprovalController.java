/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.confileapproval.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.consignfileapproval.administrativearchives.entity.AdministrativeArchivesApprove;
import com.jeeplus.modules.consignfileapproval.confileapproval.mapper.ConsignFileApprovalMapper;
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
import com.jeeplus.modules.consignfileapproval.confileapproval.entity.ConsignFileApproval;
import com.jeeplus.modules.consignfileapproval.confileapproval.service.ConsignFileApprovalService;

/**
 * 寄存档案出库申请Controller
 * @author chenl
 * @version 2019-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/consignfileapproval/confileapproval/consignFileApproval")
public class ConsignFileApprovalController extends BaseController {

	@Autowired
	private ConsignFileApprovalService consignFileApprovalService;
	@Autowired
	private ConsignFileApprovalMapper cs;
	@Autowired
	private OfficeMapper officeMapper;
	
	@ModelAttribute
	public ConsignFileApproval get(@RequestParam(required=false) String id) {
		ConsignFileApproval entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = consignFileApprovalService.get(id);
		}
		if (entity == null){
			entity = new ConsignFileApproval();
		}
		return entity;
	}
	
	/**
	 * 寄存档案出库列表页面
	 */
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:list")
	@RequestMapping(value = {"list", ""})
	public String list(ConsignFileApproval consignFileApproval, Model model) {

		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			consignFileApproval.setApplicantPerson(UserUtils.getUser().getId());
		}

		model.addAttribute("consignFileApproval", consignFileApproval);
		return "modules/consignfileapproval/confileapproval/consignFileApprovalList";
	}

	/**
	 * 寄存档案出库列表页面审核-----
	 */
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:list")
	@RequestMapping(value = {"listCheck"})
	public String listCheck(ConsignFileApproval consignFileApproval, Model model) {
		model.addAttribute("consignFileApproval", consignFileApproval);
		return "modules/consignfileapproval/confileapproval/consignFileApprovalListCheck";
	}

	/**
	 * 寄存档案出库列表页面审核通过或不通过-----
	 * ids   id
	 * state   审核状态 1待审核 2 通过  3不通过
	 * sta     区分是那个负责人审核的 1，单位负责人审核 2，办公室负责人审核
	 */

	@ResponseBody
	@RequestMapping(value = {"listCheckPassOfNoPass"})
	public AjaxJson listCheckPassOfNoPass(String ids, Model model,Integer state,Integer sta) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			ConsignFileApproval consignFileApproval = consignFileApprovalService.get(id);
			if (state==2){
				StringBuffer remarks = new StringBuffer(consignFileApproval.getRemarks());
				remarks.append(" "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"审核通过!");
				if (sta==4){
                    String officeSupervisorLeader = consignFileApproval.getOfficeSupervisorLeader(); //行领导id
                    if (StringUtils.isBlank(officeSupervisorLeader)){
                        sta=5;
                    }

                }
				//System.out.println(remarks);
				cs.updatePass(id,state,sta,remarks.toString()); //步骤1单位负责人审核
				j.setMsg("寄存档案利用审批申请通过");
			}else if (state==3){ //不通过
				String remarks = consignFileApproval.getRemarks();
				String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"驳回 待重新发起!";
				sta = 6; //不同过--驳回 可以在此发起
				cs.updatePass(id,state,sta,remarkSave); //审核失败，不通过
				j.setMsg("寄存档案利用审批申请不通过");
			}else {
				j.setMsg("参数不匹配");
			}

		}

		return j;
	}


	/**
	 * 寄存档案出库列表页面统计-----
	 */
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:list")
	@RequestMapping(value = {"listStatistics"})
	public String listStatistics(ConsignFileApproval consignFileApproval, Model model) {
		model.addAttribute("consignFileApproval", consignFileApproval);
		return "modules/consignfileapproval/confileapproval/consignFileApprovalListStatistics";
	}


		/**
	 * 寄存档案出库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ConsignFileApproval consignFileApproval, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ConsignFileApproval> page = consignFileApprovalService.findPage(new Page<ConsignFileApproval>(request, response), consignFileApproval); 
		return getBootstrapData(page);
	}

	@ResponseBody
//	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:list")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2(HttpServletRequest request, HttpServletResponse response,
									 Integer stateStep,
									 @RequestParam(defaultValue = "",required = false) String beginTime,
									 @RequestParam(defaultValue = "",required = false) String endTime,
									 @RequestParam(defaultValue = "",required = false) String applicantUnit,
									 @RequestParam(defaultValue = "",required = false) String withTheWay,
									 @RequestParam(defaultValue = "",required = false) String applicantPerson,
									 @RequestParam(defaultValue = "",required = false) String beginReturnTime,
									 @RequestParam(defaultValue = "",required = false) String endReturnTime,
									 @RequestParam(defaultValue = "",required = false) Integer state
	) {
		Page<ConsignFileApproval> page=new Page<>();
		page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
		page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		String uid="";
		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			uid=UserUtils.getUser().getId();
		}
		List<ConsignFileApproval> listConAll = cs.findListConAll(uid,(page.getPageNo()-1)*page.getPageSize()
				,page.getPageSize()
				,stateStep
				,beginTime
				,endTime
				,applicantUnit
				,withTheWay
				,applicantPerson
				,beginReturnTime
				,endReturnTime
				,state
		);
		Integer listConAll2 = cs.findListConAllCount(uid);
		page.setList(listConAll);
		page.setCount(listConAll2==null?0:listConAll2);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑寄存档案出库表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"consignfileapproval:confileapproval:consignFileApproval:view","consignfileapproval:confileapproval:consignFileApproval:add","consignfileapproval:confileapproval:consignFileApproval:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, ConsignFileApproval consignFileApproval, Model model) {
		model.addAttribute("mode", mode);
		if (mode.equals("add")){
			Office office = UserUtils.getUser().getOffice();
//			Office office = officeMapper.getByUserIdOffice(id);
			consignFileApproval.setApplicantUnit(office.getName());
            consignFileApproval.setApplicantPerson(UserUtils.getUser().getId());
            consignFileApproval.setApplicantPersonName(UserUtils.getUser().getName());
        }
        if (mode.equals("edit") && consignFileApproval.getStateStep()==6){
            String remarks = consignFileApproval.getRemarks();
            String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"重新发起申请!";
            consignFileApproval.setRemarks(remarkSave);
            consignFileApproval.setStateStep(1);
        }
		model.addAttribute("consignFileApproval", consignFileApproval);
		return "modules/consignfileapproval/confileapproval/consignFileApprovalForm";
	}

	/**
	 * 新建寄存档案出库表单页面
	 * params:
	 *
	 */
	@RequestMapping(value = "Directly/{mode}")
	public String formDirectly(@PathVariable String mode, ConsignFileApproval consignFileApproval, Model model) {
		Office office = UserUtils.getUser().getOffice();
//		Office office = officeMapper.getByUserIdOffice(id);
		consignFileApproval.setApplicantUnit(office.getName());
		model.addAttribute("mode", mode);
		model.addAttribute("consignFileApproval", consignFileApproval);
		return "modules/consignfileapproval/confileapproval/consignFileApprovalDirectlyForm";
	}

	/**
	 * 保存寄存档案出库
	 */
	@ResponseBody
	@RequiresPermissions(value={"consignfileapproval:confileapproval:consignFileApproval:add","consignfileapproval:confileapproval:consignFileApproval:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(ConsignFileApproval consignFileApproval, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(consignFileApproval);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		consignFileApprovalService.save(consignFileApproval);//保存
		j.setSuccess(true);
		j.setMsg("保存寄存档案出库成功");
		return j;
	}

	
	/**
	 * 批量删除寄存档案出库
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			consignFileApprovalService.delete(consignFileApprovalService.get(id));
		}
		j.setMsg("删除寄存档案出库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(ConsignFileApproval consignFileApproval, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "寄存档案出库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ConsignFileApproval> page = consignFileApprovalService.findPage(new Page<ConsignFileApproval>(request, response, -1), consignFileApproval);
    		new ExportExcel("寄存档案出库", ConsignFileApproval.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出寄存档案出库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ConsignFileApproval> list = ei.getDataList(ConsignFileApproval.class);
			for (ConsignFileApproval consignFileApproval : list){
				try{
					consignFileApprovalService.save(consignFileApproval);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条寄存档案出库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条寄存档案出库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入寄存档案出库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入寄存档案出库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:confileapproval:consignFileApproval:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "寄存档案出库数据导入模板.xlsx";
    		List<ConsignFileApproval> list = Lists.newArrayList(); 
    		new ExportExcel("寄存档案出库数据", ConsignFileApproval.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}