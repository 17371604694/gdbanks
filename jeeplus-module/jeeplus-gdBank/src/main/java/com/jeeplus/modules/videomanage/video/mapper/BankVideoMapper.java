/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.videomanage.video.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.videomanage.video.entity.BankVideo;

/**
 * 视频MAPPER接口
 * @author cheny
 * @version 2019-12-02
 */
@Mapper
@Repository
public interface BankVideoMapper extends BaseMapper<BankVideo> {
	
}