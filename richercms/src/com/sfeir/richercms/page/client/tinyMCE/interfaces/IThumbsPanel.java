package com.sfeir.richercms.page.client.tinyMCE.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

/**
 * Panel displays a set of miniature 
 * who may be selected
 * @author homberg.g
 *
 */
public interface IThumbsPanel extends LazyView {
	
	Widget asWidget();
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	public PageConstants getConstants();
	
	/**
	 * Add a Thumbnail in the panel
	 * @param Path : the path of the pictures : page/page1.1/img.jpg
	 * @return the click Handler on the pictures
	 */
	HasClickHandlers addThumbnail(String Path);

	/**
	 * Return the Click handler on the last thumb added in the panel
	 * @return EVENT
	 */
	HasClickHandlers onLastThumbClick();
	
	/**
	 * Return the Click handler on the submit Button
	 * @return EVENT
	 */
	HasClickHandlers onSendBtnclick();
	
	/**
	 * Return the Form, who contain all information 
	 * send after a click on the sendButton
	 * @return the Formular
	 */
	FormPanel getFormEvent();
	
	/**
	 * Submit the formular
	 */
	void submitForm();
	
	/**
	 * Clear all Thumbs in the panel
	 */
	void clearThumbNails();
	
	/**
	 * Set the path of the new picture you would like to add 
	 * thanks to the servlet. The path is include in the formular (a hidden field)
	 * @param path :  page/page1.1/
	 */
	void setCurrentPath(String path);
	
	/**
	 * Return the Path of the new picture you would like to add 
	 * thanks to the servlet. The path is include in the formular (a hidden field)
	 * @return the pictures path : page/page1.1/img.jpg
	 */
	String getCurrentPath();
}
