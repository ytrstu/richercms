package com.sfeir.richercms.page.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.interfaces.ITagManager;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.interfaces.IdisplayPage;
import com.sfeir.richercms.page.client.view.custom.CenterEventPopUp;

/**
 * Main panel, containing the others (navigation, information and editor)
 * this panel offer 2 side : the first and th administration side.
 * @author homberg.g
 *
 */
public class PageView extends ResizeComposite implements IdisplayPage {

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private LayoutPanel lgAndMenuPanel;
	private LayoutPanel rightSouthPanel; //contain widget displaying in the south  => ValidationPanel, ...
	private LayoutPanel rightNorthPanel; // page tool => tiny + information | reorderPanel | ImageManager
	private SplitLayoutPanel leftRightSpliter; //tiny + information
	private SplitLayoutPanel topBottomSpliter;
	private MenuBar mainmenu;
	private MenuItem settingsItem;
	private DockLayoutPanel dispositionPanel;
	private ListBox languages;
    private Label pseudo;
    private Anchor logOut;
	private CenterEventPopUp popUp;
	private LayoutPanel finalContainer;
	// include in the leftRightSpliter 
	private LayoutPanel leftPanel;
	private DockLayoutPanel rightPanel;
	// include in the topBottomSpliter
	private LayoutPanel topPanel;
	private LayoutPanel bottomPanel;
	//btn up and down topBottomSpliter
	private Image downSpliter = null;
	private Image upSpliter = null;
	private HorizontalPanel btnSpliterPanel =null;
	
	private boolean adminMenu;
	
	private MenuItem imageTool;
	private MenuItem pageTool;
	private MenuItem userSettings;
	private MenuItem tagSettings;
	private MenuItem templateSettings;

	private final int height = Window.getClientHeight()-30;
	private final int topPanelHeight = height/2 -100;
	
	public PageView() {
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
		this.dispositionPanel.addStyleName("disposition-panel");
		this.rightSouthPanel = new LayoutPanel();
		this.rightNorthPanel = new LayoutPanel();
		
	    this.leftRightSpliter = new SplitLayoutPanel();
	    this.topBottomSpliter = new SplitLayoutPanel();
	    
		// down and up the topBottomSpliter
		this.downSpliter = new Image("/tab_images/downBtn.png");
		this.upSpliter = new Image("/tab_images/upBtn.png");
		this.btnSpliterPanel = new HorizontalPanel();
		this.btnSpliterPanel.add(this.upSpliter);
		this.btnSpliterPanel.add(this.downSpliter);
		
		
	    
	    // When it's necessary, this popUp is show
		this.popUp = new CenterEventPopUp(400, 200,this.constants.PopUpWait());
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
	    this.topBottomSpliter.addNorth(this.topPanel, this.topPanelHeight);
		this.topBottomSpliter.add(this.bottomPanel);
	    
	    // the left and the right content of the main Spliter ( leftRightSpliter )
	    this.leftPanel = new LayoutPanel();
	    this.leftPanel.setStyleName("tab-content-left");
	    
	    this.rightPanel = new DockLayoutPanel(Unit.PX);
	    this.rightPanel.setStyleName("tab-content");
	    
	    //add panel containing south's widgets
	    this.rightPanel.addSouth(this.rightSouthPanel, 37);
	    
	    // the rightPanel contain all widget of the right part of the topBottomSpliter
	    this.rightNorthPanel.add(this.topBottomSpliter);
	    this.rightPanel.add(this.rightNorthPanel);
	    
	    // connects the two panels to the leftRightSpliter
	    this.leftRightSpliter.addWest(this.leftPanel, 200);
	    this.leftRightSpliter.add(this.rightPanel);
	    this.leftRightSpliter.setWidgetMinSize(this.leftPanel, 150);
	    

	    //add panel containing the current tool
	    this.dispositionPanel.add(this.leftRightSpliter);
	    
	    // FINAL : PopUp + dispositionPanel (=> all widget)
	    this.finalContainer = new LayoutPanel();
	    this.finalContainer.add(this.popUp);
	    this.finalContainer.add(this.dispositionPanel);
	    
	    // Add the main logo
	    Panel logoPanel = new SimplePanel();
	    logoPanel.setSize("150px","82px");
	    logoPanel.setStyleName("logostyle");
	    Image mainLogoImg = new Image("/tab_images/mainlogoMenu.png");
	    logoPanel.add(mainLogoImg);
	    LayoutPanel mainPanel = new LayoutPanel();
	    mainPanel.add(finalContainer);
	    mainPanel.add(mainLogoImg);
	    mainPanel.setWidgetTopHeight(mainLogoImg, 0, Unit.PX, 82, Unit.PX);
	    mainPanel.setWidgetLeftWidth(mainLogoImg, 0, Unit.PX, 150, Unit.PX);
	    mainPanel.setWidgetTopBottom(finalContainer, 0, Unit.PX, 0, Unit.PX);

	    
	    // wrap the finalContainer into the resizeComposite
	    this.initWidget(mainPanel);
	}
	
	/**
	 * Initialise the lgAndMenuPanel with its content
	 */
	private void createLgAndMenu() {
		// liste du choix de la langue
		this.languages = new ListBox(false);
		this.languages.setWidth("240px");
		Label listBoxTitle = new Label(this.constants.LanguageListBox());
		listBoxTitle.setStyleName("languageLabel");
		
		//patie utilisateur
		this.pseudo = new Label();
	    pseudo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	    this.logOut = new Anchor(this.constants.LogOutAnchor());
	    
	    // Create a menu bar
	    this.mainmenu = new MenuBar();
	    this.mainmenu.setStyleName("mainMenuBar");
	    this.mainmenu.setAutoOpen(true);
	    this.mainmenu.setWidth("100%");
	    this.mainmenu.setHeight("20px");
	    this.mainmenu.setAnimationEnabled(true);
	    
	    //Create the tool menu
	    MenuBar tools = new MenuBar(true);
	    tools.setAnimationEnabled(true);
	    this.pageTool = new MenuItem(constants.menuToolsPage(), new Command(){public void execute(){}});
	    this.imageTool = new MenuItem(constants.menuToolsImage(), new Command(){public void execute(){}});
	    tools.addItem(this.pageTool);
	    tools.addItem(this.imageTool);
	    this.mainmenu.addItem(new MenuItem(constants.menuTools(), tools));
	    
	    // Create the setting menu
	    MenuBar setting = new MenuBar(true);
	    setting.setAnimationEnabled(true);
	    this.userSettings = new MenuItem(constants.menuSettingsUser(), new Command(){public void execute(){}});
	    setting.addItem(this.userSettings);
	    this.tagSettings = new MenuItem(constants.menuSettingsTag(), new Command(){public void execute(){}});
	    setting.addItem(this.tagSettings);
	    this.templateSettings = new MenuItem(constants.menuSettingsTemplate(), new Command(){public void execute(){}});
	    setting.addItem(this.templateSettings);
	    settingsItem = new MenuItem(constants.menuSettings(), setting);
	    this.mainmenu.addItem(settingsItem);
	    
	    // Create the help menu
	    MenuBar help = new MenuBar(true);
	    help.setAnimationEnabled(true);
	    help.addItem(constants.menuHelpAbout(), new Command(){
	    		public void execute(){
	    			Window.open("http://richer-cms.appspot.com/site", "_blank", "");
	    		}
	    	});
	    this.mainmenu.addItem(new MenuItem(constants.menuHelp(), help));

	    // Create Language Panel
	    LayoutPanel mainLanguagesPanel = new LayoutPanel();
	    mainLanguagesPanel.add(listBoxTitle);
	    mainLanguagesPanel.add(this.languages);
	    mainLanguagesPanel.setWidgetLeftRight(listBoxTitle, 0, Unit.PX, 70, Unit.PCT);
	    mainLanguagesPanel.setWidgetLeftRight(this.languages, 32, Unit.PCT, 5, Unit.PX);
	    
	    LayoutPanel userPanel = new LayoutPanel();
	    userPanel.add(this.pseudo);
	    userPanel.add(this.logOut);
	    userPanel.setWidgetRightWidth(this.pseudo, 32, Unit.PCT, 70, Unit.PCT);
	    userPanel.setWidgetRightWidth(this.logOut, 5, Unit.PX, 30, Unit.PCT);
	    
	    // Add Menu + LanguagePanel
	    lgAndMenuPanel.setStyleName("tab-content");
	    lgAndMenuPanel.add(this.mainmenu);
	    lgAndMenuPanel.add(userPanel);
	    lgAndMenuPanel.add(mainLanguagesPanel);
	    lgAndMenuPanel.setWidgetLeftRight(this.mainmenu, 150, Unit.PX, 0, Unit.PX);
	    lgAndMenuPanel.setWidgetRightWidth(userPanel, 0, Unit.PX, 300, Unit.PX);
	    lgAndMenuPanel.setWidgetTopBottom(userPanel, 1, Unit.PX, 1, Unit.PX);
	    lgAndMenuPanel.setWidgetRightWidth(mainLanguagesPanel, 300, Unit.PX, 360, Unit.PX);
	    lgAndMenuPanel.setWidgetTopBottom(mainLanguagesPanel, 1, Unit.PX, 1, Unit.PX);
	    lgAndMenuPanel.setWidth("100%");
	}

	public void setConstants(PageConstants constants) {
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
		this.addUpDownBtn();
	}
	
	private void addUpDownBtn() {
		/* // TODO : Don't work very well
		//remove btn to replace it in front
		if(this.topPanel.getWidgetIndex(this.btnSpliterPanel) != -1)
			this.topPanel.remove(this.btnSpliterPanel);
		
		// add down and up btn in first part of the topBottomSpliter
		this.topPanel.add(this.btnSpliterPanel);
	    this.topPanel.setWidgetRightWidth(this.btnSpliterPanel, 10, Unit.PX, 65, Unit.PX);
	    this.topPanel.setWidgetBottomHeight(this.btnSpliterPanel, 0, Unit.PX, 30, Unit.PX);
	    */
	}
	
	public void displayReorderPanel(IReorderPagePanel reorderPanel) {
		this.rightNorthPanel.clear();
		this.rightNorthPanel.add((ReorderPagePanel)reorderPanel);
		//this.leftRightSpliter.add(this.rightPanel);
	}
	
	public void displayNormalPanel(){
		if(this.dispositionPanel.getWidgetIndex(this.leftRightSpliter) == -1){
			this.dispositionPanel.clear();
			this.dispositionPanel.addNorth(lgAndMenuPanel, 23);
			this.dispositionPanel.add(this.leftRightSpliter);
		}
		// evite le clignotement si on est déja sur en mode normale
		if(!this.rightNorthPanel.getWidget(0).equals(this.topBottomSpliter)) {
			this.rightNorthPanel.clear();
			this.rightNorthPanel.add(this.topBottomSpliter);
		}
	}

	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel) {
		this.bottomPanel.add((TinyMCEPanel)tinyMcePanel);
	}

	public void setValidationPanel(IValidationPanel validationPanel) {
		this.rightSouthPanel.add((ValidationPanel)validationPanel);
		//ajout de l'élément final : signifie la fin du chargement
	   // this.dispositionPanel.add(this.leftRightSpliter);
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
	
	public HasClickHandlers onLogOutClick() {
		return this.logOut;
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
	
	public void setLanguageListIndex(int index){
		this.languages.setSelectedIndex(index);
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
		
	public PageConstants getConstants() {
		return this.constants;
	}

	public void setImageToolCommand(Command cmd) {
		this.imageTool.setCommand(cmd);
	}
	
	public void setPageToolCommand(Command cmd) {
		this.pageTool.setCommand(cmd);
	}
	
	public void setUserSettingsCommand(Command cmd) {
		this.userSettings.setCommand(cmd);
	}
	
	public void setTagSettingsCommand(Command cmd) {
		this.tagSettings.setCommand(cmd);
	}
	
	public void setTemplateSettingsCommand(Command cmd) {
		this.templateSettings.setCommand(cmd);
	}
	
	public void displayImagePanel(IImageManager imageMPanel) {
		this.rightNorthPanel.clear();
		this.rightNorthPanel.add((ImageManager)imageMPanel);
	}
	
	public void displayUserManager(IUserManager userManagerPanel) {
		this.dispositionPanel.remove(this.leftRightSpliter);
		this.dispositionPanel.add((UserManager)userManagerPanel);
	}
	
	public void displayTagManager(ITagManager tagManager) {
		this.dispositionPanel.remove(this.leftRightSpliter);
		this.dispositionPanel.add((TagManager)tagManager);
	}

	public void displayTemplateManager(ITemplateManager templateManager) {
		this.dispositionPanel.remove(this.leftRightSpliter);
		this.dispositionPanel.add((TemplateManager)templateManager);
	}
	
	public void reDisplayPageView() {
		//this.toolPanel.clear();
		//this.toolPanel.add(this.leftRightSpliter);
	}
	
	public void setLogOutAnchor(String url) {
		this.logOut.setHref(url);
	}
	
	public void setPseudo(String name){
		this.pseudo.setText(name+" | ");
	}

	public HasClickHandlers upSpliterEvent(){
		return this.upSpliter;
	}

	public HasClickHandlers downSpliterEvent(){
		return this.downSpliter;
	}
	
	public void downRightSpliter(){
		int height = this.topBottomSpliter.getOffsetHeight();
		this.topBottomSpliter.remove(this.topPanel);
		this.topBottomSpliter.remove(bottomPanel);
		this.topBottomSpliter.addNorth(topPanel, height-70);
		this.topBottomSpliter.add(bottomPanel);
	}
	
	public void upRightSpliter(){
		this.topBottomSpliter.remove(this.topPanel);
		this.topBottomSpliter.remove(bottomPanel);
		this.topBottomSpliter.addNorth(topPanel, this.topPanelHeight);
		this.topBottomSpliter.add(bottomPanel);
	}

	public void initAdminMenu(boolean adminMenu) {
		this.adminMenu = adminMenu;
		if (!this.adminMenu) {
			mainmenu.removeItem(settingsItem);
			mainmenu.setWidth("110px");
		}
	}

}
