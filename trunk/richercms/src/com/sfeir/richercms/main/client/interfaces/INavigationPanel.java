package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.TreeItem;

public interface INavigationPanel {

	/**
	 * Modify the view and add a new node in the tree
	 * @param name name of the node
	 */
	void addPageInTree(String name);
	
	/**
	 * clear the webPage tree
	 */
	void clearTree();
	
	HasSelectionHandlers<TreeItem> getSelectedEvtTree();
	
	IPopUpTree getPopUpTree();
}
