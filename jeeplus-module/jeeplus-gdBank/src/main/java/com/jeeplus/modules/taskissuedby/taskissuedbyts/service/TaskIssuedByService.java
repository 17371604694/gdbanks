/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.taskissuedby.taskissuedbyts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.entity.TaskIssuedBy;
import com.jeeplus.modules.taskissuedby.taskissuedbyts.mapper.TaskIssuedByMapper;

/**
 * 任务下发Service
 * @author chenl
 * @version 2019-12-20
 */
@Service
@Transactional(readOnly = true)
public class TaskIssuedByService extends CrudService<TaskIssuedByMapper, TaskIssuedBy> {

	public TaskIssuedBy get(String id) {
		return super.get(id);
	}
	
	public List<TaskIssuedBy> findList(TaskIssuedBy taskIssuedBy) {
		return super.findList(taskIssuedBy);
	}
	
	public Page<TaskIssuedBy> findPage(Page<TaskIssuedBy> page, TaskIssuedBy taskIssuedBy) {
		return super.findPage(page, taskIssuedBy);
	}

	public Page<TaskIssuedBy> findPage2(Page<TaskIssuedBy> page, TaskIssuedBy taskIssuedBy) {
		return super.findPage222(page, taskIssuedBy);
	}

	@Transactional(readOnly = false)
	public void save(TaskIssuedBy taskIssuedBy) {
		super.save(taskIssuedBy);
	}
	
	@Transactional(readOnly = false)
	public void delete(TaskIssuedBy taskIssuedBy) {
		super.delete(taskIssuedBy);
	}
	
}