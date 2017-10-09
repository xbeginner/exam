package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.PaperSchema;

public interface IPaperSchemaService {

	List<PaperSchema> findPaperSchemaByOrg(Long orgId);

	void savePaperSchema(PaperSchema paperSchema);
	
	PaperSchema findPaperSchemaById(Long paperSchemaId);

	void deletePaperSchema(Long id);
	  
}
