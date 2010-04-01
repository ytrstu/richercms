package com.sfeir.richercms.wizardConfig.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

@RemoteServiceRelativePath("userService")
public interface UserInfoService extends RemoteService {
  public BeanUserInfo login(String requestUri);
}
