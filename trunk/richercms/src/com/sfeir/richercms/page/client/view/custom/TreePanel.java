package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.custom.ITreePanel;

/**
 * This class is a ScrollPanel who containing a tree.
 * Its contain all useful method use by all widgetTree 
 * in this application.
 * @author homberg.g
 *
 */
public class TreePanel extends ResizeComposite implements ITreePanel{

	//gestion des langues
	protected PageConstants constants = GWT.create(PageConstants.class);
	protected Tree navigationTree = null;
	protected TreeItem rootItem = null;
	
	
	public TreePanel() {
		super();
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
	
	public HasOpenHandlers<TreeItem> getExpandedEvtTree() {
		return this.navigationTree;
	}
		
	public HasSelectionHandlers<TreeItem> getSelectedEvtTree() {
		return this.navigationTree;
	}
	
	
	public void setTree(TreeItem root) {
		this.navigationTree.addItem(root);
	}
	
	public void setTextOfSelectedTI(String text){
		HorizontalPanel p =(HorizontalPanel)this.navigationTree.getSelectedItem().getWidget();
		Label l = (Label)p.getWidget(1);
		l.setText(text);
	}
	
	public void setImageOfSelectedTI(Image img) {
		HorizontalPanel p =(HorizontalPanel)this.navigationTree.getSelectedItem().getWidget();
		p.insert(img, 0);
		p.remove(1);
	}
	
	public void deleteSelectedTI() {
		this.navigationTree.getSelectedItem().remove();
	}
	
	public PageConstants getConstants() {
		return this.constants;
	}
	
	public void setSelectedItem(TreeItem item) {
		navigationTree.setSelectedItem(item, true);
		navigationTree.ensureSelectedItemVisible();
	}
	
	public void setConstants(PageConstants constants) {
		this.constants = constants;
	}
	public Tree getNavigationTree() {
		return navigationTree;
	}
	public void setNavigationTree(Tree navigationTree) {
		this.navigationTree = navigationTree;
	}
	public TreeItem getRootItem() {
		return rootItem;
	}
	public void setRootItem(TreeItem rootItem) {
		this.rootItem = rootItem;
	}
}
