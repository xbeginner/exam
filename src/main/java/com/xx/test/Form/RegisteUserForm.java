package com.xx.test.Form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RegisteUserForm {
   
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String tel;
	
	@NotEmpty
	private String idcard;
	
 

	 
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	 
	
	 
	
}
