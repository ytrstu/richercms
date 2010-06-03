package com.sfeir.richercms.image.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.image.client.interfaces.IImageTreePanel;
import com.sfeir.richercms.page.client.PageConstants;

public class ImageTreePanel extends ResizeComposite implements IImageTreePanel{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Tree navigationTree = null;
	private TreeItem rootItem = null;
	
	public Widget asWidget() {	
		return this;
	}

	public void createView() {
		
		this.navigationTree =  new Tree();
		
		this.navigationTree.setAnimationEnabled(true);
		ScrollPanel main = new ScrollPanel();
		main.add(this.navigationTree);
		this.initWidget(main);
	}
	
	/**
	 * Clear the tree
	 */
	public void clearTree() {
		this.navigationTree.clear();
		this.rootItem = null;
	}
}
