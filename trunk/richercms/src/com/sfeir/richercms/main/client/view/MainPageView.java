package com.sfeir.richercms.main.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ResizeComposite;
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
public class MainPageView extends ResizeComposite implements IdisplayMainPage {

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private NavigationPanel navPanel = null;
	private InformationPanel listPanel = null;
	private TinyMCEPanel tinyMcePanel = null;
	private ValidationPanel validationPanel = null;
	private SplitLayoutPanel splitedPanel = null;
	private MenuBar mainmenu  = null;
	DockLayoutPanel layoutPanel1 = null;
	LayoutPanel layoutPanel2 = null;
	ListBox language = null;

	private final int height = Window.getClientHeight()-30;
	
	public MainPageView() {
		super();
	}
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		TabLayoutPanel content = new TabLayoutPanel(25, Unit.PX);
		this.layoutPanel1 = new DockLayoutPanel(Unit.PX);
	    this.splitedPanel = new SplitLayoutPanel();
		
		// liste du choix de la langue
		this.language = new ListBox(false);
		this.language.addItem("Français");
		this.language.setWidth("200px");
		Label listBoxTitle = new Label("Langue en cours : ");
	    LayoutPanel placmentPanel = new  LayoutPanel();
	    
	    // Create a menu bar
	    this.mainmenu = new MenuBar();
	    this.mainmenu.setAutoOpen(true);
	    this.mainmenu.setWidth("200px");
	    this.mainmenu.setHeight("20px");
	    this.mainmenu.setAnimationEnabled(true);
	    
	    // Create the editing menu
	    MenuBar edition = new MenuBar(true);
	    edition.setAnimationEnabled(true);
	    edition.addItem("Save", new Command(){public void execute(){}});
	    edition.addItem("Clear", new Command(){public void execute(){}});
	    edition.addItem("...", new Command(){public void execute(){}});
	    this.mainmenu.addItem(new MenuItem("Edition", edition));
	    
	    // Create the setting menu
	    MenuBar setting = new MenuBar(true);
	    setting.setAnimationEnabled(true);
	    setting.addItem("Site Languages", new Command(){public void execute(){}});
	    setting.addItem("Reconfigure", new Command(){public void execute(){}});
	    this.mainmenu.addItem(new MenuItem("Setting", setting));
	    
	    // Create the help menu
	    MenuBar help = new MenuBar(true);
	    help.setAnimationEnabled(true);
	    help.addItem("?", new Command(){public void execute(){}});
	    help.addItem("about richerCMS", new Command(){public void execute(){}});
	    this.mainmenu.addItem(new MenuItem("Help", help));


	    FlexTable labelAndListBox = new FlexTable();
	    labelAndListBox.setWidget(0, 0, this.mainmenu);
	    labelAndListBox.setWidget(0, 1, listBoxTitle);
	    labelAndListBox.setWidget(0, 2, this.language);
	    placmentPanel.setStyleName("tab-content");
	    placmentPanel.add(labelAndListBox);
	    placmentPanel.setWidgetHorizontalPosition(labelAndListBox,  Alignment.END);

			    
	    this.layoutPanel1.addNorth(placmentPanel, 39);
	    // --> this.layoutPanel1.add(this.splitedPanel); fait dans le setteur du validation Panel 
	    // il faut toujours ajouter les élément du nord et du sud avant celui du centre !!
	    content.add(this.layoutPanel1, constants.TitlePanel1());
	    
	    this.layoutPanel2 = new LayoutPanel();
	    this.layoutPanel2.setStyleName("tab-content");
	    HTML h1 = new HTML("TODO...");
	    this.layoutPanel2.add(h1);
	    content.add(this.layoutPanel2, constants.TitlePanel2());

	    content.selectTab(0);

	    this.initWidget(content);
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
		this.layoutPanel1.addSouth(this.validationPanel, 50);
		//ajout de l'élément final : signifie la fin du chargement
	    this.layoutPanel1.add(this.splitedPanel);
	}
}
