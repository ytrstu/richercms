package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.RemoteService;

public interface MyHTMLPageService extends RemoteService {
	public void addPage(String content) throws NotLoggedInException;
	public void deletePage(String qualified_name) throws NotLoggedInException;
	public String[] getPages() throws NotLoggedInException;
}
