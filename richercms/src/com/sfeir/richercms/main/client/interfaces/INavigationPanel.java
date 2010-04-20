package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.TreeItem;

public interface INavigationPanel {

	/**
	 * Modify the view and add a new node in the tree
	 * @param name name of the node
	 * @param key of the Page
	 */
	void addPageInTree(String name, String key);
	
	/**
	 * clear the webPage tree
	 */
	void clearTree();
	
	HasSelectionHandlers<TreeItem> getSelectedEvtTree();
	
	HasMouseDownHandlers getTreeMouseDown();
	
	IPopUpTree getPopUpTree();
}
