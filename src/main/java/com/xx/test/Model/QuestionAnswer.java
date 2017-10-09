package com.xx.test.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_questionAnswer")
public class QuestionAnswer implements Serializable{
	
	private static final long serialVersionUID =13L;
	
	  @Id
	  @GeneratedValue
	private Long id;
	
	private Long questionId;
	
	private String questionTitle;
	
	private String questionAnswer;
	
	private String userAnswer;
	
	private Float grade;
	
	@ManyToOne
	private UserPaper userPaper;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}

	public UserPaper getUserPaper() {
		return userPaper;
	}

	public void setUserPaper(UserPaper userPaper) {
		this.userPaper = userPaper;
	}
	
	
	private Map<String,String> getQuestionAnswerMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("questionId",  String.valueOf(questionId));
		   map.put("questionTitle", questionTitle);
		   map.put("questionAnswer", questionAnswer);
		   map.put("userAnswer", userAnswer);
		   map.put("grade", String.valueOf(grade));
		   return  map;
	}
	
	
	public String getQuestionAnswerJson(){
		return JsonUtils.getJsonString(getQuestionAnswerMap());
	}
	

}
