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
	private SplitLayoutPanel splitedPanel = null;
	
	private final int height = Window.getClientHeight()-30;
	
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
	    this.splitedPanel = new SplitLayoutPanel();
	    
	    layoutPanel1.add(this.splitedPanel);
	    this.add(layoutPanel1, constants.TitlePanel1());
	    
	    LayoutPanel layoutPanel2 = new LayoutPanel();
	    layoutPanel2.setStyleName("tab-content");
	    HTML h1 = new HTML("TODO...");
	    layoutPanel2.add(h1);
	    this.add(layoutPanel2, constants.TitlePanel2());

	    this.selectTab(0);
	}

	public void setConstants(MainConstants constants) {
		this.constants = constants;
	}

	public void setNavPanel(INavigationPanel navPanel) {
		this.navPanel = (NavigationPanel)navPanel;
		this.navPanel.setStyleName("tab-content");
		this.splitedPanel.addWest(this.navPanel, 168);
	}

	public void setInfoPanel(IInformationPanel listPanel) {
		this.listPanel = (InformationPanel)listPanel;
		this.listPanel.setStyleName("tab-content");
		this.splitedPanel.addNorth(this.listPanel, this.height/2 -120);
	}

	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel) {

		this.tinyMcePanel = (TinyMCEPanel)tinyMcePanel;
		this.tinyMcePanel.setStyleName("tab-content");
		this.splitedPanel.add(this.tinyMcePanel);
	}

	public void setValidationPanel(IValidationPanel validationPanel) {
		this.validationPanel = (ValidationPanel)validationPanel;
		this.validationPanel.setStyleName("tab-content");
		this.splitedPanel.addSouth(this.validationPanel, 60);
	}
}
