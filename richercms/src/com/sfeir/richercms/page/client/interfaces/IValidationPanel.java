package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

public interface IValidationPanel extends LazyView {

	/**
	 * Show all buttons include in this Panel and show the save and quit btn
	 */
	void showModifyButtons();
	
	/**
	 * Show just the modify button. Use this one to active modification on a page
	 */
	void showJustModifyBtn();
	
	/**
	 * Show the return button
	 */
	void showReturnBtn();
	
	/**
	 * Show add btn, return btn and hide the save and quit btn
	 */
	void showAddButtons();
	
	/**
	 * hide all buttons include in this Panel
	 */
	void hideButtons();
	
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
	 * Necessary to handle ClickEvent on the save and quit btn
	 * @return EventHandler
	 */
	HasClickHandlers getClicBtnSaveAndQ();
	
	/**
	 * Necessary to handle ClickEvent on the modify button
	 * @return EventHandler
	 */
	HasClickHandlers getClicBtnModify();
	
	/**
	 *  Change the text of the btn
	 * @param text : the newText
	 */
	void setBtnAddText(String text);
	
	/**
	 * Enable modify btn
	 */
	void enableModifyBtn();

	/**
	 * Disable modify btn
	 */
	void disableModifyBtn();
	
	PageConstants getConstants();
}
