package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PageServiceAsync {
	public void addPage(String browser_title,String page_title, String url_name,String 
			description, String content, AsyncCallback<Void> async);

	public void getAllPages(AsyncCallback<String[]> callback);

	public void removePage(String page_title, AsyncCallback<Void> callback);

	public void getPageData(String page_title, AsyncCallback<String[]> asyncCallback);

	public void editPage(String browser_title, String page_title, String url_name,
			String description, String content, boolean hasTitleChanged, AsyncCallback<Void> callback);
}
