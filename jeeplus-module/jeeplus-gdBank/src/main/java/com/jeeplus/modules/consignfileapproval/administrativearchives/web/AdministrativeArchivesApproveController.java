/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.administrativearchives.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.websocket.service.system.SystemInfoSocketHandler;
import com.jeeplus.modules.consignfileapproval.administrativearchives.mapper.AdministrativeArchivesApproveMapper;
import com.jeeplus.modules.consignfileapproval.confileapproval.entity.ConsignFileApproval;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.mapper.OfficeMapper;
import com.jeeplus.modules.sys.utils.TimeUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import io.swagger.models.auth.In;
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
import com.jeeplus.modules.consignfileapproval.administrativearchives.entity.AdministrativeArchivesApprove;
import com.jeeplus.modules.consignfileapproval.administrativearchives.service.AdministrativeArchivesApproveService;

/**
 * 文书档案利用审批单Controller
 * @author chenl
 * @version 2019-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/consignfileapproval/administrativearchives/administrativeArchivesApprove")
public class AdministrativeArchivesApproveController extends BaseController {

	@Autowired
	private AdministrativeArchivesApproveService administrativeArchivesApproveService;
	@Autowired
	private AdministrativeArchivesApproveMapper as;
	@Autowired
	private OfficeMapper officeMapper;


	@ModelAttribute
	public AdministrativeArchivesApprove get(@RequestParam(required=false) String id) {
		AdministrativeArchivesApprove entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = administrativeArchivesApproveService.get(id);
		}
		if (entity == null){
			entity = new AdministrativeArchivesApprove();
		}
		return entity;
	}


	/**
	 * 首页档案申请弹出层
	 */
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = {"addlist"})
	public String addlist(AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/webaddlist";
	}

	/**
	 * 文书档案利用审批单列表页面
	 */
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = {"list", ""})
	public String list(AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			administrativeArchivesApprove.setUsePeople(UserUtils.getUser().getId());
		}
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/administrativeArchivesApproveList";
	}

	/**
	 * 文书档案利用审批单列表页面审核----
	 */
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = {"listCheck"})
	public String listCheck(AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/administrativeArchivesApproveListCheck";
	}



	/**
	 * 寄文书档案利用审批单审核通过或不通过-----不通过就是驳回
	 * ids   id
	 * state   审核状态 1待审核 2 通过  3不通过
	 * sta     区分是那个负责人审核的 1，单位负责人审核 ,2,事项涉及相关(可选,可多选) 3,资产保全部 (可选) 4,档案审核员,5，办公室负责人审核,6,行领导(可选) 7.完成 通过
     * 根据确认 事项涉及多选的有几人 所有步骤加choices
	 */

	@ResponseBody
	@RequestMapping(value = {"listCheckPassOfNoPass"})
	public AjaxJson listCheckPassOfNoPass(String ids, Model model,Integer state,Integer sta) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			AdministrativeArchivesApprove administrativeArchivesApprove = administrativeArchivesApproveService.get(id);
			//通过
			if (state==2){
				String remarks = administrativeArchivesApprove.getRemarks();
				String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"审核通过!";
				if(sta==3){ //原2
                    String mattersPrincipal = administrativeArchivesApprove.getMattersPrincipal();
                    if (mattersPrincipal.length()>0){
                        String[] split = mattersPrincipal.split(",");
                        if(administrativeArchivesApprove.getChoices()==split.length){
                            sta=sta+1; //多人审核完成
                        }

                    }else {
                        sta=sta+1; //无跳过
                    }

                }

                if (sta==4){

                    String assetPreservation = administrativeArchivesApprove.getAssetPreservation();
                    if (assetPreservation.length()<=0){ //无跳过
                        sta=sta+1;
                    }
                }

                if (sta==6){
                    String officeSupervisorLeader = administrativeArchivesApprove.getOfficeSupervisorLeader();
                    if (officeSupervisorLeader.length()<=0){ //无跳过
                        sta=sta+1;
                    }
                }



				as.updatePass(id,state,sta,remarkSave); //步骤1单位负责人审核
				j.setMsg("文书档案利用审批申请通过");
			}else if (state==3){ //不通过
				String remarks = administrativeArchivesApprove.getRemarks();
				String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"驳回  待重新发起!";
				sta = 8;
				as.updatePass(id,state,sta,remarkSave); //审核失败，不通过
				System.out.println("");
				j.setMsg("文书档案利用审批申请不通过");
			}else {
				j.setMsg("参数不匹配");
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
	 * 文书档案利用审批单列表页面统计----
	 */
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = {"listStatistics"})
	public String listStatistics(AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/administrativeArchivesApproveListStatistics";
	}
	
		/**
	 * 文书档案利用审批单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AdministrativeArchivesApprove administrativeArchivesApprove, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AdministrativeArchivesApprove> page = administrativeArchivesApproveService.findPage(new Page<AdministrativeArchivesApprove>(request, response), administrativeArchivesApprove);

		//message();
		return getBootstrapData(page);
	}


	@ResponseBody
//	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:list")
	@RequestMapping(value = "data2")
	public Map<String, Object> data2( HttpServletRequest request, HttpServletResponse response, Model model,
									  Integer stateStep,
									  @RequestParam(defaultValue = "",required = false) String beginTime,
									  @RequestParam(defaultValue = "",required = false) String endTime,
									  @RequestParam(defaultValue = "",required = false) String useDepartment,
									  @RequestParam(defaultValue = "",required = false) String withWay,
									  @RequestParam(defaultValue = "",required = false) String beginReturnTime,
									  @RequestParam(defaultValue = "",required = false) String endReturnTime,
									  @RequestParam(defaultValue = "",required = false) Integer state
	) {
		Page<AdministrativeArchivesApprove> page=new Page<>();
        page.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
        page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		String uid="";
		if(!UserUtils.getUser().getId().equals("1") && !UserUtils.getUser().getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
			uid=UserUtils.getUser().getId();
		}
		List<AdministrativeArchivesApprove> listConAll = as.findListConAll(uid,(page.getPageNo()-1)*page.getPageSize(),page.getPageSize()
				,stateStep
				,beginTime
				,endTime
				,useDepartment
				,withWay
				,beginReturnTime
				,endReturnTime
				,state

		);
        Integer listConAllCount = as.findListConAllCount(uid);
        page.setList(listConAll);
		page.setCount(listConAllCount==null?0:listConAllCount);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑文书档案利用审批单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"consignfileapproval:administrativearchives:administrativeArchivesApprove:view","consignfileapproval:administrativearchives:administrativeArchivesApprove:add","consignfileapproval:administrativearchives:administrativeArchivesApprove:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		model.addAttribute("mode", mode);
		if (mode.equals("add")){
			administrativeArchivesApprove.setUsePeople(UserUtils.getUser().getId());
			administrativeArchivesApprove.setUsePeopleName(UserUtils.getUser().getName());
			Office office = UserUtils.getUser().getOffice();
			administrativeArchivesApprove.setUseDepartmentName(office.getName());
			administrativeArchivesApprove.setUseDepartment(office.getId());
		}
        if (mode.equals("edit") && administrativeArchivesApprove.getStateStep()==8){
            String remarks = administrativeArchivesApprove.getRemarks();
            String remarkSave = remarks + " "+ TimeUtils.getTimeStr()+"--"+ UserUtils.getUser().getName()+"："+"重新发起申请!";
            administrativeArchivesApprove.setRemarks(remarkSave);
            administrativeArchivesApprove.setStateStep(1);
            administrativeArchivesApprove.setState(1);
            administrativeArchivesApprove.setChoices(0);
        }
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/administrativeArchivesApproveForm";
	}

	/**
	 * 新建文书档案利用审批单表单页面
	 * params:
	 *
	 */
	@RequestMapping(value = "Directly/{mode}")
	public String formDirectly(@PathVariable String mode, AdministrativeArchivesApprove administrativeArchivesApprove, Model model) {
		Office office = UserUtils.getUser().getOffice();
		administrativeArchivesApprove.setUseDepartmentName(office.getName());
		administrativeArchivesApprove.setUseDepartment(office.getId());
		model.addAttribute("mode", mode);
		model.addAttribute("administrativeArchivesApprove", administrativeArchivesApprove);
		return "modules/consignfileapproval/administrativearchives/administrativeArchivesApproveDirectlyForm";
	}

	/**
	 * 保存文书档案利用审批单
	 */
	@ResponseBody
	@RequiresPermissions(value={"consignfileapproval:administrativearchives:administrativeArchivesApprove:add","consignfileapproval:administrativearchives:administrativeArchivesApprove:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(AdministrativeArchivesApprove administrativeArchivesApprove, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(administrativeArchivesApprove);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		administrativeArchivesApproveService.save(administrativeArchivesApprove);//保存
		j.setSuccess(true);
		j.setMsg("保存文书档案利用审批单成功");
		return j;
	}

	
	/**
	 * 批量删除文书档案利用审批单
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			administrativeArchivesApproveService.delete(administrativeArchivesApproveService.get(id));
		}
		j.setMsg("删除文书档案利用审批单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(AdministrativeArchivesApprove administrativeArchivesApprove, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文书档案利用审批单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AdministrativeArchivesApprove> page = administrativeArchivesApproveService.findPage(new Page<AdministrativeArchivesApprove>(request, response, -1), administrativeArchivesApprove);
    		new ExportExcel("文书档案利用审批单", AdministrativeArchivesApprove.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出文书档案利用审批单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AdministrativeArchivesApprove> list = ei.getDataList(AdministrativeArchivesApprove.class);
			for (AdministrativeArchivesApprove administrativeArchivesApprove : list){
				try{
					administrativeArchivesApproveService.save(administrativeArchivesApprove);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文书档案利用审批单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条文书档案利用审批单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入文书档案利用审批单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入文书档案利用审批单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("consignfileapproval:administrativearchives:administrativeArchivesApprove:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文书档案利用审批单数据导入模板.xlsx";
    		List<AdministrativeArchivesApprove> list = Lists.newArrayList(); 
    		new ExportExcel("文书档案利用审批单数据", AdministrativeArchivesApprove.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }


    //发送消息
	@RequestMapping(value = "message")
	public void message(){
		SystemInfoSocketHandler systemInfoSocketHandler=new SystemInfoSocketHandler();
		systemInfoSocketHandler.sendMessageToUser("admin","发送消息，你收到了吗？");



	}



}