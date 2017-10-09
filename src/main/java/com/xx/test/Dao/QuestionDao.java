package com.xx.test.Dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.Question;
import com.xx.test.Model.QuestionBank;

public interface QuestionDao extends CrudRepository<Question, Long>{
	

    @Modifying
    @Transactional  
    @Query("update Question q set q.title = ?1,q.content=?2,q.answer=?3,q.questionBanks= ?4 where q.id = ?5")
	void updateQuestion(String title, String content, String answer, Set<QuestionBank> banks,Long id);

    @Modifying
    @Transactional  
    @Query("update Question q set q.questionBanks=null where q.id = ?1")
	void updateQuestionLabels(Long id);
    
    @Transactional  
    @Query(value = "SELECT q.id FROM Question q join q.questionBanks b  where  b.id  in  ?1 order by q.type")
    List<Long> findByBankNative(List<Long> ids);
    
    @Transactional  
    @Query(value = "SELECT q.id FROM Question q join q.questionBanks b  where  q.type!=3 and b.id  in  ?1 order by q.type")
    List<Long> findByBankNoWendaNative(List<Long> ids);
    
    @Transactional  
    @Query("select q.id  from Question q where q.fitOrgLog=?1 and q.fitUserLog=?2 and q.type=?3 and q.id in ?4")
	List<Long>  getQuestionsByInfos(int fitOrgLog,int fitUserLog,int type,List<Long> ids);
}
