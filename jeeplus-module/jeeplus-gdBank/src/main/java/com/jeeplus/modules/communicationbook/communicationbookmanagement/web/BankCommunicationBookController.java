/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.communicationbook.communicationbookmanagement.web;

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
import com.jeeplus.modules.communicationbook.communicationbookmanagement.entity.BankCommunicationBook;
import com.jeeplus.modules.communicationbook.communicationbookmanagement.service.BankCommunicationBookService;

/**
 * 通讯录管理Controller
 * @author cheny
 * @version 2019-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/communicationbook/communicationbookmanagement/bankCommunicationBook")
public class BankCommunicationBookController extends BaseController {

	@Autowired
	private BankCommunicationBookService bankCommunicationBookService;
	
	@ModelAttribute
	public BankCommunicationBook get(@RequestParam(required=false) String id) {
		BankCommunicationBook entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bankCommunicationBookService.get(id);
		}
		if (entity == null){
			entity = new BankCommunicationBook();
		}
		return entity;
	}
	
	/**
	 * 通讯录列表页面
	 */
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:list")
	@RequestMapping(value = {"list", ""})
	public String list(BankCommunicationBook bankCommunicationBook, Model model) {
		model.addAttribute("bankCommunicationBook", bankCommunicationBook);
		return "modules/communicationbook/communicationbookmanagement/bankCommunicationBookList";
	}
	
		/**
	 * 通讯录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BankCommunicationBook bankCommunicationBook, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BankCommunicationBook> page = bankCommunicationBookService.findPage(new Page<BankCommunicationBook>(request, response), bankCommunicationBook); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑通讯录表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"communicationbook:communicationbookmanagement:bankCommunicationBook:view","communicationbook:communicationbookmanagement:bankCommunicationBook:add","communicationbook:communicationbookmanagement:bankCommunicationBook:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BankCommunicationBook bankCommunicationBook, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bankCommunicationBook", bankCommunicationBook);
		return "modules/communicationbook/communicationbookmanagement/bankCommunicationBookForm";
	}

	/**
	 * 保存通讯录
	 */
	@ResponseBody
	@RequiresPermissions(value={"communicationbook:communicationbookmanagement:bankCommunicationBook:add","communicationbook:communicationbookmanagement:bankCommunicationBook:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BankCommunicationBook bankCommunicationBook, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bankCommunicationBook);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		bankCommunicationBookService.save(bankCommunicationBook);//保存
		j.setSuccess(true);
		j.setMsg("保存通讯录成功");
		return j;
	}

	
	/**
	 * 批量删除通讯录
	 */
	@ResponseBody
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bankCommunicationBookService.delete(bankCommunicationBookService.get(id));
		}
		j.setMsg("删除通讯录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BankCommunicationBook bankCommunicationBook, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "通讯录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BankCommunicationBook> page = bankCommunicationBookService.findPage(new Page<BankCommunicationBook>(request, response, -1), bankCommunicationBook);
    		new ExportExcel("通讯录", BankCommunicationBook.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出通讯录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BankCommunicationBook> list = ei.getDataList(BankCommunicationBook.class);
			for (BankCommunicationBook bankCommunicationBook : list){
				try{
					bankCommunicationBookService.save(bankCommunicationBook);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条通讯录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条通讯录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入通讯录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入通讯录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("communicationbook:communicationbookmanagement:bankCommunicationBook:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "通讯录数据导入模板.xlsx";
    		List<BankCommunicationBook> list = Lists.newArrayList(); 
    		new ExportExcel("通讯录数据", BankCommunicationBook.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}