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
	
	/**
	 * Add a new template
	 * @param bean : new template
	 * @return Template's id or null if another template have same name or shortlib
	 */
	public Long addTemplate(BeanTemplate bean);
	
	/**
	 * Get specific template
	 * @param id : template's id
	 * @return template or null if this id are no template corresponding
	 */
	public BeanTemplate getTemplate(Long id);
	
	/**
	 * Return all template
	 * @return List with all template stored in datastore
	 */
	public List<BeanTemplate> getAllTemplate();
	
	/**
	 * Update tags into a specific template
	 * @param templateId : template's id
	 * @param tagIds : list who containing selected tags id
	 */
	public void upDateTagsTemplate(Long templateId, List<Long> tagIds);
	
	/**
	 * Update specific template
	 * @param id : template's id who need modification
	 * @param name : template's name
	 * @param shortLib : template's shorlib
	 * @param description : template's description
	 * @return true if template are sucessfuly update, false if another template have same name or shortlib
	 */
	public boolean updateTemplate(Long id, String name, String shortLib, String description);
	
	/**
	 * Delete specific template if it was stored in dataStore
	 * @param id : template's id
	 */
	public void deleteTemplate(Long id);

}
