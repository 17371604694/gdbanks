package com.jeeplus.modules.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * 图片上传工具类
 */
public class ImgUpLoadTool {

    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public AjaxJson uploadImg(@RequestParam("file") MultipartFile file,
                              HttpServletRequest request)
            throws IllegalStateException, IOException {
        AjaxJson ajaxJson = new AjaxJson();
        // 判断上传的文件是否为空
        boolean isEmpty = file.isEmpty();
        if (isEmpty) {
            throw new RuntimeException("上传失败！上传的文件为空！");
        }

        // 检查文件大小
        long fileSize = file.getSize();
        System.out.println("\tsize=" + fileSize);
        if (fileSize > 5 * 1024 * 1024) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("上传失败！上传的文件大小超出了限制！最大为5M！");
            throw new RuntimeException("");
        }

        // 检查文件MIME类型
        String contentType = file.getContentType();
        System.out.println("\tcontentType=" + contentType);
        List<String> types = new ArrayList<String>();
        types.add("image/jpeg");
        types.add("image/png");
        types.add("image/gif");
//        types.add("image/bmp");
//        types.add("image/tiff");
        if (!types.contains(contentType)) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("上传失败！不允许上传此类型的文件！仅支持jpeg、png、gif类型图片!");
//            throw new RuntimeException("上传失败！不允许上传此类型的文件！");
        }

        // 准备文件夹
//        String parentDir = request.getServletContext().getRealPath("upload");//获取TomCat储存路径
        // request.getSession().getServletContext().getRealPath("");
        // request.getRealPath("");
        String rootPath = "C:/gdBanks/";
        String uid = UserUtils.getUser().getId()+"/";
        String fileType = "image/";
        String realPathDirStr = rootPath+uid+fileType;//文件真实存放路径
        File parent = new File(realPathDirStr);
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        String relativePath = "/files/"+uid+fileType+"/"+originalFilename;//文件存放相对路径
        // 确定最终保存时使用的文件
//        String filename = UUID.randomUUID().toString();
//        String suffix = "";
//        int beginIndex = originalFilename.lastIndexOf(".");
//        if (beginIndex != -1) {
//            suffix = originalFilename.substring(beginIndex);
//        }

        // 执行保存文件
        File dest = new File(parent, "/"+originalFilename);
        try{
            file.transferTo(dest);
            ajaxJson.setSuccess(true);
            ajaxJson.setMsg("图片保存成功！");
            ajaxJson.setUrl(relativePath);//返回图片保存路径
            ajaxJson.setName(originalFilename);//返回文件名称
        }catch (Exception e){
            e.printStackTrace();
        }
        return ajaxJson;
    }

}