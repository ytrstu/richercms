package com.sfeir.richercms.page.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.page.shared.BeanDependentTag;
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
	 * Get all dependentTag link into a page
	 * @param pageId : pageId's id
	 * @return a list who containig required BeanDependentTags
	 */
	List<BeanDependentTag> getAllDependentTag(Long pageId);
	
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
	
	/**
	 * Update list of dependentTag
	 * @param updateDTags : List of DependentTag who need modification
	 * @param addedTags : List of new DependentTag to save in datastore
	 * @param deletedTags : List of DependentTag who need to delete
	 * @param customTag : Map tagId (include in addedTags) and a string custom value.
	 * @return the new id list after fusion of Add/Delete/Update result.
	 */
	List<Long> upDateDependentTag(List<BeanDependentTag> updateDTags, List<Long> addedTags,
			List<Long> deletedTags, HashMap<Long,String> customTag);
	
	/**
	 * Return an Ids list who containing all associated tag to a specific page
	 * @param pageId : id of specific page
	 * @return : list ont associated
	 */
	List<Long> getAssociatedTag(Long pageId);
}
