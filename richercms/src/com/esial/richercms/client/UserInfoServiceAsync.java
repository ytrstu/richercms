package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	public void login(String requestUri, AsyncCallback<UserInfo> async);
}
