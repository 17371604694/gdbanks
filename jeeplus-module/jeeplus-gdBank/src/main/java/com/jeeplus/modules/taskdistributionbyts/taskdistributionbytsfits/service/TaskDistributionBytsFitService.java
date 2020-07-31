/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.entity.TaskDistributionBytsFit;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbytsfits.mapper.TaskDistributionBytsFitMapper;

/**
 * 执行人Service
 * @author chenl
 * @version 2020-05-13
 */
@Service
@Transactional(readOnly = true)
public class TaskDistributionBytsFitService extends CrudService<TaskDistributionBytsFitMapper, TaskDistributionBytsFit> {

	public TaskDistributionBytsFit get(String id) {
		return super.get(id);
	}
	
	public List<TaskDistributionBytsFit> findList(TaskDistributionBytsFit taskDistributionBytsFit) {
		return super.findList(taskDistributionBytsFit);
	}
	
	public Page<TaskDistributionBytsFit> findPage(Page<TaskDistributionBytsFit> page, TaskDistributionBytsFit taskDistributionBytsFit) {
		return super.findPage(page, taskDistributionBytsFit);
	}
	
	@Transactional(readOnly = false)
	public void save(TaskDistributionBytsFit taskDistributionBytsFit) {
		super.save(taskDistributionBytsFit);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskDistributionBytsFit taskDistributionBytsFit) {
		super.delete(taskDistributionBytsFit);
	}
	
}