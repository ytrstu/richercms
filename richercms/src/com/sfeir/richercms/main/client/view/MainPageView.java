package com.sfeir.richercms.main.client.view;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.Interface.IdisplayMainPage;
import com.sfeir.richercms.main.client.tinyMCE.TinyMCE;


public class MainPageView extends TabLayoutPanel implements IdisplayMainPage {

	private NavigationPanel navPanel = new NavigationPanel();

	public MainPageView() {
		super(25, Unit.PX);
	}
	
	public Widget asWidget() {	
		return this;
	}
	
	public void createView() {
		
	    LayoutPanel layoutPanel1 = new LayoutPanel();
	    layoutPanel1.add(createMainInterface());
	    this.add(layoutPanel1, "Site Explorator");
	    
	    LayoutPanel layoutPanel2 = new LayoutPanel();
	    layoutPanel2.setStyleName("tab-content");
	    HTML h1 = new HTML("TODO...");
	    layoutPanel2.add(h1);
	    this.add(layoutPanel2, "Administration");

	    this.selectTab(0);
		
	}

	private SplitLayoutPanel createMainInterface() {
		SplitLayoutPanel p = new SplitLayoutPanel();
		
		this.navPanel.setStyleName("tab-content");
		p.addWest(this.navPanel, 168);
		
		final int height = Window.getClientHeight()-30;
		
		InformationPanel listPanel = new InformationPanel();
		listPanel.setStyleName("tab-content");

		p.addNorth(listPanel, height/2);
		
		final LayoutPanel tinyMcePanel = new LayoutPanel();
		tinyMcePanel.setStyleName("tab-content");
	    TinyMCE tmce = new TinyMCE((height/2-50)+"px");
		tinyMcePanel.add(tmce);
		
		p.add(tinyMcePanel);
		
		return p;
	}


	public void addPageInTree(String name) {
		this.navPanel.addPageInTree(name);
	}

	public void clearTree() {
		this.navPanel.clearTree();
	}
}
