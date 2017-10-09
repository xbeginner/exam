package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Org;
import com.xx.test.Model.RegisterUser;
import com.xx.test.Model.UserInfo;

public interface IUserInfoService {
  
	   UserInfo findByNameAndPassword(String name,String password);
	   
	   UserInfo add(UserInfo userInfo);

	  /**
	   * 获取用户名和密码错误类型
	   * 1为用户名
	   * 2为密码
	   * 0为正常
	   * @param userInfo
	   * @return
	   */
	  int getErrorLog(UserInfo userInfo);
    
	UserInfo findById(Long userId);
	
	int alterUserInfoOrg(Org org, Long userId);
	
	public List<UserInfo> findUserInfoByParentOrgId(Long orgId);
	
	public UserInfo saveUserInfo(UserInfo userInfo);
	
	public void alterUserInfo(UserInfo userInfo);
	
	public void deleteUserInfo(Long id);
	
}
