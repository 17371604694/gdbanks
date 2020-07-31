/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskissuedby.taskissuedbyts.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 任务下发Entity
 * @author chenl
 * @version 2019-12-20
 */
public class TaskIssuedBy extends DataEntity<TaskIssuedBy> {
	
	private static final long serialVersionUID = 1L;
	private String publisherId;		// 下发人id
	private String publisherName;		// 下发人姓名
	private String rname;		// 下发人姓名


	private Date startTime;		// 发起时间
	private String content;		// 内容
	private Date endTime;		// 结束时间
	private String executorId;		// 执行人id
	private String executorName;		// 执行人名称
	private Integer state;		// 状态
	private String explains;		// 说明
	private String file;		// 文件
	private Date beginStartTime;		// 开始 发起时间
	private Date endStartTime;		// 结束 发起时间
	private Date beginEndTime;		// 开始 结束时间
	private Date endEndTime;		// 结束 结束时间
	
	public TaskIssuedBy() {
		super();
	}



	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}

	public TaskIssuedBy(String id){
		super(id);
	}

	@ExcelField(title="下发人id", align=2, sort=7)
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	
	@ExcelField(title="下发人姓名", align=2, sort=8)
	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发起时间", align=2, sort=9)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	@ExcelField(title="内容", align=2, sort=10)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="结束时间", align=2, sort=11)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@ExcelField(title="执行人id", fieldType=String.class, value="", align=2, sort=12)
	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	
	@ExcelField(title="执行人名称", align=2, sort=13)
	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	
	@ExcelField(title="状态", align=2, sort=14)
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	@ExcelField(title="说明", align=2, sort=15)
	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}
	
	@ExcelField(title="文件", align=2, sort=16)
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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