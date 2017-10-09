package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Org;
import com.xx.test.Model.Role;

public interface IRoleService {
	
   public List<Role>  findAllRole();
   
   public Role saveRole(Role role);
   
   public Role findRoleById(Long id);
   
   public void alterRole(Role role);
   
   public void deleteRole(Long id);

}
