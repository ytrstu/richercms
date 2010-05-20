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
	 * @param ParentId : the id of the parent ArboPage
	 * @param true if the parent are the main, false either
	 * @return the list containing all child of the current ParentPage
	 */
	public List<BeanArboPage>  getChildPages(Long ParentId, boolean isMain);
	
	/**
	 * Get the main ArboPage in a special translation
	 * @return the main Page and all its subPages
	 */
	public BeanArboPage getMainArboPage();
	
	/**
	 * Return an ArboPage according his key
	 * @param id : unique id of the ArboPage
	 * @return the good ArboPage, null either.
	 */
	public BeanArboPage getArboPage(Long id);
	
	/**
	 * Add a new ArboPage in the dataStore
	 * @param newPage : add it in dataStore
	 * @param parentId : ID of the parentPage
	 */
	public void addArboPage(BeanArboPage newArboPage, Long parentId);
	
	/**
	 * Modify this page in the datastore
	 * @param p : changed page
	 */
	public void updateArboPage(BeanArboPage p);
	
	/**
	 * delete a page and all translation of this page
	 * @param id : the id of the ArboPage
	 * @param parentId : the id of the parent : 
	 * necessary to erase the id of the deleted child in the parent's child List
	 */
	public void deleteArboPage(Long id, Long parentId);
	
	/**
	 * Return the last ArboPage added in a parentPage.
	 * @param parentId : the id of parentPage
	 * @return the last child or null if the parent are no child
	 */
	public BeanArboPage getLastChildAdded(Long parentId);
	
	/**
	 * Move a child of a page at a specific index.
	 * @param parentId : the id of the parentPage
	 * @param childId : the id of the child you need to move
	 * @param index : the new index ( 0 =< index =< child_count )
	 */
	public void moveChildPage(Long parentId, Long childId, int index);
	
	/**
	 * Modify the list of child of a page.
	 * It can be use for modify the order of children
	 * Old position 1 to x  ==> Datastore 0 to x-1
	 * @param id : the id of the current Page
	 * @param newPositionOrder : list of old position of a page sorting in a new order
	 */
	public void updateChildOrder(Long Id, List<Integer> newPositionOrder);
}
