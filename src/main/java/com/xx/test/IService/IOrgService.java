package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Org;

public interface IOrgService {
	
	public Org saveOrg(Org org);
	
	public List<Org> findOrgListByParentId(Long long1);
	
	public Org findOrgById(Long id);

	public int alterOrg(Org org);
	
	public void deleteOrg(Long id);
	
	public List<Org> findByParentOrgIdNotNull();

	public Org findOrgByName(String depName);

}
