package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanDependentTag;
import com.sfeir.richercms.page.shared.BeanTag;

public interface TagServiceAsync {

	void getAllTags(AsyncCallback<List<BeanTag>> callback);
	void getTags(List<Long> tagids, AsyncCallback<List<BeanTag>> callback);
	void getTag(Long id, AsyncCallback<BeanTag> callback);
	void deleteTag(Long id, AsyncCallback<Void> callback);
	void addTag(BeanTag bean, AsyncCallback<Long> callback);
	void updateTag(BeanTag bean, AsyncCallback<Boolean> callback);
	void upDateDependentTag(List<BeanDependentTag> customTags, AsyncCallback<Void> callback);
	void getAllDependentTag(Long pageId, AsyncCallback<List<BeanDependentTag>> callback);
	void getAssociatedTag(Long pageId, AsyncCallback<List<Long>> callback);
}
