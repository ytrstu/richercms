package com.sfeir.richercms.main.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.view.ValidationPanel;


@Presenter( view = ValidationPanel.class)
public class ValidationPanelPresenter extends LazyPresenter<IValidationPanel, MainEventBus>{

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	
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
				eventBus.cancelPage();
			}
		});
	}

	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(String key) {
		this.view.setBtnAddText(this.constants.BtnAdd());
		view.enabledButtons();
	}
	
	public void onCancelPage() {
		view.deasableButtons();
	}
	
	public void onModifyPage(String key) {
		this.view.setBtnAddText(this.constants.BtnModify());
		view.enabledButtons();
	}
	
	public void onSavePage() {
		view.deasableButtons();
	}
	
	public void onDisplayPage(String key) {
		view.deasableButtons();
	}
	
	public void onDisplayMainPage() {
		view.deasableButtons();
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.deasableButtons();
		eventBus.changeValidationPanel(this.view);
	}
}
