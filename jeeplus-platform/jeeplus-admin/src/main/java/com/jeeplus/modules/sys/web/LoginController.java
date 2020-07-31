/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.CookieUtils;
import com.jeeplus.common.utils.IdGen;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.config.properties.JeePlusProperites;
import com.jeeplus.core.security.shiro.session.SessionDAO;
import com.jeeplus.core.servlet.ValidateCodeServlet;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.security.FormAuthenticationFilter;
import com.jeeplus.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.sys.utils.ConfigUtils;
import com.jeeplus.modules.sys.utils.LogUtils;
import com.jeeplus.modules.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.Configuration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 登录Controller
 * @author jeeplus
 * @version 2016-5-31
 */
@Api(value = "LoginController", description = "登录控制器")
@Controller
public class LoginController extends BaseController
{
    
    @Autowired
    private SessionDAO sessionDAO;


    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private SystemService systemService;

    @Autowired
    JeePlusProperites jeePlusProperites;

    /**
     * 管理登录
     * @throws IOException 
     */
    @ApiOperation(notes = "login", httpMethod = "POST", value = "用户登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "string"),
        @ApiImplicitParam(name = "mobileLogin", value = "接口标志", required = true, paramType = "query", dataType = "string")})
    @RequestMapping(value = {"${adminPath}/login", ""})
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Principal principal = UserUtils.getPrincipal();
        
        if (logger.isDebugEnabled())
        {
            logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        
        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin())
        {
            System.out.println("我已经登录-------------");



            return "redirect:" + adminPath;
        }
        
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
        // 如果是手机没有登录跳转到到login，则返回JSON字符串
        if (savedRequest != null)
        {
            String queryStr = savedRequest.getQueryString();
            if (queryStr != null && (queryStr.contains("__ajax") || queryStr.contains("mobileLogin")))
            {
                AjaxJson j = new AjaxJson();
                j.setSuccess(false);
                j.setErrorCode("0");
                j.setMsg("没有登录!");
                return renderString(response, j);
            }
        }
        String typeId = request.getParameter("typeId");
        System.out.println(typeId);
        request.getSession().setAttribute("typeId",typeId);
        FormAuthenticationFilter.typeIcok=request.getSession().getAttribute("typeId")+"";



        return "modules/sys/login/sysLogin";
//        return "modules/loginCustom/index";
    }





    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Principal principal = UserUtils.getPrincipal();
        
        // 如果已经登录，则跳转到管理首页
        if (principal != null)
        {
            return "redirect:" + adminPath;
        }
        
        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
        
        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null"))
        {
            message = "用户或密码错误, 请重试.";
        }
        
        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
//        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}", sessionDAO.getActiveSessions(false).size(), message, exception);
        }


        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception))
        {
            Object countStr = request.getSession().getAttribute(username);
//            if(countStr == null){
//                countStr = 0;
//            }
//            Integer count =  1;
//            if(!"".equals(countStr) && null!=countStr){
//                count =  Integer.parseInt(countStr.toString());
//            }
//            if(Integer.parseInt(countStr.toString())>=5){
//                count = 1;
//            }
//            count++;
            Integer count = 1;
            if(countStr != null){
                if(!"".equals(countStr) && null!=countStr){
                    count =  Integer.parseInt(countStr.toString());

                }
            }

            if(count<5){
                switch (count){
                    case 1:
                        message = "密码错误次数达到5次,账号将锁定.当前剩余次数4次";
                        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
                        break;
//                        throw new AuthenticationException("msg:密码错误次数达到5次,账号将冻结.当前剩余次数3次");
                    case 2:
                        message = "密码错误次数达到5次,账号将锁定.当前剩余次数3次";
                        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
                        break;
//                        throw new AuthenticationException("msg:密码错误次数达到5次,账号将锁定.当前剩余次数3次");
                    case 3:
                        message = "密码错误次数达到5次,账号将锁定.当前剩余次数2次";
                        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
                        break;
//                        throw new AuthenticationException("msg:密码错误次数达到5次,账号将锁定.当前剩余次数2次");
                    case 4:
                        message = "密码错误次数达到5次,账号将锁定.当前剩余次数1次";
                        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
                        break;
                }
            }
            count++;
            request.getSession().setAttribute(username,count);
            model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
        }


        // 非授权异常，登录失败，验证码加1。
//        if (!UnauthorizedException.class.getName().equals(exception))
//        {
//            model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
//        }
        
        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
        
        // 如果是手机登录，则返回JSON字符串
        if (mobile)
        {
            AjaxJson j = new AjaxJson();
            j.setSuccess(false);
            j.setMsg(message);
            j.put("username", username);
            j.put("name", "");
            j.put("mobileLogin", mobile);
            j.put("JSESSIONID", "");
            return renderString(response, j.getJsonStr());
        }
        
        return "modules/sys/login/sysLogin";
    }
    
    /**
     * 管理登录
     * @throws IOException 
     */
    @RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        Principal principal = UserUtils.getPrincipal();
        // 如果已经登录，则跳转到管理首页
        if (principal != null)
        {
            UserUtils.getSubject().logout();
            CacheUtils.remove(principal.getLoginName());
            
        }
        // 如果是手机客户端退出跳转到login，则返回JSON字符串
        String ajax = request.getParameter("__ajax");
        if (ajax != null)
        {
            model.addAttribute("success", "1");
            model.addAttribute("msg", "退出成功");

            return renderString(response, model);
        }
        CookieUtils.setCookie(response,"user", "null");
//        return "redirect:" + adminPath + "/login";
        return "redirect:"+adminPath+"/logins/indexNew";
    }

    /**
     * 管理登录
     * @throws IOException
     */
    @RequestMapping(value = "${adminPath}/logouts", method = RequestMethod.GET)
    @ResponseBody
    public AjaxJson logouts(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        AjaxJson ajaxJson = new AjaxJson();
        Principal principal = UserUtils.getPrincipal();
        // 如果已经登录，则跳转到管理首页
        if (principal != null)
        {
            UserUtils.getSubject().logout();
            CacheUtils.remove(principal.getLoginName());

        }
        // 如果是手机客户端退出跳转到login，则返回JSON字符串
        String ajax = request.getParameter("__ajax");
        if (ajax != null)
//        {
//            model.addAttribute("success", "1");
//            model.addAttribute("msg", "退出成功");
//
//            return renderString(response, model);
//        }
        CookieUtils.setCookie(response,"user", "null");
//        return "redirect:" + adminPath + "/login";
        return ajaxJson;
    }



    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        Principal principal = UserUtils.getPrincipal();
        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);
        
        if (logger.isDebugEnabled())
        {
            logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
        }

        //登录成功保存用户信息到cookie
        CookieUtils.setCookie(response,"user", JSON.toJSONString(principal));

        // 如果是手机登录，则返回JSON字符串
        if (principal.isMobileLogin())
        {
                return renderString(response, principal);
        }



        if (UserUtils.getMenuList().size() == 0)
        {
            return "modules/sys/login/noAuth";
        }
        else
        {

            screenJSon();
            return "modules/loginCustom/indexNew";

        }
        
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
        System.out.println("用户菜单缓存");


    }


    /**
     * 是否是验证码登录
     * @param useruame 用户名
     * @param isFail 计数加1
     * @param clean 计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean)
    {
        Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
        if (loginFailMap == null)
        {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null)
        {
            loginFailNum = 0;
        }
        if (isFail)
        {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean)
        {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }



    /**
     * 首页
     * @throws IOException
     */
    @RequestMapping(value = "${adminPath}/sysIndex")
    public String sysIndenx(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {
        return "modules/sys/login/sysIndex";
    }

    /**
     * 首页
     * @throws IOException
     */
    @RequestMapping(value = "${adminPath}/home")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException
    {

        return "modules/sys/login/sysHome";

    }

}
