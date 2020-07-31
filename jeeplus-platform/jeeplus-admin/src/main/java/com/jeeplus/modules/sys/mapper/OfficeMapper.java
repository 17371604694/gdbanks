/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.Office;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;;

/**
 * 机构MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface OfficeMapper extends TreeMapper<Office> {
	
	public Office getByCode(String code);
	
	/**
	 * 查询含有发布资源的组织部门id和parent_ids
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<Office> releaseResOffice(Office office);

	public List<Menu> getNavMenu(Menu menu);

	public List<Menu> getNavMenuList(Menu menu);

	public List<Menu> getNavMenuInstitutions(Menu menu);

	public Office getByUserIdOffice(String id);
}