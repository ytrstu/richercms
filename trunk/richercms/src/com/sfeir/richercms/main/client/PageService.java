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
	 * @return the list containing all webPages
	 */
	public List<BeanPage>  getPages();
	
	/**
	 * Return a page according  is id
	 * @param id position in the list
	 * @return the good page
	 */
	BeanPage getPage(int id);
	
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
	 * @param id : position of the page in the list
	 */
	public void deletePage(int id);

}
