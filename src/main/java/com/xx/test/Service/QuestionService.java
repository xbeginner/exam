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
public class QuestionService implements IQuestionService{
	
	@Autowired
    QuestionDao questionDao;

 

	@Override
	public void deleteQuestion(Long id) {
        questionDao.delete(id);		
	}

	@Override
	public void alterQuestion(Question question) {
		// TODO Auto-generated method stub
//		questionDao.updateQuestionLabels(question.getId());
//		questionDao.updateQuestion(question.getTitle(), question.getContent(), question.getAnswer(), question.getQuestionBanks(), question.getId());
	       questionDao.save(question);
	}

	@Override
	public Question findQuestionById(Long id) {
		// TODO Auto-generated method stub
		return questionDao.findOne(id);
	}

	@Override
	public void saveQuestion(Question question) {
         questionDao.save(question);		
	}

	@Override
	public List<Long> findByBankNative(List<Long> bankIds,int type) {
		// 如果机试
		if(type==0){
			return questionDao.findByBankNoWendaNative(bankIds);
		}else{
			return questionDao.findByBankNative(bankIds);
		}
		
	}

	@Override
	public List<Long> findByQuestionByInfo(int fitOrgLog, int fitUserLog, int type, List<Long> ids) {
		// TODO Auto-generated method stub
		return questionDao.getQuestionsByInfos(fitOrgLog, fitUserLog, type, ids);
	}

     

	 
	 
	
	 
}
