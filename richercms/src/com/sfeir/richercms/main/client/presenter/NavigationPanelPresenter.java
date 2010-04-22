package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.shared.BeanPage;

public class NavigationPanelPresenter {

	private INavigationPanel view = null;
	private PageServiceAsync rpcPage = null;
	private TreeItem selectedItem = null; // current selected Item in tree
	
	public NavigationPanelPresenter() {
		this.view = null;
		this.rpcPage = null;
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartNavPanel(INavigationPanel navPanel) {
		this.view = navPanel;
		this.buildTree();
	}
	
	/**
	 * build the webPage tree with information in the datastore
	 */
	public void buildTree() {
		
		this.rpcPage.getPages(new AsyncCallback<List<BeanPage>>() {
	    	public void onSuccess(List<BeanPage> result) {
	    		view.clearTree();	    		
	    		for(BeanPage page : result)
	    			view.addPageInTree(page.getPageTitle(),page.getKey());
	    	}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage("Error : Build tree");
				p.show();}
			});
	}
	
	/**
	 * delete page selected in the tree
	 */
	public void popUpDeletePage() {	
		
		view.getPopUpTree().hide();
		//this.rpcPage
		final TreeItem parent = selectedItem.getParentItem();
		
		this.rpcPage.deletePage(parent.getChildIndex(selectedItem), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : DeletePage");
				p.show();}
		});
		
		selectedItem = null;
	}
	
	public String showPopUpAction() {
		
		String key = (String) this.selectedItem.getUserObject();
		
		// place the popUp at the right position
		view.getPopUpTree().setPopupPosition(this.selectedItem.getAbsoluteLeft(),
													this.selectedItem.getAbsoluteTop());
		view.getPopUpTree().show(1);
		
		return key;
	}

	public TreeItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TreeItem selectedItem) {
		this.selectedItem = selectedItem;
	}

	public PageServiceAsync getRpcPage() {
		return rpcPage;
	}

	public void setRpcPage(PageServiceAsync rpcPage) {
		this.rpcPage = rpcPage;
	}
}
