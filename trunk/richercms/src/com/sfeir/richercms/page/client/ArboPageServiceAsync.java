package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanArboPage;

/**
 * WebPage Services
 * @author homberg.g
 *
 */
public interface ArboPageServiceAsync {

	public void getChildPages(Long ParentId, boolean isMain, AsyncCallback<List<BeanArboPage>> callback);
	
	public void getMainArboPage(AsyncCallback<BeanArboPage> callback);

	public void getArboPage(Long id, AsyncCallback<BeanArboPage> callback);

	public void addArboPage(BeanArboPage newArboPage, Long parentId, AsyncCallback<Void> callback);

	public void updateArboPage(BeanArboPage p, AsyncCallback<Void> callback);

	public void deleteArboPage(Long id, Long parentId, AsyncCallback<Void> callback);

	public void getLastChildAdded(Long parentId, AsyncCallback<BeanArboPage> callback);
	
	public void moveChildPage(Long parentId, Long childId, int index, AsyncCallback<Void> callback);
	
	public void updateChildOrder(Long id, List<Integer> newPositionOrder, AsyncCallback<Void> callback);
}
 