package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.OrgDao;
import com.xx.test.Dao.RegisteUserDao;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IRegisteUserService;
import com.xx.test.Model.Org;
import com.xx.test.Model.RegisterUser;


@Service
public class RegisteUserService implements IRegisteUserService{
	
	@Autowired
    RegisteUserDao registeUserDao;
	
	 public void saveRegisteUser(RegisterUser registeUser){
		 registeUserDao.save(registeUser);
	 }
	 
	 
	 public List<RegisterUser> findRegisteUserByOrgId(Long orgId){
		   return registeUserDao.findByManageOrgId(orgId); 
	 }
	 
	 public void deleteRegisteUser(Long id){
		  registeUserDao.delete(id);
	 }
	 
	 public RegisterUser findRegisteUserById(Long id){
		 return registeUserDao.findOne(id);
	 }
}
