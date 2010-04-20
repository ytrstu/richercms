package com.sfeir.richercms.main.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;

/**
 * Information panel in the manView, use to create a new page
 * @author homberg.g
 *
 */
public class InformationPanel extends ResizeComposite implements IInformationPanel {
	
	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	
	private Label lBrowserTitle = new Label(constants.BrowserTitle());
	private Label lPageTitle = new Label(constants.PageTitle());
	private Label lUrlName = new Label(constants.UrlName());
	private Label lDescription = new Label(constants.Description());
	private Label lKeyWord = new Label(constants.KeyWord());
	private Label lPublicationStart = new Label(constants.PublicationStart());
	private Label lPublicationFinish = new Label(constants.PublicationFinish());
	
	private TextBox tBrowserTitle = new TextBox();
	private TextBox tPageTitle = new TextBox();
	private TextBox tUrlName = new TextBox();
	private TextBox tDescription = new TextBox();
	private TextBox tKeyWord = new TextBox();
	private DateBox dPublicationStart = new DateBox();
	private DateBox dPublicationFinish = new DateBox();
	
	public InformationPanel() {
		super();
		createView();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	private void createView() {
		
		ScrollPanel root = new ScrollPanel();
		FlexTable tab = new FlexTable();
		tab.setCellSpacing(10);
		tab.setWidget(0,0,this.lBrowserTitle);
		tab.setWidget(0,1,this.tBrowserTitle);
		tab.setWidget(1,0,this.lPageTitle);
		tab.setWidget(1,1,this.tPageTitle);
		tab.setWidget(2,0,this.lUrlName);
		tab.setWidget(2,1,this.tUrlName);
		tab.setWidget(3,0,this.lDescription);
		tab.setWidget(3,1,this.tDescription);
		tab.setWidget(4,0,this.lKeyWord);
		tab.setWidget(4,1,this.tKeyWord);
		tab.setWidget(5,0,this.lPublicationStart);
		tab.setWidget(5,1,this.dPublicationStart);
		tab.setWidget(6,0,this.lPublicationFinish);
		tab.setWidget(6,1,this.dPublicationFinish);
		root.add(tab);

		this.deasabledWidgets();
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

	public String getPublicationStart() {
		return dPublicationStart.getValue().toString();
	}

	public String getPublicationFinish() {
		return dPublicationFinish.getValue().toString();
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

	public void setPublicationFinish(String publicationFinish) {
		this.dPublicationFinish.setValue(new Date(publicationFinish));
	}

	public void setPublicationStart(String publicationStart) {
		this.dPublicationStart.setValue(new Date(publicationStart));
	}

	public void setUrlName(String urlName) {
		this.tUrlName.setText(urlName);
	}
	
	
}
