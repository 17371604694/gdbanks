/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.programatcontent.programatcont.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;
import java.util.List;

/**
 * 发稿内容Entity
 * @author chenl
 * @version 2019-11-22
 */
public class DistributeContent extends DataEntity<DistributeContent> {
	
	private static final long serialVersionUID = 1L;
	private String programatId;		// 栏目
	private String programatParentid;		// 副栏目
	private String title;		// 标题
	private String tagTitle;		// 是否置顶（管理员0是1否）
	private Integer stick;		// 是否置顶（发稿人）
	private Integer sorts;		//是否排序（0 是1否）
	private Integer contentType;		// 内容类型
	private String accessorys;		// 附件
	private String content;		// 内容
	private String reservec;		// 预留(栏目审核人id) 副栏目审核人id放在了remakes里
	private String departmentReviewer;		// (部门审核人id)
	private int statepid;   //栏目审核当前状态
	private int stateparentid;    //栏目最终审核状态
	private String datepm;  //栏目中文名称
	private String dateparentm; //副栏目中文名称
	private String author;  //作者
	private String loginid;//登录用户id
	private String fileName;//附件名
	private String officeName;//部门名称
	private String officeId;//部门id
	private Integer onclickNum;//查看量
	private String accessorysImg; //视频缩略图
	private List programatIdList;//审核栏目id集合
	private Date meetingTime;//(会议通知栏目里面的会议时间)
	private int stepSum;  //共几步
	protected Date beginTime;//开始时间
	protected Date endTime;//结束时间
	private String programatIdName;  //临时显示的栏目名称
	private String programatParentidName; //临时显示的副栏目栏目名称(弃用,由副栏目有多个,无法使用子查询单查)
	private String departmentReviewerName; //临时显示的部门审核人名称
	private String programatParentidNameList; //显示的栏目名称

	@JsonFormat(pattern = "MM-dd")
	public Date getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getLoginid() {
		return loginid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getProgramatParentidNameList() {
        return programatParentidNameList;
    }

    public void setProgramatParentidNameList(String programatParentidNameList) {
        this.programatParentidNameList = programatParentidNameList;
    }

    public String getDepartmentReviewerName() {
		return departmentReviewerName;
	}

	public void setDepartmentReviewerName(String departmentReviewerName) {
		this.departmentReviewerName = departmentReviewerName;
	}

	public List getProgramatIdList() {
		return programatIdList;
	}

	public void setProgramatIdList(List programatIdList) {
		this.programatIdList = programatIdList;
	}

	public String getAccessorysImg() {
		return accessorysImg;
	}

	public void setAccessorysImg(String accessorysImg) {
		this.accessorysImg = accessorysImg;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getDepartmentReviewer() {
		return departmentReviewer;
	}

	public void setDepartmentReviewer(String departmentReviewer) {
		this.departmentReviewer = departmentReviewer;
	}

	public Integer getSorts() {
		return sorts;
	}

	public void setSorts(Integer sorts) {
		this.sorts = sorts;
	}

	public Integer getOnclickNum() {
		return onclickNum;
	}

	public void setOnclickNum(Integer onclickNum) {
		this.onclickNum = onclickNum;
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

	public int getStepSum() {
		return stepSum;
	}

	public void setStepSum(int stepSum) {
		this.stepSum = stepSum;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDatepm() {
		return datepm;
	}

	public void setDatepm(String datepm) {
		this.datepm = datepm;
	}

	public String getDateparentm() {
		return dateparentm;
	}

	public void setDateparentm(String dateparentm) {
		this.dateparentm = dateparentm;
	}

	public int getStatepid() {
		return statepid;
	}

	public void setStatepid(int statepid) {
		this.statepid = statepid;
	}

	public int getStateparentid() {
		return stateparentid;
	}

	public void setStateparentid(int stateparentid) {
		this.stateparentid = stateparentid;
	}



	public String getProgramatIdName() {
		return programatIdName;
	}

	public void setProgramatIdName(String programatIdName) {
		this.programatIdName = programatIdName;
	}

	public String getProgramatParentidName() {
		return programatParentidName;
	}

	public void setProgramatParentidName(String programatParentidName) {
		this.programatParentidName = programatParentidName;
	}

	public DistributeContent() {
		super();
	}

	public DistributeContent(String id){
		super(id);
	}

	@ExcelField(title="栏目", align=2, sort=7)
	public String getProgramatId() {
		return programatId;
	}

	public void setProgramatId(String programatId) {
		this.programatId = programatId;
	}
	
	@ExcelField(title="副栏目", align=2, sort=8)
	public String getProgramatParentid() {
		return programatParentid;
	}

	public void setProgramatParentid(String programatParentid) {
		this.programatParentid = programatParentid;
	}
	
	@ExcelField(title="标题", align=2, sort=9)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="tag标签", align=2, sort=10)
	public String getTagTitle() {
		return tagTitle;
	}

	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}
	
	@ExcelField(title="是否固定", dictType="fixation", align=2, sort=11)
	public Integer getStick() {
		return stick;
	}

	public void setStick(Integer stick) {
		this.stick = stick;
	}
	
	@ExcelField(title="内容类型", dictType="contentType", align=2, sort=12)
	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	
	@ExcelField(title="附件", align=2, sort=13)
	public String getAccessorys() {
		return accessorys;
	}

	public void setAccessorys(String accessorys) {
		this.accessorys = accessorys;
	}
	
	@ExcelField(title="内容", align=2, sort=14)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="预留", align=2, sort=15)
	public String getReservec() {
		return reservec;
	}

	public void setReservec(String reservec) {
		this.reservec = reservec;
	}

}