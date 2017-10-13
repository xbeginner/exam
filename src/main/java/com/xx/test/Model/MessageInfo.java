package com.xx.test.Model;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_message_info")
public class MessageInfo implements Serializable {
	
	private static final long serialVersionUID = 14L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	private Long sendUserId;
	
	private String sendUserName;
	
	private String info;
	
	private Date sendTime;
	
	@ManyToOne
	private Message message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(Long sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendUserName() {
		return sendUserName;
	}

	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	
	private Map<String,String> getMessageInfoMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("sendUserId", String.valueOf(sendUserId));
		   map.put("sendUserName", sendUserName);
		   map.put("info", info);
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		   map.put("sendTime", format.format(sendTime));
		   return  map;
	}
	
	
	public String getMessageInfoJson(){
		return JsonUtils.getJsonString(getMessageInfoMap());
	}

}
