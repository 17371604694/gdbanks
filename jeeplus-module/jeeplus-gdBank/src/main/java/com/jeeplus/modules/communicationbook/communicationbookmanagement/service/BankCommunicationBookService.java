/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.communicationbook.communicationbookmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.communicationbook.communicationbookmanagement.entity.BankCommunicationBook;
import com.jeeplus.modules.communicationbook.communicationbookmanagement.mapper.BankCommunicationBookMapper;

/**
 * 通讯录管理Service
 * @author cheny
 * @version 2019-11-29
 */
@Service
@Transactional(readOnly = true)
public class BankCommunicationBookService extends CrudService<BankCommunicationBookMapper, BankCommunicationBook> {

	public BankCommunicationBook get(String id) {
		return super.get(id);
	}
	
	public List<BankCommunicationBook> findList(BankCommunicationBook bankCommunicationBook) {
		return super.findList(bankCommunicationBook);
	}
	
	public Page<BankCommunicationBook> findPage(Page<BankCommunicationBook> page, BankCommunicationBook bankCommunicationBook) {
		return super.findPage(page, bankCommunicationBook);
	}
	
	@Transactional(readOnly = false)
	public void save(BankCommunicationBook bankCommunicationBook) {
		super.save(bankCommunicationBook);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankCommunicationBook bankCommunicationBook) {
		super.delete(bankCommunicationBook);
	}
	
}