package com.sfeir.richercms.wizardConfig.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

public interface UserInfoServiceAsync {
	public void login(String requestUri, AsyncCallback<BeanUserInfo> async);
}
