/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import java.util.List;

import com.jeeplus.modules.sys.entity.BankVerifierTrues;
import com.jeeplus.modules.sys.entity.BankVerifierTruets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.Menu;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 菜单MAPPER接口
 * @author jeeplus
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	public List<Menu> getChildren(String parentId);

	/*获取栏目（首页新闻，部室网栏，经营机构）*/
	public List<Menu> getbusinessChildren(String parentId);
	
	public void deleteMenuRole(@Param(value = "menu_id") String menu_id);
	
	public void deleteMenuDataRule(@Param(value = "menu_id") String menu_id);
	
	public List<Menu> findAllDataRuleList(Menu menu);

	//添加审核人
	public int addBank(@Param("id")String id,@Param("delFlag")String delFlag,@Param("parentid")String parentid,
				@Param("columnName")String columnName,@Param("sort")int sort,@Param("columnId")String columnId);
//	int addBank(BankVerifierTruets bankVerifierTruets);
	public int selectByID(String mid);
    //修改
	public int updateBank(@Param("mid")String mid,@Param("columnName")String columnName);
	//删除
	public int deleteById(String id);
	//如果添加一级菜单时选择否,就把该菜单加入筛选表exclude_column
	public int addExcludeColumn(String name);

	String selectByBid(String mid);

	//根据name查询id
	public Menu findByName(String name);

    //临时使用,修改href的地址
	public int updateByHref(@Param("id")String id,@Param("cont")String cont);

	public List<Menu> findAllListAs();

	//获取需要审核的的一级栏目
	public List<BankVerifierTrues> getbs();

	//<!--根据当前用户查询他是部室网栏或者是经验单位 下的所有菜单-->
	public List<Menu> getHemuroAll(String officeName);

	//获取当前用户是部室网栏或者是经验单位  根据当前用户的offic id 将getUnites查到的值给getHemuroAll
	public String getUnites(String officeId);

    //根据office_id获取该菜单
	public String getByNameMeau(String officeId);

	//修改是否显示,根据ids
	public int updateMenus(@Param("list")List<Menu> list,@Param("isShow")String isShow);

	/*根据菜单名称查询菜单*/
	public List<Menu> getByName(String name);

	public List<Menu> programaTreeData();
}
