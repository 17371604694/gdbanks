/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifiertrue.verifiertrues.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.verifiertrue.verifiertrues.entity.BankVerifierTrue;
import com.jeeplus.modules.verifiertrue.verifiertrues.mapper.BankVerifierTrueMapper;

/**
 * 添加审核人审核栏目Service
 * @author chenl
 * @version 2019-12-09
 */
@Service
@Transactional(readOnly = true)
public class BankVerifierTrueService extends TreeService<BankVerifierTrueMapper, BankVerifierTrue> {

	public BankVerifierTrue get(String id) {
		return super.get(id);
	}
	
	public List<BankVerifierTrue> findList(BankVerifierTrue bankVerifierTrue) {
		if (StringUtils.isNotBlank(bankVerifierTrue.getParentIds())){
			bankVerifierTrue.setParentIds(","+bankVerifierTrue.getParentIds()+",");
		}
		return super.findList(bankVerifierTrue);
	}
	
	@Transactional(readOnly = false)
	public void save(BankVerifierTrue bankVerifierTrue) {
		super.save(bankVerifierTrue);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankVerifierTrue bankVerifierTrue) {
		super.delete(bankVerifierTrue);
	}
	
}