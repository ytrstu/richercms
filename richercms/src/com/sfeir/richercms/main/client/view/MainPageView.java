package com.sfeir.richercms.main.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;

/**
 * Main panel, containing the others (navigation, information and editor)
 * this panel offer 2 side : the first and th administration side.
 * @author homberg.g
 *
 */
public class MainPageView extends TabLayoutPanel implements IdisplayMainPage {

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private NavigationPanel navPanel = null;
	private InformationPanel listPanel = null;
	private TinyMCEPanel tinyMcePanel = null;
	private ValidationPanel validationPanel = null;

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
		
		final int height = Window.getClientHeight()-30;
		
		this.navPanel = new NavigationPanel();
		this.listPanel = new InformationPanel();
		this.tinyMcePanel = new TinyMCEPanel(height/2-70);
		this.validationPanel = new ValidationPanel();
		
		
	    LayoutPanel layoutPanel1 = new LayoutPanel();
	    layoutPanel1.add(this.createMainInterface(height));
	    this.add(layoutPanel1, constants.TitlePanel1());
	    
	    LayoutPanel layoutPanel2 = new LayoutPanel();
	    layoutPanel2.setStyleName("tab-content");
	    HTML h1 = new HTML("TODO...");
	    layoutPanel2.add(h1);
	    this.add(layoutPanel2, constants.TitlePanel2());

	    this.selectTab(0);
	}
	
	/**
	 * Build the split panel containing 3 part (navigation, information and editor)
	 * @return
	 */
	private SplitLayoutPanel createMainInterface(int height) {
				
		SplitLayoutPanel p = new SplitLayoutPanel();
		//style application
		this.navPanel.setStyleName("tab-content");
		this.listPanel.setStyleName("tab-content");
		this.tinyMcePanel.setStyleName("tab-content");
		this.validationPanel.setStyleName("tab-content");
		
		//add Panels in the splitPanel
		p.addWest(this.navPanel, 168);
		p.addNorth(listPanel, height/2 -120);
		p.addSouth(this.validationPanel, 60);
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
	
	public IValidationPanel getValidationPanel()
	{
		return this.validationPanel;
	}
	
	public ITinyMCEPanel getTinyMCEPanel()
	{
		return this.tinyMcePanel;
	}
}
