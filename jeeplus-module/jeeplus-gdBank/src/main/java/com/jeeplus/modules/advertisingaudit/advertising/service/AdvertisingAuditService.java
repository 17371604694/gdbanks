/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.advertisingaudit.advertising.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.advertisingaudit.advertising.entity.AdvertisingAudit;
import com.jeeplus.modules.advertisingaudit.advertising.mapper.AdvertisingAuditMapper;

/**
 * 广告审核Service
 * @author chenl
 * @version 2019-12-10
 */
@Service
@Transactional(readOnly = true)
public class AdvertisingAuditService extends CrudService<AdvertisingAuditMapper, AdvertisingAudit> {

	@Autowired
	private AdvertisingAuditMapper advertisingAuditMapper;

	public AdvertisingAudit get(String id) {
		return super.get(id);
	}
	
	public List<AdvertisingAudit> findList(AdvertisingAudit advertisingAudit) {
		return super.findList(advertisingAudit);
	}
	
	public Page<AdvertisingAudit> findPage(Page<AdvertisingAudit> page, AdvertisingAudit advertisingAudit) {
		return super.findPage(page, advertisingAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(AdvertisingAudit advertisingAudit) {
		super.save(advertisingAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdvertisingAudit advertisingAudit) {
		super.delete(advertisingAudit);
	}

	/**
	 * 广告审核操作
	 * @param advertisingAudit
	 */
	@Transactional(readOnly = false)
    public void updataStateStepAndState(AdvertisingAudit advertisingAudit) {
		advertisingAuditMapper.updataStateStepAndStateSuccess(advertisingAudit);
    }
}