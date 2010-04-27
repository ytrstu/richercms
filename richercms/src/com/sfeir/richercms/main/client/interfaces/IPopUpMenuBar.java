package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.user.client.Command;

public interface IPopUpMenuBar {

	void setPopupPosition(int left, int top);
	
	void setAddPageCommand(Command cmd);
	
	void setDelPageCommand(Command cmd);
	
	void setModifyPageCommand(Command cmd);
	
	void show();
	
	void hide();
}
