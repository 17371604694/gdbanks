/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.vote.vote.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 投票Entity
 * @author cheny
 * @version 2020-01-17
 */
public class BankVote extends DataEntity<BankVote> {
	
	private static final long serialVersionUID = 1L;
	private String themeName;		// 主题名
	private String selectId;		// 选项id
	private Date beginTimes;		// 投票开始时间
	private Date endTimes;		// 投票截止时间
	private String selectResult;		// 选出结果
	private Date beginBeginTimes;		// 开始 投票开始时间
	private Date endBeginTimes;		// 结束 投票开始时间
	private Date beginEndTimes;		// 开始 投票截止时间
	private Date endEndTimes;		// 结束 投票截止时间
	private String img;		// 图片
	private String bankVoteId;		// 主题id
	private String video;		// 视频
	private String describe;		// 描述
	private String content;		// 正文
	private Integer voteNum;  //投票数
	private String votePreson; //投票人
//	private String selectid;//选项iD
	
	public BankVote() {
		super();
	}

	public BankVote(String id){
		super(id);
	}

//	public String getSelectid() {
//		return selectid;
//	}
//
//	public void setSelectid(String selectid) {
//		this.selectid = selectid;
//	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getBankVoteId() {
		return bankVoteId;
	}

	public void setBankVoteId(String bankVoteId) {
		this.bankVoteId = bankVoteId;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}

	public String getVotePreson() {
		return votePreson;
	}

	public void setVotePreson(String votePreson) {
		this.votePreson = votePreson;
	}

	@ExcelField(title="主题名", align=2, sort=7)
	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	
	@ExcelField(title="选项", dictType="", align=2, sort=8)
	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="投票开始时间不能为空")
	@ExcelField(title="投票开始时间", align=2, sort=9)
	public Date getBeginTimes() {
		return beginTimes;
	}

	public void setBeginTimes(Date beginTimes) {
		this.beginTimes = beginTimes;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="投票截止时间不能为空")
	@ExcelField(title="投票截止时间", align=2, sort=10)
	public Date getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(Date endTimes) {
		this.endTimes = endTimes;
	}
	
	@ExcelField(title="选出结果", align=2, sort=11)
	public String getSelectResult() {
		return selectResult;
	}

	public void setSelectResult(String selectResult) {
		this.selectResult = selectResult;
	}
	
	public Date getBeginBeginTimes() {
		return beginBeginTimes;
	}

	public void setBeginBeginTimes(Date beginBeginTimes) {
		this.beginBeginTimes = beginBeginTimes;
	}
	
	public Date getEndBeginTimes() {
		return endBeginTimes;
	}

	public void setEndBeginTimes(Date endBeginTimes) {
		this.endBeginTimes = endBeginTimes;
	}
		
	public Date getBeginEndTimes() {
		return beginEndTimes;
	}

	public void setBeginEndTimes(Date beginEndTimes) {
		this.beginEndTimes = beginEndTimes;
	}
	
	public Date getEndEndTimes() {
		return endEndTimes;
	}

	public void setEndEndTimes(Date endEndTimes) {
		this.endEndTimes = endEndTimes;
	}
		
}