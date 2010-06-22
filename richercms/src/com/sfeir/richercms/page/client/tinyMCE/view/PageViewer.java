package com.sfeir.richercms.page.client.tinyMCE.view;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;


public class PageViewer extends ResizeComposite implements IPageViewer {

	private HTMLPanel htmlPanel;
	private ScrollPanel container;
	
	public Widget asWidget() {
		return this;
	}
	

	public void createView() {
		this.container = new ScrollPanel();
		//use static dimension for scrollPanel
		this.container.setHeight("400px");
		this.container.setWidth("450px");
		this.initWidget(this.container);
	}
	
	public void addContent(String html){
		this.container.clear();
		this.htmlPanel = new HTMLPanel(html);
		this.container.setWidget(this.htmlPanel);
	}
}
