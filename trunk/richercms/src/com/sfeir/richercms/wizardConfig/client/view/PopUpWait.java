package com.sfeir.richercms.wizardConfig.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * popUp display during loading elements
 * @author homberg.g
 * TODO in progress
 */
public class PopUpWait extends PopupPanel {

	public PopUpWait()
	{
		final PopUpWait instance = this;
		setWidget(new Label("wait Data"));
		
		this.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
	          public void setPosition(int offsetWidth, int offsetHeight) {
	            int left = (Window.getClientWidth() - offsetWidth) / 3;
	            int top = (Window.getClientHeight() - offsetHeight) / 3;
	            instance.setPopupPosition(left, top);
	          }
	        });
		RootPanel.get().add(this);	
	}

}
