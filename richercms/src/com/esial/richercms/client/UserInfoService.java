package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface UserInfoService extends RemoteService {

	public String getEmail();
	public String logout();
}
