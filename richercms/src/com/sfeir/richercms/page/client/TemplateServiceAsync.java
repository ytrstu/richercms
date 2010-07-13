package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanTemplate;

public interface TemplateServiceAsync {

	public void addTemplate(BeanTemplate bean, AsyncCallback<Long> callback);
	public void getTemplate(Long id, AsyncCallback<BeanTemplate> callback);
	public void getAllTemplate(AsyncCallback<List<BeanTemplate>> callback);
	public void upDateTagsTemplate(Long templateId, List<Long> tagIds, AsyncCallback<Void> callback);
	public void updateTemplate(Long id, String name, String shortLib, String description, AsyncCallback<Boolean> callback) ;
	public void deleteTemplate(Long id, AsyncCallback<Void> callback);
}
