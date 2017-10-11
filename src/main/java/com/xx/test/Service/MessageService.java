package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.MessageDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;


@Service
public class MessageService implements IMessageService{
	
	@Autowired
    MessageDao messageDao;

	@Override
	public List<Message> findAllMessagesByOrgAndAllLog(Long orgId,int allLog) {
		// TODO Auto-generated method stub
		return messageDao.findByOrgIdAndAllLog(orgId,allLog);
	}
	
	
	@Override
	public List<Message> findAllMessagesByOrg(Long orgId) {
		// TODO Auto-generated method stub
		return messageDao.findByOrgId(orgId);
	}

	@Override
	public void deleteMessage(Long id) {
		 messageDao.delete(id);
	}

	@Override
	public void alterMessage(Message message) {
		 messageDao.updateMessage(message.getName(),message.getContent(),message.getId());
	}

	@Override
	public Message findMessageById(Long id) {
		return messageDao.findOne(id);
	}

	@Override
	public void saveMessage(Message message) {
         messageDao.save(message);		
	}

	 
	
	 
}
