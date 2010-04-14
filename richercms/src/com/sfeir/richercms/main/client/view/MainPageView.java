package com.sfeir.richercms.main.client.view;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.tinyMCE.TinyMCE;

/**
 * Main panel, containing the others (navigation, information and editor)
 * this panel offer 2 side : the first and th administration side.
 * @author homberg.g
 *
 */
public class MainPageView extends TabLayoutPanel implements IdisplayMainPage {

	private NavigationPanel navPanel = new NavigationPanel();
	private InformationPanel listPanel = new InformationPanel();

	public MainPageView() {
		super(25, Unit.PX);
	}
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
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
	
	/**
	 * Build the split panel containing 3 part (navigation, information and editor)
	 * @return
	 */
	private SplitLayoutPanel createMainInterface() {
		SplitLayoutPanel p = new SplitLayoutPanel();
		
		this.navPanel.setStyleName("tab-content");
		this.listPanel.setStyleName("tab-content");
		
		p.addWest(this.navPanel, 168);
		
		final int height = Window.getClientHeight()-30;


		p.addNorth(listPanel, height/2);
		
		final LayoutPanel tinyMcePanel = new LayoutPanel();
		tinyMcePanel.setStyleName("tab-content");
	    TinyMCE tmce = new TinyMCE((height/2-50)+"px");
		tinyMcePanel.add(tmce);
		
		p.add(tinyMcePanel);
		
		return p;
	}
	
	public INavigationPanel getNavigationPanel()
	{
		return this.navPanel;
	}
	
	public IInformationPanel getInformationPanel()
	{
		return this.listPanel;
	}
}
