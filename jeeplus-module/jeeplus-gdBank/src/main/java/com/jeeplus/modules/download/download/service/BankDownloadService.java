/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.download.download.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.download.download.entity.BankDownload;
import com.jeeplus.modules.download.download.mapper.BankDownloadMapper;

/**
 * 文件下载Service
 * @author cheny
 * @version 2019-12-31
 */
@Service
@Transactional(readOnly = true)
public class BankDownloadService extends CrudService<BankDownloadMapper, BankDownload> {

	public BankDownload get(String id) {
		return super.get(id);
	}
	
	public List<BankDownload> findList(BankDownload bankDownload) {
		return super.findList(bankDownload);
	}
	
	public Page<BankDownload> findPage(Page<BankDownload> page, BankDownload bankDownload) {
		return super.findPage(page, bankDownload);
	}
	
	@Transactional(readOnly = false)
	public void save(BankDownload bankDownload) {
		super.save(bankDownload);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankDownload bankDownload) {
		super.delete(bankDownload);
	}
	
}