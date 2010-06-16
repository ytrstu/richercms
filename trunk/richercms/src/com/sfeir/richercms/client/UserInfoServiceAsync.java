package com.sfeir.richercms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.shared.BeanUser;
import com.sfeir.richercms.shared.BeanUserInfo;

/**
 * User Services
 * @author homberg.g
 *
 */
public interface UserInfoServiceAsync {
	
	public void login(String requestUri, AsyncCallback<BeanUserInfo> async);
	
	public void isAutorized(BeanUserInfo usrInfo, AsyncCallback<BeanUser> async);
	
	public void modifyUser(BeanUser user, AsyncCallback<Void> async);
	
	public void addUser(String email, AsyncCallback<Long> async);
	
	public void logOutUser(Long id, AsyncCallback<Void> async);
	
	public void UserAdminChange(Long id, boolean admin, AsyncCallback<Void> async);
	
	public void getUsers(AsyncCallback<List<BeanUser>> async);
}
