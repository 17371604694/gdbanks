/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.advertisingaudit.advertising.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 广告审核Entity
 * @author chenl
 * @version 2019-12-10
 */
public class AdvertisingAudit extends DataEntity<AdvertisingAudit> {
	
	private static final long serialVersionUID = 1L;
	private Date time;		// 时间
	private String applicantUnit;		// 申请单位
	private String applicantPerson;		// 申请人
	private String advertisingCentent;		// 广告内容
	private String advertisingFile;		// 文件
	private String unitPrincipal;	//单位负责人
	private String department;	//相关部门负责人
	private String advertisingAuditor;	//广告审核员

	private String offerPrincipal;		// 办公室负责人
	private String satrapPrincipal;		// 行领导
	private Integer state;		// 状态
	private Integer stateStep;		// 审核步骤
	private String offerPrincipalName;		// 办公室负责人 中文
	private String satrapPrincipalName;		// 行领导 中文
	private String unitPrincipalName;	//单位负责人 中文
	private String applicantPersonName;		// 申请人 中文
	private String departmentName;	//相关部门负责人  中文
	private String advertisingAuditorName;	//广告审核员  中文
	private Integer choices;		// 确认 多选到第几步



	private Date beginTime;		// 开始 时间
	private Date endTime;		// 结束 时间

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAdvertisingAuditor() {
		return advertisingAuditor;
	}

	public void setAdvertisingAuditor(String advertisingAuditor) {
		this.advertisingAuditor = advertisingAuditor;
	}

	public String getApplicantPersonName() {
		return applicantPersonName;
	}

	public void setApplicantPersonName(String applicantPersonName) {
		this.applicantPersonName = applicantPersonName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAdvertisingAuditorName() {
		return advertisingAuditorName;
	}

	public void setAdvertisingAuditorName(String advertisingAuditorName) {
		this.advertisingAuditorName = advertisingAuditorName;
	}

	public Integer getChoices() {
		return choices;
	}

	public void setChoices(Integer choices) {
		this.choices = choices;
	}

	
	public AdvertisingAudit() {
		super();
	}

	public AdvertisingAudit(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=2, sort=7)
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
	
	@ExcelField(title="广告内容", align=2, sort=10)
	public String getAdvertisingCentent() {
		return advertisingCentent;
	}

	public void setAdvertisingCentent(String advertisingCentent) {
		this.advertisingCentent = advertisingCentent;
	}
	
	@ExcelField(title="文件", align=2, sort=11)
	public String getAdvertisingFile() {
		return advertisingFile;
	}

	public void setAdvertisingFile(String advertisingFile) {
		this.advertisingFile = advertisingFile;
	}
	
	@ExcelField(title="办公室负责人", fieldType=String.class, value="", align=2, sort=12)
	public String getOfferPrincipal() {
		return offerPrincipal;
	}

	public void setOfferPrincipal(String offerPrincipal) {
		this.offerPrincipal = offerPrincipal;
	}
	
	@ExcelField(title="办公室主管行领导", fieldType=String.class, value="", align=2, sort=13)
	public String getSatrapPrincipal() {
		return satrapPrincipal;
	}

	public void setSatrapPrincipal(String satrapPrincipal) {
		this.satrapPrincipal = satrapPrincipal;
	}
	
	@ExcelField(title="状态", align=2, sort=14)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@ExcelField(title="审核步骤", align=2, sort=15)
	public Integer getStateStep() {
		return stateStep;
	}

	public void setStateStep(Integer stateStep) {
		this.stateStep = stateStep;
	}
	
	@ExcelField(title="办公室负责人 中文", align=2, sort=16)
	public String getOfferPrincipalName() {
		return offerPrincipalName;
	}

	public void setOfferPrincipalName(String offerPrincipalName) {
		this.offerPrincipalName = offerPrincipalName;
	}
	
	@ExcelField(title="办公室主管行领导 中文", align=2, sort=17)
	public String getSatrapPrincipalName() {
		return satrapPrincipalName;
	}

	public void setSatrapPrincipalName(String satrapPrincipalName) {
		this.satrapPrincipalName = satrapPrincipalName;
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

	public String getUnitPrincipal() {
		return unitPrincipal;
	}

	public void setUnitPrincipal(String unitPrincipal) {
		this.unitPrincipal = unitPrincipal;
	}

	public String getUnitPrincipalName() {
		return unitPrincipalName;
	}

	public void setUnitPrincipalName(String unitPrincipalName) {
		this.unitPrincipalName = unitPrincipalName;
	}
}