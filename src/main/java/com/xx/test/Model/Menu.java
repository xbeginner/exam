package com.xx.test.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_menu")
public class Menu implements Serializable{
	
	private static final long serialVersionUID = 6L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String info;
	
	private String url;
	
	@ManyToMany(mappedBy="menus")
	private Set<Role> roles=new HashSet<Role>(0);
	
 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

    
   

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuJson(){
		return JsonUtils.getJsonString(getMenuMap());
	}
	

 

	public Map<String,String> getMenuMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("name", name);
		   map.put("info", info);
		   map.put("url", url==null?"#":"/"+url);
		   return  map;
	}
	
}
