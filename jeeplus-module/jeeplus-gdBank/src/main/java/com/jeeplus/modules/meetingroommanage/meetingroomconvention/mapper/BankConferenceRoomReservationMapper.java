/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroomconvention.mapper;

import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.MeetingTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.meetingroommanage.meetingroomconvention.entity.BankConferenceRoomReservation;

import java.util.Date;
import java.util.List;

/**
 * 会议管理MAPPER接口
 * @author cheny
 * @version 2019-11-19
 */
@Mapper
@Repository
public interface BankConferenceRoomReservationMapper extends BaseMapper<BankConferenceRoomReservation> {

    /**
     *查询所有会议室预约记录
     * @return
     */
   public List<MeetingTable> findAllMeeting();

    /**
     * 根据会议时间会议室预约记录
     * @param bankConferenceRoomReservation
     * @return
     */
   public List<BankConferenceRoomReservation> findByBeginTime(BankConferenceRoomReservation bankConferenceRoomReservation);

    /**
     *根据会议时间会议室预约记录
     * @param bankConferenceRoomReservation
     * @return
     */
   public List<BankConferenceRoomReservation> findByEndTime(BankConferenceRoomReservation bankConferenceRoomReservation);

    /**
     * 查询当前登录用户的角设
     */
    public List<String> findrole(@Param("id")String id);
}