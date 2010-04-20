package com.sfeir.richercms.main.client.presenter;


import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Tree;
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
					popUpAction();
					view.getInformationPanel().deasabledWidgets();
				}
		});
		
		view.getNavigationPanel().getTreeMouseDown()
			.addMouseDownHandler(new MouseDownHandler(){
				public void onMouseDown(MouseDownEvent event) {
					//popUpAction();
					//view.getInformationPanel().deasabledWidgets();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnDelPage()
			.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					popUpDeletePage();
					view.getInformationPanel().clearFields();
					view.getNavigationPanel().getPopUpTree().hide();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnAddPage()
		.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.getInformationPanel().clearFields();
				view.getInformationPanel().enabledWidgets();
				view.getNavigationPanel().getPopUpTree().hide();
			}
		});
		
		view.getValidationPanel().getClicBtnAdd().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addPage();
				view.getInformationPanel().deasabledWidgets();
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
	    			view.getNavigationPanel().addPageInTree(page.getPageTitle(),page.getKey());
	    	}
			public void onFailure(Throwable caught){}
			});
	}
	
	private void popUpAction() {
		
		String key = (String) this.selectedItem.getUserObject();
		this.rpcPage.getPage(key, new AsyncCallback<BeanPage>() {
			public void onSuccess(BeanPage result) {
				displayPage(result);
			}
			public void onFailure(Throwable caught) {
				Window.alert("Error : Get current Page");}
		});
		
		// place the popUp at the right position
		view.getNavigationPanel().getPopUpTree().setPopupPosition(this.selectedItem.getAbsoluteLeft(),
													this.selectedItem.getAbsoluteTop());
		view.getNavigationPanel().getPopUpTree().show(1);
		
		
	}

	private void displayPage(BeanPage result) {
		view.getInformationPanel().setBrowserTitle(result.getBrowserTitle());
		view.getInformationPanel().setDescription(result.getDescription());
		view.getInformationPanel().setKeyWord(result.getKeyWord());
		view.getInformationPanel().setPageTitle(result.getPageTitle());
		//view.getInformationPanel().setPublicationFinish(result.getPublicationFinish());
		//view.getInformationPanel().setPublicationStart(result.getPublicationStart());
		view.getInformationPanel().setUrlName(result.getUrlName());
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
	
	private void addPage()
	{
		BeanPage newPage = new BeanPage();
		newPage.setBrowserTitle(view.getInformationPanel().getBrowserTitle());
		newPage.setContent("");
		newPage.setDescription(view.getInformationPanel().getDescription());
		newPage.setKeyWord(view.getInformationPanel().getKeyWord());
		newPage.setPageTitle(view.getInformationPanel().getPageTitle());
		newPage.setPublicationFinish(view.getInformationPanel().getPublicationFinish());
		newPage.setPublicationStart(view.getInformationPanel().getPublicationStart());
		newPage.setUrlName(view.getInformationPanel().getUrlName());
		
		this.rpcPage.addPage(newPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
			Window.alert("Error : AddPage");}
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
