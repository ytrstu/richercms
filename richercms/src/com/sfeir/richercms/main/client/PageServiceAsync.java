package com.sfeir.richercms.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.main.shared.Page;


/**
 * WebPage Services
 * @author homberg.g
 *
 */
public interface PageServiceAsync {

	public void getPages(AsyncCallback<List<Page>> callback);
	public void getPage(int id, AsyncCallback<Page> callback);
	public void addPage(Page newPage, AsyncCallback<Void> callback);
	public void updatePage(Page p, AsyncCallback<Void> callback);
	public void deletePage(int id, AsyncCallback<Void> callback);
}
