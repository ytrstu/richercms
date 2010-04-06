package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class NavigationPanel extends ResizeComposite{

	private Tree navigationTree = new Tree();
	
	public NavigationPanel() {
		super();
		createView();
	}

	private void createView() {
		LayoutPanel main = new LayoutPanel();
		
		TreeItem rootItem = new TreeItem("site");
		rootItem.addItem(new Label("item 1"));
		
		TreeItem item2 = new TreeItem("item 2");
		item2.addItem(new Label("item 2.1"));
		item2.addItem(new Label("item 2.2"));
		
		rootItem.addItem(item2);
		rootItem.addItem(new Label("item 3"));
		
		this.navigationTree.addItem(rootItem);
		
		main.add(this.navigationTree);
		this.initWidget(main);
	}
}
