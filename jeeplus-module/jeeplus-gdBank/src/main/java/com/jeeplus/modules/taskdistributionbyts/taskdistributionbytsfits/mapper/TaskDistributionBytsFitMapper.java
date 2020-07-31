/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.entity.TaskDistributionBytsFit;

import java.util.List;

/**
 * 执行人MAPPER接口
 * @author chenl
 * @version 2020-05-13
 */
@Mapper
@Repository
public interface TaskDistributionBytsFitMapper extends BaseMapper<TaskDistributionBytsFit> {

    //根据外键查询
    List<TaskDistributionBytsFit> findByTidAll(String tid);
    //根据tid删除
    int delTaskByTid(String tid);
}