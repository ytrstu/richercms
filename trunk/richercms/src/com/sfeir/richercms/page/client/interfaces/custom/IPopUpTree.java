package com.sfeir.richercms.page.client.interfaces.custom;

import com.google.gwt.event.dom.client.HasClickHandlers;

/**
 * Allows a presenter to communicate with the popUp
 * @author homberg.g
 *
 */
public interface IPopUpTree {

	/**
	 * show the popUp
	 * @param type : 1 for add
	 */
	void show(int type);
	
	/**
	 * hide popUp
	 */
	void hide();
	
	/**
	 * Set the popUp position
	 * @param left : range with the left border
	 * @param top : range with the top border
	 */
	void setPopupPosition(int left, int top);
	
	/**
	 * Click event on the addPage btn
	 * @return Event
	 */
	HasClickHandlers getClickBtnAddPage();
	
	/**
	 * Click event on the deletePage btn
	 * @return Event
	 */
	HasClickHandlers getClickBtnDelPage();
}
