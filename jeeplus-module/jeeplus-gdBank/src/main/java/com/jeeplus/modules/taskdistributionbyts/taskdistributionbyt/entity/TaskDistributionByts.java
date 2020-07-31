/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 任务下发Entity
 * @author chenl
 * @version 2020-05-13
 */
public class TaskDistributionByts extends DataEntity<TaskDistributionByts> {
	
	private static final long serialVersionUID = 1L;
	private String publisherId;		// 任务下发人id
	private String publisherName;		// 下发人名称
	private Date startTime;		// 发起时间
	private Date endTime;		// 结束时间
	private String taskName;		// 任务名称
	private String content;		// 内容
	private String file;		// 模板文件
	private Integer state;		// 状态
	private String taskIds;		// 执行人id
	private Date beginStartTime;		// 开始 发起时间
	private Date endStartTime;		// 结束 发起时间
	private Date beginEndTime;		// 开始 结束时间
	private Date endEndTime;		// 结束 结束时间
	
	public TaskDistributionByts() {
		super();
	}

	public TaskDistributionByts(String id){
		super(id);
	}

	@ExcelField(title="任务下发人id", align=2, sort=7)
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	
	@ExcelField(title="下发人名称", align=2, sort=8)
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发起时间不能为空")
	@ExcelField(title="发起时间", align=2, sort=9)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=10)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="任务名称", align=2, sort=11)
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@ExcelField(title="内容", align=2, sort=12)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="模板文件", align=2, sort=13)
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	@ExcelField(title="状态", align=2, sort=14)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@ExcelField(title="执行人id", fieldType=String.class, value="", align=2, sort=15)
	public String getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}
	
	public Date getBeginStartTime() {
		return beginStartTime;
	}

	public void setBeginStartTime(Date beginStartTime) {
		this.beginStartTime = beginStartTime;
	}
	
	public Date getEndStartTime() {
		return endStartTime;
	}

	public void setEndStartTime(Date endStartTime) {
		this.endStartTime = endStartTime;
	}
		
	public Date getBeginEndTime() {
		return beginEndTime;
	}

	public void setBeginEndTime(Date beginEndTime) {
		this.beginEndTime = beginEndTime;
	}
	
	public Date getEndEndTime() {
		return endEndTime;
	}

	public void setEndEndTime(Date endEndTime) {
		this.endEndTime = endEndTime;
	}
		
}