package com.xx.test.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_registerUser")
public class RegisterUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String userName;
	
	
	private String idcard;
	
	@ManyToOne
	private Org org;
	
	private String tel;
	
	//管理机构id
	private Long manageOrgId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
 

	public Long getManageOrgId() {
		return manageOrgId;
	}

	public void setManageOrgId(Long manageOrgId) {
		this.manageOrgId = manageOrgId;
	}

	private Map<String,String> getRegisteUserMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("userName", userName);
		   map.put("tel", tel);
		   map.put("idcard", idcard);
		   map.put("orgName", org.getOrgName());
		   return  map;
	}
	
	
	public String getRegisteUserJson(){
		return JsonUtils.getJsonString(getRegisteUserMap());
	}

}
