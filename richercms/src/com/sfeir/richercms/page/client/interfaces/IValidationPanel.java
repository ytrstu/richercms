package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.view.LazyView;

public interface IValidationPanel extends LazyView {

	/**
	 * Enable all buttons include in this Panel
	 */
	void enabledButtons();
	
	/**
	 * Disable all buttons include in this Panel
	 */
	void deasableButtons();
	
	/**
	 * Necessary to handle ClickEvent on the Add Button
	 * @return : EventHandler
	 */
	HasClickHandlers getClicBtnAdd();
	
	/**
	 * Necessary to handle ClickEvent on the Add Cancel Button
	 * @return : EventHandler
	 */
	HasClickHandlers getClicBtnCancel();
	
	/**
	 *  Change the text of the btn
	 * @param text : the newText
	 */
	void setBtnAddText(String text);
}
