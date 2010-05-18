package com.sfeir.richercms.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.main.shared.BeanArboPage;

/**
 * WebPage Services
 * @author homberg.g
 *
 */
public interface ArboPageServiceAsync {

	public void getChildPages(String ParentKey, boolean isMain, AsyncCallback<List<BeanArboPage>> callback);
	
	public void getMainArboPage(AsyncCallback<BeanArboPage> callback);

	public void getArboPage(String key, AsyncCallback<BeanArboPage> callback);

	public void addArboPage(BeanArboPage newArboPage, String parentKey, AsyncCallback<Void> callback);

	public void updateArboPage(BeanArboPage p, AsyncCallback<Void> callback);

	public void deleteArboPage(String key, String parentKey, AsyncCallback<Void> callback);

	public void getLastChildAdded(String parentKey, AsyncCallback<BeanArboPage> callback);
	
	public void moveChildPage(String parentKey,String childKey,int index, AsyncCallback<Void> callback);
	
	public void updateChildOrder(String key, List<Integer> newPositionOrder, AsyncCallback<Void> callback);
}
 