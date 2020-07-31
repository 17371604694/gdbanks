/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity.TaskDistributionByts;

/**
 * 执行人Entity
 * @author chenl
 * @version 2020-05-13
 */
public class TaskDistributionBytsFit extends DataEntity<TaskDistributionBytsFit> {
	
	private static final long serialVersionUID = 1L;
	private String executorId;		// 执行人id
	private String executorName;		// 执行人名称
	private String executorFile;		// 执行文件
	private String executorGive;		// 执行说明
	private Date executorTime;		// 执行时间
	private String taskDistributionBytsId;		// 主表id


	private Integer state; //默认1 未执行,2已执行

	private TaskDistributionByts taskDistributionByts;  //主表信息


	public TaskDistributionByts getTaskDistributionByts() {
		return taskDistributionByts;
	}

	public void setTaskDistributionByts(TaskDistributionByts taskDistributionByts) {
		this.taskDistributionByts = taskDistributionByts;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	private Date beginExecutorTime;		// 开始 执行时间
	private Date endExecutorTime;		// 结束 执行时间
	
	public TaskDistributionBytsFit() {
		super();
	}

	public TaskDistributionBytsFit(String id){
		super(id);
	}

	@ExcelField(title="执行人id", align=2, sort=7)
	public String getExecutorId() {
		return executorId;
	}

	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	
	@ExcelField(title="执行人名称", align=2, sort=8)
	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	
	@ExcelField(title="执行文件", align=2, sort=9)
	public String getExecutorFile() {
		return executorFile;
	}

	public void setExecutorFile(String executorFile) {
		this.executorFile = executorFile;
	}
	
	@ExcelField(title="执行说明", align=2, sort=10)
	public String getExecutorGive() {
		return executorGive;
	}

	public void setExecutorGive(String executorGive) {
		this.executorGive = executorGive;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="执行时间", align=2, sort=11)
	public Date getExecutorTime() {
		return executorTime;
	}

	public void setExecutorTime(Date executorTime) {
		this.executorTime = executorTime;
	}
	
	@ExcelField(title="主表id", align=2, sort=12)
	public String getTaskDistributionBytsId() {
		return taskDistributionBytsId;
	}

	public void setTaskDistributionBytsId(String taskDistributionBytsId) {
		this.taskDistributionBytsId = taskDistributionBytsId;
	}
	
	public Date getBeginExecutorTime() {
		return beginExecutorTime;
	}

	public void setBeginExecutorTime(Date beginExecutorTime) {
		this.beginExecutorTime = beginExecutorTime;
	}
	
	public Date getEndExecutorTime() {
		return endExecutorTime;
	}

	public void setEndExecutorTime(Date endExecutorTime) {
		this.endExecutorTime = endExecutorTime;
	}
		
}