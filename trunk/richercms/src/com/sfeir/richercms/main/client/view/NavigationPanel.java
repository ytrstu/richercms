package com.sfeir.richercms.main.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IPopUpMenuBar;

/**
 * Panel containing the tree who represent the architectural view of the site.
 * @author homberg.g
 *
 */
public class NavigationPanel extends ResizeComposite implements INavigationPanel{

	private Tree navigationTree = new Tree();
	private TreeItem rootItem = null;
	private PopUpMenuBar menuBar = new PopUpMenuBar();
	
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
	
	public HasClickHandlers addPageInTree(String name, String key)
	{
		Button b = new Button(">");
		HorizontalPanel p = new HorizontalPanel();
		p.add(new Label(name));
		p.add(b);
		b.setVisible(false);
		TreeItem t = new TreeItem();
		t.setUserObject(key);
		t.setWidget(p);
		
		if(this.rootItem == null) {
			this.rootItem = t;
			this.navigationTree.addItem(this.rootItem);
		}
		else
			this.rootItem.addItem(t);
		
		
		return b;
	}
	
	public void clearTree() {
		this.navigationTree.clear();
		this.rootItem = null;
	}
	
	public HasSelectionHandlers<TreeItem> getSelectedEvtTree() {
		return this.navigationTree;
	}
	
	
	public IPopUpMenuBar getPopUpMenuBar () {
		return this.menuBar;
	}
	
}
