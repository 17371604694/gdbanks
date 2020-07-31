/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.videomanage.video.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.videomanage.video.entity.BankVideo;
import com.jeeplus.modules.videomanage.video.mapper.BankVideoMapper;

/**
 * 视频Service
 * @author cheny
 * @version 2019-12-02
 */
@Service
@Transactional(readOnly = true)
public class BankVideoService extends CrudService<BankVideoMapper, BankVideo> {

	public BankVideo get(String id) {
		return super.get(id);
	}
	
	public List<BankVideo> findList(BankVideo bankVideo) {
		return super.findList(bankVideo);
	}
	
	public Page<BankVideo> findPage(Page<BankVideo> page, BankVideo bankVideo) {
		return super.findPage(page, bankVideo);
	}
	
	@Transactional(readOnly = false)
	public void save(BankVideo bankVideo) {
		super.save(bankVideo);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankVideo bankVideo) {
		super.delete(bankVideo);
	}
	
}