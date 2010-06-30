package com.sfeir.richercms.page.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;

/**
 * Information panel in the manView, use to create a new page
 * @author homberg.g
 *
 */
public class InformationPanel extends ResizeComposite implements IInformationPanel {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private boolean help = false; // savoir si on active ou non l'aide pour la traduction
	
	private ArrayList<Label> cpyLabelLst = null;
	private ArrayList<Button> cpyButtonLst = null;

	private LayoutPanel root = null;
	private VerticalPanel infoContainer = null;
	private VerticalPanel tagContainer = null;
	
	private FlexTable infoTab = null;
	private FlexTable tagTab = null;
	
	private Label Title = null;
	private Label tagTitle = null;
	private Label lock = null;
	private Label helpUrlName = null;
	private Label helpPageTitle = null;
	private TextBox tBrowserTitle = null;
	private TextBox tPageTitle = null;
	private TextBox tUrlName = null;
	private TextBox tDescription = null;
	private TextBox tKeyWord = null;
	private DateBox dPublicationStart = null;
	private DateBox dPublicationFinish = null;
	private final static String textBoxWidth = "250px";
	//parse this to know what tag are selected
	private List<CheckBox> CheckBoxTag = null;
	
	
	public InformationPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {

		
		this.lock = new Label("");
		this.lock.setStyleName("lockLabel");

		//create the first part : information
		this.createInfoPanel();
		
		//create the second part : tag
		this.createTagPanel();
		
		// layoutPanel attach content + lockDisplay
		this.root = new LayoutPanel();
		this.root.add(this.infoContainer);
		this.root.add(this.lock);
		this.root.setWidgetLeftWidth(this.infoContainer, 10, Unit.PX, 700, Unit.PX);
		this.root.setWidgetTopHeight(this.infoContainer, 10, Unit.PX, 280, Unit.PX);
		this.root.setWidgetRightWidth(this.lock, 10, Unit.PX, 300, Unit.PX);
		this.root.setWidgetTopHeight(this.lock, 10, Unit.PX, 20, Unit.PX);

		this.deasabledWidgets();
		this.hideAllHelpField();
		
		//masterPanel is scrollable
		ScrollPanel scroll = new ScrollPanel();
		scroll.add(this.root);
		this.initWidget(scroll);
	}
	
	private void createInfoPanel(){
		Image img;
		this.cpyLabelLst = new ArrayList<Label>();
		this.cpyButtonLst = new ArrayList<Button>();
		
		this.Title = new Label(this.constants.DefaultTitleInformation());
		this.Title.setStyleName("informationTitle");
		

		this.tBrowserTitle = new TextBox();
		this.tBrowserTitle.setWidth(textBoxWidth);
		this.tPageTitle = new TextBox();
		this.tPageTitle.setWidth(textBoxWidth);
		this.tUrlName = new TextBox();
		this.tUrlName.setWidth(textBoxWidth);
		this.tDescription = new TextBox();
		this.tDescription.setWidth(textBoxWidth);
		this.tKeyWord = new TextBox();
		this.tKeyWord.setWidth(textBoxWidth);
		this.dPublicationStart = new DateBox();
		this.dPublicationStart.setWidth(textBoxWidth);
		this.dPublicationFinish = new DateBox();
		this.dPublicationFinish.setWidth(textBoxWidth);
		
		//display required if user miss them
		this.helpUrlName = new Label(this.constants.ObligationMsg());
		this.helpUrlName.setVisible(false);
		this.helpUrlName.addStyleName("requiredField");
		this.helpPageTitle = new Label(this.constants.ObligationMsg());
		this.helpPageTitle.setVisible(false);
		this.helpPageTitle.addStyleName("requiredField");
		
		for(int i=0; i<5; i++) {
			Button b = new Button();
			b.setText("<");
			this.cpyButtonLst.add(b);
		}
		
		for(int i=0; i<5; i++) {
			this.cpyLabelLst.add(new Label(""));
		}
		
		HorizontalPanel p;
		
		this.infoTab = new FlexTable();
		this.infoTab.setCellSpacing(10);
		
		this.infoTab.setWidget(0,0, new Label(constants.BrowserTitle()));
		this.infoTab.setWidget(0,1,this.tBrowserTitle);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessBrowserTitle());
		this.infoTab.setWidget(0,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(0));p.add(this.cpyLabelLst.get(0));
		this.infoTab.setWidget(0,3,p);
		
		this.infoTab.setWidget(1,0, new Label(constants.PageTitle()));
		this.infoTab.setWidget(1,1,this.tPageTitle);
		this.infoTab.setWidget(1,3,this.helpPageTitle);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessPageTitle());
		this.infoTab.setWidget(1,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(1));p.add(this.cpyLabelLst.get(1));
		this.infoTab.setWidget(1,4,p);
		
		this.infoTab.setWidget(2,0, new Label(constants.UrlName()));
		this.infoTab.setWidget(2,1,this.tUrlName);
		this.infoTab.setWidget(2,3,this.helpUrlName);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessUrlName());
		this.infoTab.setWidget(2,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(2));p.add(this.cpyLabelLst.get(2));
		this.infoTab.setWidget(2,4,p);
		
		this.infoTab.setWidget(3,0, new Label(constants.Description()));
		this.infoTab.setWidget(3,1,this.tDescription);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDescription());
		this.infoTab.setWidget(3,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(3));p.add(this.cpyLabelLst.get(3));
		this.infoTab.setWidget(3,3,p);
		
		this.infoTab.setWidget(4,0, new Label(constants.KeyWord()));
		this.infoTab.setWidget(4,1,this.tKeyWord);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessKeyWord());
		this.infoTab.setWidget(4,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(4));p.add(this.cpyLabelLst.get(4));
		this.infoTab.setWidget(4,3,p);
		
		this.infoTab.setWidget(5,0, new Label(constants.PublicationStart()));
		this.infoTab.setWidget(5,1,this.dPublicationStart);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDateStart());
		this.infoTab.setWidget(5,2,img);
		
		this.infoTab.setWidget(6,0, new Label(constants.PublicationFinish()));
		this.infoTab.setWidget(6,1,this.dPublicationFinish);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDateStop());
		this.infoTab.setWidget(6,2,img);
		
		//add all in container
		this.infoContainer = new VerticalPanel();
		this.infoContainer.add(this.Title);
		this.infoContainer.add(this.infoTab);
		this.infoContainer.add(new Label(this.constants.Obligation()));
		
	}
	
	private void createTagPanel(){
		this.tagTitle = new Label("Selection des tag associé à la page :");
		this.tagTitle.setStyleName("informationTitle");
		
		this.tagTab = new FlexTable();
	
		this.CheckBoxTag = new ArrayList<CheckBox>();
		
		this.tagContainer = new VerticalPanel();
		this.tagContainer.add(this.tagTitle);
		this.tagContainer.add(this.tagTab);
	}
	
	public void addTagLine(Long id, String tagName, String shortLib, String Description){
		int numRow = this.tagTab.getRowCount();
		CheckBox cb = new CheckBox(shortLib);
		cb.setHTML(id.toString());
		this.tagTab.setText(numRow, 1, shortLib);
	}
	
	public void enabledWidgets() {
		this.tBrowserTitle.setEnabled(true);
		this.tPageTitle.setEnabled(true);
		this.tUrlName.setEnabled(true);
		this.tDescription.setEnabled(true);
		this.tKeyWord.setEnabled(true);
		this.dPublicationStart.setEnabled(true);
		this.dPublicationFinish.setEnabled(true);
	}
	
	public void deasabledWidgets() {
		this.tBrowserTitle.setEnabled(false);
		this.tPageTitle.setEnabled(false);
		this.tUrlName.setEnabled(false);
		this.tDescription.setEnabled(false);
		this.tKeyWord.setEnabled(false);
		this.dPublicationStart.setEnabled(false);
		this.dPublicationFinish.setEnabled(false);
	}
	
	public void clearFields()
	{
		this.tBrowserTitle.setText("");
		this.tDescription.setText("");
		this.tKeyWord.setText("");
		this.tPageTitle.setText("");
		this.tUrlName.setText("");
		this.dPublicationFinish.setValue(new Date());
		this.dPublicationStart.setValue(new Date());
	}

	public String getBrowserTitle() {
		return tBrowserTitle.getText();
	}


	public String getPageTitle() {
		return tPageTitle.getText();
	}

	public String getUrlName() {
		return tUrlName.getText();
	}

	public String getDescription() {
		return tDescription.getText();
	}

	public String getKeyWord() {
		return tKeyWord.getText();
	}

	public Date getPublicationStart() {
		return dPublicationStart.getValue();
	}

	public Date getPublicationFinish() {
		return dPublicationFinish.getValue();
	}

	public void setBrowserTitle(String browserTitle) {
		this.tBrowserTitle.setText(browserTitle);	
	}

	public void setDescription(String description) {
		this.tDescription.setText(description);
	}

	public void setKeyWord(String keyWord) {
		this.tKeyWord.setText(keyWord);
	}

	public void setPageTitle(String pageTitle) {
		this.tPageTitle.setText(pageTitle);
	}
	
	public void setlockInfo(String pseudo) {
		if(pseudo.length() == 0)
			this.lock.setText("");
		else
			this.lock.setText("en cours de modification par : "+pseudo);
	}

	public void setPublicationFinish(Date publicationFinish) {
		this.dPublicationFinish.setValue(publicationFinish);
	}

	public void setPublicationStart(Date publicationStart) {
		this.dPublicationStart.setValue(publicationStart);
	}

	public void setUrlName(String urlName) {
		this.tUrlName.setText(urlName);
	}

	public Widget asWidget() {
		return this;
	}
	
	public void setHelp(String browserTitle, String description, String keyWord, 
			String pageTitle, String urlName) {
		this.cpyLabelLst.get(0).setText(browserTitle);
		this.cpyLabelLst.get(3).setText(description);
		this.cpyLabelLst.get(4).setText(keyWord);
		this.cpyLabelLst.get(1).setText(pageTitle);
		this.cpyLabelLst.get(2).setText(urlName);
	}
	
	public void hideAllHelpField() {
		for(int i = 0 ; i<5 ; i++) {
			this.cpyButtonLst.get(i).setVisible(false);
			this.cpyLabelLst.get(i).setVisible(false);
		}
	}
	
	public void showOneHelp(int number) {
		if(this.help) {
			this.hideAllHelpField();
			this.cpyButtonLst.get(number).setVisible(true);
			this.cpyLabelLst.get(number).setVisible(true);
		}
	}
	
	public void hideOneHelp(int number) {
		this.cpyButtonLst.get(number).setVisible(false);
		this.cpyLabelLst.get(number).setVisible(false);
	}
	

	
	public void showRequiredTitle() {
		this.helpPageTitle.setVisible(true);
	}
	
	public void showRequiredUrl() {
		this.helpUrlName.setText(this.constants.ObligationMsg());
		this.helpUrlName.setVisible(true);
	}
	
	public void hideRequiredField(){
		this.helpPageTitle.setVisible(false);
		this.helpUrlName.setVisible(false);
	}
	
	public void showErrorInUrl(){
		this.helpUrlName.setText(this.constants.ErrorInUrl());
		this.helpUrlName.setVisible(true);
	}
	
	public void hideErrorInUrl(){
		this.helpUrlName.setVisible(false);
	}
	
	public void enableHelp(){
		this.help = true;
	}
	
	public void disableHelp(){
		this.help = false;
		this.hideAllHelpField();
	}
	
	public HasFocusHandlers getFocusOnTB0() {
		return this.tBrowserTitle;
	}
	
	public HasFocusHandlers getFocusOnTB1() {
		return this.tPageTitle;
	}
	
	public HasFocusHandlers getFocusOnTB2() {
		return this.tUrlName;
	}
	
	public HasFocusHandlers getFocusOnTB3() {
		return this.tDescription;
	}
	
	public HasFocusHandlers getFocusOnTB4() {
		return this.tKeyWord;
	}
	
	public HasClickHandlers getclickBtnCpy(int number) {
		return this.cpyButtonLst.get(number);
	}
	
	public void setTitle(String title) {
		this.Title.setText(title);
	}
	
	public String getTitle() {
		return this.Title.getText();
	}
	
	public void cpyHelpInField(int number) {
		switch(number) {
		case 0 :
			this.tBrowserTitle.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 1 :
			this.tPageTitle.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 2 :
			this.tUrlName.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 3 :
			this.tDescription.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 4 :
			this.tKeyWord.setText(this.cpyLabelLst.get(number).getText());
			break;
		}
	}
	
	public PageConstants getConstants() {
		return this.constants;
	}
}
