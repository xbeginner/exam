package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.MessageDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.Dao.QuestionBankDao;
import com.xx.test.Dao.QuestionDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IQuestionBankService;
import com.xx.test.IService.IQuestionService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.Question;
import com.xx.test.Model.QuestionBank;


@Service
public class QuestionBankService implements IQuestionBankService{
	
	@Autowired
    QuestionBankDao questionBankDao;

	@Override
	public List<QuestionBank> findQuestionBankByOrg(Long orgId) {
		return questionBankDao.findByOrgId(orgId);
	}

	@Override
	public void deleteQuestionBank(Long id) {
		questionBankDao.delete(id);		
	}

	@Override
	public void alterQuestionBank(QuestionBank questionBank) {
		// TODO Auto-generated method stub
		questionBankDao.updateQuestionBank(questionBank.getName(), questionBank.getInfo(), questionBank.getId());
	}

	@Override
	public QuestionBank findQuestionBankById(Long id) {
		return questionBankDao.findOne(id);
	}

	@Override
	public void saveQuestionBank(QuestionBank questionBank) {
         questionBankDao.save(questionBank);
	}

	@Override
	public QuestionBank findQuestionBankByName(String name) {
		// TODO Auto-generated method stub
		return questionBankDao.findByName(name);
	}

	 
 
	
	 
}
