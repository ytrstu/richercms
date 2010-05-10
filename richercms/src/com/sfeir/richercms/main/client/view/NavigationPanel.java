package com.sfeir.richercms.main.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IPopUpMenuBar;

/**
 * Panel containing the tree who represent the architectural view of the site.
 * @author homberg.g
 *
 */
public class NavigationPanel extends ResizeComposite implements INavigationPanel{

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private Tree navigationTree = null;
	private TreeItem rootItem = null;
	private PopUpMenuBar menuBar = null;
	
	public NavigationPanel() {
		super();
	}

	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.menuBar = new PopUpMenuBar();
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
	
	public HasOpenHandlers<TreeItem> getExpandedEvtTree() {
		return this.navigationTree;
	}
		
	public HasSelectionHandlers<TreeItem> getSelectedEvtTree() {
		return this.navigationTree;
	}
	
	public IPopUpMenuBar getPopUpMenuBar () {
		return this.menuBar;
	}
	
	public void setTree(TreeItem root) {
		this.navigationTree.addItem(root);
	}
	
	public void setTextOfSelectedTI(String text){
		HorizontalPanel p =(HorizontalPanel)this.navigationTree.getSelectedItem().getWidget();
		Label l = (Label)p.getWidget(1);
		l.setText(text);
	}
	
	public void deleteSelectedTI() {
		this.navigationTree.getSelectedItem().remove();
	}
	
	public MainConstants getConstants() {
		return this.constants;
	}
}
