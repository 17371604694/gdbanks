/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity.TaskDistributionByts;

/**
 * 任务下发MAPPER接口
 * @author chenl
 * @version 2020-05-13
 */
@Mapper
@Repository
public interface TaskDistributionBytsMapper extends BaseMapper<TaskDistributionByts> {



}