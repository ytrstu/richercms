package com.sfeir.richercms.page.client.interfaces;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.custom.IPopUpMenuBar;

public interface INavigationPanel extends LazyView{

	/**
	 * Modify the view and add a new node in the tree
	 * @param name name of the node
	 * @param key of the Page
	 * @return HasClickHandlers of the button 
	 * for show the popUp on the clic
	 */
	HasClickHandlers addPageInTree(String name, String key);
	
	/**
	 * clear the webPage tree
	 */
	void clearTree();
	
	/**
	 * Necessary to handle the SelectedEvent on the NavigationTree
	 * @return the EvtHandler
	 */
	HasSelectionHandlers<TreeItem> getSelectedEvtTree();
	
	/**
	 * Necessary to handle the expandedEvent on the NavigationTree
	 * @return the EvtHandler
	 */
	HasOpenHandlers<TreeItem> getExpandedEvtTree();
	
	/**
	 * Return the interface of the PopUpMenuBar to control it in the presenter.
	 * @return : the PopUp
	 */
	IPopUpMenuBar getPopUpMenuBar ();
	
	/**
	 * Set the root tree
	 * @param root
	 */
	void setTree(TreeItem root);
	
	/**
	 * Set the display text of the selected TreeItem
	 * @param text : new value to display
	 */
	void setTextOfSelectedTI(String text);
	
	/**
	 * Set the image displaying in the selectedNode
	 * @param img
	 */
	void setImageOfSelectedTI(Image img);
	
	/**
	 * Delete the selected TreeItem in the tree
	 */
	void deleteSelectedTI();
	
	/**
	 * Set the selected item
	 * @param item item to be selected
	 */
	void setSelectedItem(TreeItem item);
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	public PageConstants getConstants();
}
