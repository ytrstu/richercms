package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;

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
	 * @param recPath : recursive path : selectedPageId => root
	 */
	public void updateArboPage(BeanArboPage p, List<Long> recPath);
	
	/**
	 * delete a page and all translation of this page
	 * @param id : the id of the ArboPage
	 * @param parentId : the id of the parent : 
	 * necessary to erase the id of the deleted child in the parent's child List
	 */
	public boolean deleteArboPage(Long id, Long parentId);
	
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
	
	/**
	 * Take a list of id and build a path with ArboPage URL name
	 * @param ids : list of id => leaf to root
	 * @return path : "rootUrlName/.../leafUrlName/"
	 */
	public String getPath(List<Long> ids);
	
	/**
	 * Unlock a specific page and allows other 
	 * user to modify this one
	 * @param pageId : the id of the specific page
	 */
	public void unlockThisPage(Long pageId);
	
	/**
	 * This function make any modification on the lock field
	 * use this one if you need information about the state of the page lock
	 * @param pageId : id of the specific page
	 * @return Id of the user who lock this page, null if this page is free
	 */
	public Long lockPageInfo(Long pageId);
	
	/**
	 * This function lock the specific page with this user id but
	 * just if this page was'nt lock by an other user.
	 * @param pageId : id of the specific page
	 * @param userId : id of the user who would modify this page
	 * @return Id of an user if this page is already lock, null if it free
	 */
	public Long lockThisPage(Long pageId,Long userId);
	
	/**
	 * return all locked page by users
	 * @return list of beanArboPage
	 */
	public List<BeanArboPage> getAllLockedPages();
	
	/**
	 * return the default translation of this page
	 * @param pageId : id of needed page
	 * @return : the corresponding BeanTranslationPage
	 */
	public BeanTranslationPage getDefaultTranslation(Long pageId);
	
	/**
	 * Unlock all page locked by a specific user.
	 * @param idUser : id of the specific user
	 */
	public void unlockAllUserPage(Long idUser);
	
	/**
	 * Test if an urlName are no doublon's Path
	 * @param parentId : parent page's id : necessary to research doublon in a specific path
	 * @param pageId : id of th current page
	 * @param urlNames : urlName who you need a test
	 * @return true if path does not exist, false either
	 */
	public boolean existSameUrl(Long parentId, Long pageId, List<String> urlNames);
}
