package com.sfeir.richercms.image.client.view;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;
import com.sfeir.richercms.image.client.interfaces.IImagePanel;
import com.sfeir.richercms.image.client.interfaces.ILinkPanel;

/**
 * Panel needed to manage image 
 * @author homberg.g
 *
 */
public class ImagePanel extends ResizeComposite implements IImagePanel{

	private TabLayoutPanel tabPanel;
	private LayoutPanel addPanel;
	private LayoutPanel linkPanel;
	
	public Widget asWidget() {	
		return this;
	}
	
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.tabPanel = new TabLayoutPanel(25, Unit.PX);
		this.addPanel = new LayoutPanel();
		this.linkPanel = new LayoutPanel();
		
		this.tabPanel.add(this.addPanel, "Add Image");
		this.tabPanel.add(this.linkPanel, "Link Image");
		
		this.initWidget(this.tabPanel);
	}
	
	public void setAddPanel(IAddPanel p) {
		this.addPanel.clear();	
		this.addPanel.add((AddPanel) p);
	}
	
	public void setLinkPanel(ILinkPanel p) {
		this.linkPanel.clear();
		this.linkPanel.add((LinkPanel) p);
	}
}
