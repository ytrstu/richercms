package com.esial.richercms.server;

import com.esial.richercms.client.UserInfoService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserInfoServiceImpl extends RemoteServiceServlet implements
		UserInfoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8597003183050309756L;
	
	@Override
	public String getEmail() {
	  UserService userService = UserServiceFactory.getUserService(); 
	  User user = userService.getCurrentUser(); 
	  if(user!=null) {
		  return user.getEmail();
	  } else {
		  return null;
	  }
	}

	@Override
	public String logout() {
		UserService userService = UserServiceFactory.getUserService(); 
		String logout = userService.createLogoutURL("/Richercms.html");
		System.out.println(logout);
		return logout;
	}

}
