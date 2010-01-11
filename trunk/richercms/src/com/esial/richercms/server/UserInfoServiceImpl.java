package com.esial.richercms.server;

import com.esial.richercms.client.UserInfo;
import com.esial.richercms.client.UserInfoService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UserInfoServiceImpl extends RemoteServiceServlet implements UserInfoService {

  public UserInfo login(String requestUri) {
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    UserInfo loginInfo = new UserInfo();

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
