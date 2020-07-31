/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.communicationbook.communicationbookmanagement.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.communicationbook.communicationbookmanagement.entity.BankCommunicationBook;

/**
 * 通讯录管理MAPPER接口
 * @author cheny
 * @version 2019-11-29
 */
@Mapper
@Repository
public interface BankCommunicationBookMapper extends BaseMapper<BankCommunicationBook> {
	
}