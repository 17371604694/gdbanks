/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifiertrue.verifiertrues.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 添加审核人审核栏目Entity
 * @author chenl
 * @version 2019-12-09
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class BankVerifierTrue extends TreeEntity<BankVerifierTrue> {
	
	private static final long serialVersionUID = 1L;
	private String columnName;		// 栏目名称
	private String verifyPerson;		// 审核人
	private String columnId;		// 栏目id

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public BankVerifierTrue() {
		super();
	}

	public BankVerifierTrue(String id){
		super(id);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getVerifyPerson() {
		return verifyPerson;
	}

	public void setVerifyPerson(String verifyPerson) {
		this.verifyPerson = verifyPerson;
	}
	
	public  BankVerifierTrue getParent() {
			return parent;
	}
	
	@Override
	public void setParent(BankVerifierTrue parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}