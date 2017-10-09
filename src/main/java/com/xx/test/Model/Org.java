package com.xx.test.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_org")
public class Org implements Serializable{

	  
	  /**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String orgName;
	
	private String tel;
	
	private String address;
	
	private String master;
	
	private String masterTel;
	
	@OneToMany(mappedBy="org")
	private List<UserInfo> userInfoList;
	
 
    private Long parentOrgId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getMasterTel() {
		return masterTel;
	}

	public void setMasterTel(String masterTel) {
		this.masterTel = masterTel;
	}

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public String getOrgJson(){
		return JsonUtils.getJsonString(getOrgMap());
	}
	
	
	
  
	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	private Map<String,String> getOrgMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   
		   map.put("id", String.valueOf(id));
		   map.put("orgName", orgName);
		   map.put("tel", tel);
		   map.put("address", address);
		   map.put("master",master);
		   map.put("masterTel",masterTel);
		   return  map;
	}
	
	
}
