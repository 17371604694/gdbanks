/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.employeevoice.leavemessage.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.employeevoice.leavemessage.entity.BankEmployeeVoice;
import com.jeeplus.modules.employeevoice.leavemessage.mapper.BankEmployeeVoiceMapper;

/**
 * 员工心声Service
 * @author cheny
 * @version 2019-12-18
 */
@Service
@Transactional(readOnly = true)
public class BankEmployeeVoiceService extends CrudService<BankEmployeeVoiceMapper, BankEmployeeVoice> {

	@Autowired
	private BankEmployeeVoiceMapper bankEmployeeVoiceMapper;

	public BankEmployeeVoice get(String id) {
		return super.get(id);
	}
	
	public List<BankEmployeeVoice> findList(BankEmployeeVoice bankEmployeeVoice) {
		return super.findList(bankEmployeeVoice);
	}
	
	public Page<BankEmployeeVoice> findPage(Page<BankEmployeeVoice> page, BankEmployeeVoice bankEmployeeVoice) {
		return super.findPage(page, bankEmployeeVoice);
	}
	
	@Transactional(readOnly = false)
	public void save(BankEmployeeVoice bankEmployeeVoice) {
		super.save(bankEmployeeVoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankEmployeeVoice bankEmployeeVoice) {
		super.delete(bankEmployeeVoice);
	}

	/**
	 * 保存回复
	 * @param bankEmployeeVoice
	 */
	@Transactional(readOnly = false)
    public void saveReply(BankEmployeeVoice bankEmployeeVoice) {
		bankEmployeeVoice.setLeaveMessageUserId(UUID.randomUUID().toString());//设置id
		bankEmployeeVoice.setReplyTime(new Date());//回复时间
		bankEmployeeVoice.setDelFlag("0");//
		bankEmployeeVoice.setIsReply("是");
		bankEmployeeVoiceMapper.saveReply(bankEmployeeVoice);
    }

	/**
	 * 根据留言id查询回复
	 * @param bankEmployeeVoice
	 */
	public List<BankEmployeeVoice> getReply(BankEmployeeVoice bankEmployeeVoice) {
		return bankEmployeeVoiceMapper.getReply(bankEmployeeVoice);
	}

	@Transactional(readOnly = false)
	public void updateisReply(BankEmployeeVoice bankEmployeeVoice) {
		 bankEmployeeVoiceMapper.updateisReply(bankEmployeeVoice);
	}
	@Transactional(readOnly = false)
	public void updataReply(BankEmployeeVoice bankEmployeeVoice) {
		bankEmployeeVoiceMapper.updataReply(bankEmployeeVoice);
	}


	public List<BankEmployeeVoice> getReplyList(BankEmployeeVoice bankEmployeeVoice1) {
		return bankEmployeeVoiceMapper.getReplyList(bankEmployeeVoice1);
	}
}