package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.view.NavigationPanel;
import com.sfeir.richercms.main.shared.BeanPage;


@Presenter( view = NavigationPanel.class)
public class NavigationPanelPresenter extends LazyPresenter<INavigationPanel, MainEventBus>{

	private TreeItem selectedItem = null; // current selected Item in tree
	private PageServiceAsync rpcPage = null;
	
	public NavigationPanelPresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
		view.getSelectedEvtTree()
		.addSelectionHandler(new SelectionHandler<TreeItem>(){
			public void onSelection(SelectionEvent<TreeItem> event) {
				setSelectedItem(event.getSelectedItem()); // fait des actions spécifique
				eventBus.displayPage((String) selectedItem.getUserObject());
				showMenuButton();
			}
		});
		// commande pour la suppression d'une page
		this.view.getPopUpMenuBar().setDelPageCommand(new Command(){
			public void execute() {
				popUpDeletePage();
				NavigationPanelPresenter.this.eventBus.deletePage();
			}});
		// commande pour l'ajout d'une sous-page
		this.view.getPopUpMenuBar().setAddPageCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.eventBus.addPage();
				view.getPopUpMenuBar().hide();
			}});
	}
	
	/**
	 * Show button using to display menuBar
	 */
	public void showMenuButton() {
		HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
		panel.getWidget(1).setVisible(true);
	}

	/**
	 * Use this when a new Item in the tree is selected.
	 * @param selectedItem
	 */
	public void setSelectedItem(TreeItem selectedItem) {
		if(this.selectedItem!=null) {
			HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
			panel.getWidget(1).setVisible(false);
		}
		this.selectedItem = selectedItem;
	}
	
	/**
	 * delete page selected in the tree
	 */
	public void popUpDeletePage() {	
		
		view.getPopUpMenuBar().hide();
		
		this.rpcPage.deletePage((String)selectedItem.getUserObject(), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				onBuildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : DeletePage");
				p.show();}
		});
		
		selectedItem = null;
	}
	
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////

	public void onStartPanels() {		
		this.eventBus.changeNavPanel(this.view);
		this.onBuildTree();
	}
	
	/**
	 * build the webPage tree with information in the datastore
	 */
	public void onBuildTree() {
		
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
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( PageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
