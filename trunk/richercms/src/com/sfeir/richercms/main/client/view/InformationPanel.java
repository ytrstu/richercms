package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;

/**
 * Information panel in the manView, use to create a new page
 * @author homberg.g
 *
 */
public class InformationPanel extends ResizeComposite implements IInformationPanel {
	
	private Label lBrowserTitle = new Label("Browser Title");
	private Label lPageTitle = new Label("Page title");
	private Label lUrlName = new Label("Url name");
	private Label lDescription = new Label("Desciption");
	private Label lKeyWord = new Label("Keyword");
	private Label lPublicationStart = new Label("Start date of publication");
	private Label lPublicationFinish = new Label("End date of publication");
	
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
	public void createView() {
		
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


		this.initWidget(root);
	}
}
