package com.jeeplus.common.tag;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.beetl.core.GeneralVarTagBinding;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * 类描述：菜单标签
 * 
 * 刘高峰
 * 
 * @date： 日期：2015-1-23 时间：上午10:17:45
 * 
 * @version 1.0
 */
public class JPMenuTag extends GeneralVarTagBinding {
	private static final long serialVersionUID = 1L;

	protected static final String DEFAULT_ICON= "fa-th-list";//左侧一级菜单图标为空时，默认显示的图标，不显示默认图标时使用空字符即可。



	@Override
	public void render(){
		String position = (String)getAttributeValue("position");
		Menu menu = (Menu)getAttributeValue("menu");
		StringBuffer sb = new StringBuffer();
		if(position.equals("top")){
			sb.append(getTopMenu(menu, 0, UserUtils.getMenuList(), -1));
		}else{
			sb.append(getLeftMenu(menu, 0, UserUtils.getMenuList(), -1));
		}

		try{
			ctx.byteWriter.writeString(sb.toString());
		}catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	private static  String getTopMenu(Menu menuItem, int level, List<Menu> menuList, int first){



		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!menuItem.hasPermisson())
			return "";




		if ((menuItem.getHref() == null || menuItem.getHref().trim().equals("")) && menuItem.getIsShow().equals("1")) {// 如果是父节点且显示
			if (level == 0) {// 如果是功能菜单
				int flag = 0;
				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getTopMenu(child, level + 1, menuList, ++flag));
					}
				}
			}

			if (level  == 1) {// 不是功能菜单
				menuString.append("<li class='nav-item'>\n");
				if(first == 1){
					menuString.append("<a class=\"nav-link show active\" href=\"#id"+menuItem.getId()+"\" role=\"tab\" data-toggle=\"tab\">");
				}else{
					menuString.append("<a class=\"nav-link\" href=\"#id"+menuItem.getId()+"\" role=\"tab\" data-toggle=\"tab\">");
				}

				menuString.append("<i class=\"fa " + menuItem.getIcon() +"\"></i> <span>"+ menuItem.getName() +"</span>\n");
				menuString.append("</a>");
				menuString.append("</li>");
			}

		}

		return menuString.toString();

	}

	private static String getLeftMenu(Menu menuItem, int level, List<Menu> menuList, int first){


		StringBuffer menuString = new StringBuffer();
		String href = "";
		if (!menuItem.hasPermisson())
			return "";

		if (level > 1) {// level 为1是top菜单

			ServletContext context = SpringContextHolder.getBean(ServletContext.class);



			if (menuItem.getHref() != null && menuItem.getHref().length() > 0) {// 如果是子节点

				if (menuItem.getHref().startsWith("http://") || menuItem.getHref().startsWith("https://")) {// 如果是互联网资源
					href = menuItem.getHref();
				} else if (menuItem.getHref().endsWith(".html")) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + menuItem.getHref();
				} else if (menuItem.getHref().startsWith("ref:") ) {// 如果是静态资源并且不是ckfinder.html，直接访问不加adminPath
					href = context.getContextPath() + menuItem.getHref().substring(4);
				} else {
					href = context.getContextPath() + SpringContextHolder.getBean(JeePlusProperites.class).getAdminPath() + menuItem.getHref();
				}


				String icon = menuItem.getIcon();
				if(level == 2){
					if(StringUtils.isBlank(icon)){
						icon =  DEFAULT_ICON;
					}
				}
				if("blank".equals(menuItem.getTarget())){
					menuString.append("<li><a class=\"J_menuItem\" href=\"" + href + "\" target=\"_blank\">" + (StringUtils.isBlank(icon)?"":("<i class=\"fa " + icon + "\"></i>"))+"<span> "
							+ menuItem.getName() + " </span></a></li>\n");
				}else{
					menuString.append("<li><a class=\"J_menuItem\"  href=\"" + href +"\"  >"+ (StringUtils.isBlank(icon)?"":("<i class=\"fa " + icon + "\"></i>"))+"<span> "
							+ menuItem.getName() + " </span></a></li>\n");
				}

			}
		}

		if ((menuItem.getHref() == null || menuItem.getHref().trim().equals("")) && menuItem.getIsShow().equals("1")) {// 如果是父节点且显示
			if (level == 0) {// 如果是功能菜单
				int flag = 0;
				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getLeftMenu(child, level + 1, menuList, ++flag));
					}
				}
			}

			if(level == 1){
				if(first == 1){
					menuString.append("<div class=\"tab-pane show active\" id=\"id"+menuItem.getId()+"\">");
				}else{
					menuString.append("<div class=\"tab-pane show\" id=\"id"+menuItem.getId()+"\">");
				}
				menuString.append("<li class=\"text-muted menu-title\">"+menuItem.getName()+"</li>");
				menuString.append("<ul>");
				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getLeftMenu(child, level + 1, menuList, -1));
					}
				}
				menuString.append("</ul>");
				menuString.append("</div>");
			}

			if (level > 1) {// 不是功能菜单
				String icon = menuItem.getIcon();
				if(level == 2){
					if(StringUtils.isBlank(icon)){
						icon =  DEFAULT_ICON;
					}
				}
				menuString.append("<li class=\"has-sub\">\n");
				menuString.append(" <a href=\"javascript:;\" class=\"waves-effect\">  "+ (StringUtils.isBlank(icon)?"":("<i class=\"fa " + icon + "\"></i>"))+"<span> "
						+ menuItem.getName() + " </span><span class=\"menu-arrow\"></span></a>\n");
				menuString.append(
						"  <ul>");

				for (Menu child : menuList) {
					if (child.getParentId().equals(menuItem.getId()) && child.getIsShow().equals("1")) {
						menuString.append(getLeftMenu(child, level + 1, menuList, -1));
					}
				}
				menuString.append("</ul>\n");
				menuString.append("</li>\n");
			}

		}

		return menuString.toString();
	}

}
