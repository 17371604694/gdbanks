/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.consignfileapproval.administrativearchives.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.consignfileapproval.administrativearchives.entity.AdministrativeArchivesApprove;
import com.jeeplus.modules.consignfileapproval.administrativearchives.mapper.AdministrativeArchivesApproveMapper;

/**
 * 文书档案利用审批单Service
 * @author chenl
 * @version 2019-12-02
 */
@Service
@Transactional(readOnly = true)
public class AdministrativeArchivesApproveService extends CrudService<AdministrativeArchivesApproveMapper, AdministrativeArchivesApprove> {

	public AdministrativeArchivesApprove get(String id) {
		return super.get(id);
	}
	
	public List<AdministrativeArchivesApprove> findList(AdministrativeArchivesApprove administrativeArchivesApprove) {
		return super.findList(administrativeArchivesApprove);
	}
	
	public Page<AdministrativeArchivesApprove> findPage(Page<AdministrativeArchivesApprove> page, AdministrativeArchivesApprove administrativeArchivesApprove) {
		return super.findPage(page, administrativeArchivesApprove);
	}
	
	@Transactional(readOnly = false)
	public void save(AdministrativeArchivesApprove administrativeArchivesApprove) {
		super.save(administrativeArchivesApprove);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdministrativeArchivesApprove administrativeArchivesApprove) {
		super.delete(administrativeArchivesApprove);
	}
	
}