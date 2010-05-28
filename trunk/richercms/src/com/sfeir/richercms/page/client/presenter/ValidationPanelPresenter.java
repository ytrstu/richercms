package com.sfeir.richercms.page.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.MainConstants;
import com.sfeir.richercms.page.client.MainState;
import com.sfeir.richercms.page.client.event.MainEventBus;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.view.ValidationPanel;


@Presenter( view = ValidationPanel.class)
public class ValidationPanelPresenter extends LazyPresenter<IValidationPanel, MainEventBus>{

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private MainState state = MainState.display;
	
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
				eventBus.confirmCancelPage();
			}
		});
	}

	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(Long id) {
		this.view.setBtnAddText(this.constants.BtnAdd());
		view.enabledButtons();
		this.state = MainState.add;
	}
	
	public void onCancelPage() {
		view.deasableButtons();
		this.state = MainState.display;
	}
	
	public void onModifyPage(Long id) {
		this.view.setBtnAddText(this.constants.BtnModify());
		view.enabledButtons();
		this.state = MainState.modify;
	}
	
	public void onSavePage() {
		//if the state is not modify
		if(!this.state.equals(MainState.modify))
			view.deasableButtons();
	}
	
	public void onDisplayPage(Long id) {
		view.deasableButtons();
		this.state = MainState.display;
	}
	
	public void onDisplayMainPage() {
		view.deasableButtons();
		this.state = MainState.display;
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.deasableButtons();
		eventBus.changeValidationPanel(this.view);
		this.state = MainState.display;
	}
}
