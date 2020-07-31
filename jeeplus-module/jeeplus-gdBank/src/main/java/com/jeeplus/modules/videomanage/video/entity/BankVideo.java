/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.videomanage.video.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 视频Entity
 * @author cheny
 * @version 2019-12-02
 */
public class BankVideo extends DataEntity<BankVideo> {
	
	private static final long serialVersionUID = 1L;
	private String state;		// 状态
	private String videoName;		// 名称
	private String videoSize;		// 大小
	private String videoPath;		// 路径
	private String videoType;		// 类型
	private String uid;		// 关联用户id
	
	public BankVideo() {
		super();
	}

	public BankVideo(String id){
		super(id);
	}

	@ExcelField(title="状态", align=2, sort=7)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@ExcelField(title="名称", align=2, sort=8)
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	@ExcelField(title="大小", align=2, sort=9)
	public String getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(String videoSize) {
		this.videoSize = videoSize;
	}
	
	@ExcelField(title="路径", align=2, sort=10)
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	@ExcelField(title="类型", align=2, sort=11)
	public String getVideoType() {
		return videoType;
	}

	public void setVideoType(String videoType) {
		this.videoType = videoType;
	}
	
	@ExcelField(title="关联用户id", align=2, sort=12)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
}