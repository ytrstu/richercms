package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pages")
public interface PageService extends RemoteService {
	public void addPage(String browser_title,String page_title, String url_name,String 
			description, String content) throws NotLoggedInException;
	public String[] getAllPages() throws NotLoggedInException;
	public void removePage(String page_title) throws NotLoggedInException;
	public String[] getPageData(String page_title) throws NotLoggedInException;
	public void editPage(String browser_title,String page_title, String url_name,String 
			description, String content, boolean hasTitleChanged) throws NotLoggedInException;
}
