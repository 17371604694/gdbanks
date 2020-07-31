/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.download.download.web;

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
import com.jeeplus.modules.download.download.entity.BankDownload;
import com.jeeplus.modules.download.download.service.BankDownloadService;

/**
 * 文件下载Controller
 * @author cheny
 * @version 2019-12-31
 */
@Controller
@RequestMapping(value = "${adminPath}/download/download/bankDownload")
public class BankDownloadController extends BaseController {

	@Autowired
	private BankDownloadService bankDownloadService;
	
	@ModelAttribute
	public BankDownload get(@RequestParam(required=false) String id) {
		BankDownload entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankDownloadService.get(id);
		}
		if (entity == null){
			entity = new BankDownload();
		}
		return entity;
	}
	
	/**
	 * 文件下载列表页面
	 */
	@RequiresPermissions("download:download:bankDownload:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankDownload bankDownload, Model model) {
		model.addAttribute("bankDownload", bankDownload);
		return "modules/download/download/bankDownloadList";
	}
	
		/**
	 * 文件下载列表数据
	 */
	@ResponseBody
	@RequiresPermissions("download:download:bankDownload:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankDownload bankDownload, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankDownload> page = bankDownloadService.findPage(new Page<BankDownload>(request, response), bankDownload); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑文件下载表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"download:download:bankDownload:view","download:download:bankDownload:add","download:download:bankDownload:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankDownload bankDownload, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankDownload", bankDownload);
		return "modules/download/download/bankDownloadForm";
	}

	/**
	 * 保存文件下载
	 */
	@ResponseBody
	@RequiresPermissions(value={"download:download:bankDownload:add","download:download:bankDownload:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankDownload bankDownload, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankDownload);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankDownloadService.save(bankDownload);//保存
		j.setSuccess(true);
		j.setMsg("保存文件下载成功");
		return j;
	}

	
	/**
	 * 批量删除文件下载
	 */
	@ResponseBody
	@RequiresPermissions("download:download:bankDownload:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankDownloadService.delete(bankDownloadService.get(id));
		}
		j.setMsg("删除文件下载成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("download:download:bankDownload:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankDownload bankDownload, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文件下载"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankDownload> page = bankDownloadService.findPage(new Page<BankDownload>(request, response, -1), bankDownload);
    		new ExportExcel("文件下载", BankDownload.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出文件下载记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("download:download:bankDownload:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankDownload> list = ei.getDataList(BankDownload.class);
			for (BankDownload bankDownload : list){
				try{
					bankDownloadService.save(bankDownload);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文件下载记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条文件下载记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入文件下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入文件下载数据模板
	 */
	@ResponseBody
	@RequiresPermissions("download:download:bankDownload:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文件下载数据导入模板.xlsx";
    		List<BankDownload> list = Lists.newArrayList(); 
    		new ExportExcel("文件下载数据", BankDownload.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}