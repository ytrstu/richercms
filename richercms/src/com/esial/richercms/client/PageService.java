package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pages")
public interface PageService extends RemoteService {
	public void addPage(String unique_name) throws NotLoggedInException;
}
