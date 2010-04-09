package com.sfeir.richercms.client.view;



import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.Interface.IrootDisplay;


/**
 * Main view : contain all the other view
 * @author homberg.g
 */
public class RootView extends ResizeComposite implements IrootDisplay {

	private LayoutPanel body;
	
	public RootView() {
		this.body = new LayoutPanel();
		this.body.addStyleName("bodyPanel");
		this.initWidget(this.body);
	}

	public Panel getBody() {
		return this.body;
	}

	public Widget getViewWidget() {
		return this;
	}




}
