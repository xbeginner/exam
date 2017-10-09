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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.xx.test.Utils.JsonUtils;

@Entity
@Table(name="t_userPaper")
public class UserPaper implements Serializable{
	
	private static final long serialVersionUID =12L;
	
	  @Id
	  @GeneratedValue
	private Long id;
	
	private Long userId;
	
	private Long paperId;
	
	private String questionPanduanIds;
	
	private String questionDanxuanIds;
	
	private String questionDuoxuanIds;
	
	private String questionWendaIds;
	
	  //开始时间
	  private Date startTime;
	  
	  //结束时间
	  private Date endTime;
	
	//0为未开始，1为做过了，2为正在做
	private int doLog;
	
//	@OneToMany(mappedBy="userPaper")
//	private List<QuestionAnswer> questionAnswers;
	
	private String questionAnswers;
	
	@ManyToOne
	private PaperSchema paperSchema;
	
	private Float grade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getPaperId() {
		return paperId;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

   
	
	public String getQuestionPanduanIds() {
		return questionPanduanIds;
	}

	public void setQuestionPanduanIds(String questionPanduanIds) {
		this.questionPanduanIds = questionPanduanIds;
	}

	public String getQuestionDanxuanIds() {
		return questionDanxuanIds;
	}

	public void setQuestionDanxuanIds(String questionDanxuanIds) {
		this.questionDanxuanIds = questionDanxuanIds;
	}

	public String getQuestionDuoxuanIds() {
		return questionDuoxuanIds;
	}

	public void setQuestionDuoxuanIds(String questionDuoxuanIds) {
		this.questionDuoxuanIds = questionDuoxuanIds;
	}

	public String getQuestionWendaIds() {
		return questionWendaIds;
	}

	public void setQuestionWendaIds(String questionWendaIds) {
		this.questionWendaIds = questionWendaIds;
	}

	public int getDoLog() {
		return doLog;
	}

	public void setDoLog(int doLog) {
		this.doLog = doLog;
	}

//	public List<QuestionAnswer> getQuestionAnswers() {
//		return questionAnswers;
//	}
//
//	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
//		this.questionAnswers = questionAnswers;
//	}
	
	

	public PaperSchema getPaperSchema() {
		return paperSchema;
	}

	public String getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(String questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public void setPaperSchema(PaperSchema paperSchema) {
		this.paperSchema = paperSchema;
	}

	public Float getGrade() {
		return grade;
	}

	public void setGrade(Float grade) {
		this.grade = grade;
	}
 
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private Map<String,String> getUserPaperMap(){
		   String[] logs = {"未开始","已完成","考试中"};
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("userId", String.valueOf(userId));
		   map.put("paperId", String.valueOf(paperId));
		   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   if(startTime!=null){
			   map.put("startTime", format.format(startTime));
		   }
		   if(endTime!=null){
			   map.put("endTime", format.format(endTime));
		   }
		   map.put("doLog", logs[doLog]);
		   if(grade!=null){
			   map.put("grade", String.valueOf(grade));
		   }else{
			   map.put("grade", "");
		   }
		   return  map;
	}
	
	
	public String getUserPaperJson(){
		return JsonUtils.getJsonString(getUserPaperMap());
	}

	
	private Map<String,String> getUserMainPaperSchemaMap(){
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("id", String.valueOf(id));
		   map.put("paperName", paperSchema.getPaperName());
		   map.put("doTime", String.valueOf(paperSchema.getDoTime()));
		   map.put("doLog", String.valueOf(getDoLog()));
		   return  map;
	}
	
	
	
	public String getUserMainPaperSchemaJson(){
		return JsonUtils.getJsonString(getUserMainPaperSchemaMap());
	}
	
}
