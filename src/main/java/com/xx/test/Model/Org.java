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
import javax.persistence.JoinTable;
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
	
	private String name;
	
	private String displayName;
	
	//机构标识
	private String ou;
	
	//上级机构标识
	private String pou;
	
	private Integer pbcOrder;
	
	//启用标识，1为启用，0为停用
	private Integer status;
	
	//机构编码
	private String orgNumber;
	
	//地区编码
	private String areaCode;
	
	//0机构，1部门
	private int ouType;
	
	//金融机构编码
	private String orgCode;
	
	//人力资源编码
	private String pbcCode;
	
	@OneToMany(mappedBy="org")
	private List<UserInfo> userInfoList;
	
    
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.EAGER,optional=true)
	private Org parentOrg;
	
	@OneToMany(mappedBy="parentOrg")
	private List<Org> childOrgs;
 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}
 

	public Integer getPbcOrder() {
		return pbcOrder;
	}

	public void setPbcOrder(Integer pbcOrder) {
		this.pbcOrder = pbcOrder;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgNumber() {
		return orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public int getOuType() {
		return ouType;
	}

	public void setOuType(int ouType) {
		this.ouType = ouType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}

	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}

	public Org getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(Org parentOrg) {
		this.parentOrg = parentOrg;
	}

	public List<Org> getChildOrgs() {
		return childOrgs;
	}

	public void setChildOrgs(List<Org> childOrgs) {
		this.childOrgs = childOrgs;
	}
	
	

	public String getPbcCode() {
		return pbcCode;
	}

	public void setPbcCode(String pbcCode) {
		this.pbcCode = pbcCode;
	}

	private Map<String,String> getOrgMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("name", name);
		   map.put("displayName", displayName);
		   map.put("parentName", parentOrg.getName());
		   map.put("parentId", String.valueOf(parentOrg.getId()));
		   map.put("ouType", String.valueOf(ouType));
		   map.put("pbcOrder", String.valueOf(pbcOrder));
		   return  map;
	}
	
	public String getOrgJson(){
		return JsonUtils.getJsonString(getOrgMap());
	}
 
	
	
}
