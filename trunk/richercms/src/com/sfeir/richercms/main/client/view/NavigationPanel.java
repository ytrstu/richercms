package com.sfeir.richercms.main.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IPopUpTree;

/**
 * Panel containing the tree who represent the architectural view of the site.
 * @author homberg.g
 *
 */
public class NavigationPanel extends ResizeComposite implements INavigationPanel{

	private Tree navigationTree = new Tree();
	private TreeItem rootItem = null;
	private PopUpTree popUp = new PopUpTree();
	
	public NavigationPanel() {
		super();
		createView();
	}

	/**
	 * Create the widget and attached all component
	 */
	private void createView() {
		this.navigationTree.setAnimationEnabled(true);
		LayoutPanel main = new LayoutPanel();
		main.add(this.navigationTree);
		this.initWidget(main);
	}
	
	public void addPageInTree(String name, String key)
	{
		TreeItem t = new TreeItem();
		t.setUserObject(key);
		t.setText(name);
		
		if(this.rootItem == null) {
			this.rootItem = t;
			this.navigationTree.addItem(this.rootItem);
		}
		else
			this.rootItem.addItem(t);
	}
	
	public void clearTree() {
		this.navigationTree.clear();
		this.rootItem = null;
	}
	
	public HasMouseDownHandlers getTreeMouseDown()
	{
		return this.navigationTree;
	}
	public HasSelectionHandlers<TreeItem> getSelectedEvtTree() {
		return this.navigationTree;
	}
	
	public IPopUpTree getPopUpTree() {
		return this.popUp;
	}
	
}
