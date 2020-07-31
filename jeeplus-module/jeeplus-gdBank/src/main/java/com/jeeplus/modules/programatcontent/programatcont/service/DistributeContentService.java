/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.programatcontent.programatcont.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.programatcontent.programatcont.entity.DistributeContent;
import com.jeeplus.modules.programatcontent.programatcont.mapper.DistributeContentMapper;

/**
 * 发稿内容Service
 * @author chenl
 * @version 2019-11-22
 */
@Service
@Transactional(readOnly = true)
public class DistributeContentService extends CrudService<DistributeContentMapper, DistributeContent> {

	@Autowired
	private DistributeContentMapper distributeContentMapper;

	public DistributeContent get(String id) {
		return super.get(id);
	}
	
	public List<DistributeContent> findList(DistributeContent distributeContent) {
		return super.findList(distributeContent);
	}
	
	public Page<DistributeContent> findPage(Page<DistributeContent> page, DistributeContent distributeContent) {
		return super.findPage(page, distributeContent);
	}

	public Page<DistributeContent> findPage(Page<DistributeContent> page, DistributeContent distributeContent,String noaudit) {
		return super.findPage(page, distributeContent,noaudit);
	}

	/**
	 * 统计列表数据
	 * @param page
	 * @param distributeContent
	 * @return
	 */
	public Page<DistributeContent> findStatisticalPage(Page<DistributeContent> page, DistributeContent distributeContent) {
		return super.findStatisticalPage(page, distributeContent);
	}
	
	@Transactional(readOnly = false)
	public void save(DistributeContent distributeContent) {
		super.save(distributeContent);
	}
	
	@Transactional(readOnly = false)
	public void delete(DistributeContent distributeContent) {
		super.delete(distributeContent);
	}

	@Transactional(readOnly = false)
    public void admintop(DistributeContent distributeContent) {
		distributeContentMapper.admintop(distributeContent);
    }

    /*
    * 查询未审核发稿数据
    * */
	@Transactional(readOnly = false)
	public Page<DistributeContent> findallPage(Page<DistributeContent> distributeContentPage, DistributeContent distributeContent) {
		return super.findallPage(distributeContentPage, distributeContent);
	}

	//查询首页二级页面栏目的数据，需要审核通过的
	public Page<DistributeContent> findPage1(Page<DistributeContent> page, DistributeContent distributeContent) {
		return super.findPage1(page, distributeContent);
	}

	//根据父级栏目名称和子栏目名查询栏目id
	public DistributeContent findprogramatId(DistributeContent distributeContent) {
		return distributeContentMapper.findprogramatId(distributeContent);
	}

	//查询审核通过的发稿数据
	public List<DistributeContent> findListtg(DistributeContent distributeContent) {
		return distributeContentMapper.findListtg(distributeContent);
	}

	@Transactional(readOnly = false)
	public int again(DistributeContent distributeContent) {
		return distributeContentMapper.again(distributeContent);
	}

	/*保存正文*/
	@Transactional(readOnly = false)
	public void saveContent(DistributeContent distributeContent) {
		distributeContentMapper.insertContentText(distributeContent);
	}

	/*updata正文*/
	@Transactional(readOnly = false)
	public void updataContent(DistributeContent distributeContent) {
		distributeContentMapper.updataContentText(distributeContent);
	}

	/*delete*/
	@Transactional(readOnly = false)
	public void deleteContentText(String id) {
		distributeContentMapper.deleteContentText(id);
	}
}