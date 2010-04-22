package com.sfeir.richercms.client.view;



import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.interfaces.IrootDisplay;


/**
 * Main view : contain all the other view
 * @author homberg.g
 */
public class RootView extends ResizeComposite implements IrootDisplay {

	private LayoutPanel body;
	//the wait popUp
	private PopUpWait popUpWait = null;
	
	public RootView() {
		this.body = new LayoutPanel();
		this.body.addStyleName("bodyPanel");
		this.initWidget(this.body);
		this.popUpWait = new PopUpWait();
	}

	public Panel getBody() {
		return this.body;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void AddStyle(String styleName) {
		this.body.addStyleName(styleName);
	}
	
	public void RemoveStyle(String styleName) {
		this.body.removeStyleName(styleName);
	}
	
	public void showPopUpWait() {		
		this.popUpWait.show();
	}
	
	public void hidePopUpWait() {
		this.popUpWait.hide();
	}

}
