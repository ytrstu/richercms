package com.sfeir.richercms.page.client.view;

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
import com.sfeir.richercms.page.client.MainConstants;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.page.client.view.custom.CenterEventPopUp;

/**
 * Main panel, containing the others (navigation, information and editor)
 * this panel offer 2 side : the first and th administration side.
 * @author homberg.g
 *
 */
public class MainPageView extends ResizeComposite implements IdisplayMainPage {

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private LayoutPanel lgAndMenuPanel = null;
	private SplitLayoutPanel leftRightSpliter = null;
	private SplitLayoutPanel topBottomSpliter = null;
	private MenuBar mainmenu  = null;
	private DockLayoutPanel dispositionPanel = null;
	private ListBox languages = null;
	private CenterEventPopUp popUp = null;
	private LayoutPanel finalContainer = null;
	// include in the leftRightSpliter 
	private LayoutPanel leftPanel = null;
	private LayoutPanel rightPanel = null;
	// include in the topBottomSpliter
	private LayoutPanel topPanel = null;
	private LayoutPanel bottomPanel = null;

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
		
		this.dispositionPanel = new DockLayoutPanel(Unit.PX);
	    this.leftRightSpliter = new SplitLayoutPanel();
	    this.topBottomSpliter = new SplitLayoutPanel();
	    
	    // When it's necessary, this popUp is show
		this.popUp = new CenterEventPopUp(400, 200,"Sauvegarde en cours");
	    this.popUp.setVisible(false);

		
	    this.lgAndMenuPanel = new  LayoutPanel();
	    this.createLgAndMenu();
		
	    //north : language + menu
	    this.dispositionPanel.addNorth(lgAndMenuPanel, 23);
	    
	    // the top and the bottom content of the second Spliter ( topBottomSpliter )
	    this.topPanel = new LayoutPanel();
	    this.topPanel.setStyleName("tab-content");
	    this.bottomPanel = new LayoutPanel();
	    this.bottomPanel.setStyleName("tab-content");
	    
	    // connects the two panels to the topBottomSpliter
	    this.topBottomSpliter.addNorth(this.topPanel, this.height/2 -120);
		this.topBottomSpliter.add(this.bottomPanel);
	    
	    // the left and the right content of the main Spliter ( leftRightSpliter )
	    this.leftPanel = new LayoutPanel();
	    this.leftPanel.setStyleName("tab-content");
	    this.rightPanel = new LayoutPanel();
	    this.rightPanel.setStyleName("tab-content");
	    
	    // the rightPanel contain all widget of the right part of the topBottomSpliter
	    this.rightPanel.add(this.topBottomSpliter);
	    
	    // connects the two panels to the leftRightSpliter
	    this.leftRightSpliter.addWest(this.leftPanel, 168);
	    this.leftRightSpliter.add(this.rightPanel);
	    
	    // FINAL : PopUp + dispositionPanel (=> all widget)
	    this.finalContainer = new LayoutPanel();
	    this.finalContainer.add(this.popUp);
	    this.finalContainer.add(this.dispositionPanel);
	    
	    // wrap the finalContainer into the resizeComposite
	    this.initWidget(this.finalContainer);
	}
	
	/**
	 * Initialise the lgAndMenuPanel with its content
	 */
	private void createLgAndMenu() {
		// liste du choix de la langue
		this.languages = new ListBox(false);
		this.languages.setWidth("240px");
		Label listBoxTitle = new Label("Langue en cours : ");
		listBoxTitle.setStyleName("languageLabel");
	    
	    
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

	    // Create Language Panel
	    LayoutPanel mainLanguagesPanel = new LayoutPanel();
	    mainLanguagesPanel.add(listBoxTitle);
	    mainLanguagesPanel.add(languages);
	    mainLanguagesPanel.setWidgetLeftRight(listBoxTitle, 0, Unit.PX, 70, Unit.PCT);
	    mainLanguagesPanel.setWidgetLeftRight(languages, 30, Unit.PCT, 5, Unit.PX);
	   
	    // Add Menu + LanguagePanel
	    lgAndMenuPanel.setStyleName("tab-content");
	    lgAndMenuPanel.add(mainmenu);
	    lgAndMenuPanel.add(mainLanguagesPanel);
	    lgAndMenuPanel.setWidgetLeftWidth(mainmenu, 0, Unit.PX, 350, Unit.PX);
	    lgAndMenuPanel.setWidgetRightWidth(mainLanguagesPanel, 0, Unit.PX, 350, Unit.PX);
	    lgAndMenuPanel.setWidgetTopBottom(mainLanguagesPanel, 1, Unit.PX, 1, Unit.PX);
	    lgAndMenuPanel.setWidth("100%");
	}

	public void setConstants(MainConstants constants) {
		this.constants = constants;
	}
	
	public void setTopPanel() {
		
	}
	public void setBottomPanel() {
		
	}
	public void setLeftPanel() {
		
	}
	public void setRightPanel() {
		
	}

	public void setNavPanel(INavigationPanel navPanel) {
		this.leftPanel.add((NavigationPanel)navPanel);
	}

	public void setInfoPanel(IInformationPanel listPanel) {
		this.topPanel.add((InformationPanel)listPanel);
		
	}
	
	public void displayReorderPanel(IReorderPagePanel reorderPanel) {
		this.rightPanel.clear();
		this.rightPanel.add((ReorderPagePanel)reorderPanel);
		//this.leftRightSpliter.add(this.rightPanel);
	}
	
	public void displayNormalPanel(){
		// evite le clignotement si on est déja sur en mode normale
		if(!this.rightPanel.getWidget(0).equals(this.topBottomSpliter)) {
			this.rightPanel.clear();
			this.rightPanel.add(this.topBottomSpliter);
		}
	}

	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel) {
		this.bottomPanel.add((TinyMCEPanel)tinyMcePanel);
	}

	public void setValidationPanel(IValidationPanel validationPanel) {
		this.dispositionPanel.addSouth((ValidationPanel)validationPanel, 37);
		//ajout de l'élément final : signifie la fin du chargement
	    this.dispositionPanel.add(this.leftRightSpliter);
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
