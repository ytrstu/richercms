package com.sfeir.richercms.page.client.view;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.richercms.page.client.MainConstants;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;

/**
 * Information panel in the manView, use to create a new page
 * @author homberg.g
 *
 */
public class InformationPanel extends ResizeComposite implements IInformationPanel {
	
	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private boolean help = false; // savoir si on active ou non l'aide pour la traduction
	
	private ArrayList<Label> cpyLabelLst = null;
	private ArrayList<Button> cpyButtonLst = null;

	private Label Title = null;
	private TextBox tBrowserTitle = null;
	private TextBox tPageTitle = null;
	private TextBox tUrlName = null;
	private TextBox tDescription = null;
	private TextBox tKeyWord = null;
	private DateBox dPublicationStart = null;
	private DateBox dPublicationFinish = null;
	
	public InformationPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.Title = new Label(this.constants.DefaultTitleInformation());
		this.Title.setStyleName("informationTitle");
		
		this.cpyLabelLst = new ArrayList<Label>();
		this.cpyButtonLst = new ArrayList<Button>();

		this.tBrowserTitle = new TextBox();
		this.tPageTitle = new TextBox();
		this.tUrlName = new TextBox();
		this.tDescription = new TextBox();
		this.tKeyWord = new TextBox();
		this.dPublicationStart = new DateBox();
		this.dPublicationFinish = new DateBox();
		
		for(int i=0; i<5; i++) {
			Button b = new Button();
			b.setText("<");
			this.cpyButtonLst.add(b);
		}
		
		for(int i=0; i<5; i++) {
			this.cpyLabelLst.add(new Label(""));
		}
		
		HorizontalPanel p;
		ScrollPanel root = new ScrollPanel();
		FlexTable tab = new FlexTable();
		tab.setCellSpacing(10);
		
		tab.setWidget(0,0, new Label(constants.BrowserTitle()));
		tab.setWidget(0,1,this.tBrowserTitle);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(0));p.add(this.cpyLabelLst.get(0));
		tab.setWidget(0,2,p);
		
		tab.setWidget(1,0, new Label(constants.PageTitle()));
		tab.setWidget(1,1,this.tPageTitle);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(1));p.add(this.cpyLabelLst.get(1));
		tab.setWidget(1,2,p);
		
		tab.setWidget(2,0, new Label(constants.UrlName()));
		tab.setWidget(2,1,this.tUrlName);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(2));p.add(this.cpyLabelLst.get(2));
		tab.setWidget(2,2,p);
		
		tab.setWidget(3,0, new Label(constants.Description()));
		tab.setWidget(3,1,this.tDescription);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(3));p.add(this.cpyLabelLst.get(3));
		tab.setWidget(3,2,p);
		
		tab.setWidget(4,0, new Label(constants.KeyWord()));
		tab.setWidget(4,1,this.tKeyWord);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(4));p.add(this.cpyLabelLst.get(4));
		tab.setWidget(4,2,p);
		
		tab.setWidget(5,0, new Label(constants.PublicationStart()));
		tab.setWidget(5,1,this.dPublicationStart);
		
		tab.setWidget(6,0, new Label(constants.PublicationFinish()));
		tab.setWidget(6,1,this.dPublicationFinish);
		
		VerticalPanel container = new VerticalPanel();
		container.add(this.Title);
		container.add(tab);
		root.add(container);

		this.deasabledWidgets();
		this.hideAllHelpField();
		
		this.initWidget(root);
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
	
	public MainConstants getConstants() {
		return this.constants;
	}
}
