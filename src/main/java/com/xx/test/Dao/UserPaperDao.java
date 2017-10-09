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
import com.xx.test.Model.PaperSchema;
import com.xx.test.Model.UserPaper;

public interface UserPaperDao extends CrudRepository<UserPaper, Long>{

   List<UserPaper> findByUserId(Long userId);
}
