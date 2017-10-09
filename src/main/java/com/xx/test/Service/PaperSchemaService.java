package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.MessageDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.Dao.PaperSchemaDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IPaperSchemaService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.PaperSchema;


@Service
public class PaperSchemaService implements IPaperSchemaService{
	
	@Autowired
    PaperSchemaDao paperSchemaDao;

	@Override
	public List<PaperSchema> findPaperSchemaByOrg(Long orgId) {
		// TODO Auto-generated method stub
		return paperSchemaDao.findByOrgId(orgId);
	}

	@Override
	public void savePaperSchema(PaperSchema paperSchema) {
		// TODO Auto-generated method stub
		 paperSchemaDao.save(paperSchema);
	}

	@Override
	public PaperSchema findPaperSchemaById(Long paperSchemaId) {
		return paperSchemaDao.findOne(paperSchemaId);
	}

	@Override
	public void deletePaperSchema(Long id) {
		// TODO Auto-generated method stub
		 paperSchemaDao.delete(id);
	}
 
}
