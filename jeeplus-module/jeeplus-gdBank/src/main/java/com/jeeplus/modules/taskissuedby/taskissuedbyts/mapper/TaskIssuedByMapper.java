/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskissuedby.taskissuedbyts.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.entity.TaskIssuedBy;

import java.util.Date;

/**
 * 任务下发MAPPER接口
 * @author chenl
 * @version 2019-12-20
 */
@Mapper
@Repository
public interface TaskIssuedByMapper extends BaseMapper<TaskIssuedBy> {


    //修改状态 和时间
    int updateStateById(@Param("id") String id, @Param("state") Integer state,@Param("time") Date time);
	
}