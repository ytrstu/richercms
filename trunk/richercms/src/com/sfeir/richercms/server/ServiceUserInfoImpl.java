package com.sfeir.richercms.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.client.UserInfoService;
import com.sfeir.richercms.server.business.CmsUser;
import com.sfeir.richercms.shared.BeanUser;
import com.sfeir.richercms.shared.BeanUserInfo;
/**
 * Implementation of all User Services.
 * @author homberg.g
 *
 */
@SuppressWarnings("serial")
public class ServiceUserInfoImpl extends RemoteServiceServlet implements UserInfoService {

	static {
        ObjectifyService.register(CmsUser.class);
	}
	
	public BeanUserInfo login(String requestUri) {
	  UserService userService = UserServiceFactory.getUserService();
	  User user = userService.getCurrentUser();
	  BeanUserInfo loginInfo = new BeanUserInfo();
	
	  if (user != null) {
		loginInfo.setLoggedIn(true);
		loginInfo.setEmailAddress(user.getEmail());
		loginInfo.setNickname(user.getNickname());
		loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		
		if(userService.isUserAdmin()) 
			loginInfo.setAdmin(true);
		else 
			loginInfo.setAdmin(false);
	    
	  }else {
	    loginInfo.setLoggedIn(false);
	    loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
	  }
	  return loginInfo;
	}
	
	public BeanUser isAutorized(BeanUserInfo usrInfo){
		Objectify ofy = ObjectifyService.begin();
		
		Query<CmsUser> users  = ofy.query(CmsUser.class).filter("emailAddress =", usrInfo.getEmailAddress());
		
		// les admin sont automatiquement inscrit
		if(usrInfo.isAdmin() && users.countAll() == 0){
			CmsUser usr = this.userInfoTocmsUser(usrInfo);
			usr.setLoggedIn(true);// now the user is connected
			ofy.put(usr);
			return this.cmsUserToBean(usr);
		}else if(users.countAll() != 0){
			CmsUser usr = users.get();
			//set his nickName the first times and if it was different
			if(usr.getNickname().equals(usrInfo.getNickname()))
				usr.setNickname(usrInfo.getNickname());
			usr.setLoggedIn(true);// now the user is connected
			ofy.put(usr);
			
			return this.cmsUserToBean(usr);
		}
		
		return null;
	}
	
	public void modifyUser(BeanUser user){
		Objectify ofy = ObjectifyService.begin();
		ofy.put(this.BeanToCmsUser(user));
	}
	
	public void addUser(String email){
		Objectify ofy = ObjectifyService.begin();
		
		CmsUser newUsr = new CmsUser();
		newUsr.setEmailAddress(email);
		
		ofy.put(newUsr);
	}
	
	public void logOutUser(Long id){
		Objectify ofy = ObjectifyService.begin();
		
		CmsUser usr = ofy.get(CmsUser.class, id);
		usr.setLoggedIn(false);
		ofy.put(usr);
	}
	
	public void UserAdminChange(Long id, boolean admin){
		Objectify ofy = ObjectifyService.begin();
		
		CmsUser usr = ofy.get(CmsUser.class, id);
		usr.setAdmin(admin);
		ofy.put(usr);
	}
	
	private CmsUser BeanToCmsUser(BeanUser BeanUsr) {
		return new CmsUser(BeanUsr.getId(),BeanUsr.getEmailAddress(), 
				BeanUsr.getNickname(),BeanUsr.isLoggedIn(), BeanUsr.isAdmin());
	}
	
	private BeanUser cmsUserToBean(CmsUser usr) {
		return new BeanUser(usr.getId(),usr.getEmailAddress(), 
				usr.getNickname(),usr.isLoggedIn(), usr.isAdmin());
	}
	
	private CmsUser userInfoTocmsUser(BeanUserInfo usrInfo) {
		CmsUser usr = new CmsUser();
		usr.setAdmin(usrInfo.isAdmin());
		usr.setEmailAddress(usrInfo.getEmailAddress());
		usr.setNickname(usrInfo.getNickname());
		
		return usr;
	}
}
