package com.xx.test.IService;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.Question;
import com.xx.test.Model.QuestionBank;

public interface IQuestionService {
	

	void deleteQuestion(Long id);

	void alterQuestion(Question question);

	Question findQuestionById(Long id);

	void saveQuestion(Question question);
	
	List<Long> findByBankNative(List<Long> bankIds,int type);
	
	List<Long> findByQuestionByInfo(int fitOrgLog,int fitUserLog,int type,@Param(value = "ids") List<Long> ids);
}
