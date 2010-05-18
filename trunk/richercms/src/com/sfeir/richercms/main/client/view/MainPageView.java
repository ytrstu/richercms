package com.sfeir.richercms.main.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;
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
	private ReorderPagePanel reoderPanel = null;
	private ValidationPanel validationPanel = null;
	private SplitLayoutPanel splitedPanel = null;
	private MenuBar mainmenu  = null;
	private DockLayoutPanel layoutPanel1 = null;
	private ListBox languages = null;
	private CenterEventPopUp popUp = null;
	private LayoutPanel finalContainer = null;
	
	private LayoutPanel westPanel = null;
	private LayoutPanel northPanel = null;
	private LayoutPanel southPanel = null;

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
		
		this.layoutPanel1 = new DockLayoutPanel(Unit.PX);
	    this.splitedPanel = new SplitLayoutPanel();
	    
	    // When it's necessary, this popUp is show
		this.popUp = new CenterEventPopUp(400, 200,"Sauvegarde en cours");
	    this.popUp.setVisible(false);
		
		// liste du choix de la langue
		this.languages = new ListBox(false);
		this.languages.setWidth("240px");
		Label listBoxTitle = new Label("Langue en cours : ");
		listBoxTitle.setStyleName("languageLabel");
	    LayoutPanel placmentPanel = new  LayoutPanel();
	    
	    // Create a menu bar
	    this.mainmenu = new MenuBar();
	    mainmenu.setStyleName("mainMenuBar");
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


	    LayoutPanel mainLanguagesPanel = new LayoutPanel();
	    mainLanguagesPanel.add(listBoxTitle);
	    mainLanguagesPanel.add(languages);
	    mainLanguagesPanel.setWidgetLeftRight(listBoxTitle, 0, Unit.PX, 70, Unit.PCT);
	    mainLanguagesPanel.setWidgetLeftRight(languages, 30, Unit.PCT, 5, Unit.PX);
	    
	    placmentPanel.setStyleName("tab-content");
	    placmentPanel.add(mainmenu);
	    placmentPanel.add(mainLanguagesPanel);
	    placmentPanel.setWidgetLeftWidth(mainmenu, 0, Unit.PX, 350, Unit.PX);
	    placmentPanel.setWidgetRightWidth(mainLanguagesPanel, 0, Unit.PX, 350, Unit.PX);
	    placmentPanel.setWidgetTopBottom(mainLanguagesPanel, 1, Unit.PX, 1, Unit.PX);
	    placmentPanel.setWidth("100%");
			    
	    this.layoutPanel1.addNorth(placmentPanel, 23);
	    
	    this.finalContainer = new LayoutPanel();
	    this.finalContainer.add(this.popUp);
	    this.finalContainer.add(this.layoutPanel1);
	    
	    this.westPanel = new LayoutPanel();
	    this.northPanel = new LayoutPanel();
	    this.southPanel = new LayoutPanel();
	    
	    this.splitedPanel.addWest(this.westPanel, 168);
	    this.splitedPanel.addNorth(this.northPanel, this.height/2 -120);
	    this.splitedPanel.add(southPanel);
	    
	    this.initWidget(this.finalContainer);
	}

	public void setConstants(MainConstants constants) {
		this.constants = constants;
	}

	public void setNavPanel(INavigationPanel navPanel) {
		this.navPanel = (NavigationPanel)navPanel;
		this.navPanel.setStyleName("tab-content");
		this.westPanel.add(this.navPanel);
	}

	public void setInfoPanel(IInformationPanel listPanel) {
		this.listPanel = (InformationPanel)listPanel;
		this.listPanel.setStyleName("tab-content");
		this.northPanel.add(this.listPanel);
	}
	
	public void displayReorderPanel(IReorderPagePanel reorderPanel) {
		this.reoderPanel = (ReorderPagePanel)reorderPanel;
		this.reoderPanel.setStyleName("tab-content");
		this.northPanel.remove(0);
		this.northPanel.add(this.reoderPanel);
	}
	
	public void displayNormalPanel(){
		this.northPanel.remove(0);
		this.northPanel.add(this.listPanel);
	}

	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel) {

		this.tinyMcePanel = (TinyMCEPanel)tinyMcePanel;
		this.tinyMcePanel.setStyleName("tab-content");
		//this.splitedPanel.add(this.tinyMcePanel);
		this.southPanel.add(this.tinyMcePanel);
	}

	public void setValidationPanel(IValidationPanel validationPanel) {
		this.validationPanel = (ValidationPanel)validationPanel;
		this.validationPanel.setStyleName("tab-content");
		this.layoutPanel1.addSouth(this.validationPanel, 37);
		//ajout de l'élément final : signifie la fin du chargement
	    this.layoutPanel1.add(this.splitedPanel);
	}
	
	public void addLanguageInListBox(String name, String key, boolean defaultLg) {
		
		if(defaultLg){
			this.languages.addItem(name+" (DEFAULT)", key);
			this.languages.setSelectedIndex(this.languages.getItemCount()-1);
		}
		else
			this.languages.addItem(name, key);
	}
	
	public HasChangeHandlers onChangeSelectedLg() {
		return this.languages;
	}
	
	public String getKeyOfSelectedLg() {
		return this.languages.getValue(this.languages.getSelectedIndex());
	}
	
	public int countTranslation() {
		return this.languages.getItemCount();
	}
	
	public int getIndexOfCurrentLg() {
		return this.languages.getSelectedIndex();
	}
	
	public void setIndexOfLgToDefault() {
		this.languages.setSelectedIndex(0);
	}
	
	public void disableLanguageBox() {
		this.languages.setEnabled(false);
	}
	
	public void enableLanguageBox(){
		this.languages.setEnabled(true);
	}
	
	public void showWaitPopUp(){

		this.finalContainer.remove(this.popUp);
	    this.finalContainer.add(this.popUp);
		this.popUp.setVisible(true);
	}
	
	public void hideWaitPopUp(){

		Timer t = new Timer() {
			public void run() {
				popUp.setVisible(false);
				finalContainer.remove(popUp);
				popUp.ClearTable();
			}
		};
		t.schedule(1000);
	}
	
	public void addLineInPopUp(String text, int state) {
		this.popUp.AddLine(text, state);
	}
		
	public MainConstants getConstants() {
		return this.constants;
	}
}