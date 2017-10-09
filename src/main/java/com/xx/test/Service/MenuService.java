package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IOrgService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Org;


@Service
public class MenuService implements IMenuService{
	
	@Autowired
    MenuDao menuDao;

	@Override
	public List<Menu> findAllMenuList() {
		// TODO Auto-generated method stub
		return (List<Menu>) menuDao.findAll();
	}

	@Override
	public Menu findMenuById(Long id) {
		// TODO Auto-generated method stub
		return menuDao.findOne(id);
	}

	@Override
	public List<Menu> findMenuByRoleId(Long id) {
		// TODO Auto-generated method stub
		return menuDao.findMenuByRoleId(id);
	}
	
	 
}
