package com.sfeir.richercms.main.client.interfaces.custom;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface IPopUpTree {

	void show(int type);
	
	void hide();
	
	void setPopupPosition(int left, int top);
	
	HasClickHandlers getClickBtnAddPage();
	
	HasClickHandlers getClickBtnDelPage();
}
