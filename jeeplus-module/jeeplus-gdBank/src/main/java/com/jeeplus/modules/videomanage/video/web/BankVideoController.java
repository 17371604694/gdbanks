/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.videomanage.video.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.exception.uploadexception.FileTypeNotAllowException;
import com.jeeplus.modules.exception.uploadexception.SizeBeyondException;
import com.jeeplus.modules.utils.FileUploadTool;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.videomanage.video.entity.BankVideo;
import com.jeeplus.modules.videomanage.video.service.BankVideoService;

/**
 * 视频Controller
 * @author cheny
 * @version 2019-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/videomanage/video/bankVideo")
public class BankVideoController extends BaseController {

	@Autowired
	private BankVideoService bankVideoService;
	
	@ModelAttribute
	public BankVideo get(@RequestParam(required=false) String id) {
		BankVideo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankVideoService.get(id);
		}
		if (entity == null){
			entity = new BankVideo();
		}
		return entity;
	}
	
	/**
	 * 视频列表页面
	 */
	@RequiresPermissions("videomanage:video:bankVideo:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankVideo bankVideo, Model model) {
		model.addAttribute("bankVideo", bankVideo);
		return "modules/videomanage/video/bankVideoList";
	}
	
		/**
	 * 视频列表数据
	 */
	@ResponseBody
	@RequiresPermissions("videomanage:video:bankVideo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankVideo bankVideo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankVideo> page = bankVideoService.findPage(new Page<BankVideo>(request, response), bankVideo); 
		return getBootstrapData(page);
	}

	/**
	 * 增加，编辑视频表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"videomanage:video:bankVideo:add","videomanage:video:bankVideo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankVideo bankVideo, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankVideo", bankVideo);
		return "modules/videomanage/video/bankVideoForm";
	}

	/**
	 * 保存视频
	 */
	@ResponseBody
	@RequiresPermissions(value={"videomanage:video:bankVideo:add","videomanage:video:bankVideo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankVideo bankVideo, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankVideo);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankVideoService.save(bankVideo);//保存
		j.setSuccess(true);
		j.setMsg("保存视频成功");
		return j;
	}

	
	/**
	 * 批量删除视频
	 */
	@ResponseBody
	@RequiresPermissions("videomanage:video:bankVideo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankVideoService.delete(bankVideoService.get(id));
		}
		j.setMsg("删除视频成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("videomanage:video:bankVideo:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankVideo bankVideo, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "视频"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankVideo> page = bankVideoService.findPage(new Page<BankVideo>(request, response, -1), bankVideo);
    		new ExportExcel("视频", BankVideo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出视频记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("videomanage:video:bankVideo:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankVideo> list = ei.getDataList(BankVideo.class);
			for (BankVideo bankVideo : list){
				try{
					bankVideoService.save(bankVideo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条视频记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条视频记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入视频失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入视频数据模板
	 */
	@ResponseBody
	@RequiresPermissions("videomanage:video:bankVideo:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "视频数据导入模板.xlsx";
    		List<BankVideo> list = Lists.newArrayList(); 
    		new ExportExcel("视频数据", BankVideo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

   /* @ResponseBody
	@RequiresPermissions(value={"videomanage:video:bankVideo:add","videomanage:video:bankVideo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson upload(@RequestParam("file") MultipartFile file,
							HttpServletRequest request)
			throws IllegalStateException, IOException {
		AjaxJson ajaxJson = new AjaxJson();
		if(file!=null){
			FileUploadTool fileUploadTool = new FileUploadTool();
			BankVideo bankVideo = null;
			try {
				bankVideo = fileUploadTool.createFile(file, request);
			} catch (SizeBeyondException e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(e.getMessage());
			} catch (FileTypeNotAllowException e) {
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(e.getMessage());
			}catch (FileNotFoundException e){
				e.printStackTrace();
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(e.getMessage());
			}
			bankVideoService.save(bankVideo);//保存
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setMsg("上传视频成功！");
		return ajaxJson;
	}*/

	/**
	 * 视频播放页面
	 * params:
	 *
	 */
	@RequiresPermissions(value={"videomanage:video:bankVideo:player"},logical=Logical.OR)
	@RequestMapping(value = "player/{mode}")
	public String player(@PathVariable String mode, BankVideo bankVideo, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankVideo", bankVideo);
		return "modules/videomanage/video/bankVideoPlayer";
	}
}