package com.xx.test.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xx.test.Model.Org;
import com.xx.test.Model.RegisterUser;

public interface RegisteUserDao extends CrudRepository<RegisterUser, Long>{
    
	     public List<RegisterUser> findByManageOrgId(Long id) ;

		  public List<RegisterUser> findByParentOrgId(Long orgId);
 
	
}
