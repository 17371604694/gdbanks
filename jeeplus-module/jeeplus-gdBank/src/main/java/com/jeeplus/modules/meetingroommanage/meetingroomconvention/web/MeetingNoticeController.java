package com.jeeplus.modules.meetingroommanage.meetingroomconvention.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.service.BankConferenceRoomReservationService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 会议通知controller
 */
@Controller
@RequestMapping(value = "${adminPath}/meetingroommanage/meetingroomconvention/meetingnotice")
public class MeetingNoticeController extends BaseController {

    //会议预定service
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
     * 会议通知列表页面
     */
    @RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:list")
    @RequestMapping(value = {"list", ""})
    public String list(BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
        model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
        return "modules/meetingroommanage/meetingnotice/bankConferenceNoticList";
    }

    /**
     * 会议通知列表数据
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
            String loginOfficeId = UserUtils.getUser().getOffice().getId();
            bankConferenceRoomReservation.setHostDept(loginOfficeId);
        }
        bankConferenceRoomReservation.setIsNoticeMeeting("1");//设置查询条件
        Page<BankConferenceRoomReservation> page = bankConferenceRoomReservationService.findPage(new Page<BankConferenceRoomReservation>(request, response), bankConferenceRoomReservation);
        return getBootstrapData(page);
    }

    /**
     * 查看，增加，编辑会议通知表单页面
     * params:
     * 	mode: add, edit, view, 代表三种模式的页面
     */
    @RequiresPermissions(value={"meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:view","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:add","meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:edit"},logical= Logical.OR)
    @RequestMapping(value = "form/{mode}")
    public String form(@PathVariable String mode, BankConferenceRoomReservation bankConferenceRoomReservation, Model model) {
        model.addAttribute("mode", mode);
        model.addAttribute("bankConferenceRoomReservation", bankConferenceRoomReservation);
        return "modules/meetingroommanage/meetingnotice/bankConferenceNoticForm";
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
            if(!bankConferenceRoomReservationService.isAppointment(bankConferenceRoomReservation)){
                String errMsg = beanValidator(bankConferenceRoomReservation);
                if (StringUtils.isNotBlank(errMsg)){
                    j.setSuccess(false);
                    j.setMsg(errMsg);
                    return j;
                }
                //新增或编辑表单保存
                bankConferenceRoomReservation.setIsNoticeMeeting("1");
                bankConferenceRoomReservationService.save(bankConferenceRoomReservation);//保存
                j.setSuccess(true);
                j.setMsg("保存会议室预定成功");
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
     *批量取消会议通知
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
        j.setMsg("取消会议通知成功");
        return j;
    }
}
