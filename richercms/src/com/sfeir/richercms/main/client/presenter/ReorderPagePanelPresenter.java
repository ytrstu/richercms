package com.sfeir.richercms.main.client.presenter;

import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.main.client.view.ReorderPagePanel;

@Presenter( view = ReorderPagePanel.class)
public class ReorderPagePanelPresenter extends LazyPresenter<IReorderPagePanel, MainEventBus>{


	private ArboPageServiceAsync rpcPage = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onStartReorderPanel() {
		this.eventBus.displayReorderPage(this.view);
	}
	
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
