package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.page.shared.BeanTemplate;

/**
 * All services needed to handle templates
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("templateService")
public interface TemplateService extends RemoteService {
	
	public Long addTemplate(BeanTemplate bean);
	public BeanTemplate getTemplate(Long id);
	public List<BeanTemplate> getAllTemplate();
	public void upDateTagsTemplate(Long templateId, List<Long> tagIds);
	public void updateTemplate(Long id, String name, String description);
	public void deleteTemplate(Long id);

}
