package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("stats")
public interface StatService extends RemoteService {
	public int getPagesCount() throws NotLoggedInException;
}
