package com.xx.test.Model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
@Table(name="t_message")
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String content;
	
	private Date createTime;
	
	private Long creatorId;
	
	//管理orgId
	private Long orgId;
	
	
 
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Long getCreatorId() {
		return creatorId;
	}


	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}


	public Long getOrgId() {
		return orgId;
	}


	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}


	private Map<String,String> getMessageMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("name", name);
		   map.put("content", content);
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   map.put("createTime", format.format(createTime));
		   return  map;
	}
	
	
	public String getMessageJson(){
		return JsonUtils.getJsonString(getMessageMap());
	}
	
	

}
