package com.jeeplus.modules.bankimagemanagement.image.web;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.backgroundmanage.imagemanage.entity.BankImage;
import com.jeeplus.modules.bankimagemanagement.image.entity.Image;
import com.jeeplus.modules.bankimagemanagement.image.service.BankImageUplaoService;
import com.jeeplus.modules.utils.ImgUpLoadTool;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 图片上传控制器
 */
@RequestMapping(value = "${adminPath}/gdBank/uplaod")
@Controller
public class ImageUplaoController extends BaseController {

    @Autowired
    private BankImageUplaoService bankImageUplaoService;


    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "image")
    public AjaxJson uplaoImage(@RequestParam ("file") MultipartFile file, HttpServletRequest request){
        AjaxJson ajaxJson = new AjaxJson();
        Image image = new Image();
        ImgUpLoadTool imgUpLoadTool = new ImgUpLoadTool();
        String imageType = request.getParameter("imageType");
        try {
            ajaxJson = imgUpLoadTool.uploadImg(file,request);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(ajaxJson.isSuccess()){
            String path = ajaxJson.getUrl();
            String name = ajaxJson.getName();
            image.setImageName(name);
            image.setImagePath(path);
            image.setImageType(imageType);
            bankImageUplaoService.save(image);
            return ajaxJson;
        }else {
            return ajaxJson;
        }

    }


}
