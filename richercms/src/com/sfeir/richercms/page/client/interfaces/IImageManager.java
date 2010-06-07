package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
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
	
	HasClickHandlers addThumbnail(String Path);

	HasClickHandlers onThumbClick();
	
	HasClickHandlers onSendBtnclick();
	
	FormPanel getFormEvent();
	
	void submitForm();
	
	void clearThumbNails();
	
	void setCurrentPath(String path);
	
	String getCurrentPath();
	
	void showPopUpImgPreview(String path);
}
