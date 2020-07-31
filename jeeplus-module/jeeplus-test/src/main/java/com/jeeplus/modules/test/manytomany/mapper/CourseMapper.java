/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.manytomany.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.test.manytomany.entity.Course;

/**
 * 课程MAPPER接口
 * @author lgf
 * @version 2019-08-13
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {
	
}