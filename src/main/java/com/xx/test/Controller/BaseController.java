package com.xx.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageInfoService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IPaperSchemaService;
import com.xx.test.IService.IQuestionAnswerService;
import com.xx.test.IService.IQuestionBankService;
import com.xx.test.IService.IQuestionService;
import com.xx.test.IService.IRegisteUserService;
import com.xx.test.IService.IRoleService;
import com.xx.test.IService.IUserInfoService;
import com.xx.test.IService.IUserPaperService;

public abstract class BaseController {
	
	protected static final String SUCCESS="操作成功";
	
	  @Autowired
	  protected IUserInfoService userInfoService;
	  
	  @Autowired
	  protected IOrgService orgService;
	  
	  @Autowired
	  protected IRegisteUserService registeUserService;
	  
	  @Autowired
	  protected IRoleService roleService;
	  
	  @Autowired
	  protected IMenuService menuService;
	  
	  @Autowired
	  protected IMessageService messageService;
	  
	  @Autowired
	  protected IQuestionBankService questionBankService;
	  
	  @Autowired
	  protected IQuestionService questionService;
	  
	  @Autowired
	  protected IPaperSchemaService paperSchemaService;
	  
	  @Autowired
	  protected IUserPaperService userPaperService;
	  
	  @Autowired
	  protected IQuestionAnswerService questionAnswerService;
	  
	  @Autowired
	  protected IMessageInfoService messageInfoService;
	  

}
