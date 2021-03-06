package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.OrgDao;
import com.xx.test.IService.IOrgService;
import com.xx.test.Model.Org;


@Service
public class OrgService implements IOrgService{
	
	@Autowired
    OrgDao orgDao;
	
	public Org saveOrg(Org org){
		return orgDao.save(org);
	}
	
	public int alterOrg(Org org){
		//return orgDao.updateOrg(org.getOrgName(), org.getTel(), org.getAddress(), org.getMaster(), org.getMasterTel(), org.getId());
		return 0;
	}
	
	public List<Org> findOrgListByParentId(Long parentOrgId){
 		 return orgDao.findByParentOrgId(parentOrgId);
		 
	}

	@Override
	public Org findOrgById(Long id) {
		return orgDao.findOne(id);
	}
	
	
	public void deleteOrg(Long id){
		   orgDao.delete(id);
	}
	
	
	public List<Org> findByParentOrgIdNotNull(){
//		  return orgDao.findByParentOrgIdNotNull();
		return null;
	}

	@Override
	public Org findOrgByName(String depName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Org findTopOrg(){
		return orgDao.findByParentOrgIsNull();
	}

	@Override
	public boolean orgIsEmpty() {
		List<Org> orgList = orgDao.findByAllOrg();
		if(orgList.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
   
	public Org findOrgByOu(String ou){
		return orgDao.findByOu(ou);
	}

	@Override
	public List<Org> findByFirstLevelOrgs() {
		Org org = orgDao.findByParentOrgIsNull();
		return org.getChildOrgs();
	}

}
