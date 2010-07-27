package com.sfeir.richercms.main.view;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.view.PopUpWait;
import com.sfeir.richercms.main.interfaces.IMainDisplay;

/**
 * 
 * @author homberg.g
 * Main view
 */
public class MainView extends ResizeComposite implements IMainDisplay{

	private LayoutPanel body;
	//the wait popUp
	private PopUpWait popUpWait = null;

	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.body = new LayoutPanel();
		//this.body.addStyleName("bodyPanel");
		this.initWidget(this.body);
		this.popUpWait = new PopUpWait();
	}
	
	public void AddStyle(String styleName) {
		this.body.addStyleName(styleName);	
	}

	public void RemoveStyle(String styleName) {
		this.body.removeStyleName(styleName);	
	}

	public Panel getBody() {
		return this.body;
	}
	
	public Widget asWidget() {	
		return this;
	}
	
	public Widget getViewWidget() {
		return this;
	}

	public void hidePopUpWait() {
		this.popUpWait.show();
	}

	public void showPopUpWait() {
		this.popUpWait.hide();
	}

}
