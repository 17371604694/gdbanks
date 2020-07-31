package com.jeeplus.modules.sys.web;


import com.jeeplus.modules.sys.entity.Menu;
import com.jeeplus.modules.sys.mapper.MenuMapper;
import com.jeeplus.modules.sys.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("${adminPath}/logins")
public class LoginIndexController {
    @Autowired
    private OfficeService officeService;
    @Autowired
    private MenuMapper menuMapper;

    /**
    * 静态html首页
    * */
    @RequestMapping(value = {"/index"})
    public String loginSi(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/index";
    }

    /**
     * 静态html首页  indexNew新首页
     * */
    @RequestMapping(value = {"/indexNew"})
    public String loginSiN(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/indexNew";
    }
    /**
     * 静态html首页  indexNewIe新首页
     * */
    @RequestMapping(value = {"/indexNewIe"})
    public String loginSiNIe(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/indexNewIe";
    }


    /**
     * 静态html列表
     * */
    @RequestMapping(value = {"/list"})
    public String list(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/list";
    }

    /**
     * 静态html部门页
     * */
    @RequestMapping(value = {"/dept"})
    public String dept(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/dept";
    }


    /**
     * 静态html下载页
     * */
    @RequestMapping(value = {"/down"})
    public String down(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/down";
    }

    /**
     * 静态html详情页
     * */
    @RequestMapping(value = {"/content"})
    public String content(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        model.addAttribute("id",id);
        return "modules/loginCustom/content";
    }

    /**
     * 静态html分组页
     * */
    @RequestMapping(value = {"/grouplist"})
    public String grouplist(HttpServletRequest request, HttpServletResponse response, Model model){
        String navId = request.getParameter("id");
        Menu menu = menuMapper.get(navId);
        model.addAttribute("menu",menu);
        return "modules/loginCustom/grouplist";
    }

    /**
     * 静态html公共头页
     * */
    @RequestMapping(value = {"/header"})
    public String header(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/header";
    }

    /**
     * 静态html栏目数据列表
     * */
    @RequestMapping(value = {"/programadatalist"})
    public String programadatalist(HttpServletRequest request, HttpServletResponse response,Model model){
        String name  = request.getParameter("name");
        String id = request.getParameter("id");
        model.addAttribute("name",name);
//        model.addAttribute("id",menuMapper.findByName(name).getId());
        model.addAttribute("id",id);
        return "modules/loginCustom/programadatalist";
    }

    /**
     * 通讯录
     * */
    @RequestMapping(value = {"/addressbook"})
    public String addressbook(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/addressbook";
    }

    /**
     * 员工心声、建言献策列表页面
     * */
    @RequestMapping(value = {"/voice"})
    public String voice(HttpServletRequest request, HttpServletResponse response,Model model){
        String type =request.getParameter("type");
        model.addAttribute("type",type);
        return "modules/loginCustom/voicelist";
    }

    /**
     * 员工心声详情页面
     * */
    @RequestMapping(value = {"/voicecontent"})
    public String voicecontent(HttpServletRequest request, HttpServletResponse response,Model model){
        String id = request.getParameter("id");
        model.addAttribute("id",id);
        return "modules/loginCustom/voicecontent";
    }
    /**
     * 会议
     * */
    @RequestMapping(value = {"/bankConferenList"})
    public String bankConferenList(HttpServletRequest request, HttpServletResponse response){
        return "modules/loginCustom/bankConferenList";
    }

    /**
     * 投票详情页面
     * */
    @RequestMapping(value = {"/toupiao"})
    public String toupiao(HttpServletRequest request, HttpServletResponse response,Model model){
        String tpId = request.getParameter("id");
        model.addAttribute("id",tpId);
        return "modules/loginCustom/toupiao";
    }

}
