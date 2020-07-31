/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meetingroommanage.meetingroom.entity.MeetingRoom;
import com.jeeplus.modules.meetingroommanage.meetingroom.mapper.MeetingRoomMapper;

/**
 * 会议室Service
 * @author cheny
 * @version 2019-11-20
 */
@Service
@Transactional(readOnly = true)
public class MeetingRoomService extends CrudService<MeetingRoomMapper, MeetingRoom> {

	@Autowired
	private MeetingRoomMapper meetingRoomMapper;

	public MeetingRoom get(String id) {
		return super.get(id);
	}
	
	public List<MeetingRoom> findList(MeetingRoom meetingRoom) {
		return super.findList(meetingRoom);
	}
	
	public Page<MeetingRoom> findPage(Page<MeetingRoom> page, MeetingRoom meetingRoom) {
		return super.findPage(page, meetingRoom);
	}
	
	@Transactional(readOnly = false)
	public void save(MeetingRoom meetingRoom) {
		super.save(meetingRoom);
	}
	
	@Transactional(readOnly = false)
	public void delete(MeetingRoom meetingRoom) {
		super.delete(meetingRoom);
	}

	/**
	 *
	 * 查询所有会议室
	 * @return
	 */
    public List<MeetingRoom> findAllRoom() {
    	List<MeetingRoom> list = meetingRoomMapper.findAllRoom();
    	return list;
    }

	/**
	 * 根据名称查询会议室
	 * @param roomName
	 * @return
	 */
	public MeetingRoom findRoomByName(String roomName) {
		return meetingRoomMapper.findRoomByName(roomName);
    }
}