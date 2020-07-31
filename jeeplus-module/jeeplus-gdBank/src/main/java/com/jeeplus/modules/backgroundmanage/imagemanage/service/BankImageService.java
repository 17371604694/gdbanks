/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.backgroundmanage.imagemanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.backgroundmanage.imagemanage.entity.BankImage;
import com.jeeplus.modules.backgroundmanage.imagemanage.mapper.BankImageMapper;

import javax.xml.ws.soap.Addressing;

/**
 * 背景Service
 * @author cheny
 * @version 2019-12-10
 */
@Service
@Transactional(readOnly = true)
public class BankImageService extends CrudService<BankImageMapper, BankImage> {

	@Autowired
	private BankImageMapper bankImageMapper;

	public BankImage get(String id) {
		return super.get(id);
	}
	
	public List<BankImage> findList(BankImage bankImage) {
		return super.findList(bankImage);
	}
	
	public Page<BankImage> findPage(Page<BankImage> page, BankImage bankImage) {
		return super.findPage(page, bankImage);
	}
	
	@Transactional(readOnly = false)
	public void save(BankImage bankImage) {
		super.save(bankImage);
	}
	
	@Transactional(readOnly = false)
	public void delete(BankImage bankImage) {
		super.delete(bankImage);
	}

	/**
	 * 修改背景图片
	 * @param path
	 */
	@Transactional(readOnly = false)
	public void updataBackgroundImage(String path,String imgaeType) {
		bankImageMapper.updataBackgroundImage(path,imgaeType);
	}

	/**
	 * 获取背景图片路径
	 * @return
	 */
	public String getPath(BankImage bankImage) {
		return bankImageMapper.getPath(bankImage);
	}
}