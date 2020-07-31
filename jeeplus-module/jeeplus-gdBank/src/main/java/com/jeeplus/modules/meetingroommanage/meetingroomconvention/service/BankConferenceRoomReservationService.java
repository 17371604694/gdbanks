/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroomconvention.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jeeplus.modules.meetingroommanage.meetingroom.entity.MeetingRoom;
import com.jeeplus.modules.meetingroommanage.meetingroom.mapper.MeetingRoomMapper;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.MeetingTable;
import com.jeeplus.modules.sys.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.mapper.BankConferenceRoomReservationMapper;

/**
 * 会议管理Service
 * @author cheny
 * @version 2019-11-19
 */
@Service
@Transactional(readOnly = true)
public class BankConferenceRoomReservationService extends CrudService<BankConferenceRoomReservationMapper, BankConferenceRoomReservation> {

	@Autowired
	private BankConferenceRoomReservationMapper bankConferenceRoomReservationMapper;

	@Autowired
	private MeetingRoomMapper meetingRoomMapper;

	public BankConferenceRoomReservation get(String id) {
		return super.get(id);
	}
	
	public List<BankConferenceRoomReservation> findList(BankConferenceRoomReservation bankConferenceRoomReservation) {
		return super.findList(bankConferenceRoomReservation);
	}
	
	public Page<BankConferenceRoomReservation> findPage(Page<BankConferenceRoomReservation> page, BankConferenceRoomReservation bankConferenceRoomReservation) {
		return super.findPage(page, bankConferenceRoomReservation);
	}
	
	@Transactional(readOnly = false)
	public void save(BankConferenceRoomReservation bankConferenceRoomReservation) {
		super.save(bankConferenceRoomReservation);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankConferenceRoomReservation bankConferenceRoomReservation) {
		super.delete(bankConferenceRoomReservation);
	}

	/**
	 * 查询所有会议(根据查看时间bt)
	 * @return
	 */
	public List<MeetingTable> findAllMeeting(String bt) {
		TimeUtils.setTimeList(bt);//设置查询周期表头（一周）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		List<MeetingRoom> roomList = meetingRoomMapper.findAllRoom();//获取表格左侧div会议室列
		List<MeetingTable> meetingList = bankConferenceRoomReservationMapper.findAllMeeting();//查询所有预约记录
		for(int j = 0;j<meetingList.size();j++){//设置会议记录的WeekAndDataStr
			meetingList.get(j).setMeetingTimeStr(TimeUtils.getTimeStr(meetingList.get(j).getBeginTime(),meetingList.get(j).getEndTime()));
			meetingList.get(j).setWeekAndDataStr(TimeUtils.getWeekAndDataStr(sdf.format(meetingList.get(j).getBeginTime())));;
		}
		return meetingList;
	}

	/**
	 * 判断会议室在同一时间是否被预约,true被占用，false未被占用
	 * @param
	 * @return
	 */
	public boolean isAppointment(BankConferenceRoomReservation bankConferenceRoomReservation) {
		boolean result = false;
		List<BankConferenceRoomReservation> resultList = null;
		if(bankConferenceRoomReservation.getBeginTime()!=null&&bankConferenceRoomReservation.getEndTime()!=null){
			resultList = bankConferenceRoomReservationMapper.findByBeginTime(bankConferenceRoomReservation);
			if(resultList==null||resultList.size()==0){
				result = true;
			}
		}
//		if(resultList==null||resultList.size()==0){
//			if(bankConferenceRoomReservation!=null){
//				resultList = bankConferenceRoomReservationMapper.findByEndTime(bankConferenceRoomReservation);
//				if(resultList==null||resultList.size()==0){
//					return result;
//				}
//			}
//
//		}

		return result;
	}

	/**
	 * 查询登录用户角设
	 */
	public List<String> findrole(String id){

		return bankConferenceRoomReservationMapper.findrole(id);
	}
}