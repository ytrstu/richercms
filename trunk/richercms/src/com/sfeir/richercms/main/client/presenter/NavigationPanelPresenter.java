package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.shared.BeanPage;

public class NavigationPanelPresenter {

	private INavigationPanel view = null;
	private PageServiceAsync rpcPage = null;
	private TreeItem selectedItem = null; // current selected Item in tree
	private MainEventBus eventBus = null;
	
	public NavigationPanelPresenter() {
		this.view = null;
		this.rpcPage = null;
		this.eventBus = null;
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
	public void onStartNavPanel(INavigationPanel navPanel, MainEventBus eventBus) {
		this.eventBus = eventBus;
		this.view = navPanel;
		
		this.view.getPopUpMenuBar().setDelPageCommand(new Command(){
			public void execute() {
				popUpDeletePage();
				NavigationPanelPresenter.this.eventBus.DeletePage();
			}});
		
		this.view.getPopUpMenuBar().setAddPageCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.eventBus.AddPage();
				view.getPopUpMenuBar().hide();
			}});
		
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
	    			view.addPageInTree(page.getPageTitle(),page.getKey())
	    			.addClickHandler(new ClickHandler() { // open the popUpMenu
	    				public void onClick(ClickEvent event) {
	    					Button b = (Button)event.getSource();
	    					view.getPopUpMenuBar().setPopupPosition(b.getAbsoluteLeft() + b.getOffsetWidth(),
	    															b.getAbsoluteTop() + b.getOffsetHeight());
	    					view.getPopUpMenuBar().show();
	    				}});
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
		
		view.getPopUpMenuBar().hide();
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
		HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
		panel.getWidget(1).setVisible(true);
		
		return key;
	}

	public TreeItem getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TreeItem selectedItem) {
		if(this.selectedItem!=null) {
			HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
			panel.getWidget(1).setVisible(false);
		}
		this.selectedItem = selectedItem;
	}

	public PageServiceAsync getRpcPage() {
		return rpcPage;
	}

	public void setRpcPage(PageServiceAsync rpcPage) {
		this.rpcPage = rpcPage;
	}
}
