package com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * 会议室查询实体类
 */
public class MeetingTable extends DataEntity<MeetingTable> {
    private static final long serialVersionUID = 1L;
    private String conferenceTheme;		// 会议主题
    private String hostDept;		// 主办部门
    private Date beginTime;		// 开始时间
    private String conferenceRoom;		// 会议室
    private Date endTime;		// 结束时间
    private String weekAndDataStr;//data格式转化成星期+日期的格式传回页面
    private String meetingTimeStr;//会议时间，字符串形式
    private String conferenceContactPreson;

    public MeetingTable() {
        super();
    }

    public MeetingTable(String id){
        super(id);
    }

    public String getConferenceContactPreson() {
        return conferenceContactPreson;
    }

    public void setConferenceContactPreson(String conferenceContactPreson) {
        this.conferenceContactPreson = conferenceContactPreson;
    }

    public String getConferenceTheme() {
        return conferenceTheme;
    }

    public void setConferenceTheme(String conferenceTheme) {
        this.conferenceTheme = conferenceTheme;
    }

    public String getHostDept() {
        return hostDept;
    }

    public void setHostDept(String hostDept) {
        this.hostDept = hostDept;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBeginTime() {
        return beginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getConferenceRoom() {
        return conferenceRoom;
    }

    public void setConferenceRoom(String conferenceRoom) {
        this.conferenceRoom = conferenceRoom;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWeekAndDataStr() {
        return weekAndDataStr;
    }

    public void setWeekAndDataStr(String weekAndDataStr) {
        this.weekAndDataStr = weekAndDataStr;
    }

    public String getMeetingTimeStr() {
        return meetingTimeStr;
    }

    public void setMeetingTimeStr(String meetingTimeStr) {
        this.meetingTimeStr = meetingTimeStr;
    }

    //    public List<String> getTimeList() {
//        return timeList;
//    }
//
//    public void setTimeList(List<String> timeList) {
//        this.timeList = timeList;
//    }

    //    public String getBeginTimeStr() {
//        return beginTimeStr;
//    }
//
//    public void setBeginTimeStr(String beginTimeStr) {
//        this.beginTimeStr = beginTimeStr;
//    }
//
//    public String getEndTimeStr() {
//        return endTimeStr;
//    }
//
//    public void setEndTimeStr(String endTimeStr) {
//        this.endTimeStr = endTimeStr;
//    }
}
