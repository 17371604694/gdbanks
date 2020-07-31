/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifierts.verifier.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.verifierts.verifier.entity.BankVerifier;
import com.jeeplus.modules.verifierts.verifier.mapper.BankVerifierMapper;

/**
 * 添加审核人审核栏目Service
 * @author chenl
 * @version 2019-11-21
 */
@Service
@Transactional(readOnly = true)
public class BankVerifierService extends CrudService<BankVerifierMapper, BankVerifier> {

	public BankVerifier get(String id) {
		return super.get(id);
	}
	
	public List<BankVerifier> findList(BankVerifier bankVerifier) {
		return super.findList(bankVerifier);
	}
	
	public Page<BankVerifier> findPage(Page<BankVerifier> page, BankVerifier bankVerifier) {
		return super.findPage(page, bankVerifier);
	}
	
	@Transactional(readOnly = false)
	public void save(BankVerifier bankVerifier) {
		super.save(bankVerifier);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankVerifier bankVerifier) {
		super.delete(bankVerifier);
	}
	
}