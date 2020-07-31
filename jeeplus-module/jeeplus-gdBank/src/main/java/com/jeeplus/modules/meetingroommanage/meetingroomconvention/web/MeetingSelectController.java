package com.jeeplus.modules.meetingroommanage.meetingroomconvention.web;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.MeetingTable;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.service.BankConferenceRoomReservationService;
import com.jeeplus.modules.sys.utils.TimeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 会议室查询Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/meetingroommanage/meetingselect/meetingselect")
public class MeetingSelectController extends BaseController {

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
     * 查看会议表格
     * @param meetingTable
     * @param model
     * @return
     */
    @RequiresPermissions("meetingroommanage:meetingroomconvention:bankConferenceRoomReservation:fromTable")
    @RequestMapping(value = {"fromTable", ""})
    public String list(MeetingTable meetingTable, Model model) {
        model.addAttribute("bankConferenceRoomReservation", meetingTable);
        return "modules/meetingroommanage/meetingselect/meetingselectList";
    }

    /**
     * 返回所有会议数据
     * @param time
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "dataAll")
    public List<MeetingTable> dataAll(String time, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<MeetingTable> list = bankConferenceRoomReservationService.findAllMeeting(time);
        return list;
    }

    /**
     * 返回所有表头数据（时间）
     * @param time
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "weekAll")
    public List<String> weekAll(String time, HttpServletRequest request, HttpServletResponse response, Model model) {
        return TimeUtils.getTimeList(time);//获取查询时间表头;
    }

}
