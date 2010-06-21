package com.sfeir.richercms.page.client.tinyMCE.view;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;


public class PageViewer extends ResizeComposite implements IPageViewer {

	
	public Widget asWidget() {
		return this;
	}
	

	public void createView() {
		this.initWidget(new LayoutPanel());
	}
}
