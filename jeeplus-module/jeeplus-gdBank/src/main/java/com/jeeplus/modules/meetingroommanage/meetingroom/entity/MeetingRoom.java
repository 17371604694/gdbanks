/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroom.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 会议室Entity
 * @author cheny
 * @version 2019-11-20
 */
public class MeetingRoom extends DataEntity<MeetingRoom> {
	
	private static final long serialVersionUID = 1L;
	private String crrId;		// 关联会议预定id
	private String roomName;		// 会议室名称
	private String state;		// 状态
	
	public MeetingRoom() {
		super();
	}

	public MeetingRoom(String id){
		super(id);
	}

	@ExcelField(title="关联会议预定id", align=2, sort=7)
	public String getCrrId() {
		return crrId;
	}

	public void setCrrId(String crrId) {
		this.crrId = crrId;
	}
	
	@ExcelField(title="会议室名称", align=2, sort=8)
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	@ExcelField(title="状态", align=2, sort=9)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}