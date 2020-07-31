/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.backgroundmanage.imagemanage.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.backgroundmanage.imagemanage.entity.BankImage;
import com.jeeplus.modules.backgroundmanage.imagemanage.service.BankImageService;

/**
 * 背景Controller
 * @author cheny
 * @version 2019-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/backgroundmanage/imagemanage/bankImage")
public class BankImageController extends BaseController {

	@Autowired
	private BankImageService bankImageService;
	
	@ModelAttribute
	public BankImage get(@RequestParam(required=false) String id) {
		BankImage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankImageService.get(id);
		}
		if (entity == null){
			entity = new BankImage();
		}
		return entity;
	}
	
	/**
	 * 背景列表页面
	 */
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankImage bankImage, Model model) {
		model.addAttribute("bankImage", bankImage);
		return "modules/backgroundmanage/imagemanage/bankImageList";
	}
	
		/**
	 * 背景列表数据
	 */
	@ResponseBody
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankImage bankImage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankImage> page = bankImageService.findPage(new Page<BankImage>(request, response), bankImage); 
		return getBootstrapData(page);
	}

	/**
	 * 增加背景表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"backgroundmanage:imagemanage:bankImage:view","backgroundmanage:imagemanage:bankImage:add","backgroundmanage:imagemanage:bankImage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankImage bankImage, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankImage", bankImage);
		return "modules/backgroundmanage/imagemanage/bankImageForm";
	}

	/**
	 * 设置背景图片
	 * params:
	 *
	 */
	@ResponseBody
	@RequiresPermissions(value={"backgroundmanage:imagemanage:bankImage:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/editform")
	public AjaxJson editform(BankImage bankImage, Model model) {
		AjaxJson ajaxJson = new AjaxJson();
		if(bankImage!=null){
			String path = bankImage.getImagePath();
			String imgaeType = bankImage.getImageType();
			if("1".equals(bankImage.getImageType())){
				if(path!=null){
					bankImageService.updataBackgroundImage(path,imgaeType);
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("更换背景图片成功!");
				}else {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("更换背景图片失败！图片不存在！请重新上传图片！");
				}
			}
			if("2".equals(bankImage.getImageType())){
				if(path!=null){
					bankImageService.updataBackgroundImage(path,imgaeType);
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("更换背景图片成功!");
				}else {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("更换背景图片失败！图片不存在！请重新上传图片！");
				}
			}
			if("3".equals(bankImage.getImageType())){
				if(path!=null){
					bankImageService.updataBackgroundImage(path,imgaeType);
					ajaxJson.setSuccess(true);
					ajaxJson.setMsg("更换背景图片成功!");
				}else {
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("更换背景图片失败！图片不存在！请重新上传图片！");
				}
			}
		}
		return ajaxJson;
	}

	/**
	 * 获取背景图片路径
	 * params:
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "getPath")
	public AjaxJson getPath(BankImage bankImage) {
		AjaxJson ajaxJson = new AjaxJson();
		String path = bankImageService.getPath(bankImage);
		ajaxJson.setUrl(path);
		return ajaxJson;
	}

	/**
	 * 查看背景图片表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"backgroundmanage:imagemanage:bankImage:view"},logical=Logical.OR)
	@RequestMapping(value = "form/viewform")
	public String viewform(BankImage bankImage, Model model) {
//		model.addAttribute("mode", mode);
		model.addAttribute("bankImage", bankImage);
		return "modules/backgroundmanage/imagemanage/bankImageviewForm";
	}

	/**
	 * 保存背景
	 */
	@ResponseBody
	@RequiresPermissions(value={"backgroundmanage:imagemanage:bankImage:add","backgroundmanage:imagemanage:bankImage:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankImage bankImage, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankImage);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankImageService.save(bankImage);//保存
		j.setSuccess(true);
		j.setMsg("保存背景成功");
		return j;
	}

	
	/**
	 * 批量删除图片
	 */
	@ResponseBody
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankImageService.delete(bankImageService.get(id));
		}
		j.setMsg("删除背景图片成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankImage bankImage, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "背景"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankImage> page = bankImageService.findPage(new Page<BankImage>(request, response, -1), bankImage);
    		new ExportExcel("背景", BankImage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出背景记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankImage> list = ei.getDataList(BankImage.class);
			for (BankImage bankImage : list){
				try{
					bankImageService.save(bankImage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条背景记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条背景记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入背景失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入背景数据模板
	 */
	@ResponseBody
	@RequiresPermissions("backgroundmanage:imagemanage:bankImage:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "背景数据导入模板.xlsx";
    		List<BankImage> list = Lists.newArrayList(); 
    		new ExportExcel("背景数据", BankImage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}