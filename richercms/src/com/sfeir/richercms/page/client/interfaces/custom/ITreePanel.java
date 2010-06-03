package com.sfeir.richercms.page.client.interfaces.custom;

import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.page.client.PageConstants;

/**
 * Interface of TreePanel
 * 
 * @author homberg.g
 *
 */
public interface ITreePanel {

	/**
	 * This function create the panel and instanciate all component
	 * its very useful for async loading => accurate performance
	 */
	void createView();
	
	/**
	 * Remove all node in the tree and set the rootItem to null;
	 */
	void clearTree();
	
	/**
	 * Needed to handle the Expanded Event of a node in the tree
	 * @return EVENT
	 */
	HasOpenHandlers<TreeItem> getExpandedEvtTree();
	
	/**
	 * Needed to handle the selection Event of a node in the tree
	 * @return
	 */
	HasSelectionHandlers<TreeItem> getSelectedEvtTree();
	
	/**
	 * Add the rootNode of the tree
	 * @param root : the new rootNode
	 */
	void setTree(TreeItem root);
	
	/**
	 * Modify the text of the selectedNode
	 * @param text : new text
	 */
	void setTextOfSelectedTI(String text);
	
	/**
	 * Modify the Image of the selectedNode
	 * @param img
	 */
	void setImageOfSelectedTI(Image img);
	
	/**
	 * Delete the selectedNode to the tree
	 */
	void deleteSelectedTI();
	
	/**
	 * Get the page constant
	 * @return constant
	 */
	PageConstants getConstants();
	
	/**
	 * Set a node to select state
	 * @param item : the node who need to change the state
	 */
	void setSelectedItem(TreeItem item);
}
