package com.sfeir.richercms.main.client.view;

import com.google.gwt.core.client.GWT;
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

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private Tree navigationTree = new Tree();
	private TreeItem rootItem = new TreeItem(constants.MainWebSitePage());
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
		this.rootItem = new TreeItem(constants.MainWebSitePage());
		this.navigationTree.addItem(rootItem);
	}
	
	public HasSelectionHandlers<TreeItem> getSelectedEvtTree() {
		return this.navigationTree;
	}
	
	public IPopUpTree getPopUpTree() {
		return this.popUp;
	}
	
}
