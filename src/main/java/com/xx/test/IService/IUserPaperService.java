package com.xx.test.IService;

import java.util.List;

import com.xx.test.Model.Menu;
import com.xx.test.Model.Message;
import com.xx.test.Model.Org;
import com.xx.test.Model.UserPaper;

public interface IUserPaperService {
	
	  public void saveUserPaper(UserPaper userPaper);

	public List<UserPaper> findUserPaperByUserId(Long id);
	
	public UserPaper findUserPaperById(Long id);

	public void deleteUserPaper(Long id);
	
}
