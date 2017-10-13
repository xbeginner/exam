package com.xx.test.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xx.test.Model.Org;

public interface OrgDao extends PagingAndSortingRepository<Org, Long>{
    
	@Query("SELECT o FROM Org o")  
	List<Org> findByAllOrg();
	
//    @Modifying
//    @Transactional  
//    @Query("update Org o set o.displayName = ?1,o.tel=?2,o.address=?3,o.master=?4,o.masterTel=?5  where o.id = ?6")
//    int updateOrg(String orgName,String tel,String address,String master,String masterTel, long id);
	
    
//    List<Org> findByParentOrgIdNotNull();
	
	Org findByParentOrgIsNull();
	
    
//    Org findByOrgName(String orgName);
}
