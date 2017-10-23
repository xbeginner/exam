package com.xx.test.Dao;


import java.util.List;

import javax.persistence.QueryHint;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xx.test.Model.Org;
import com.xx.test.Model.RegisterUser;
import com.xx.test.Model.UserInfo;

@Repository
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long>{

	UserInfo findByUserNameAndPassword(String name, String password);

	UserInfo findByUserName(String userName);

	UserInfo findById(Long userId);
	
	UserInfo findByIdcard(String idcard);
 
	@Transactional
	@Modifying 
	@Query("update UserInfo u set u.org = ?1 where u.id = ?2") 
	public int update(Org org, Long userId);
	
	 @Query("SELECT u FROM UserInfo u where u.org.parentOrg.id=?1")
	 public List<UserInfo> findByParentOrgId(Long id) ;
	 
	 
	@Transactional
	@Modifying 
	@Query("update UserInfo u set u.userName = ?1,u.tel=?2,u.idcard=?3 where u.id = ?4")
	public int updateUserInfo(String userName, String tel,String idcard,Long id);
 
	//@QueryHints(value={@QueryHint(name="hint_comment",value="a query for pageable")})
	@Query("select u from UserInfo u where u.parentOrgId=:id")
    public Page<UserInfo> findByParentOrgId(@Param("id") Long id,Pageable pageable);
		
}
