package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.PageState;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.view.ValidationPanel;


@Presenter( view = ValidationPanel.class)
public class ValidationPanelPresenter extends LazyPresenter<IValidationPanel, PageEventBus>{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private PageState state = PageState.display;
	
	public ValidationPanelPresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
		this.view.getClicBtnAdd().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.savePage();
			}
		});
		
		this.view.getClicBtnCancel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.confirmCancelPage(PageState.display, true);
			}
		});
		
		this.view.getClicBtnSaveAndQ().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.savePage();
				eventBus.confirmCancelPage(PageState.display, false);
			}
		});
		
		this.view.getClicBtnModify().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//lock the current page if its possible and fired modifyPageEvent
				eventBus.goInModification();
			}
		});
	}

	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(Long id) {
		this.view.setBtnAddText(this.constants.BtnAdd());
		view.showAddButtons();
		this.state = PageState.add;
	}
	
	public void onCancelPage(PageState newState) {
		view.hideButtons();
		this.state = newState;
	}
	
	public void onEnableReturnBtn(){
		view.showReturnBtn();
	}
	
	public void onModifyPage(Long id) {
		this.view.setBtnAddText(this.constants.BtnSave());
		view.showModifyButtons();
		this.state = PageState.modify;
	}
	
	public void onSendContent(List<String> translationsContent) {
		//if the state is not modify
		if(!this.state.equals(PageState.modify))
			view.hideButtons();
	}
	
	public void onDisplayPage(Long id) {
		view.showJustModifyBtn();
		this.state = PageState.display;
	}
	
	public void onDisplayMainPage() {
		view.hideButtons();
		this.state = PageState.display;
	}
	
	public void onDisableModifyBtn(){
		this.view.disableModifyBtn();
	}
	
	public void onEnableModifyBtn(){
		this.view.enableModifyBtn();
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.hideButtons();
		eventBus.changeValidationPanel(this.view);
		this.state = PageState.display;
	}
}
