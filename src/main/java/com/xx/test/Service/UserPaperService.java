package com.xx.test.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xx.test.Dao.MenuDao;
import com.xx.test.Dao.MessageDao;
import com.xx.test.Dao.OrgDao;
import com.xx.test.Dao.PaperSchemaDao;
import com.xx.test.Dao.UserPaperDao;
import com.xx.test.IService.IMenuService;
import com.xx.test.IService.IMessageService;
import com.xx.test.IService.IOrgService;
import com.xx.test.IService.IPaperSchemaService;
import com.xx.test.IService.IUserPaperService;
import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.UserInfo;
import com.xx.test.Model.UserPaper;


@Service
public class UserPaperService implements IUserPaperService{
	
	@Autowired
    UserPaperDao userPaperDao;

	@Override
	public void saveUserPaper(UserPaper userPaper) {
			userPaperDao.save(userPaper);
	}

	@Override
	public List<UserPaper> findUserPaperByUserId(Long id) {
		// TODO Auto-generated method stub
		return userPaperDao.findByUserId(id);
	}
	
	public List<UserPaper> findPageUserPaperByUserId(int pageNumber, int pagzSize,Long id){
		Pageable pageable = buildPageRequest(pageNumber, pagzSize);
		Page<UserPaper> userPaperPage = userPaperDao.findByUserId(id,pageable);
		return userPaperPage.getContent();
	}

	@Override
	public UserPaper findUserPaperById(Long id) {
		// TODO Auto-generated method stub
		return userPaperDao.findOne(id);
	}

	@Override
	public void deleteUserPaper(Long id) {
		// TODO Auto-generated method stub
		  userPaperDao.delete(id);
	}
	
    private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
        return new PageRequest(pageNumber - 1, pagzSize, null);
    }

 
 
}
