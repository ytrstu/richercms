package com.sfeir.richercms.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.main.shared.BeanPage;


/**
 * All services needed to handle WebPages
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("pageService")
public interface PageService extends RemoteService {
	
	/**
	 * Return the list containing all webPages needed to display
	 * @param translationKey : the key of the current translation, if null we take the default result
	 * @return the list containing all webPages
	 */
	public List<BeanPage>  getPages();
	
	/**
	 * Get the main Page in a special translation
	 * @param translationKey : key of the type of translation
	 * @return the main Page and all its subPages
	 */
	public BeanPage getMainPage(String translationKey);
	
	/**
	 * Return a page according  is key
	 * @param key unique id of the Page
	 * @return the good page
	 */
	BeanPage getPage(String key);
	
	/**
	 * Add a new Page in the dataStore
	 * @param newPage : add it in dataStore
	 */
	public void addPage(BeanPage newPage);
	
	/**
	 * Modify this page in the datastore
	 * @param p : changed page
	 */
	public void updatePage(BeanPage p);
	
	/**
	 * delete a page 
	 * @param key : the key of the Page
	 */
	public void deletePage(String key);
	
}
