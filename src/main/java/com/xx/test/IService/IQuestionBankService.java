package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.QuestionBank;

public interface IQuestionBankService {
	
	List<QuestionBank> findQuestionBankByOrg(Long orgId);

	void deleteQuestionBank(Long id);

	void alterQuestionBank(QuestionBank questionBank);

	QuestionBank findQuestionBankById(Long id);

	void saveQuestionBank(QuestionBank questionBank);

	QuestionBank findQuestionBankByName(String name);
}
