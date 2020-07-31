/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

//import com.jeeplus.modules.verifiertrue.verifiertrues.mapper.BankVerifierTrueMapper;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.modules.sys.entity.*;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.utils.LogUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 菜单Controller
 * @author jeeplus
 * @version 2016-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private MenuMapper menuMapper;

/*	@Autowired
	private BankVerifierTrueMapper bankVerifierTrueMapper;*/
    /*private static List<Menu> menuList=null;
    private static User userList=null;*/


	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:list")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {

		return "modules/sys/menu/menuList";
	}

	@RequiresPermissions(value={"sys:menu:view","sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		model.addAttribute("menu", menu);
		return "modules/sys/menu/menuForm";
	}
	
	@ResponseBody
	@RequiresPermissions(value={"sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Menu menu, Model model) {
		AjaxJson j = new AjaxJson();
		if(!UserUtils.getUser().isAdmin()){
			j.setSuccess(false);
			j.setMsg("越权操作，只有超级管理员才能添加或修改数据！");
			return j;
		}
		if(jeePlusProperites.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(menu);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}

		systemService.saveMenu(menu);

		if (menu.getIfcolumn().equals("是")){
			if (menuMapper.selectByID(menu.getId())>0){//栏目审核人设置表已经存在改栏目设置记录
				menuMapper.updateBank(menu.getId(),menu.getName());//更新
			}else {
				menuMapper.addBank(IdGen.uuid(),"0",null,menu.getName(),30,menu.getId());
			}
		}
			//如果已存在,就修改,不存在就添加
		if (menuMapper.selectByID(menu.getId())>0){
			menuMapper.updateBank(menu.getId(),menu.getName());
		}
		if (menu.getIfcolumn().equals("子栏目")){
			if (menuMapper.selectByID(menu.getId())>0){//栏目审核人设置表已经存在改栏目设置记录
				menuMapper.updateBank(menu.getId(),menu.getName());//更新
			}else {
				menuMapper.addBank(IdGen.uuid(),"0",menuMapper.selectByBid(menu.getParent().getId()),menu.getName(),30,menu.getId());
			}
		}
		if (menu.getIfcolumn().equals("子栏目默认链接")){
			if (menuMapper.selectByID(menu.getId())>0){//栏目审核人设置表已经存在改栏目设置记录
				menuMapper.updateBank(menu.getId(),menu.getName());//更新
			}else {
				menuMapper.addBank(IdGen.uuid(),"0",menuMapper.selectByBid(menu.getParent().getId()),menu.getName(),30,menu.getId());
				//保存时,赋值href的地址,/programatcontent/programatcont/distributeContent/list?name=加menu.getId()
				menu.setHref("/programatcontent/programatcont/distributeContent/list?name="+menu.getId());
				systemService.saveMenu(menu);
			}
		}

		if (menu.getIfcolumn().equals("否")){
			menuMapper.addExcludeColumn(menu.getName());
		}

		j.setMsg("保存成功!");
		j.put("menu", menu);
		return j;
	}

	/***
	 *  用户登录成功后,获取部门,进行筛选,只显示当前所在部门的部室网栏
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getScreen")
	public AjaxJson getScreen(){
		AjaxJson j = new AjaxJson();
		screenJSon();
        j.setMsg("筛选完成");
		return j;
	}




    public synchronized void screenJSon(){
	    User user=UserUtils.getUser();
        List<Menu> hemuroAllTwo = new ArrayList<>();
        //管理员和超管使用
        List<Menu> hemuroAllThree = menuMapper.getHemuroAll("部室网栏");
        List<Menu> hemuroAllForn = menuMapper.getHemuroAll("经营单位");
        hemuroAllTwo.addAll(hemuroAllThree);
        hemuroAllTwo.addAll(hemuroAllForn);
            if(user.getId().equals("1") || user.getId().equals("c6d9c07543f64a21b4454c23c8dedde2")){
                menuMapper.updateMenus(hemuroAllTwo,"1");
            }else {
                String byNameMeau = menuMapper.getByNameMeau(user.getOffice().getId());
                for (int i = 0; i < hemuroAllTwo.size(); i++) {
                    if(hemuroAllTwo.get(i).getId().equals(byNameMeau)){
                        hemuroAllTwo.remove(i);   //从全部菜单里删除当前用户的部门
                        Menu menu = menuMapper.get(byNameMeau);
                        menu.setIsShow("1");
                        systemService.saveMenu(menu);
                    }else {
                        menuMapper.updateMenus(hemuroAllTwo,"0");
                    }

                }

            }
        // 清除用户菜单缓存
      UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
       CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);


    }



	@RequiresPermissions("sys:menu:del")
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(Menu menu) {
		AjaxJson j = new AjaxJson();
		if(jeePlusProperites.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作");
			return j;
		}
		systemService.deleteMenu(menu);
		if (menuMapper.selectByID(menu.getId())>0){
			menuMapper.deleteById(menu.getId());
		}

		j.setMsg("删除成功!");
		return  j;
	}

	@RequiresPermissions("sys:menu:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		if(jeePlusProperites.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作");
			return j;
		}
		String idArray[] =ids.split(",");
		for(String id : idArray){
			Menu menu = systemService.getMenu(id);
			if(menu != null){
				systemService.deleteMenu(systemService.getMenu(id));
			}
		}
			
		j.setMsg("删除菜单成功！");
		return j;
	}

	/**
	 * 修改菜单排序
	 */
	@RequiresPermissions("sys:menu:updateSort")
	@ResponseBody
	@RequestMapping(value = "sort")
	public AjaxJson sort(String id1, int sort1, String id2, int sort2) {
		AjaxJson j = new AjaxJson();
		if(jeePlusProperites.isDemoMode()){
			j.setSuccess(false);
			j.setMsg("演示模式，不允许操作！");
			return j;
		}
		Menu menu = new Menu();
		menu.setId(id1);
		menu.setSort(sort1);
		systemService.updateMenuSort(menu);
		menu.setId(id2);
		menu.setSort(sort2);
		systemService.updateMenuSort(menu);
		j.setMsg("排序成功！");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<Menu> getChildren(String parentId){
		if("-1".equals(parentId)){
			parentId = "1";
		}
		return systemService.getChildren(parentId);
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String roleId, @RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		Role role = systemService.getRole(roleId);
		String menuIds = "";
		if(role !=null){
			menuIds = ","+role.getMenuIds()+",";
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				if("0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
					
				}else{
					if(i == 0){
						map.put("parent", "#");
					}else{
						map.put("parent", e.getParentId());
					}
				}

				if(menuIds.contains(","+e.getId()+",")&& systemService.getChildren(e.getId()).size() == 0){
					Map<String, Object> state = Maps.newHashMap();
					state.put("selected", true);
					map.put("state", state);
				}
				
				if(StringUtils.isNotBlank(e.getIcon())){
					map.put("icon", e.getIcon());
				}
				if("2".equals(e.getType())){
					map.put("type", "btn");
				}else if("1".equals(e.getType())){
					map.put("type", "menu");
				}
				map.put("text", e.getName());
				map.put("name", e.getName());
				
				mapList.add(map);
			}
		}
		return mapList;
	}

	/**
	 *返回主栏目树
	 */
	@ResponseBody
	@RequestMapping(value = "programaTreeData")
	public List<Map<String, Object>> programaTreeData(@RequestParam(required=false) String roleId, @RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		Role role = systemService.getRole(roleId);
		String menuIds = "";
		if(role !=null){
			menuIds = ","+role.getMenuIds()+",";
		}
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.programaTreeData();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				if("0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);

				}else{
					if(i == 0){
						map.put("parent", "#");
					}else{
						map.put("parent", e.getParentId());
					}
				}

				if(menuIds.contains(","+e.getId()+",")&& systemService.getChildren(e.getId()).size() == 0){
					Map<String, Object> state = Maps.newHashMap();
					state.put("selected", true);
					map.put("state", state);
				}

				if(StringUtils.isNotBlank(e.getIcon())){
					map.put("icon", e.getIcon());
				}
				if("2".equals(e.getType())){
					map.put("type", "btn");
				}else if("1".equals(e.getType())){
					map.put("type", "menu");
				}
				map.put("text", e.getName());
				map.put("name", e.getName());

				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequestMapping(value = "iframe")
	public String iframe(@RequestParam("title") String title,@RequestParam("url") String url,@RequestParam("parent") String parent,Model model) {
		model.addAttribute("title", title);
		model.addAttribute("url", url);
		model.addAttribute("parent", parent);
		return "modules/common/iframe";
	}

	/*栏目管理（只显示业务菜单的栏目）*/
	@RequiresPermissions("sys:menu:list")
	@RequestMapping(value = {"businesslist"})
	public String businesslist(Model model) {
		return "modules/sys/menu/businessmenuList";
	}

	/*获取栏目（首页新闻，部室网栏，经营机构）*/
	@ResponseBody
	@RequestMapping(value = "getbusinessChildren")

	/**/
	public List<Menu> getbusinessChildren(String parentId){
		List<Menu> lists = new ArrayList<>();
		if("-1".equals(parentId)){//不带参时查询以及菜单
			parentId = "1";
			lists = systemService.getChildren(parentId);
			List<BankVerifierTrues> list = menuMapper.getbs();
			List<Menu> listresult = new ArrayList<>();
			for(int i =0;i<lists.size();i++){
				for(int j = 0;j<list.size();j++){
					//筛选出发稿业务菜单
					if(lists.get(i).getId().equalsIgnoreCase(list.get(j).getColumnId())){
						System.out.println((lists.get(i).toString()));
						listresult.add(lists.get(i));
					}
				}
			}
			return listresult;
		}else {
			lists = systemService.getChildren(parentId);
			return lists;
		}

	}

	@RequiresPermissions(value={"sys:menu:view","sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "programaform")
	public String programaform(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		model.addAttribute("menu", menu);
		return "modules/sys/menu/programaForm";
	}

}
