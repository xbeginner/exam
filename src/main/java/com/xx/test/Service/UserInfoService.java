package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.UserInfoDao;
import com.xx.test.IService.IUserInfoService;
import com.xx.test.Model.Org;
import com.xx.test.Model.UserInfo;

@Service
public class UserInfoService implements IUserInfoService{
	
	@Autowired
	UserInfoDao userInfoDao;

	@Override
	public UserInfo findByNameAndPassword(String name, String password) {
		return userInfoDao.findByUserNameAndPassword(name,password);
	}

	@Override
	public UserInfo add(UserInfo userInfo) {
		return userInfoDao.save(userInfo);
	}

	@Override
	public int getErrorLog(UserInfo userInfo) {
		int log = 0;
		
		UserInfo user = userInfoDao.findByUserName(userInfo.getUserName());
		if(user==null){
			return 1;
		}else{
			user = userInfoDao.findByUserNameAndPassword(userInfo.getUserName(), userInfo.getPassword());
			if(user==null){
				return 2;
			}
		}
		
		return log;
	}

	@Override
	public UserInfo findById(Long userId) {
		
		return userInfoDao.findById(userId);
	}
	
	@Override
	public int alterUserInfoOrg(Org org, Long userId){
		   return userInfoDao.update(org, userId);
	}
	
	
	public List<UserInfo> findUserInfoByParentOrgId(Long orgId){
		return userInfoDao.findByParentOrgId(orgId);
	}
	
	
	public UserInfo saveUserInfo(UserInfo userInfo){
		return userInfoDao.save(userInfo);
	}
	
	
	public void alterUserInfo(UserInfo userInfo){
		userInfoDao.updateUserInfo(userInfo.getUserName(), userInfo.getTel(), userInfo.getIdcard(), userInfo.getId());
	}
	
	
	public void deleteUserInfo(Long id){
		  userInfoDao.delete(id);
	}

}
