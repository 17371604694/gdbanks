/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

import java.util.Date;

/**
 * 会议管理Entity
 * @author cheny
 * @version 2019-11-19
 */
public class BankConferenceRoomReservation extends DataEntity<BankConferenceRoomReservation> {
	
	private static final long serialVersionUID = 1L;
	private String conferenceTheme;		// 会议主题
	private String hostDept;		// 主办部门ID
	private String conferenceContactPreson;		// 会议联系人
	private String conferenceContactPhone;		// 联系电话
	private String conferenceRoom;		// 会议室
	private Date beginTime;		// 开始时间
	private Date endTime;		// 结束时间
	private String conferenceType;		// 会议类型
	private String conferencePreson;		// 参会人员
	private String conferencePresonId; // 参会人员ID
	private String accessory;		// 附件
	private String context;		// 会议内容
	private String conferenceDemand;		// 会议需求
	private String applicantId;		// 创建人关联用户id
	private String deptId;		// 创建人部门关联部门id
	private String hostDeptName;	// 主办部门名称
	private String isNoticeMeeting;//是否是会议通知

	public BankConferenceRoomReservation() {
		super();
	}

	public BankConferenceRoomReservation(String id){
		super(id);
	}

	public String getIsNoticeMeeting() {
		return isNoticeMeeting;
	}

	public void setIsNoticeMeeting(String isNoticeMeeting) {
		this.isNoticeMeeting = isNoticeMeeting;
	}

	public String getConferencePresonId() {
		return conferencePresonId;
	}

	public void setConferencePresonId(String conferencePresonId) {
		this.conferencePresonId = conferencePresonId;
	}

	@ExcelField(title="会议主题", align=2, sort=7)
	public String getConferenceTheme() {
		return conferenceTheme;
	}

	public void setConferenceTheme(String conferenceTheme) {
		this.conferenceTheme = conferenceTheme;
	}
	
	@ExcelField(title="主办部门", align=2, sort=8)
	public String getHostDept() {
		return hostDept;
	}

	public void setHostDept(String hostDept) {
		this.hostDept = hostDept;
	}
	
	@ExcelField(title="会议联系人", align=2, sort=9)
	public String getConferenceContactPreson() {
		return conferenceContactPreson;
	}

	public void setConferenceContactPreson(String conferenceContactPreson) {
		this.conferenceContactPreson = conferenceContactPreson;
	}
	
	@ExcelField(title="联系电话", align=2, sort=10)
	public String getConferenceContactPhone() {
		return conferenceContactPhone;
	}

	public void setConferenceContactPhone(String conferenceContactPhone) {
		this.conferenceContactPhone = conferenceContactPhone;
	}
	
	@ExcelField(title="会议室", dictType="", align=2, sort=11)
	public String getConferenceRoom() {
		return conferenceRoom;
	}

	public void setConferenceRoom(String conferenceRoom) {
		this.conferenceRoom = conferenceRoom;
	}
	
	@ExcelField(title="开始时间", align=2, sort=12)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@ExcelField(title="结束时间", align=2, sort=13)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="会议类型", dictType="", align=2, sort=14)
	public String getConferenceType() {
		return conferenceType;
	}

	public void setConferenceType(String conferenceType) {
		this.conferenceType = conferenceType;
	}
	
	@ExcelField(title="参会人员", fieldType=String.class, value="", align=2, sort=15)
	public String getConferencePreson() {
		return conferencePreson;
	}

	public void setConferencePreson(String conferencePreson) {
		this.conferencePreson = conferencePreson;
	}
	
	@ExcelField(title="附件", align=2, sort=16)
	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}
	
	@ExcelField(title="会议内容", align=2, sort=17)
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	@ExcelField(title="会议需求", align=2, sort=18)
	public String getConferenceDemand() {
		return conferenceDemand;
	}

	public void setConferenceDemand(String conferenceDemand) {
		this.conferenceDemand = conferenceDemand;
	}
	
	@ExcelField(title="申请人", align=2, sort=19)
	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	
	@ExcelField(title="申请人部门", align=2, sort=20)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getHostDeptName() {
		return hostDeptName;
	}

	public void setHostDeptName(String hostDeptName) {
		this.hostDeptName = hostDeptName;
	}
}