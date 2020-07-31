/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.meetingroommanage.meetingroom.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.meetingroommanage.meetingroom.entity.MeetingRoom;

import java.util.List;

/**
 * 会议室MAPPER接口
 * @author cheny
 * @version 2019-11-20
 */
@Mapper
@Repository
public interface MeetingRoomMapper extends BaseMapper<MeetingRoom> {

    /**
     * 查询所有会议室
     * @return
     */
   public List<MeetingRoom> findAllRoom();

   public MeetingRoom findRoomByName(String roomName);
}