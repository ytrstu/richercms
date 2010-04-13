package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Panel containing the tree who represent the architectural view of the site.
 * @author homberg.g
 *
 */
public class NavigationPanel extends ResizeComposite{

	private Tree navigationTree = new Tree();
	private TreeItem rootItem = new TreeItem("site");
	
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
		
		this.navigationTree.addItem(rootItem);
		
		main.add(this.navigationTree);
		this.initWidget(main);
	}
	
	public void addPageInTree(String name)
	{
		this.rootItem.addItem(name);
	}
	
	public void clearTree() {
		this.navigationTree.clear();
		this.rootItem = new TreeItem("site");
		this.navigationTree.addItem(rootItem);
	}
}
