package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;

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

	public void updateArboPage(BeanArboPage p, List<Long> recPath, AsyncCallback<Void> callback);

	public void deleteArboPage(Long id, Long parentId, AsyncCallback<Boolean> callback);

	public void getLastChildAdded(Long parentId, AsyncCallback<BeanArboPage> callback);
	
	public void moveChildPage(Long parentId, Long childId, int index, AsyncCallback<Void> callback);
	
	public void updateChildOrder(Long id, List<Integer> newPositionOrder, AsyncCallback<Void> callback);
	
	public void getPath(List<Long> ids, AsyncCallback<String> callback);
	
	public void unlockThisPage(Long pageId, AsyncCallback<Void> callback);
	
	public void lockPageInfo(Long pageId, AsyncCallback<Long> callback);
	
	public void lockThisPage(Long pageId,Long userId, AsyncCallback<Long> callback);
	
	public void getAllLockedPages(AsyncCallback<List<BeanArboPage>> callback);
	
	public void getDefaultTranslation(Long pageId, AsyncCallback<BeanTranslationPage> callBack);
	
	public void unlockAllUserPage(Long idUser, AsyncCallback<Void> callback);
	
	public void existSameUrl(Long parentId, Long pageId, List<String> urlNames, AsyncCallback<Boolean> callback);
}
 