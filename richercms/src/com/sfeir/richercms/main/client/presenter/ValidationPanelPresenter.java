package com.sfeir.richercms.main.client.presenter;

import com.sfeir.richercms.main.client.interfaces.IValidationPanel;

public class ValidationPanelPresenter {

	private IValidationPanel view = null;
	
	public ValidationPanelPresenter() {
		this.view = null;
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartValidationPanel(IValidationPanel ValidationPanel) {
		this.view = ValidationPanel;
		this.view.deasableButtons();
	}
}
