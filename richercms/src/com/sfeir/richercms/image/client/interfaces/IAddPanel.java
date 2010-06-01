package com.sfeir.richercms.image.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IAddPanel  extends LazyView{
	
	Widget asWidget();
	
	void addThumbnail(Long id);
	
	HasClickHandlers onSendBtnclick();
	
	FormPanel getFormEvent();
	
	void submitForm();
	
	void clearThumbNails();
}
