/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.entity.TaskDistributionByts;
import com.jeeplus.modules.taskdistributionbyts.taskdistributionbyt.mapper.TaskDistributionBytsMapper;

/**
 * 任务下发Service
 * @author chenl
 * @version 2020-05-13
 */
@Service
@Transactional(readOnly = true)
public class TaskDistributionBytsService extends CrudService<TaskDistributionBytsMapper, TaskDistributionByts> {

	public TaskDistributionByts get(String id) {
		return super.get(id);
	}
	
	public List<TaskDistributionByts> findList(TaskDistributionByts taskDistributionByts) {
		return super.findList(taskDistributionByts);
	}
	
	public Page<TaskDistributionByts> findPage(Page<TaskDistributionByts> page, TaskDistributionByts taskDistributionByts) {
		return super.findPage(page, taskDistributionByts);
	}
	
	@Transactional(readOnly = false)
	public void save(TaskDistributionByts taskDistributionByts) {
		super.save(taskDistributionByts);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskDistributionByts taskDistributionByts) {
		super.delete(taskDistributionByts);
	}
	
}