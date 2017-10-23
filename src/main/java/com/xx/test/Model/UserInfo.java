package com.xx.test.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_userinfo")
public class UserInfo implements Serializable {
	
    private final static Map<String,String> POSITIONMAP = new HashMap<String,String>(){{
    	put("217A", "厅长");
    	put("217B", "副厅长");
    	put("218A", "司长");
    	put("011K", "副调研员");
    	put("218B", "副司长");
    	put("219A", "处长");
    	put("219B", "副处长");
    	put("220A", "科长");
    	put("220B", "副科长");
    	put("221A", "科员");
    	put("221S", "主任科员");
    	put("221T", "副主任科员");
    	put("224A", "办事员");
    	put("ZZZZ", "未定职");
    	put("ZZZ1", "合同制人员");
    	put("ZZZ2", "文印员");	
    }};
	
    
    private final static Map<String,String> LEVELMAP = new HashMap<String,String>(){{
    	put("0100","国家公务员职别");
    	put("0111","总理职");
    	put("0112","副总理(国务委员)职");
    	put("0121","省部");
    	put("0122","副省部");
    	put("0131","司局");
    	put("0132","副司局");
    	put("0141","县处");
    	put("0142","副县处");
    	put("0151","正科");
    	put("0152","副科");
    	put("0161","科员");
    	put("0171","办事员");
    	put("0199","未定职");			
    }};

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String userName;
	
	private String password;
	
	private String idcard;
	
	private String tel;
	
	private String uid;
	
	private String ou;
	
	private int pbcGender;
	
	private String pbcPosition;
	
	private String pbcPoliticalLevel;
	
	@Column(columnDefinition="INT default 1")
	private int status;
	
	private Integer pbcOrder;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
	private Org org;
	
	//保存父机构信息
	private Long parentOrgId;
	
	@ManyToOne
	private Role role;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	
 
	public Long getParentOrgId() {
		return parentOrgId;
	}

	public void setParentOrgId(Long parentOrgId) {
		this.parentOrgId = parentOrgId;
	}

	private Map<String,String> getUserMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("userName", userName);
		   if(pbcPoliticalLevel!=null&!"".equals(pbcPoliticalLevel)){
			   map.put("level", LEVELMAP.get(pbcPoliticalLevel));
		   }else{
			   map.put("level", LEVELMAP.get("0199"));
		   }
		   map.put("idcard", idcard);
		   map.put("orgName", org.getName());
		   return  map;
	}
	
	
	public String getUserJson(){
		return JsonUtils.getJsonString(getUserMap());
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
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

	public Integer getPbcOrder() {
		return pbcOrder;
	}

	public void setPbcOrder(Integer pbcOrder) {
		this.pbcOrder = pbcOrder;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	

}
