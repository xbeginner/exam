package com.xx.test.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Org;

public interface MenuDao extends CrudRepository<Menu, Long>{
    
    @Transactional  
    @Query(value = "SELECT m FROM Menu m join m.roles r  where  r.id=?1  order by m.id")
    List<Menu> findMenuByRoleId(Long roleId);
	
}
