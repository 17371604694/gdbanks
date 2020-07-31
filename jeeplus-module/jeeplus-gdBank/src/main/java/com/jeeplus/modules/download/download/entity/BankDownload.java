/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.download.download.entity;

import java.util.Date;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 文件下载Entity
 * @author cheny
 * @version 2019-12-31
 */
public class BankDownload extends DataEntity<BankDownload> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 附件名称
	private String type;		// 附件类型
	private String dept;		// 部门
	private String useType;		// 使用类型
	private String accessorys;		// 附件
	private String reservec;		// 预留 
	private String author;		// 上传人
	private String officename;		// 作者部门
	private Date beginCreateDate;		// 开始 创建时间
	private Date endCreateDate;		// 结束 创建时间
	
	public BankDownload() {
		super();
	}

	public BankDownload(String id){
		super(id);
	}

	@ExcelField(title="附件名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="附件类型", align=2, sort=8)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="部门", fieldType=String.class, value="", align=2, sort=9)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="使用类型", dictType="use_type", align=2, sort=10)
	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}
	
	@ExcelField(title="附件", align=2, sort=11)
	public String getAccessorys() {
		return accessorys;
	}

	public void setAccessorys(String accessorys) {
		this.accessorys = accessorys;
	}
	
	@ExcelField(title="预留 ", align=2, sort=12)
	public String getReservec() {
		return reservec;
	}

	public void setReservec(String reservec) {
		this.reservec = reservec;
	}
	
	@ExcelField(title="上传人", align=2, sort=13)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@ExcelField(title="作者部门", align=2, sort=14)
	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}
	
	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	
	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
		
}