package com.sfeir.richercms.main.client.presenter;


import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanPage;

@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private PageServiceAsync rpcPage = null;
	private TreeItem selectedItem = null; // current selected Item in tree
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		view.getNavigationPanel().getSelectedEvtTree()
			.addSelectionHandler(new SelectionHandler<TreeItem>(){
				public void onSelection(SelectionEvent<TreeItem> event) {
					selectedItem = event.getSelectedItem();
					popUpAction(); // all action on the popUp
					view.getInformationPanel().deasabledWidgets();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnDelPage()
			.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					popUpDeletePage();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnAddPage()
		.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				view.getInformationPanel().enabledWidgets();
			}
	});
	}
	
	/**
	 * Fired when the main do start
	 */
	public void onStartMain() {
		eventBus.changeBody(view.asWidget());
		this.buildTree();
	}
	
	/**
	 * build the webPage tree with information in the datastore
	 */
	private void buildTree() {
		
		this.rpcPage.getPages(new AsyncCallback<List<BeanPage>>() {
	    	public void onSuccess(List<BeanPage> result) {
	    		view.getNavigationPanel().clearTree();	    		
	    		for(BeanPage page : result)
	    			view.getNavigationPanel().addPageInTree(page.getPageTitle());
	    	}
			public void onFailure(Throwable caught){}
			});
	}
	
	private void popUpAction() {
		
		// place the popUp at the right position
		view.getNavigationPanel().getPopUpTree().setPopupPosition(this.selectedItem.getAbsoluteLeft(),
													this.selectedItem.getAbsoluteTop());
		view.getNavigationPanel().getPopUpTree().show(1);
		
		
	}

	private void popUpDeletePage() {	
		
		view.getNavigationPanel().getPopUpTree().hide();
		//this.rpcPage
		final TreeItem parent = selectedItem.getParentItem();
		
		this.rpcPage.deletePage(parent.getChildIndex(selectedItem), new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				Window.alert("Error : DeletePage");}
		});
		
		selectedItem = null;
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
