package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Org;

public interface IMenuService {
	
    List<Menu> findAllMenuList();
    
    Menu findMenuById(Long id);

	List<Menu> findMenuByRoleId(Long id);
}
