package com.sfeir.richercms.wizardConfig.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

/**
 * All services needed to handle user
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("userService")
public interface UserInfoService extends RemoteService {
	
	/**
	 * return informations about the user
	 * @param requestUri => necessary for using google-login Agent
	 * @return information about user and if it was connected
	 */
	public BeanUserInfo login(String requestUri);
}
