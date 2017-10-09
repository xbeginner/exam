package com.xx.test.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.QuestionBank;

public interface QuestionBankDao extends CrudRepository<QuestionBank, Long>{
	
	List<QuestionBank> findByOrgId(Long orgId);
	
	QuestionBank findByName(String name);

    @Modifying
    @Transactional  
    @Query("update QuestionBank q set q.name = ?1,q.info=?2 where q.id = ?3")
	void updateQuestionBank(String name, String info, Long id);
}
