package com.sfeir.richercms.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.main.shared.BeanPage;

/**
 * WebPage Services
 * @author homberg.g
 *
 */
public interface PageServiceAsync {

	public void getPages(AsyncCallback<List<BeanPage>> callback);
	public void getPage(String key, AsyncCallback<BeanPage> callback);
	public void addPage(BeanPage newPage, AsyncCallback<Void> callback);
	public void updatePage(BeanPage p, AsyncCallback<Void> callback);
	public void deletePage(String key, AsyncCallback<Void> callback);
}
