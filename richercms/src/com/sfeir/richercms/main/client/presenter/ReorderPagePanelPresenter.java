package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.FlexTableRowDragController;
import com.sfeir.richercms.main.client.event.FlexTableRowDropController;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.main.client.view.ReorderPagePanel;
import com.sfeir.richercms.main.shared.BeanArboPage;

@Presenter( view = ReorderPagePanel.class)
public class ReorderPagePanelPresenter extends LazyPresenter<IReorderPagePanel, MainEventBus>{


	private ArboPageServiceAsync rpcPage = null;
	private FlexTableRowDragController listDragController = null;
	private FlexTableRowDropController listDropController = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
	    // instantiate our drag controller
	     this.listDragController = new FlexTableRowDragController(
	    		this.view.getPageListPanel());
	     
	     // instantiate a drop controller for each table
	     this.listDropController = new FlexTableRowDropController(this.view.getPageList());
	     this.listDragController.registerDropController(this.listDropController);
	}
	
	public void addChildInList(String parentKey){
		
		this.rpcPage.getChildPages(parentKey, false,
				new AsyncCallback<List<BeanArboPage>>() {
			public void onSuccess(List<BeanArboPage> result) {
				for(BeanArboPage subPage : result) {
					Widget w = view.addNewPage(subPage.getTranslation().get(0).getUrlName(), 1,
							subPage.getTranslation().get(0).getEncodedKey());
					listDragController.makeDraggable(w);
				}
			}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage("ERROR BUILD LIST");
				p.show();}
		});
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onStartReorderPanel(String parentKey) {
		this.addChildInList(parentKey);
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
