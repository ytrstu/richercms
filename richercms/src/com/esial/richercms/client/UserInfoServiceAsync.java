package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {

	void getEmail(AsyncCallback<String> callback);

	void logout(AsyncCallback<String> callback);

}
