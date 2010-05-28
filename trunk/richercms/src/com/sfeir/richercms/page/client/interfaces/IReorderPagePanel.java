package com.sfeir.richercms.page.client.interfaces;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.MainConstants;

public interface IReorderPagePanel extends LazyView {
	
	public Widget asWidget();
	
	/**
	 * Add a Page in the display list
	 * @param text : Text to diplay
	 * @param numberOfChild : the number of child where is containing in this node
	 * @param date : date of creation of the current Page
	 * @return : The widget for handle drag and drop event
	 */
	Widget addNewPage(String text, Date date);
	
	/**
	 * Return the flexTable uses for displaying childPage.
	 * This method is necessary to handle drag and drop event
	 * @return
	 */
	FlexTable getPageList();
	
	/**
	 * Return the Absolute Panel who contain FlexTable
	 * Its necessary to fix limit of drag and drop action.
	 * @return : the absolute panel
	 */
	AbsolutePanel getPageListPanel();
	
	/**
	 * Return old position of a page sorting in a new order
	 * @return new the position of all page
	 */
	List<Integer> getNewOrder();
	
	/**
	 * return the clickEvent on the apply button
	 * @return : the Event
	 */
	HasClickHandlers  getApplyClick();
	
	/**
	 * return the clickEvent on the cancel button
	 * @return : the Event
	 */
	HasClickHandlers  getCancelClick();
	
	
	/**
	 * return the clickEvent on the reset button
	 * @return : the Event
	 */
	HasClickHandlers  getResetClick();
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	MainConstants getConstants();
	
	/**
	 * clear the display PageList
	 */
	void clearList();
	
	/**
	 * Reset the position of all line
	 * in the reorderTable
	 */
	void resetPosition();
}
