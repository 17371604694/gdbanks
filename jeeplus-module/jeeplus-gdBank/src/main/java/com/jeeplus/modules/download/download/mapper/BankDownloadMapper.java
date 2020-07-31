/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.download.download.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.download.download.entity.BankDownload;

/**
 * 文件下载MAPPER接口
 * @author cheny
 * @version 2019-12-31
 */
@Mapper
@Repository
public interface BankDownloadMapper extends BaseMapper<BankDownload> {
	
}