package com.xx.test.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.xx.test.Form.OrgAddForm;
import com.xx.test.Form.PersonForm;
import com.xx.test.Form.RegisteUserForm;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.PaperSchema;
import com.xx.test.Model.Question;
import com.xx.test.Model.QuestionBank;
import com.xx.test.Model.RegisterUser;
import com.xx.test.Model.Role;
import com.xx.test.Model.UserInfo;
import com.xx.test.Model.UserPaper;
import com.xx.test.Utils.JsonUtils;

import redis.clients.jedis.Jedis;

@Controller
public class MainController extends BaseController {

	  @RequestMapping(value="/index/initMainMenu",method=RequestMethod.GET)
	  @ResponseBody
	  public String initMainMenu(HttpServletRequest request , HttpServletResponse response){
  
		      StringBuffer json = new StringBuffer();
		      json.append("[");
		      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		      Role role = userInfo.getRole();
		      if(role!=null){
		    	   List<Menu> menus = menuService.findMenuByRoleId(role.getId());
		    	   // Set<Menu> menus = role.getMenus();
		    	    if(menus.size()>0){
			    	    Map<String,String> map = new HashMap<String,String>();
			    	    for(Menu m:menus){
			    	    	map  =  m.getMenuMap();
			    	    	json.append("{");
			    	    	json.append(JsonUtils.getAddableJsonString(map));
			    	    	json.append("},");
			    	    }
		    	    }
		    	    json.deleteCharAt(json.length()-1);
		      }
		      json.append( "]" );
		 
		      return json.toString();
	  }
	
	  
	  
	  @RequestMapping(value="/index/initExamInfo",method=RequestMethod.GET)
	  @ResponseBody
	  public String initExamInfo(HttpServletRequest request , HttpServletResponse response){
               UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
               List<UserPaper> userPapers = userPaperService.findUserPaperByUserId(userInfo.getId());
		       String json = "{\"info\":[";
		       if(userPapers.size()>0){
		    	     for(UserPaper userPaper:userPapers){
//		    	    	 if(userPaper.getDoLog()==0){
		    	    	     json += userPaper.getUserMainPaperSchemaJson()+",";
//		    	    	 }else{
//		    	    		 
//		    	    	 }
		    	     }
		    	     json = json.substring(0, json.length()-1);
		       }
		       json += "]}";
		      return json;
	  }
	
	  
	  
	  @RequestMapping(value="/index/initMessageInfo",method=RequestMethod.GET)
	  @ResponseBody
	  public String initMessageInfo(HttpServletRequest request , HttpServletResponse response){
			     UserInfo userInfo = (UserInfo) request.getSession().getAttribute("currentUserInfo");
			     List<Message> messages = new ArrayList<Message>();
			     if(userInfo.getOrg().getParentOrgId()!=null){
			    	 messages = messageService.findAllMessagesByOrgAndAllLog(userInfo.getOrg().getParentOrgId(),1);
			     }else{
			    	 messages = messageService.findAllMessagesByOrgAndAllLog(userInfo.getOrg().getId(),1);
			     }
			     
			     if(messages.size()>0){
			    	 return "1";
			     }else{
			    	 List<UserPaper> userPapers = userPaperService.findUserPaperByUserId(userInfo.getId());
			    	 if(userInfo.getOrg().getParentOrgId()!=null){
				    	 messages = messageService.findAllMessagesByOrgAndAllLog(userInfo.getOrg().getParentOrgId(),0);
				     }else{
				    	 messages = messageService.findAllMessagesByOrgAndAllLog(userInfo.getOrg().getId(),0);
				     }
			    	 for(Message m:messages){
			    		 Set<PaperSchema> papers = m.getPaperSchemas();
			    		 for(UserPaper userPaper:userPapers){
			    			 if(papers.contains(userPaper.getPaperSchema())){
			    				 return "1";
			    			 }
			    		 }
			    	 }
			     }
			     String json = "0";
		         return json;
	  }
	  
	  
	  @RequestMapping(value="/index/manageUserInfo",method=RequestMethod.GET)
	  public ModelAndView manageUserInfo(HttpServletRequest request , HttpServletResponse response){
		  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		  ModelAndView modelAndView = new ModelAndView("manageUserInfo");
		  modelAndView.addObject("userInfo",userInfo);
		  return modelAndView;
	  }
	  
	  
	  @RequestMapping(value="/index/manageOrg",method=RequestMethod.GET)
	  public ModelAndView manageOrg(HttpServletRequest request , HttpServletResponse response){
		  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		  ModelAndView modelAndView = new ModelAndView("manageOrg");
		  modelAndView.addObject("userInfo",userInfo);
		  modelAndView.addObject("org",userInfo.getOrg());
		  return modelAndView;
	  }
	  
	    @GetMapping("/manage/toAddOrg")
	    public String toAddOrg(OrgAddForm orgAddForm) {
	        return "addOrg";
	    }
	  
	  
		 @PostMapping("/manage/addOrg")
	    public String addOrg(@Valid OrgAddForm orgAddForm, BindingResult bindingResult,HttpServletRequest request) {
		 
	        if (bindingResult.hasErrors()) {
	               return "addOrg";
	        }
	        Org org = new Org();
	         org.setAddress(orgAddForm.getAddress());
	         org.setMaster(orgAddForm.getMaster());
	         org.setMasterTel(orgAddForm.getMasterTel());
	         org.setOrgName(orgAddForm.getOrgName());
	         org.setTel(orgAddForm.getTel());
	        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
	        if(userInfo.getOrg()==null){
	        	 userInfo.setOrg(org);
	 	         this.userInfoService.alterUserInfoOrg(org, userInfo.getId());
	        }else{
	        	 org.setParentOrgId(userInfo.getOrg().getId());
	        }
	         this.orgService.saveOrg(org);
 
	         return "redirect:/success";
	    }
		 
		 
		    @PostMapping("/manage/registerUser")
		    public String registerUser(@Valid RegisteUserForm registeUserForm, BindingResult bindingResult,HttpServletRequest request) {
			 
		        if (bindingResult.hasErrors()) {
		               return "registeUser";
		        }
		        RegisterUser registerUser = new RegisterUser();
		        registerUser.setIdcard(registeUserForm.getIdcard());
		        registerUser.setTel(registeUserForm.getTel());
		        registerUser.setUserName(registeUserForm.getUserName());
		        if(request.getParameter("org")!=null){
		            Long id = Long.valueOf(request.getParameter("org"));
			         Org org = this.orgService.findOrgById(id);
			         registerUser.setOrg(org);
			         registerUser.setManageOrgId(org.getParentOrgId());
		        }
		         this.registeUserService.saveRegisteUser(registerUser);
		         return "redirect:/success";
		    }
			 
		 
		 
		    @GetMapping("/index/initOrgList")
		    @ResponseBody
		    public String initOrgList(HttpServletRequest request , HttpServletResponse response) {
		    	String json = "[";
		    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		    	int parentId = Integer.valueOf(request.getParameter("parentId"));
		    	if(parentId==0){
		    		  Org org = this.orgService.findOrgById(userInfo.getOrg().getId());
		    		  json += org.getOrgJson();
		    		  List<Org> orgList = this.orgService.findOrgListByParentId(userInfo.getOrg().getId());
                      if(!orgList.isEmpty()){
                    	  json = json.replace("}",",\"childCount\":\""+orgList.size()+"\"}");
                      }else{
                    	  json = json.replace("}",",\"childCount\":\"0\"}");
                      }
		    	}else{
		    		
		    	}
		    	json += "]";
		        return json;
		    }
		    
		    
		    @GetMapping("/manage/getAllRegisteOrgs")
		    @ResponseBody
		    public String getAllRegisteOrgs(HttpServletRequest request , HttpServletResponse response) {
		    	String json = "[";
		    	List<Org> orgs = this.orgService.findByParentOrgIdNotNull();
		    	for(Org o:orgs){
		    		json += o.getOrgJson();
		    		json += ",";
		    	}
		    	json = json.substring(0,json.length()-1);
		    	json += "]";
		    	System.out.println(json);
		        return json;
		    }
		    
		    
		    
		    @GetMapping("/index/showOwnOrgList")
		    @ResponseBody
		    public String showOwnOrgList(HttpServletRequest request , HttpServletResponse response) {
		    	String json = "[";
		    	Long parentId = Long.valueOf(request.getParameter("parentId"));
		    	List<Org> orgList = this.orgService.findOrgListByParentId(parentId);
		    	for(Org org:orgList){
		    		 json += org.getOrgJson();
		    		 json += ",";
		    	}
                json = json.substring(0, json.length()-1);	    	
		    	json += "]";
		        return json;
		    }
		    
		    
		    @PostMapping(value="/index/addOwnOrg")
		    @ResponseBody
		    public String addOwnOrg(HttpServletRequest request , HttpServletResponse response) {
		    	     UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
			         Org org = new Org();
			         org.setAddress(request.getParameter("address"));
			         org.setMaster(request.getParameter("master"));
			         org.setMasterTel(request.getParameter("tel"));
			         org.setOrgName(request.getParameter("orgName"));
			         org.setTel(request.getParameter("tel"));
			         org.setParentOrgId(userInfo.getOrg().getId());
			         this.orgService.saveOrg(org);
		             return SUCCESS;
		    }
		    
		    
		    @PostMapping(value="/index/alterOrg")
		    @ResponseBody
		    public String alterOrg(HttpServletRequest request , HttpServletResponse response) {
		    	     Long id = Long.valueOf(request.getParameter("orgId"));
			         Org org = orgService.findOrgById(id);
			         org.setAddress(request.getParameter("address"));
			         org.setMaster(request.getParameter("master"));
			         org.setMasterTel(request.getParameter("tel"));
			         org.setOrgName(request.getParameter("orgName"));
			         org.setTel(request.getParameter("tel"));
			         this.orgService.alterOrg(org);
		             return SUCCESS;
		    }
		 
		    @GetMapping("/index/showOrgInfo")
		    @ResponseBody
		    public String showOrgInfo(HttpServletRequest request , HttpServletResponse response) {
		    	Long orgId = Long.valueOf(request.getParameter("orgId"));
		    	Org org = orgService.findOrgById(orgId);
		    	String json = org.getOrgJson();
		        return json;
		    }
		    
		    
		    
		    @GetMapping("/index/deleteOrg")
		    @ResponseBody
		    public String deleteOrg(HttpServletRequest request , HttpServletResponse response) {
		    	Long orgId = Long.valueOf(request.getParameter("id"));
		    	orgService.deleteOrg(orgId);
		        return SUCCESS;
		    }
		    
		    
		    @GetMapping("/index/showNoRegistUserInfo")
		    @ResponseBody
		    public String showNoRegistUserInfo(HttpServletRequest request , HttpServletResponse response) {
		    	String json = "[";
		    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		    	Long orgId = userInfo.getOrg().getId();
		    	List<RegisterUser> registUsers = this.registeUserService.findRegisteUserByOrgId(orgId);
		    	if(registUsers.size()==0){
		    		return "[]";
		    	}
		    	for(RegisterUser user:registUsers){
		    		   json += user.getRegisteUserJson();
		    		   json += ",";
		    	}
		    	json = json.substring(0, json.length()-1);
		    	json += "]";
		        return json;
		    }
		    
		    
		    @GetMapping("/index/showOwnUserInfo")
		    @ResponseBody
		    public String showOwnUserInfo(HttpServletRequest request , HttpServletResponse response) {
		    	String json = "[";
		    	UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
		    	Long orgId = userInfo.getOrg().getId();
		    	List<UserInfo> userInfoList = this.userInfoService.findUserInfoByParentOrgId(orgId);
		    	for(UserInfo user:userInfoList){
		    		   json += user.getUserJson();
		    		   json += ",";
		    	}
		    	json = json.substring(0, json.length()-1);
		    	json += "]";
		        return json;
		    }
		    
		    
		    
		    @GetMapping("/index/passRegister")
		    @ResponseBody
		    public String passRegister(HttpServletRequest request , HttpServletResponse response) {
		    	Long userId = Long.valueOf(request.getParameter("id"));
		    	RegisterUser reUser = registeUserService.findRegisteUserById(userId);
		    	UserInfo userInfo = new UserInfo();
		    	userInfo.setIdcard(reUser.getIdcard());
		    	userInfo.setOrg(reUser.getOrg());
		    	userInfo.setPassword("123456");
		    	userInfo.setTel(reUser.getTel());
		    	userInfo.setUserName(reUser.getUserName());
		    	this.userInfoService.saveUserInfo(userInfo);
		    	registeUserService.deleteRegisteUser(userId);
		    	return SUCCESS;
		    }
		    
		    
		    @GetMapping("/index/noPassRegister")
		    @ResponseBody
		    public String noPassRegister(HttpServletRequest request , HttpServletResponse response) {
		    	Long userId = Long.valueOf(request.getParameter("id"));
		    	registeUserService.deleteRegisteUser(userId);
		        return SUCCESS;
		    }
		    
		    
		    @GetMapping("/index/showUserInfo")
		    @ResponseBody
		    public String showUserInfo(HttpServletRequest request , HttpServletResponse response) {
		    	Long userId = Long.valueOf(request.getParameter("id"));
		    	UserInfo user = userInfoService.findById(userId);
		    	String json = user.getUserJson();
		    	System.out.println(json);
		        return json;
		    }
		    
		    
		    @PostMapping(value="/index/alterUserInfo")
		    @ResponseBody
		    public String alterUserInfo(HttpServletRequest request , HttpServletResponse response) {
		    	     Long id = Long.valueOf(request.getParameter("userId"));
			         UserInfo user = userInfoService.findById(id);
			         user.setIdcard(request.getParameter("idcard"));
			         user.setTel(request.getParameter("userTel"));
			         user.setUserName(request.getParameter("userName"));
			         userInfoService.alterUserInfo(user);
		             return SUCCESS;
		    }
		    
		    
		    @GetMapping(value="/index/deleteUserInfo")
		    @ResponseBody
		    public String deleteUserInfo(HttpServletRequest request , HttpServletResponse response) {
		    	     Long id = Long.valueOf(request.getParameter("id"));
			         userInfoService.deleteUserInfo(id);
		             return SUCCESS;
		    }
		    
		    
		    
			  @RequestMapping(value="/index/manageRole",method=RequestMethod.GET)
			  public ModelAndView manageRole(HttpServletRequest request , HttpServletResponse response){
				  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
				  ModelAndView modelAndView = new ModelAndView("manageRole");
				  modelAndView.addObject("userInfo",userInfo);
				  return modelAndView;
			  }
			  
			  
			  @RequestMapping(value="/index/initRole",method=RequestMethod.GET)
			  @ResponseBody
			  public String initRole(HttpServletRequest request , HttpServletResponse response){
		  
				      StringBuffer json = new StringBuffer();
				      json.append("[");
				      List<Role> roleList = this.roleService.findAllRole();
				      for(Role r:roleList){
				    	    json.append(r.getRoleJson());
				    	    json.append(",");
				      }
				      json.deleteCharAt(json.length()-1);
				      json.append( "]" );
				      return json.toString();
			  }
			  
			  
			  @PostMapping(value="/index/addRole")
			    @ResponseBody
			    public String addRole(HttpServletRequest request , HttpServletResponse response) {
				         Role role = new Role();
				         role.setInfo(request.getParameter("info"));
				         role.setManageLog(Integer.valueOf(request.getParameter("managelog")));
				         role.setName(request.getParameter("name"));
				         this.roleService.saveRole(role);
			             return SUCCESS;
			    }
			  
			  
			  @GetMapping("/index/showRoleInfo")
			    @ResponseBody
			    public String showRoleInfo(HttpServletRequest request , HttpServletResponse response) {
			    	Long id = Long.valueOf(request.getParameter("id"));
			    	Role role = roleService.findRoleById(id);
			    	String json = role.getRoleJson();
			        return json;
			    }
			  
			  
			  
			  @PostMapping(value="/index/alterRole")
			    @ResponseBody
			    public String alterRole(HttpServletRequest request , HttpServletResponse response) {
			    	     Long id = Long.valueOf(request.getParameter("roleId"));
				         Role role = roleService.findRoleById(id);
				         role.setInfo(request.getParameter("info"));
				         role.setName(request.getParameter("name"));
				         roleService.alterRole(role);
			             return SUCCESS;
			    }
		    
			  
			  
			  @GetMapping(value="/index/deleteRole")
			    @ResponseBody
			    public String deleteRole(HttpServletRequest request , HttpServletResponse response) {
			    	     Long id = Long.valueOf(request.getParameter("id"));
				         roleService.deleteRole(id);
			             return SUCCESS;
			    }
			  
			  
			  
			  @RequestMapping(value="/index/showRoleMenuInfo",method=RequestMethod.GET)
			  @ResponseBody
			  public String showRoleMenuInfo(HttpServletRequest request , HttpServletResponse response){
		              Long roleId = Long.valueOf(request.getParameter("roleId"));
		              Role role = roleService.findRoleById(roleId);
		              List<Long> ids = new ArrayList<Long>();
		              if(!role.getMenus().isEmpty()){
		            	   for(Menu m: role.getMenus()){
		            		    ids.add(m.getId());
		            	   }
		              }
		              List<Menu> menuList = menuService.findAllMenuList();
				      StringBuffer json = new StringBuffer();
				      json.append("[");
				      for(Menu m:menuList){
				    	    json.append(m.getMenuJson());
				    	    if(ids.contains(m.getId())){
				    	    		json.deleteCharAt(json.length()-1);
				    	    		json.append(",\"checklog\":true}");
				    	    }else{
				    	    	json.deleteCharAt(json.length()-1);
			    	    		json.append(",\"checklog\":false}");
				    	    }
				    	    json.append(",");
				      }
				      json.deleteCharAt(json.length()-1);
				      json.append( "]" );
				      return json.toString();
			  }
			  
			  
			  
			  @PostMapping(value="/index/setRoleMenu")
			    @ResponseBody
			    public String setRoleMenu(HttpServletRequest request , HttpServletResponse response) {
				         Long roleId = Long.valueOf(request.getParameter("roleId"));
				         Role role = roleService.findRoleById(roleId);
			    	     String[] ids =  request.getParameterValues("menus");
			    	     Set<Menu> menus = new HashSet<Menu>();
			    	     for(String s:ids){
			    	    	 Menu menu = menuService.findMenuById(Long.valueOf(s));
			    	    	 menus.add(menu);
			    	     }
			    	     role.setMenus(menus);
			    	     roleService.alterRole(role);
			             return SUCCESS ;
			    }
			  
			  
			  
			  
			  @RequestMapping(value="/index/initMessage",method=RequestMethod.GET)
			  @ResponseBody
			  public String initMessage(HttpServletRequest request , HttpServletResponse response){
				      StringBuffer json = new StringBuffer();
				      json.append("[");
				      UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
				      Long orgId = userInfo.getOrg().getId();
				      List<Message> messages = this.messageService.findAllMessagesByOrg(orgId);
				      if(messages.size()==0){
				    	  return "[]";
				      }
				      for(Message m:messages){
				    	    json.append(m.getMessageJson());
				    	    json.append(",");
				      }
				      json.deleteCharAt(json.length()-1);
				      json.append( "]" );
				      return json.toString();
			  }
			  
			  
			  @RequestMapping(value="/index/manageMessage",method=RequestMethod.GET)
			  public ModelAndView manageMessage(HttpServletRequest request , HttpServletResponse response){
				  UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
				  ModelAndView modelAndView = new ModelAndView("manageMessage");
				  modelAndView.addObject("userInfo",userInfo);
				  return modelAndView;
			  }
			  
			  
			  
			  @PostMapping(value="/index/addMessage")
			    @ResponseBody
			    public String addMessage(HttpServletRequest request , HttpServletResponse response) {
				         UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
				         String[] targetPapers = request.getParameterValues("contact-name");
				         Message message = new Message();
				         message.setName(request.getParameter("name"));
				         message.setContent(request.getParameter("content"));
				         message.setCreateTime(new java.sql.Date(new Date().getTime()));
				         message.setCreatorId(userInfo.getId());
				         message.setOrgId(userInfo.getOrg().getId());
				         Set<PaperSchema> papers = new HashSet<PaperSchema>();
				         for(String s:targetPapers){
				        	 if(s.equals("0")){
				        		 message.setAllLog(1);
				        		 break;
				        	 }
				        	 PaperSchema p = paperSchemaService.findPaperSchemaById(Long.valueOf(s));
				        	 papers.add(p);
				         }
				         if(papers.size()>0){
				        	 message.setPaperSchemas(papers);
				         }
				         this.messageService.saveMessage(message);
			             return SUCCESS;
			    }
			  
			  
			    @GetMapping("/index/showMessageInfo")
			    @ResponseBody
			    public String showMessageInfo(HttpServletRequest request , HttpServletResponse response) {
			    	Long id = Long.valueOf(request.getParameter("id"));
			    	Message message = messageService.findMessageById(id);
			    	String json = message.getMessageJson();
			        return json;
			    }
			  
			  
			  
			  @PostMapping(value="/index/alterMessage")
			    @ResponseBody
			    public String alterMessage(HttpServletRequest request , HttpServletResponse response) {
				 
			    	     Long id = Long.valueOf(request.getParameter("id"));
				         Message message = messageService.findMessageById(id);
				         message.setName(request.getParameter("name"));
				         message.setContent(request.getParameter("content"));
				         messageService.alterMessage(message);
			             return SUCCESS;
			    }
		    
			  
			  
			  @GetMapping(value="/index/deleteMessage")
			    @ResponseBody
			    public String deleteMessage(HttpServletRequest request , HttpServletResponse response) {
			    	     Long id = Long.valueOf(request.getParameter("id"));
				         messageService.deleteMessage(id);
			             return SUCCESS;
			    }
			  
			  
			  
			   @RequestMapping(value="/index/importUserInfo")
				@ResponseBody
				public String importQuestion(HttpServletRequest request , HttpServletResponse response,@RequestParam(value = "uploadFile", required = false) MultipartFile userInfoFile) throws Exception {  
				     UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
				     String msg = dealWithTheUserInfoFile(userInfoFile,userInfo);
				     return msg;
			  } 
			  
			  
			  
			  private String dealWithTheUserInfoFile(MultipartFile file,UserInfo userInfo) {
					try {
						    HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());	
							HSSFSheet aSheet = workbook.getSheetAt(0);
							 for (int rowNumOfSheet = 1; rowNumOfSheet <= aSheet
					    		       .getLastRowNum(); rowNumOfSheet++) {
								 if(aSheet.getRow(rowNumOfSheet)!=null&&aSheet.getRow(rowNumOfSheet).getCell(0)!=null){
									 HSSFRow row = aSheet.getRow(rowNumOfSheet);
									 if(row.getCell(0)!=null&&!row.getCell(0).getStringCellValue().equals("") ){
										      row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
										      row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
										     String name = row.getCell(0).getStringCellValue().trim();
										     String idcard= row.getCell(1).getStringCellValue().trim();
										     String tel = row.getCell(2).getStringCellValue().trim();
										     String depName = row.getCell(3).getStringCellValue().trim();
										     UserInfo user = new UserInfo();
										     user.setUserName(name);
										     user.setIdcard(idcard);
										     user.setPassword("123456");
										     Role role = roleService.findRoleById(Long.valueOf(2));
										     user.setRole(role);
										     user.setTel(tel);
										     Org org = orgService.findOrgByName(depName);
										     if(org!=null&&userInfo.getOrg().getId()==org.getParentOrgId()){
											     user.setOrg(org);
											     userInfoService.saveUserInfo(user);
										     }
									 }
								 }
							 }
						 return "操作成功";
					} catch (Exception e) {
						
						e.printStackTrace();
						return "操作失败，请重新检查题库模板";
					}
				}
			  
			  
			  
			    @GetMapping("/index/showAllTargetPaper")
			    @ResponseBody
			    public String showAllTargetPaper(HttpServletRequest request , HttpServletResponse response) {
                    UserInfo userInfo = (UserInfo)request.getSession().getAttribute("currentUserInfo");
			    	List<PaperSchema> paperList = paperSchemaService.findPaperSchemaByOrg(userInfo.getOrg().getId());
			        String json = "[";
			        json += "{\"id\":\""+0+"\",\"name\":\"辖区全体人员\"},";
			        if(paperList.size()>0){
			        	for(PaperSchema paper:paperList){
				        	json += "{";
				        	json += "\"id\":\""+paper.getId()+"\",";
				        	json += "\"name\":\""+paper.getPaperName()+"\"";
				        	json += "},";
				        }
			        }
			        json = json.substring(0, json.length()-1);
			        json += "]";
			    	return json;
			    }

}
