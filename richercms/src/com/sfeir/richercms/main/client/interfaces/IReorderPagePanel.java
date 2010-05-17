package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IReorderPagePanel extends LazyView {
	
	public Widget asWidget();
	
	/**
	 * Add a Page in the display list
	 * @param text : Text to diplay
	 * @param numberOfChild : the number of child where is containing in this node
	 * @param key : the unique key of the node
	 * @return : The widget for handle drag and drop event
	 */
	Widget addNewPage(String text,int numberOfChild, String key);
	
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
}
