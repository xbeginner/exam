package com.xx.test.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.xx.test.Dao.QuestionDao;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.PaperSchema;
import com.xx.test.Model.Question;
import com.xx.test.Model.QuestionAnswer;
import com.xx.test.Model.QuestionBank;
import com.xx.test.Model.UserInfo;
import com.xx.test.Model.UserPaper;

@Controller
public class QuestionController extends BaseController {
	
	  @RequestMapping(value="/index/manageQuestionBank",method=RequestMethod.GET)
	  public ModelAndView manageQuestionBank(HttpServletRequest request , HttpServletResponse response){
		  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		  ModelAndView modelAndView = new ModelAndView("manageQuestionBank");
		  modelAndView.addObject("userInfo",userInfo);
		  return modelAndView;
	  }
	  
	  
	  @RequestMapping(value="/index/initQuestionBank",method=RequestMethod.GET)
	  @ResponseBody
	  public String initQuestionBank(HttpServletRequest request , HttpServletResponse response){
		      StringBuffer json = new StringBuffer();
		      json.append("[");
		      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		      Long orgId = userInfo.getOrg().getId();
		      List<QuestionBank> questionBanks = this.questionBankService.findQuestionBankByOrg(orgId);
		      if(questionBanks.size()==0){
		    	  return "[]";
		      }
		      for(QuestionBank q:questionBanks){
		    	    json.append(q.getQuestionBankJson());
		    	    json.append(",");
		      }
		      json.deleteCharAt(json.length()-1);
		      json.append( "]" );
		      return json.toString();
	  }
	  
	  
	    @PostMapping(value="/index/addQuestionBank")
	    @ResponseBody
	    public String addQuestionBank(HttpServletRequest request , HttpServletResponse response) {
		         UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		         QuestionBank questionBank = new QuestionBank();
		         questionBank.setName(request.getParameter("name"));
		         questionBank.setInfo(request.getParameter("info"));
		         questionBank.setOrgId(userInfo.getOrg().getId());
		         this.questionBankService.saveQuestionBank(questionBank);
	             return SUCCESS;
	    }
	  
	  
	  
	    @GetMapping("/index/showQuestionBankInfo")
	    @ResponseBody
	    public String showQuestionBankInfo(HttpServletRequest request , HttpServletResponse response) {
	    	Long id = Long.valueOf(request.getParameter("id"));
	    	QuestionBank questionBank = questionBankService.findQuestionBankById(id);
	    	String json = questionBank.getQuestionBankJson();
	        return json;
	    }
	    
	    
	    
	    @PostMapping(value="/index/alterQuestionBank")
	    @ResponseBody
	    public String alterQuestionBank(HttpServletRequest request , HttpServletResponse response) {
	    	     Long id = Long.valueOf(request.getParameter("id"));
	    	     QuestionBank questionBank = questionBankService.findQuestionBankById(id);
	    	     questionBank.setName(request.getParameter("name"));
	    	     questionBank.setInfo(request.getParameter("info"));
	    	     questionBankService.alterQuestionBank(questionBank);
	             return SUCCESS;
	    }
    
	  
	  
	  @GetMapping(value="/index/deleteQuestionBank")
	    @ResponseBody
	    public String deleteQuestionBank(HttpServletRequest request , HttpServletResponse response) {
	    	     Long id = Long.valueOf(request.getParameter("id"));
	    	     questionBankService.deleteQuestionBank(id);
	             return SUCCESS;
	    }
	  
	  
	  @RequestMapping(value="/index/manageQuestion",method=RequestMethod.GET)
	  public ModelAndView manageQuestion(HttpServletRequest request , HttpServletResponse response){
		  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		  ModelAndView modelAndView = new ModelAndView("manageQuestion");
		  String bankId = request.getParameter("id");
		  QuestionBank questionBank = questionBankService.findQuestionBankById(Long.valueOf(bankId));
		  modelAndView.addObject("userInfo",userInfo);
		  modelAndView.addObject("questionBank",questionBank);
		  return modelAndView;
	  }
	  
	  
	  
	    @PostMapping(value="/index/addQuestion")
	    @ResponseBody
	    public String addQuestion(HttpServletRequest request , HttpServletResponse response) {
	    	     Question question = new Question();
		         String title = request.getParameter("title");
		         question.setTitle(title);
		         String answer = request.getParameter("answer");
		         question.setAnswer(answer);
		         String questionType = request.getParameter("questionType");
		         question.setType(Integer.valueOf(questionType));
		         String[] questionLabels = request.getParameterValues("questionLabels");
		         if(questionLabels.length>0){
		        	 Set<QuestionBank> banks = new HashSet<QuestionBank>();
		        	  for(String s:questionLabels){
				        	QuestionBank bank = questionBankService.findQuestionBankById(Long.valueOf(s));
				        	banks.add(bank);
				         }
		        	  question.setQuestionBanks(banks);
		         }
		         if(questionType.equals("1")||questionType.equals("2")){
		        	 question.setContent(request.getParameter("content"));
		         }
		         questionService.saveQuestion(question);
	             return SUCCESS;
	    }
	  
	    
	    
	      @RequestMapping(value="/index/initQuestionByBank",method=RequestMethod.GET)
		  @ResponseBody
		  public String initQuestionByBank(HttpServletRequest request , HttpServletResponse response){
			      StringBuffer json = new StringBuffer();
			      json.append("[");
			      Long bankId = Long.valueOf(request.getParameter("bankId"));
			      QuestionBank questionBank = this.questionBankService.findQuestionBankById(bankId);
			      if(questionBank.getQuestions().size()==0){
			    	  return "[]";
			      }
			      for(Question q:questionBank.getQuestions()){
			    	    json.append(q.getQuestionJson());
			    	    json.append(",");
			      }
			      json.deleteCharAt(json.length()-1);
			      json.append( "]" );
			      return json.toString();
		  }
	    
	    
	    
	    
	    @PostMapping(value="/index/alterQuestion")
	    @ResponseBody
	    public String alterQuestion(HttpServletRequest request , HttpServletResponse response) {
	    	 Long id = Long.valueOf(request.getParameter("id"));
    	     Question question = questionService.findQuestionById(id);
    	     question.setTitle(request.getParameter("title"));
    	     question.setAnswer(request.getParameter("answer"));
    	     if(question.getType()==1||question.getType()==2){
    	    	 question.setContent(request.getParameter("content")); 
    	     }
    	     String[] questionLabels = request.getParameterValues("questionLabels");
	         if(questionLabels!=null&&questionLabels.length>0){
	        	 Set<QuestionBank> banks = new HashSet<QuestionBank>();
	        	  for(String s:questionLabels){
			        	QuestionBank bank = questionBankService.findQuestionBankById(Long.valueOf(s));
			        	banks.add(bank);
			         }
	        	  question.setQuestionBanks(banks);
	         }
    	     questionService.alterQuestion(question);
             return SUCCESS;
	    }
    
	  
	  
	  @GetMapping(value="/index/deleteQuestion")
	    @ResponseBody
	    public String deleteQuestion(HttpServletRequest request , HttpServletResponse response) {
	    	     Long id = Long.valueOf(request.getParameter("id"));
	    	     questionService.deleteQuestion(id);
	             return SUCCESS;
	    }
	  
	    
	  @GetMapping("/index/showQuestionInfo")
	    @ResponseBody
	    public String showQuestionInfo(HttpServletRequest request , HttpServletResponse response) {
	    	Long id = Long.valueOf(request.getParameter("id"));
	    	Question question = questionService.findQuestionById(id);
	    	String json = question.getQuestionJson();
	        return json;
	    }
	  
	    @RequestMapping(value="/index/importQuestion")
		@ResponseBody
		public String importQuestion(HttpServletRequest request , HttpServletResponse response,@RequestParam(value = "uploadFile", required = false) MultipartFile questionFile) throws Exception {  
		     UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		     String msg = dealWithTheQuestionFile(questionFile,userInfo);
		     return msg;
	  } 
	  
	  
	  
	  private String dealWithTheQuestionFile(MultipartFile file,UserInfo userInfo) {
			try {
				    Map<String,Integer> maps = new HashMap<String,Integer>(){{
				    	     put("判断题", 0);
				    	     put("单选题", 1);
				    	     put("多选题", 2);
				    	     put("问答题", 3);
				    	};
				    };
//				    Map<String,Integer> fitUserMaps = new HashMap<String,Integer>(){{
//			    	     put("个人", 0);
//			    	     put("企业", 1);
//			    	     put("通用", 2);
//			    	};
//			    };
//			    Map<String,Integer> fitOrgMaps = new HashMap<String,Integer>(){{
//		    	     put("人行机构", 0);
//		    	     put("金融机构", 1);
//		    	     put("征信机构", 2);
//		    	     put("评级机构", 3);
//		    	};
//		    };
				    HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());	
					HSSFSheet aSheet = workbook.getSheetAt(0);
					String result = "";
					 for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet
			    		       .getLastRowNum(); rowNumOfSheet++) {
						 if(aSheet.getRow(rowNumOfSheet)!=null&&aSheet.getRow(rowNumOfSheet).getCell(0)!=null){
							 HSSFRow row = aSheet.getRow(rowNumOfSheet);
							 if(row.getCell(0)!=null&&!row.getCell(0).getStringCellValue().equals("") ){
								     String title = row.getCell(0).getStringCellValue().trim();
								     String type= row.getCell(3).getStringCellValue().trim();
								   
								     String answer = row.getCell(2).getStringCellValue().trim();
								     String lableStr = row.getCell(4).getStringCellValue().trim();
								     String fitUserLog= row.getCell(6).getStringCellValue().trim();
								     String fitOrgLog= row.getCell(5).getStringCellValue().trim();
								     Question question = new Question();
								     question.setAnswer(answer);
								     question.setTitle(title);
								     question.setType(maps.get(type));
//								     question.setFitOrgLog(fitOrgMaps.get(fitOrgLog));
//								     question.setFitUserLog(fitUserMaps.get(fitUserLog));
								     if(type.equals("单选题")||type.equals("多选题")){
								    	  String content = row.getCell(1).getStringCellValue().trim();
								    	    question.setContent(content);
								     }
								     Set<QuestionBank> banks = new HashSet<QuestionBank>();
								     if(lableStr.contains("#")){
								    	  String[] labels = lableStr.split("#");
								    	  for(String s:labels){
								    		    QuestionBank bank = questionBankService.findQuestionBankByName(s);
								    		    if(bank!=null&&bank.getOrgId()==userInfo.getOrg().getId()){
								    		    	  banks.add(bank);
								    		    }
								    	  }
								     }else{
								    	    QuestionBank bank = questionBankService.findQuestionBankByName(lableStr);
								    	    if(bank!=null&&bank.getOrgId()==userInfo.getOrg().getId()){
							    		    	  banks.add(bank);
							    		    }
								     }
								     question.setQuestionBanks(banks);
								     questionService.saveQuestion(question);
							 }
						 }
					 }
				 return "操作成功";
			} catch (Exception e) {
				
				e.printStackTrace();
				return "操作失败，请重新检查题库模板";
			}
		}
	  
	  
	  
	  @RequestMapping(value="/index/managePaper",method=RequestMethod.GET)
	  public ModelAndView managePaper(HttpServletRequest request , HttpServletResponse response){
		  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		  ModelAndView modelAndView = new ModelAndView("managePaperSchema");
		  modelAndView.addObject("userInfo",userInfo);
		  return modelAndView;
	  }

	  
	  
	  @RequestMapping(value="/index/initPaperSchema",method=RequestMethod.GET)
	  @ResponseBody
	  public String initPaperSchema(HttpServletRequest request , HttpServletResponse response){
		      StringBuffer json = new StringBuffer();
		      json.append("[");
		      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		      Long orgId = userInfo.getOrg().getId();
		      List<PaperSchema> paperSchemas = this.paperSchemaService.findPaperSchemaByOrg(orgId);
		      if(paperSchemas.size()==0){
		    	  return "[]";
		      }
		      for(PaperSchema p:paperSchemas){
		    	    json.append(p.getPaperSchemaJson());
		    	    json.append(",");
		      }
		      json.deleteCharAt(json.length()-1);
		      json.append( "]" );
		      return json.toString();
	  }
	  
	  
	   @PostMapping(value="/index/addPaperSchema")
	    @ResponseBody
	    public String addPaperSchema(HttpServletRequest request , HttpServletResponse response) {
	    	     PaperSchema paperSchema = new PaperSchema();
	    	     UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
	    	     paperSchema.setCreatorId(userInfo.getId());
	    	     paperSchema.setDoTime(Integer.valueOf(request.getParameter("doTime")));
	    	     paperSchema.setLog(Integer.valueOf(request.getParameter("log")));
	    	     paperSchema.setOrgId(userInfo.getOrg().getId());
	    	     paperSchema.setPaperName(request.getParameter("paperName"));
	    	     paperSchema.setType(Integer.valueOf(request.getParameter("type")));
	    	     paperSchema.setStep(0);
		         paperSchemaService.savePaperSchema(paperSchema);
	             return SUCCESS;
	    }
	   
	   
	   
	   
	      @RequestMapping(value="/index/getOrgUsers",method=RequestMethod.GET)
		  @ResponseBody
		  public String getOrgUsers(HttpServletRequest request , HttpServletResponse response){
			      StringBuffer json = new StringBuffer();
			      json.append("[");
			      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
			      List<Org> orgList = orgService.findOrgListByParentId(userInfo.getOrg().getId());
			      if(orgList.size()>0){
//			    	  json.append("\"orgs\":[" );
			    	   for(Org o:orgList){
			    		       json.append(o.getOrgJson());
			    			   json.deleteCharAt(json.length()-1);
					    	   json.append(",\"users\":[");
					    	   if(o.getUserInfoList().size()>0){
					    		      for(UserInfo u:o.getUserInfoList()){
					    		    	  json.append(u.getUserJson());
					    		    	  json.append(",");
					    		      }
					    	   }
					    	   json.deleteCharAt(json.length()-1);
					    	   json.append("]}");
			    	   }
			    	  
			      }
			           json.append("]");
//			      json.append("}");
			      return json.toString();
		  }
	      
	      
	      
	      
	        @PostMapping(value="/index/setPaperQuestionInfo")
		    @ResponseBody
		    public String setPaperQuestionInfo(HttpServletRequest request , HttpServletResponse response) {
			         Long paperId = Long.valueOf(request.getParameter("paperId"));
			         PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(paperId);
	        	     String questionPanduanIds = "";
	        	     String questionDanxuanIds = "";
	        	     String questionDuoxuanIds = "";
	        	     String questionWendaIds = "";
	        	     Float paperGrade = 0.0f;
	        	     String paperQuestionBankIds = "";
	        	     String[] bankIds = request.getParameterValues("questionLabels");
			         String panduanNum = request.getParameter("panduanNum");
	        	     paperSchema.setPanduanNum(Integer.valueOf(panduanNum));
			         String panduanGrade = request.getParameter("panduanGrade");
			         paperSchema.setPanduanGrade(Float.valueOf(panduanGrade));
			         paperGrade += Float.valueOf(panduanGrade);
			         String danxuanNum = request.getParameter("danxuanNum");
			         paperSchema.setDanxuanNum(Integer.valueOf(danxuanNum));
			         String danxuanGrade = request.getParameter("danxuanGrade");
			         paperGrade += Float.valueOf(danxuanGrade);
			         paperSchema.setDanxuanGrade(Float.valueOf(danxuanGrade));
			         String duoxuanNum = request.getParameter("duoxuanNum");
			         paperSchema.setDuoxuanNum(Integer.valueOf(duoxuanNum));
			         String duoxuanGrade = request.getParameter("duoxuanGrade");
			         paperSchema.setDuoxuanGrade(Float.valueOf(duoxuanGrade));
			         paperGrade += Float.valueOf(duoxuanGrade);
			         String userIds = request.getParameter("chooseUserIds"); 
			         if(paperSchema.getType()==1){
				         String wendaNum = request.getParameter("wendaNum");
				         paperSchema.setWendaNum(Integer.valueOf(wendaNum));
				         String wendaGrade = request.getParameter("wendaGrade");
				         paperSchema.setWendaGrade(Float.valueOf(wendaGrade));
				         paperGrade += Float.valueOf(wendaGrade);
			         }
			         List<Long> labels = new ArrayList<Long>();
						for(String s:bankIds){
							labels.add(Long.valueOf(s));
							paperQuestionBankIds += s+",";
						}
			         if(paperSchema.getLog()==0){
				        	
							List<Long> questionIds = questionService.findByBankNative(labels,1);
			        	    if(paperSchema.getType()==1){
						         String wendaNum = request.getParameter("wendaNum");
						         paperSchema.setWendaNum(Integer.valueOf(wendaNum));
					         }
			        	    
			        		if(!userIds.equals("")&&userIds!=null){
					             String[] ids = userIds.split(",");
						         for(String userId:ids){
						        	  UserPaper userPaper = new UserPaper();
						        	  userPaper.setDoLog(0);
						        	  userPaper.setPaperId(paperId);
						        	  userPaper.setPaperSchema(paperSchema);
						        	  userPaper.setUserId(Long.valueOf(userId));
						        	  userPaper.setQuestionPanduanIds(questionPanduanIds);
						        	  userPaper.setQuestionDanxuanIds(questionDanxuanIds);
						        	  userPaper.setQuestionDuoxuanIds(questionDuoxuanIds);
						        	  userPaperService.saveUserPaper(userPaper);
						         }
					         }
			         }
			         paperSchema.setUserIds(userIds);
			         paperSchema.setStep(1);
			         paperSchema.setGrade(paperGrade);
			         if(!paperQuestionBankIds.equals("")){
			        	 paperQuestionBankIds  = paperQuestionBankIds.substring(0, paperQuestionBankIds.length()-1);
			         }
			         paperSchema.setQuestionBankIds(paperQuestionBankIds);
			         paperSchemaService.savePaperSchema(paperSchema);
		             return SUCCESS;
		    }

            /**
             * 
             * @param bankIds
             * @param fitOrgLog
             * @param fitUserLog
             * @param type  0判断，1单选，2多选，3问答
             * @return
             */
			private String getRandomQuestionsByLabel(List<Long> questionIds,int questionNum, int fitOrgLog, int fitUserLog, int type) {
				String result = "";
				List<Long> questions = questionService.findByQuestionByInfo(fitOrgLog, fitUserLog, type, questionIds);
				int num = questions.size()<questionNum?questions.size():questionNum;
				Set<Long> idSet = new HashSet<Long>(num);
				for(int i=0;i<num;i++){
					Random random = new Random();
					int index = random.nextInt(questions.size());
					Long id = questions.get(index);
					if(idSet.contains(id)){
						i = i-1;
						continue;
					}else{
						idSet.add(id);
					}
				}
				for(Long l:idSet){
					result += l;
					result += ",";
				}
				result = result.substring(0, result.length()-1);
				return result;
			}
			
			
			
			
			
			@RequestMapping(value="/index/getPaperFitQuestions",method=RequestMethod.GET)
			 @ResponseBody
			  public String getPaperFitQuestions(HttpServletRequest request , HttpServletResponse response){
				      StringBuffer json = new StringBuffer();
				      json.append("[");
				      Long paperSchemaId = Long.valueOf(request.getParameter("paperId"));
				      PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(paperSchemaId);
				      List<Long> labelIds = new ArrayList<Long>();
				      for(String s:paperSchema.getQuestionBankIds().split(",")){
				    	  labelIds.add(Long.valueOf(s));
				      }
				      List<Long> questionIds = questionService.findByBankNative(labelIds,paperSchema.getType());
				      List<Question> questionList = new ArrayList<Question>(); 
				      
				      for(Long id:questionIds){
				    	  Question question = questionService.findQuestionById(id);
				    	  if(!questionList.contains(question)){
				    		  questionList.add(question);
				    	  }
				      }
				      for(Question q:questionList){
				    	     json.append(q.getSimpleQuestionJson());
				    	     json.append(",");
				      }
				      if(!questionIds.isEmpty()){
				    	  json.deleteCharAt(json.length()-1);
				      }
				      json.append("]");
				      return json.toString();
			  }
			
			
			
			@PostMapping(value="/index/setPaperQuestionsByChoose")
		    @ResponseBody
		    public String setPaperQuestionsByChoose(HttpServletRequest request , HttpServletResponse response) {
				Long paperId = Long.valueOf(request.getParameter("paperId"));
				PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(paperId);
				String[] questionIds = request.getParameterValues("targetQuestions");

				int panduanNum = 0;
				int danxuanNum = 0;
				int duoxuanNum = 0;
				int wendaNum = 0;
				
				String panduanIds = "";
				String danxuanIds = "";
				String duoxuanIds = "";
				String wendaIds = "";
						
				
				for(String s:questionIds){
					  Long id = Long.valueOf(s);
					  Question question = questionService.findQuestionById(id);
					  switch (question.getType()) {
							 case 0:
								panduanNum += 1;
								panduanIds += s+",";
								break;
							case 1:
								danxuanNum += 1;
								danxuanIds += s+",";
								break;
							case 2:
								duoxuanNum += 1;
								duoxuanIds += s+",";
								break;
							case 3:
								wendaNum += 1;
								wendaIds += s+",";
								break;
							default:
								break;
					}
				}
				if(panduanIds.contains(",")){
					panduanIds = panduanIds.substring(0, panduanIds.length()-1);
				}
				if(danxuanIds.contains(",")){
					danxuanIds = danxuanIds.substring(0, danxuanIds.length()-1);
				}
				if(duoxuanIds.contains(",")){
					duoxuanIds = duoxuanIds.substring(0, duoxuanIds.length()-1);
				}
				if(wendaIds.contains(",")){
					wendaIds = wendaIds.substring(0, wendaIds.length()-1);
				}
				String userIds = paperSchema.getUserIds();
	             String[] ids = userIds.split(",");
		         for(String userId:ids){
		        	  UserPaper userPaper = new UserPaper();
		        	  userPaper.setDoLog(0);
		        	  userPaper.setPaperId(paperId);
		        	  userPaper.setPaperSchema(paperSchema);
		        	  userPaper.setUserId(Long.valueOf(userId));
		        	  userPaper.setQuestionPanduanIds(panduanIds);
		        	  userPaper.setQuestionDanxuanIds(danxuanIds);
		        	  userPaper.setQuestionDuoxuanIds(duoxuanIds);
		        	  userPaper.setQuestionWendaIds(wendaIds);
		        	  userPaperService.saveUserPaper(userPaper);
		         }
		         paperSchema.setStep(2);
		         paperSchemaService.savePaperSchema(paperSchema);
				return SUCCESS;
			}
			
			
			
			
			  @RequestMapping(value="/openExamPage",method=RequestMethod.GET)
			  public ModelAndView openExamPage(HttpServletRequest request , HttpServletResponse response){
				  ModelAndView modelAndView = new ModelAndView("examPage");
				  String userPaperId = request.getParameter("paperId");
				  UserPaper userPaper = userPaperService.findUserPaperById(Long.valueOf(userPaperId));
				  if(userPaper.getQuestionPanduanIds()!=null&&!"".equals(userPaper.getQuestionPanduanIds())){
					  modelAndView.addObject("panduan","1");
				  }else{
					  modelAndView.addObject("panduan","0");
				  }
				  if(userPaper.getQuestionDanxuanIds()!=null&&!"".equals(userPaper.getQuestionDanxuanIds())){
					  modelAndView.addObject("danxuan","1");
				  }else{
					  modelAndView.addObject("danxuan","0");
				  }
				  if(userPaper.getQuestionDuoxuanIds()!=null&&!"".equals(userPaper.getQuestionDuoxuanIds())){
					  modelAndView.addObject("duoxuan","1");
				  }else{
					  modelAndView.addObject("duoxuan","0");
				  }
				  if(userPaper.getQuestionWendaIds()!=null&&!"".equals(userPaper.getQuestionWendaIds())){
					  modelAndView.addObject("wenda","1");
				  }else{
					  modelAndView.addObject("wenda","0");
				  }
				  modelAndView.addObject("userPaperId",userPaperId);
				  modelAndView.addObject("type",userPaper.getPaperSchema().getType());
				  modelAndView.addObject("paperName",userPaper.getPaperSchema().getPaperName());
				  modelAndView.addObject("doTime",userPaper.getPaperSchema().getDoTime());
				  return modelAndView;
			  }
			  
			  
			  
			  
			  
			  
			  @RequestMapping(value="/index/showPaperQuestions",method=RequestMethod.GET)
			  @ResponseBody
			  public String showPaperQuestions(HttpServletRequest request , HttpServletResponse response){
				      StringBuffer json = new StringBuffer();
				      Long userPaperId = Long.valueOf(request.getParameter("userPaperId"));
				      UserPaper userPaper = userPaperService.findUserPaperById(userPaperId);
				      String panduanQuestionIds = userPaper.getQuestionPanduanIds();
				      String danxuanQuestionIds = userPaper.getQuestionDanxuanIds();
				      String duoxuanQuestionIds = userPaper.getQuestionDuoxuanIds();
				      String wendaQuestionIds = userPaper.getQuestionWendaIds();
				      json.append("{");
				      json.append("\"panduan\":[");
				      if(panduanQuestionIds!=null&&!"".equals(panduanQuestionIds)){
				    	     String[] panduanIds = panduanQuestionIds.split(",");
				    	     for(String s:panduanIds){
				    	    	   Long id = Long.valueOf(s);
				    	    	   Question question = questionService.findQuestionById(id);
				    	    	   json.append(question.getExamQuestionJson());
				    	    	   json.append(",");
				    	     }
				    	     json.deleteCharAt(json.length()-1);
				      }
				      json.append("],");
				      
				      json.append("\"danxuan\":[");
				      if(danxuanQuestionIds!=null&&!"".equals(danxuanQuestionIds)){
				    	     String[] danxuanIds = danxuanQuestionIds.split(",");
				    	     for(String s:danxuanIds){
				    	    	   Long id = Long.valueOf(s);
				    	    	   Question question = questionService.findQuestionById(id);
				    	    	   json.append(question.getExamQuestionJson());
				    	    	   json.append(",");
				    	     }
				    	     json.deleteCharAt(json.length()-1);
				      }
				      json.append("],");
				      
				      json.append("\"duoxuan\":[");
				      if(duoxuanQuestionIds!=null&&!"".equals(duoxuanQuestionIds)){
				    	     String[] duoxuanIds = duoxuanQuestionIds.split(",");
				    	     for(String s:duoxuanIds){
				    	    	   Long id = Long.valueOf(s);
				    	    	   Question question = questionService.findQuestionById(id);
				    	    	   json.append(question.getExamQuestionJson());
				    	    	   json.append(",");
				    	     }
				    	     json.deleteCharAt(json.length()-1);
				      }
				      json.append("],");
				      
				      json.append("\"wenda\":[");
				      if(wendaQuestionIds!=null&&!"".equals(wendaQuestionIds)){
				    	     String[] wendaIds = wendaQuestionIds.split(",");
				    	     for(String s:wendaIds){
				    	    	   Long id = Long.valueOf(s);
				    	    	   Question question = questionService.findQuestionById(id);
				    	    	   json.append(question.getExamQuestionJson());
				    	    	   json.append(",");
				    	     }
				    	     json.deleteCharAt(json.length()-1);
				      }
				      json.append("]");
				      json.append( "}" );
				      return json.toString();
			  }
			  
			  
				@PostMapping(value="/index/setUserQuestions")
			    public String setUserQuestions(HttpServletRequest request , HttpServletResponse response) throws Exception {
					Map<String,String> panduanMap = new HashMap<String,String>(){{
						put("0", "对");
						put("1","错");
					}};
					Long userPaperId = Long.valueOf(request.getParameter("userPaperId"));
					UserPaper userPaper = userPaperService.findUserPaperById(userPaperId);
					String panduanQuestionIds = userPaper.getQuestionPanduanIds();
					String danxuanQuestionIds = userPaper.getQuestionDanxuanIds();
					String duoxuanQuestionIds = userPaper.getQuestionDuoxuanIds();
					String wendaQuestionIds = userPaper.getQuestionWendaIds();
					
					Float grade = 0.0f;
					
					String questionAnswers = "";
					
					if(panduanQuestionIds!=null&&!"".equals(panduanQuestionIds)){
						Float panduanGrade = userPaper.getPaperSchema().getPanduanGrade();
						String[] panduanIds = panduanQuestionIds.split(",");
						for(String s:panduanIds){
							 Long id = Long.valueOf(s);
							 Question question = questionService.findQuestionById(id);
							 String userAnswer = request.getParameter("panduan"+question.getId());
							 if(question.getAnswer().equals(panduanMap.get(userAnswer))){
								 grade += panduanGrade;
							 }
							 questionAnswers += s+":"+question.getAnswer()+":"+panduanMap.get(userAnswer);
							 questionAnswers += ",";
						 }
					}
					
					if(danxuanQuestionIds!=null&&!"".equals(danxuanQuestionIds)){
						Float danxuanGrade = userPaper.getPaperSchema().getDanxuanGrade();
						String[] danxuanIds =danxuanQuestionIds.split(",");
						for(String s:danxuanIds){
							 Long id = Long.valueOf(s);
							 Question question = questionService.findQuestionById(id);
							 String userAnswer = request.getParameter("danxuan"+question.getId());
							 if(question.getAnswer().equals(userAnswer)){
								 grade += danxuanGrade;
							 }
							 questionAnswers += s+":"+question.getAnswer()+":"+userAnswer;
							 questionAnswers += ",";
						 }
					}
					
					
					if(duoxuanQuestionIds!=null&&!"".equals(duoxuanQuestionIds)){
						Float duoxuanGrade = userPaper.getPaperSchema().getDuoxuanGrade();
						String[] duoxuanIds =duoxuanQuestionIds.split(",");
						for(String s:duoxuanIds){
							 Long id = Long.valueOf(s);
							 Question question = questionService.findQuestionById(id);
							 String[] userAnswers = request.getParameterValues("duoxuan"+question.getId());
							 String userAnswer = "";
							 for(String u:userAnswers){
								 userAnswer += u;
							 }
							 System.out.println(userAnswer);
							 if(question.getAnswer().equals(userAnswer)){
								 grade += duoxuanGrade;
							 }
							 questionAnswers += s+":"+question.getAnswer()+":"+userAnswer;
							 questionAnswers += ",";
						 }
					}
					userPaper.setGrade(grade);
					userPaper.setDoLog(1);
					userPaper.setQuestionAnswers(questionAnswers);
					userPaperService.saveUserPaper(userPaper);
					response.sendRedirect("/openMainPage");
					return null;
				}
				
				
				  @RequestMapping(value="/index/checkGrade",method=RequestMethod.GET)
				  public ModelAndView checkGrade(HttpServletRequest request , HttpServletResponse response){
					  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
					  ModelAndView modelAndView = new ModelAndView("checkGrade");
					  modelAndView.addObject("userInfo",userInfo);
					  return modelAndView;
				  }
				
				
				/**
				 * 区分管理员和一般人员查看成绩
				 * @param request
				 * @param response
				 * @return
				 */
				 @RequestMapping(value="/index/showGrade",method=RequestMethod.GET)
				  @ResponseBody
				  public String showGrade(HttpServletRequest request , HttpServletResponse response){
					      StringBuffer json = new StringBuffer();
					      json.append("{");
					      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
					      json.append("\"type\":"+userInfo.getRole().getManageLog()).append(",\"paper\":[");
					      //管理员
					      if(userInfo.getRole().getManageLog()!=0){
					    	    List<PaperSchema> papers = paperSchemaService.findPaperSchemaByOrg(userInfo.getOrg().getId());
					    	    if(papers.size()>0){
					    	    	   json.append("{");
					    	    	   for(PaperSchema paper:papers){
					    	    		     json.append("\"id\":").append(paper.getId()).append(",");
					    	    		     json.append("\"paperName\":\"").append(paper.getPaperName()).append("\"");
					    	    	   }
					    	    	   json.append("},");
					    	    }
					    	    json.deleteCharAt(json.length()-1);
					      }else{
					    	  //普通用户
					    	  List<UserPaper> userPaperList = userPaperService.findUserPaperByUserId(userInfo.getId());
					    	  if(userPaperList.size()>0){
				    	    	   json.append("{");
				    	    	   for(UserPaper userPaper:userPaperList){
				    	    		     json.append("\"grade\":").append(userPaper.getGrade()).append(",");
				    	    		     json.append("\"paperName\":\"").append(userPaper.getPaperSchema().getPaperName()).append("\"");
				    	    	   }
				    	    	   json.append("},");
				    	    }
				    	    json.deleteCharAt(json.length()-1);
					      }
					      json.append("]");
					      json.append( "}" );
					      return json.toString();
				  }
				 
				 
				 @RequestMapping(value="/index/showPaperGrade",method=RequestMethod.GET)
				  @ResponseBody
				  public String showPaperGrade(HttpServletRequest request , HttpServletResponse response){
					      StringBuffer json = new StringBuffer();
					      json.append("[");
					      Long paperId = Long.valueOf(request.getParameter("paperId"));
					      PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(paperId);
					      List<UserPaper> userPaperList = paperSchema.getUserPapers();
				    	  if(userPaperList.size()>0){
			    	    	   for(UserPaper userPaper:userPaperList){
			    	    		     json.append("{");
			    	    		     UserInfo userInfo = userInfoService.findById(userPaper.getUserId());
			    	    		     json.append("\"grade\":").append(userPaper.getGrade()==null?0:userPaper.getGrade()).append(",");
			    	    		     json.append("\"userName\":\"").append(userInfo.getUserName()).append("\"");
			    	    		     json.append("},");
			    	    	   }
			    	    	   
			    	    }
				    	   json.deleteCharAt(json.length()-1);
					      json.append("]");
					      System.out.println(json.toString());
					      return json.toString();
				  }
				 
				 
				  @GetMapping(value="/index/deletePaperSchema")
				    @ResponseBody
				    public String deletePaperSchema(HttpServletRequest request , HttpServletResponse response) {
				    	     Long id = Long.valueOf(request.getParameter("id"));
				    	     PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(id);
				    	     List<UserPaper> userPaperList = paperSchema.getUserPapers();
				    	     for(UserPaper userPaper:userPaperList){
				    	    	 userPaperService.deleteUserPaper(userPaper.getId());
				    	     }
				    	     paperSchemaService.deletePaperSchema(id);
				             return SUCCESS;
				    }
			    
				  
				  
				  
				  @GetMapping("/index/showPaperSchemaInfo")
				    @ResponseBody
				    public String showPaperSchemaInfo(HttpServletRequest request , HttpServletResponse response) {
				    	Long id = Long.valueOf(request.getParameter("id"));
				    	PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(id);
				    	String json = paperSchema.getAlterPaperSchemaJson();
				        return json;
				    }
				  
				  
				  @PostMapping(value="/index/alterPaperSchema")
				    @ResponseBody
				    public String alterPaperSchema(HttpServletRequest request , HttpServletResponse response) {
				    	     Long id = Long.valueOf(request.getParameter("id"));     
					         PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(id);
				    	     paperSchema.setDoTime(Integer.valueOf(request.getParameter("doTime")));
				    	     paperSchema.setLog(Integer.valueOf(request.getParameter("log")));
				    	     paperSchema.setPaperName(request.getParameter("paperName"));
				    	     paperSchema.setType(Integer.valueOf(request.getParameter("type")));
					         paperSchemaService.savePaperSchema(paperSchema);
				             return SUCCESS;
				    }
				  
				  
				  @GetMapping("/index/showPaperSchemaQuestionInfo")
				    @ResponseBody
				    public String showPaperSchemaQuestionInfo(HttpServletRequest request , HttpServletResponse response) {
				    	Long id = Long.valueOf(request.getParameter("id"));
				    	PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(id);
				    	String json = paperSchema.getPaperSchemaQuestionInfoJson();
				        return json;
				    }
				  
				  
				  
				  @RequestMapping(value="/index/initQuestionBankByPaper",method=RequestMethod.GET)
				  @ResponseBody
				  public String initQuestionBankByPaper(HttpServletRequest request , HttpServletResponse response){
					      StringBuffer json = new StringBuffer();
					      json.append("[");
					      Long id = Long.valueOf(request.getParameter("id"));
					      PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(id);
					      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
					      Long orgId = userInfo.getOrg().getId();
					      List<QuestionBank> questionBanks = this.questionBankService.findQuestionBankByOrg(orgId);
					      if(questionBanks.size()==0){
					    	  return "[]";
					      }
					   	    String[] labelIds = paperSchema.getQuestionBankIds().split(",");
				    	    List<Long> bankIds = new ArrayList<Long>();
				    	    for(String s:labelIds){
				    	    	bankIds.add(Long.valueOf(s));
				    	    }
					      for(QuestionBank q:questionBanks){
					    	    json.append(q.getQuestionBankJson());
					    	    json.deleteCharAt(json.length()-1);
					            if(bankIds.contains(q.getId())){
					            	   json.append(",\"check\":\"0\"}");
					            }else{
					                	 json.append(",\"check\":\"1\"}");
					            }
					    	    json.append(",");
					      }
					      json.deleteCharAt(json.length()-1);
					      json.append( "]" );
					      return json.toString();
				  }
				  
				  
				  
				  @PostMapping(value="/index/alterPaperSchemaQuestionInfo")
				    @ResponseBody
				    public String alterPaperSchemaQuestionInfo(HttpServletRequest request , HttpServletResponse response) {
					         Long paperId = Long.valueOf(request.getParameter("id"));
					         PaperSchema paperSchema = paperSchemaService.findPaperSchemaById(paperId);
			        	     Float paperGrade = 0.0f;
			        	     String paperQuestionBankIds = "";
			        	     String[] bankIds = request.getParameterValues("questionLabels");
			        	     List<Long> labels = new ArrayList<Long>();
								for(String s:bankIds){
									labels.add(Long.valueOf(s));
									paperQuestionBankIds += s+",";
								}
					         String panduanNum = request.getParameter("panduanNum");
			        	     paperSchema.setPanduanNum(Integer.valueOf(panduanNum));
					         String panduanGrade = request.getParameter("panduanGrade");
					         paperSchema.setPanduanGrade(Float.valueOf(panduanGrade));
					         paperGrade += Float.valueOf(panduanGrade);
					         String danxuanNum = request.getParameter("danxuanNum");
					         paperSchema.setDanxuanNum(Integer.valueOf(danxuanNum));
					         String danxuanGrade = request.getParameter("danxuanGrade");
					         paperGrade += Float.valueOf(danxuanGrade);
					         paperSchema.setDanxuanGrade(Float.valueOf(danxuanGrade));
					         String duoxuanNum = request.getParameter("duoxuanNum");
					         paperSchema.setDuoxuanNum(Integer.valueOf(duoxuanNum));
					         String duoxuanGrade = request.getParameter("duoxuanGrade");
					         paperSchema.setDuoxuanGrade(Float.valueOf(duoxuanGrade));
					         paperGrade += Float.valueOf(duoxuanGrade);
					         String userIds = request.getParameter("chooseUserIds"); 
					         if(paperSchema.getType()==1){
						         String wendaNum = request.getParameter("wendaNum");
						         paperSchema.setWendaNum(Integer.valueOf(wendaNum));
						         String wendaGrade = request.getParameter("wendaGrade");
						         paperSchema.setWendaGrade(Float.valueOf(wendaGrade));
						         paperGrade += Float.valueOf(wendaGrade);
					         }
					         if(userIds!=null&!"".equals(userIds)){
					        	 paperSchema.setUserIds(userIds);
					         }
					         paperSchema.setGrade(paperGrade);
					         if(!paperQuestionBankIds.equals("")){
					        	 paperQuestionBankIds  = paperQuestionBankIds.substring(0, paperQuestionBankIds.length()-1);
					        	   paperSchema.setQuestionBankIds(paperQuestionBankIds);
					         }
					         paperSchemaService.savePaperSchema(paperSchema);
				             return SUCCESS;
				    }
}
