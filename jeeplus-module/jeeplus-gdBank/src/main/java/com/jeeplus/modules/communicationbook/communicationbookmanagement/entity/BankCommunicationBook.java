/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.communicationbook.communicationbookmanagement.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 通讯录管理Entity
 * @author cheny
 * @version 2019-11-29
 */
public class BankCommunicationBook extends DataEntity<BankCommunicationBook> {

	private static final long serialVersionUID = 1L;
	private String name;        // 姓名
	private String phone;        // 电话
	private String address;        // 地址
	private String office;    // 归属部门
	private String no;        // 工号
	private String email;    // 邮箱
	private String mobile;    // 手机
	private String psotes;		//岗位
	private String statement;  //职责
	private String loginName;

	public BankCommunicationBook() {
		super();
	}

	public BankCommunicationBook(String id) {
		super(id);
	}

	@ExcelField(title = "登录名", align = 2, sort = 27)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@ExcelField(title = "姓名", align = 2, sort = 7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title = "电话", align = 2, sort = 8)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ExcelField(title = "部门", align = 2, sort = 10)
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	@ExcelField(title = "工号", align = 2, sort = 11)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@ExcelField(title = "邮箱", align = 2, sort = 12)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title = "手机", align = 2, sort = 13)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "地址", align = 2, sort = 9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title = "岗位", align = 2, sort = 14)
	public String getPsotes() {
		return psotes;
	}

	public void setPsotes(String psotes) {
		this.psotes = psotes;
	}

	@ExcelField(title = "工作职责", align = 2, sort = 15)
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}
}

