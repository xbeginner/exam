package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.MessageDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.Dao.PaperSchemaDao;
import com.xx.test.Dao.QuestionAnswerDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IPaperSchemaService;
import com.xx.test.IService.IQuestionAnswerService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;


@Service
public class QuestionAnswerService implements IQuestionAnswerService{
	
	@Autowired
    QuestionAnswerDao questionAnswerDao;
 
}
