package com.sfeir.richercms.wizardConfig.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

/**
 * User Services
 * @author homberg.g
 *
 */
public interface UserInfoServiceAsync {
	public void login(String requestUri, AsyncCallback<BeanUserInfo> async);
}
