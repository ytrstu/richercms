package com.sfeir.richercms.wizardConfig.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.wizardConfig.client.UserInfoService;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

/**
 * Implementation of all User Services.
 * @author homberg.g
 *
 */
@SuppressWarnings("serial")
public class ServiceUserInfoImpl extends RemoteServiceServlet implements UserInfoService {

  public BeanUserInfo login(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    BeanUserInfo loginInfo = new BeanUserInfo();

    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());
      loginInfo.setNickname(user.getNickname());
      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
      if(userService.isUserAdmin()) loginInfo.setAdmin(true);
      else loginInfo.setAdmin(false);
    } else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
    }
    return loginInfo;
  }

}
