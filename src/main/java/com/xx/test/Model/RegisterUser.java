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
	
    private Long parentOrgId;
	
	private String tel;
	
	private int pbcGender;
	
	private String pbcPosition;
	
	private String pbcPoliticalLevel;
	
	private Long manageOrgId;
	
	private String partyMember;
	
	//0为党员，1为非党员
	private int partyLog;

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

 

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
 
	public int getPbcGender() {
		return pbcGender;
	}

	public void setPbcGender(int pbcGender) {
		this.pbcGender = pbcGender;
	}

	public String getPbcPosition() {
		return pbcPosition;
	}

	public void setPbcPosition(String pbcPosition) {
		this.pbcPosition = pbcPosition;
	}

	public String getPbcPoliticalLevel() {
		return pbcPoliticalLevel;
	}

	public void setPbcPoliticalLevel(String pbcPoliticalLevel) {
		this.pbcPoliticalLevel = pbcPoliticalLevel;
	}
	
	

	public Long getManageOrgId() {
		return manageOrgId;
	}

	public void setManageOrgId(Long manageOrgId) {
		this.manageOrgId = manageOrgId;
	}
	
	

	public String getPartyMember() {
		return partyMember;
	}

	public void setPartyMember(String partyMember) {
		this.partyMember = partyMember;
	}

	public int getPartyLog() {
		return partyLog;
	}

	public void setPartyLog(int partyLog) {
		this.partyLog = partyLog;
	}
	
	

	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	private Map<String,String> getRegisteUserMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("userName", userName);
		   map.put("tel", tel);
		   map.put("idcard", idcard);
		   return map;
	}
	
	
	public String getRegisteUserJson(){
		return JsonUtils.getJsonString(getRegisteUserMap());
	}

}
