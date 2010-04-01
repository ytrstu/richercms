package com.sfeir.richercms.wizardConfig.client.view;



import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.wizardConfig.client.Interface.IrootDisplay;


/**
 * Main view : contain all the other view
 * @author homberg.g
 */
public class RootView extends Composite implements IrootDisplay {

	private LayoutPanel body;
	
	public RootView() {
		this.body = new LayoutPanel();
		this.initWidget(this.body);
	}

	public Panel getBody() {
		return this.body;
	}

	public Widget getViewWidget() {
		return this;
	}




}
