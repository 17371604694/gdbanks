/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.confileapproval.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 寄存档案出库申请Entity
 * @author chenl
 * @version 2019-12-02
 */
public class ConsignFileApproval extends DataEntity<ConsignFileApproval> {
	
	private static final long serialVersionUID = 1L;
	private Date time;		// 日期
	private String applicantUnit;		// 申请单位
	private String applicantPerson;		// 申请人id
	private String goalOfWork;		// 工作目的
	private String withTheWay;		// 用  途
	private String outboundContent;		// 出库内容
	private Integer quantity;		// 数 量
	private Date returnTime;		// 预计归还时间
	private String chargeApplicantPerson;		// 申请单位负责人 用户id
	private String materialAuditor;		// 档案审核员 用户id
	private String officeHead;		// 办公室负责人 用户id
	private String officeSupervisorLeader;		// 办公室主管行领导 id
	private Integer state;		// 审核状态
	private Integer stateStep;		//审核步骤 1，申请单位负责人审核，2，办公室负责人审核

	private Date beginTime;		// 开始 日期
	private Date endTime;		// 结束 日期
	private Date beginReturnTime;		// 开始 预计归还时间
	private Date endReturnTime;		// 结束 预计归还时间





	private String chargeApplicantPersonName;		// 申请单位负责人中文名
	private String materialAuditorName;		// 档案审核员 中文名
	private String officeHeadName;		// 办公室负责人 中文名
	private String officeSupervisorLeaderName;		// 办公室主管行领导 中文
	private String applicantPersonName;		// 申请人 中文

	public String getApplicantPersonName() {
		return applicantPersonName;
	}

	public void setApplicantPersonName(String applicantPersonName) {
		this.applicantPersonName = applicantPersonName;
	}

	public String getMaterialAuditor() {
		return materialAuditor;
	}

	public void setMaterialAuditor(String materialAuditor) {
		this.materialAuditor = materialAuditor;
	}

	public String getMaterialAuditorName() {
		return materialAuditorName;
	}

	public void setMaterialAuditorName(String materialAuditorName) {
		this.materialAuditorName = materialAuditorName;
	}



	public String getOfficeSupervisorLeader() {
		return officeSupervisorLeader;
	}

	public void setOfficeSupervisorLeader(String officeSupervisorLeader) {
		this.officeSupervisorLeader = officeSupervisorLeader;
	}

	public String getOfficeSupervisorLeaderName() {
		return officeSupervisorLeaderName;
	}

	public void setOfficeSupervisorLeaderName(String officeSupervisorLeaderName) {
		this.officeSupervisorLeaderName = officeSupervisorLeaderName;
	}



	public Integer getStateStep() {
		return stateStep;
	}

	public void setStateStep(Integer stateStep) {
		this.stateStep = stateStep;
	}

	public String getChargeApplicantPersonName() {
		return chargeApplicantPersonName;
	}

	public void setChargeApplicantPersonName(String chargeApplicantPersonName) {
		this.chargeApplicantPersonName = chargeApplicantPersonName;
	}

	public String getOfficeHeadName() {
		return officeHeadName;
	}

	public void setOfficeHeadName(String officeHeadName) {
		this.officeHeadName = officeHeadName;
	}


	public ConsignFileApproval() {
		super();
	}

	public ConsignFileApproval(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=7)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@ExcelField(title="申请单位", align=2, sort=8)
	public String getApplicantUnit() {
		return applicantUnit;
	}

	public void setApplicantUnit(String applicantUnit) {
		this.applicantUnit = applicantUnit;
	}
	
	@ExcelField(title="申请人", align=2, sort=9)
	public String getApplicantPerson() {
		return applicantPerson;
	}

	public void setApplicantPerson(String applicantPerson) {
		this.applicantPerson = applicantPerson;
	}
	
	@ExcelField(title="工作目的", dictType="workType", align=2, sort=10)
	public String getGoalOfWork() {
		return goalOfWork;
	}

	public void setGoalOfWork(String goalOfWork) {
		this.goalOfWork = goalOfWork;
	}
	
	@ExcelField(title="用  途", align=2, sort=11)
	public String getWithTheWay() {
		return withTheWay;
	}

	public void setWithTheWay(String withTheWay) {
		this.withTheWay = withTheWay;
	}
	
	@ExcelField(title="出库内容", align=2, sort=12)
	public String getOutboundContent() {
		return outboundContent;
	}

	public void setOutboundContent(String outboundContent) {
		this.outboundContent = outboundContent;
	}
	
	@ExcelField(title="数 量", align=2, sort=13)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="预计归还时间", align=2, sort=14)
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@ExcelField(title="申请单位负责人", align=2, sort=15)
	public String getChargeApplicantPerson() {
		return chargeApplicantPerson;
	}

	public void setChargeApplicantPerson(String chargeApplicantPerson) {
		this.chargeApplicantPerson = chargeApplicantPerson;
	}
	
	@ExcelField(title="办公室负责人", align=2, sort=16)
	public String getOfficeHead() {
		return officeHead;
	}

	public void setOfficeHead(String officeHead) {
		this.officeHead = officeHead;
	}
	
	@ExcelField(title="审核状态", align=2, sort=17)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
		
	public Date getBeginReturnTime() {
		return beginReturnTime;
	}

	public void setBeginReturnTime(Date beginReturnTime) {
		this.beginReturnTime = beginReturnTime;
	}
	
	public Date getEndReturnTime() {
		return endReturnTime;
	}

	public void setEndReturnTime(Date endReturnTime) {
		this.endReturnTime = endReturnTime;
	}
		
}