package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanTag;

public interface TagServiceAsync {

	void getAllTags(AsyncCallback<List<BeanTag>> callback);
	void getTag(Long id, AsyncCallback<BeanTag> callback);
	void deleteTag(Long id, AsyncCallback<Void> callback);
	void addTag(BeanTag bean, AsyncCallback<Void> callback);
	void updateTag(BeanTag bean, AsyncCallback<Void> callback);
}
