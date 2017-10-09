package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;

public interface IMessageService {
	  List<Message> findAllMessagesByOrg(Long orgId);

	void deleteMessage(Long id);

	void alterMessage(Message message);

	Message findMessageById(Long id);

	void saveMessage(Message message);
}
