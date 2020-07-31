/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.administrativearchives.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 文书档案利用审批单Entity
 * @author chenl
 * @version 2019-12-02
 */
public class AdministrativeArchivesApprove extends DataEntity<AdministrativeArchivesApprove> {
	
	private static final long serialVersionUID = 1L;
	private Date time;		// 日期
	private String useDepartment;		// 利用部门 id
	private String usePeople;		// 利用人
	private String useContent;		// 利用内容
	private String denseLevel;		// 密  级
	private String usingPurpose;		// 利用目的
	private String withWay;		// 用  途
	private String usePattern;		// 利用方式
	private Integer quantity;		// 数 量
	private Date returnTime;		// 外借档案退还时间
	private String  utilizationUnit;		// 利用单位负责人 id
	private String mattersPrincipal;		// 事项涉及的相关归口管理部门负责人 id
	private String assetPreservation;		// 资产保全部负责人（诉讼调取档案需） id
	private String materialAuditor;		// 档案审核员 id
	private String officeHead;		// 办公室负责人 id
	private String officeSupervisorLeader;		// 办公室主管行领导 id
	private Integer state;		// 状态
	private Date beginTime;		// 开始 日期




	private Date endTime;		// 结束 日期
	private Date beginReturnTime;		// 开始 外借档案退还时间


	private Date endReturnTime;		// 结束 外借档案退还时间

	private String useDepartmentName;		// 利用部门 中文名

	private String utilizationUnitName;		// 利用单位负责人 中文名
	private String mattersPrincipalName;		// 事项涉及的相关归口管理部门负责人 中文名
	private String assetPreservationName;		// 资产保全部负责人（诉讼调取档案需） 中文名
	private String officeHeadName;		// 办公室负责人 中文名
	private String officeSupervisorLeaderName;		// 办公室主管行领导 中文名
	private Integer stateStep;		// 审核步骤
	private String usePeopleName;		// 利用人 中文名
	private String materialAuditorName;		// 档案审核员 中文名
	private Integer choices;		// 确认 事项涉及多选的有几人

	public Integer getChoices() {
		return choices;
	}

	public void setChoices(Integer choices) {
		this.choices = choices;
	}

	public String getMaterialAuditor() {
		return materialAuditor;
	}

	public void setMaterialAuditor(String materialAuditor) {
		this.materialAuditor = materialAuditor;
	}

	public String getUsePeopleName() {
		return usePeopleName;
	}

	public void setUsePeopleName(String usePeopleName) {
		this.usePeopleName = usePeopleName;
	}

	public String getMaterialAuditorName() {
		return materialAuditorName;
	}

	public void setMaterialAuditorName(String materialAuditorName) {
		this.materialAuditorName = materialAuditorName;
	}




	public Integer getStateStep() {
		return stateStep;
	}

	public void setStateStep(Integer stateStep) {
		this.stateStep = stateStep;
	}

	public String getUseDepartmentName() {
		return useDepartmentName;
	}

	public void setUseDepartmentName(String useDepartmentName) {
		this.useDepartmentName = useDepartmentName;
	}


	public String getUtilizationUnitName() {
		return utilizationUnitName;
	}

	public void setUtilizationUnitName(String utilizationUnitName) {
		this.utilizationUnitName = utilizationUnitName;
	}

	public String getMattersPrincipalName() {
		return mattersPrincipalName;
	}

	public void setMattersPrincipalName(String mattersPrincipalName) {
		this.mattersPrincipalName = mattersPrincipalName;
	}

	public String getAssetPreservationName() {
		return assetPreservationName;
	}

	public void setAssetPreservationName(String assetPreservationName) {
		this.assetPreservationName = assetPreservationName;
	}

	public String getOfficeHeadName() {
		return officeHeadName;
	}

	public void setOfficeHeadName(String officeHeadName) {
		this.officeHeadName = officeHeadName;
	}

	public String getOfficeSupervisorLeaderName() {
		return officeSupervisorLeaderName;
	}

	public void setOfficeSupervisorLeaderName(String officeSupervisorLeaderName) {
		this.officeSupervisorLeaderName = officeSupervisorLeaderName;
	}

	public AdministrativeArchivesApprove() {
		super();
	}

	public AdministrativeArchivesApprove(String id){
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
	
	@ExcelField(title="利用部门", fieldType=String.class, value="", align=2, sort=8)
	public String getUseDepartment() {
		return useDepartment;
	}

	public void setUseDepartment(String useDepartment) {
		this.useDepartment = useDepartment;
	}
	
	@ExcelField(title="利用人", align=2, sort=9)
	public String getUsePeople() {
		return usePeople;
	}

	public void setUsePeople(String usePeople) {
		this.usePeople = usePeople;
	}
	
	@ExcelField(title="利用内容", align=2, sort=10)
	public String getUseContent() {
		return useContent;
	}

	public void setUseContent(String useContent) {
		this.useContent = useContent;
	}
	
	@ExcelField(title="密  级", dictType="securityType", align=2, sort=11)
	public String getDenseLevel() {
		return denseLevel;
	}

	public void setDenseLevel(String denseLevel) {
		this.denseLevel = denseLevel;
	}
	
	@ExcelField(title="利用目的", dictType="workType", align=2, sort=12)
	public String getUsingPurpose() {
		return usingPurpose;
	}

	public void setUsingPurpose(String usingPurpose) {
		this.usingPurpose = usingPurpose;
	}
	
	@ExcelField(title="用  途", align=2, sort=13)
	public String getWithWay() {
		return withWay;
	}

	public void setWithWay(String withWay) {
		this.withWay = withWay;
	}
	
	@ExcelField(title="利用方式", dictType="takeType", align=2, sort=14)
	public String getUsePattern() {
		return usePattern;
	}

	public void setUsePattern(String usePattern) {
		this.usePattern = usePattern;
	}
	
	@ExcelField(title="数 量", align=2, sort=15)
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="外借档案退还时间", align=2, sort=16)
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@ExcelField(title="利用单位负责人", align=2, sort=17)
	public String getUtilizationUnit() {
		return  utilizationUnit;
	}

	public void setUtilizationUnit(String  utilizationUnit) {
		this.utilizationUnit =  utilizationUnit;
	}
	
	@ExcelField(title="事项涉及的相关归口管理部门负责人", align=2, sort=18)
	public String getMattersPrincipal() {
		return mattersPrincipal;
	}

	public void setMattersPrincipal(String mattersPrincipal) {
		this.mattersPrincipal = mattersPrincipal;
	}
	
	@ExcelField(title="资产保全部负责人（诉讼调取档案需）", align=2, sort=19)
	public String getAssetPreservation() {
		return assetPreservation;
	}

	public void setAssetPreservation(String assetPreservation) {
		this.assetPreservation = assetPreservation;
	}
	
	@ExcelField(title="办公室负责人", align=2, sort=20)
	public String getOfficeHead() {
		return officeHead;
	}

	public void setOfficeHead(String officeHead) {
		this.officeHead = officeHead;
	}
	
	@ExcelField(title="办公室主管行领导", align=2, sort=21)
	public String getOfficeSupervisorLeader() {
		return officeSupervisorLeader;
	}

	public void setOfficeSupervisorLeader(String officeSupervisorLeader) {
		this.officeSupervisorLeader = officeSupervisorLeader;
	}
	
	@ExcelField(title="状态", align=2, sort=22)
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