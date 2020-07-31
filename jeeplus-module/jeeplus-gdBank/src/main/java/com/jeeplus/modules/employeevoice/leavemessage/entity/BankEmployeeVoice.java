/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.employeevoice.leavemessage.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工心声Entity
 * @author cheny
 * @version 2019-12-18
 */
public class BankEmployeeVoice extends DataEntity<BankEmployeeVoice> {
	
	private static final long serialVersionUID = 1L;
	private String leaveMessage;		// 留言
	private String reply;		// 回复
	private String leaveMessageUserId;		// 留言人id
	private String replyUserId;		// 回复人id
	private Date replyTime;		// 回复时间
	private String replyName;		// 回复人姓名
	private String leaveMessageName;		// 留言人姓名
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	private String isReply;//是否回复
	private String employeeVoiceReplyId;//回复内容id
	private String replyB;//回复内容
	private String replyNameB;//回复人名称
	private String replyTimeB;//回复时间
	private String replyUserIdB;//回复人id
	private String type;//留言类型（2为建言献策）
	private String isShow;//是否显示真实姓名
	private String replyDept;//回复部门
	private String replyDeptName;//回复部门名称
	private String isAllowReply;//允许回复的人

	public BankEmployeeVoice() {
		super();
	}

	public BankEmployeeVoice(String id){
		super(id);
	}

	public String getAllowReply() {
		return isAllowReply;
	}

	public void setAllowReply(String allowReply) {
		isAllowReply = allowReply;
	}

	public String getReplyDeptName() {
		return replyDeptName;
	}

	public void setReplyDeptName(String replyDeptName) {
		this.replyDeptName = replyDeptName;
	}

	public String getReplyDept() {
		return replyDept;
	}

	public void setReplyDept(String replyDept) {
		this.replyDept = replyDept;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getReplyB() {
		return replyB;
	}

	public void setReplyB(String replyB) {
		this.replyB = replyB;
	}

	public String getReplyNameB() {
		return replyNameB;
	}

	public void setReplyNameB(String replyNameB) {
		this.replyNameB = replyNameB;
	}

	public String getReplyTimeB() {
		return replyTimeB;
	}

	public void setReplyTimeB(String replyTimeB) {
		this.replyTimeB = replyTimeB;
	}

	public String getReplyUserIdB() {
		return replyUserIdB;
	}

	public void setReplyUserIdB(String replyUserIdB) {
		this.replyUserIdB = replyUserIdB;
	}

	public String getEmployeeVoiceReplyId() {
		return employeeVoiceReplyId;
	}

	public void setEmployeeVoiceReplyId(String employeeVoiceReplyId) {
		this.employeeVoiceReplyId = employeeVoiceReplyId;
	}

	@ExcelField(title="留言", align=2, sort=7)
	public String getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}
	
	@ExcelField(title="回复", align=2, sort=8)
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	@ExcelField(title="留言人id", fieldType=String.class, value="", align=2, sort=9)
	public String getLeaveMessageUserId() {
		return leaveMessageUserId;
	}

	public void setLeaveMessageUserId(String leaveMessageUserId) {
		this.leaveMessageUserId = leaveMessageUserId;
	}
	
	@ExcelField(title="回复人id", fieldType=String.class, value="", align=2, sort=10)
	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="回复时间不能为空")
	@ExcelField(title="回复时间", align=2, sort=11)
	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	
	@ExcelField(title="回复人姓名", align=2, sort=12)
	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	
	@ExcelField(title="留言人姓名", align=2, sort=13)
	public String getLeaveMessageName() {
		return leaveMessageName;
	}

	public void setLeaveMessageName(String leaveMessageName) {
		this.leaveMessageName = leaveMessageName;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}