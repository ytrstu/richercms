package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface IValidationPanel {

	void enabledButtons();
	void deasableButtons();
	
	HasClickHandlers getClicBtnAdd();
	HasClickHandlers getClicBtnCancel();
}
