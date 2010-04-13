package com.sfeir.richercms.main.client.presenter;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.Page;


@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private PageServiceAsync rpcPage = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
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
	private void buildTree()
	{
		this.rpcPage.getPages(new AsyncCallback<List<Page>>() {
	    	public void onSuccess(List<Page> result) {
	    		view.clearTree();	    		
	    		for(Page page : result)
	    			view.addPageInTree(page.getPageTitle());
	    	}
			public void onFailure(Throwable caught){}
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
