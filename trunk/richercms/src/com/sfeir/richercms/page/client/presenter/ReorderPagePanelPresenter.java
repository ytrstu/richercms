package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.event.FlexTableRowDragController;
import com.sfeir.richercms.page.client.event.FlexTableRowDropController;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.view.ReorderPagePanel;
import com.sfeir.richercms.page.shared.BeanArboPage;

/**
 * Presenter of the reoderPage panel view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = ReorderPagePanel.class)
public class ReorderPagePanelPresenter extends LazyPresenter<IReorderPagePanel, PageEventBus>{


	private ArboPageServiceAsync rpcPage = null;
	private FlexTableRowDragController listDragController = null;
	private FlexTableRowDropController listDropController = null;
	private Long currentPageId;
	
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
	     
	     // the apply button Click Event
	     view.getApplyClick().addClickHandler(new ClickHandler() {  
		        public void onClick(ClickEvent event) {
		        	//PopUp
		        	eventBus.showInformationPopUp();
		        	eventBus.addSuccessPopUp(view.getConstants().PopUpTakeInfo());
		        	eventBus.addWaitLinePopUp(view.getConstants().PopUpSaveInProgress());
	
		        	// Save the new order in the parentPage
		        	rpcPage.updateChildOrder(currentPageId, view.getNewOrder(), 
		        			new AsyncCallback<Void>() {
		        		public void onSuccess(Void result) {
		        			eventBus.addSuccessPopUp(view.getConstants().PopUpSaveFinish());
		        			eventBus.displayCurrentStatePanel();
		        			eventBus.reloadChildInTree();
		        			eventBus.hideInformationPopUp();
		    			}
		    			public void onFailure(Throwable caught){
		    				eventBus.addErrorLinePopUp(view.getConstants().PopUpSaveFail());
		    				eventBus.hideInformationPopUp();}
		        	});
	        }});
	     
	     view.getCancelClick().addClickHandler(new ClickHandler() {  
		        public void onClick(ClickEvent event) {
		        	eventBus.displayCurrentStatePanel();
	        }});
	     
	     view.getResetClick().addClickHandler(new ClickHandler() {  
		        public void onClick(ClickEvent event) {
		        	view.resetPosition();
	        }});
	}
	
	/**
	 * add all child of a specific page
	 * in the list
	 * @param parentId : parent id
	 */
	private void addChildInList(Long parentId) {
		
		this.rpcPage.getChildPages(parentId, false,
				new AsyncCallback<List<BeanArboPage>>() {
			public void onSuccess(List<BeanArboPage> result) {
				for(BeanArboPage subPage : result) {
					Widget w = view.addNewPage(subPage.getTranslation().get(0).getPageTitle(), subPage.getCreationDate());
					listDragController.makeDraggable(w);
				}
				// if no child = do not display the reorderPanel
				if(result.size()==0)
					eventBus.displayCurrentStatePanel();
			}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage(view.getConstants().EBuildList());
				p.show();}
		});
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onStartReorderPanel(Long parentId) {
		this.currentPageId = parentId;
		view.clearList(); // erase the table
		this.addChildInList(parentId);
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
