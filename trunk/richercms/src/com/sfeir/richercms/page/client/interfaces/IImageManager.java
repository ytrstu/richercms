package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

public interface IImageManager  extends LazyView {
	
	Widget asWidget();
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	public PageConstants getConstants();
	
	/**
	 * Handle click on the add thumbnail button
	 * @param Path
	 * @return Event
	 */
	HasClickHandlers addThumbnail(String Path);

	/**
	 * Handle click on a thumbnail
	 * @return Event
	 */
	HasMouseDownHandlers onThumbClick();
	
	/**
	 * Handle click on the send formular button
	 * @return Event
	 */
	HasClickHandlers onSendBtnclick();
	
	/**
	 * @return image uploader formular
	 */
	FormPanel getFormEvent();
	
	/**
	 * submit the formular
	 */
	void submitForm();
	
	/**
	 * Erase all thumbnails in the layout
	 */
	void clearThumbNails();
	
	/**
	 * Set the current path stored in the view
	 * @param path the new path
	 */
	void setCurrentPath(String path);
	
	/**
	 * @return the current path stored in the view
	 */
	String getCurrentPath();
	
	/**
	 * Display an image in a popup
	 * @param path : image's path
	 */
	void showPopUpImgPreview(String path);
	
	/**
	 * Set the Title
	 * @param title : new title
	 */
	void setTitle(String title);
	
	/**
	 * Delete a thumb into the view
	 * @param thumb : element return by the click Event.
	 */
	void deleteThumb(Element thumb);
}
