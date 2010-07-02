package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.page.shared.BeanTag;

/**
 * All services needed to handle tags
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("tagService")
public interface TagService extends RemoteService {

	/**
	 * Get all tag
	 * @return All tag
	 */
	List<BeanTag> getAllTags();
	
	/**
	 * Get many tags
	 * @param tagids : list of tag's id
	 * @return a list who containig required tags
	 */
	List<BeanTag> getTags(List<Long> tagids);
	
	/**
	 * Get a specific tag
	 * @param id : id of specific tag
	 * @return corresponding BeanTag or null if not exist in datastore
	 */
	BeanTag getTag(Long id);
	
	/**
	 * Delete specific tag
	 * @param id : if of specific tag
	 */
	void deleteTag(Long id);
	
	/**
	 * Add a new tag in datastore
	 * @param bean : corresponding bean with id == null
	 */
	void addTag(BeanTag bean);
	
	/**
	 * Update a tag
	 * @param bean : corresponding bean with id != null
	 */
	void updateTag(BeanTag bean);
}
