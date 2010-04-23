package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.TreeItem;

public interface INavigationPanel {

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
	
	HasSelectionHandlers<TreeItem> getSelectedEvtTree();
	
	IPopUpMenuBar getPopUpMenuBar ();
}
