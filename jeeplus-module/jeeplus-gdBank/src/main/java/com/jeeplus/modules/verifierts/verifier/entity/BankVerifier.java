/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.verifierts.verifier.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 添加审核人审核栏目Entity
 * @author chenl
 * @version 2019-11-21
 */
public class BankVerifier extends DataEntity<BankVerifier> {
	
	private static final long serialVersionUID = 1L;
	private String programa;		// 栏目
	private String verifierPersion;		// 审核人id
	private String programaName; //栏目名称
	private String verifierPersionName; //审核人名称

	public String getProgramaName() {
		return programaName;
	}

	public void setProgramaName(String programaName) {
		this.programaName = programaName;
	}

	public String getVerifierPersionName() {
		return verifierPersionName;
	}

	public void setVerifierPersionName(String verifierPersionName) {
		this.verifierPersionName = verifierPersionName;
	}



	public BankVerifier() {
		super();
	}

	public BankVerifier(String id){
		super(id);
	}

	@ExcelField(title="栏目", align=2, sort=7)
	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}
	
	@ExcelField(title="审核人id", align=2, sort=8)
	public String getVerifierPersion() {
		return verifierPersion;
	}

	public void setVerifierPersion(String verifierPersion) {
		this.verifierPersion = verifierPersion;
	}
	
}