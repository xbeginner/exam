package com.xx.test.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xx.test.Model.Org;
import com.xx.test.Model.RegisterUser;
import com.xx.test.Model.UserInfo;

@Repository
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long>{

	UserInfo findByUserNameAndPassword(String name, String password);

	UserInfo findByUserName(String userName);

	UserInfo findById(Long userId);
 
	@Transactional
	@Modifying 
	@Query("update UserInfo u set u.org = ?1 where u.id = ?2") 
	public int update(Org org, Long userId);
	
	 @Query("SELECT u FROM UserInfo u where u.org.parentOrgId=?1")
	 public List<UserInfo> findByParentOrgId(Long id) ;
	 
	 
	@Transactional
	@Modifying 
	@Query("update UserInfo u set u.userName = ?1,u.tel=?2,u.idcard=?3 where u.id = ?4")
	public int updateUserInfo(String userName, String tel,String idcard,Long id);
		
		
		
}
