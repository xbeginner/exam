package com.xx.test.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_question")
public class Question implements Serializable{
     
	/**
	 * 
	 */
	private static final long serialVersionUID =9L;
	
	
	  @Id
	  @GeneratedValue
	private Long id;
	
	//题目
	private String title;
	
	//备选项
	private String content;
	
	/**
	 * 0判断，1单选，2多选，3问答
	 */
	private int type;
	
	private  String  answer;
	
	//0个人,1企业,2通用
	private int fitUserLog;
	
	//企业类别，0人行机构，1金融机构，2征信机构，3评级机构
	private int fitOrgLog;
	
	 @ManyToMany
	  @JoinTable(name="t_questionTypes" ,
      joinColumns = { @JoinColumn(name = "question_id") },
	  inverseJoinColumns = { @JoinColumn(name = "questionBank_Id") })
	  private Set<QuestionBank> questionBanks = new HashSet<QuestionBank>(0);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Set<QuestionBank> getQuestionBanks() {
		return questionBanks;
	}

	public void setQuestionBanks(Set<QuestionBank> questionBanks) {
		this.questionBanks = questionBanks;
	}
 
	public int getFitUserLog() {
		return fitUserLog;
	}

	public void setFitUserLog(int fitUserLog) {
		this.fitUserLog = fitUserLog;
	}

	public int getFitOrgLog() {
		return fitOrgLog;
	}

	public void setFitOrgLog(int fitOrgLog) {
		this.fitOrgLog = fitOrgLog;
	}

	private Map<String,String> getQuestionMap(){
		   String[] types = {"判断题","单选题","多选题","问答题"};
		   String[] fitUserLogs = {"个人","企业","通用"};
		   String[] fitOrgLogs = {"人行机构","金融机构","征信机构","评级机构"};
		   String banks = "";
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("title", title);
		   map.put("content", content);
		   map.put("answer", answer);
		   map.put("type", types[type]);
		   map.put("fitUserLog", fitUserLogs[fitUserLog]);
		   map.put("fitOrgLog", fitOrgLogs[fitOrgLog]);
		   if(questionBanks.size()>0){
			    for(QuestionBank q:questionBanks){
			    	banks += q.getName();
			    	banks += ",";
			    }
			    banks = banks.substring(0, banks.length()-1);
		   }
		   map.put("banks", banks);
		   return  map;
	}
	
	
	public String getSimpleQuestionJson(){
		return JsonUtils.getJsonString(getSimpleQuestionMap());
	}
	
	

	private Map<String,String> getSimpleQuestionMap(){
		   String[] types = {"判断题","单选题","多选题","问答题"};
		   String[] fitUserLogs = {"个人","企业","通用"};
		   String[] fitOrgLogs = {"人行机构","金融机构","征信机构","评级机构"};
		   String banks = "";
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("title", title);
		   map.put("type", types[type]);
		   map.put("fitUserLog", fitUserLogs[fitUserLog]);
		   map.put("fitOrgLog", fitOrgLogs[fitOrgLog]);
		   return  map;
	}
	
	
	public String getQuestionJson(){
		return JsonUtils.getJsonString(getQuestionMap());
	}
	
	
	private Map<String,String> getExamQuestionMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("title", title);
		   map.put("content",content);
		   return  map;
	}
	
	
	public String getExamQuestionJson(){
		return JsonUtils.getJsonString(getExamQuestionMap());
	}

}
