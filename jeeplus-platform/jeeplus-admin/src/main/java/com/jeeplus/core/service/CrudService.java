/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.service;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service基类
 * @author jeeplus
 * @version 2017-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<M extends BaseMapper<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected M mapper;

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return mapper.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return mapper.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		dataRuleFilter(entity);
		return mapper.findList(entity);
	}


	/**
	 * 查询所有列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findAllList(T entity) {
		dataRuleFilter(entity);
		return mapper.findAllList(entity);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findList(entity));
		return page;
	}

	public Page<T> findPage(Page<T> page, T entity,String noaudit) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findListSearch(entity));
		return page;
	}

	public Page<T> findPage1(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findPage1(entity));
		return page;
	}

	/**
	 * 查询发稿统计分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findStatisticalPage(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findStatisticalList(entity));
		return page;
	}

	/**
	 *
	 * @param page
	 * @param entity
	 * @return
	 */
	public Page<T> findPage222(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findList222(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage2(Page<T> page, T entity) {
		dataRuleFilter(entity);
		entity.setPage(page);
		page.setList(mapper.findList2(entity));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			mapper.insert(entity);
		}else{
			entity.preUpdate();
			mapper.update(entity);
		}
	}

	/**
	 * 保存数据（插入或更新（投票））
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void saveselect(T entity) {
		entity.preInsert();
		mapper.saveselect(entity);
	}

	/**
	 * 保存数据（插入（发稿））
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void saveDistributeContent(T entity) {
		entity.preInsertDis();
		mapper.insert(entity);
	}

	/*保存数据（更新（发稿））*/
	@Transactional(readOnly = false)
	public void updataDistributeContent(T entity){
		entity.preUpdate();
		mapper.update(entity);
	}

	/**
	 * 删除数据
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void batchDelete(String[] ids) {
		mapper.batchDelete(ids);
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		mapper.delete(entity);
	}

	/**
	 * 删除数据
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteselect(String id) {
		mapper.deleteselect(id);
	}
	
	
	/**
	 * 删除全部数据
	 * @param entitys
	 */
	@Transactional(readOnly = false)
	public void deleteAll(Collection<T> entitys) {
		for (T entity : entitys) {
			mapper.delete(entity);
		}
	}

	/**
	 * 删除全部数据
	 * @param entitys
	 */
	@Transactional(readOnly = false)
	public void deleteAllByLogic(Collection<T> entitys) {
		for (T entity : entitys) {
			mapper.deleteByLogic(entity);
		}
	}

	
	/**
	 * 获取单条数据
	 * @param propertyName, value
	 * @return
	 */
	public T findUniqueByProperty(String propertyName, Object value){
		return mapper.findUniqueByProperty(propertyName, value);
	}

	/**
	 * 动态sql
	 * @param sql
	 */

	public List<Map<String, Object>>  executeSelectSql(String sql){
		return mapper.execSelectSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeInsertSql(String sql){
		mapper.execInsertSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeUpdateSql(String sql){
		mapper.execUpdateSql(sql);
	}

	@Transactional(readOnly = false)
	public void executeDeleteSql(String sql){
		mapper.execDeleteSql(sql);
	}

	/*查询未审核数据分页对象*/
	@Transactional(readOnly = false)
    protected Page<T> findallPage(Page<T> distributeContentPage, T distributeContent) {
		dataRuleFilter(distributeContent);
		distributeContent.setPage(distributeContentPage);
		distributeContentPage.setList(mapper.findallPage(distributeContent));
		return distributeContentPage;
    }

}
