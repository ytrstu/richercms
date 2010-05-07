package com.sfeir.richercms.main.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.main.shared.BeanArboPage;

/**
 * All services needed to handle WebPages
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("arboPageService")
public interface ArboPageService extends RemoteService {
	
	/**
	 * Return the list containing all webPages child of a parent arboPage, needed to display
	 * @param ParentKey : the key of the parent ArboPage
	 * @param true if the parent are the main, false either
	 * @return the list containing all child of the current ParentPage
	 */
	public List<BeanArboPage>  getChildPages(String ParentKey, boolean isMain);
	
	/**
	 * Get the main ArboPage in a special translation
	 * @return the main Page and all its subPages
	 */
	public BeanArboPage getMainArboPage();
	
	/**
	 * Return an ArboPage according his key
	 * @param key unique id of the ArboPage
	 * @return the good ArboPage, null either.
	 */
	public BeanArboPage getArboPage(String key);
	
	/**
	 * Add a new ArboPage in the dataStore
	 * @param newPage : add it in dataStore
	 * @param parentKey : ID of the parentPage
	 */
	public void addArboPage(BeanArboPage newArboPage, String parentKey);
	
	/**
	 * Modify this page in the datastore
	 * @param p : changed page
	 */
	public void updateArboPage(BeanArboPage p);
	
	/**
	 * delete a page and all translation of this page
	 * @param key : the key of the ArboPage
	 * @param parentKey : the key of the parent : 
	 * necessary to erase the key of the deleted child in the parent's child List
	 */
	public void deleteArboPage(String key, String parentKey);
	
	/**
	 * Return the last ArboPage added in a parentPage.
	 * @param parentKey : the key of parentPage
	 * @return the last child or null if the parent are no child
	 */
	public BeanArboPage getLastChildAdded(String parentKey);
}
