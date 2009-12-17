package com.esial.richercms.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class CmsPageEdition extends Composite {

	private TextBox browserTitle = new TextBox();
	private TextBox pageTitle = new TextBox();
	private TextBox urlName = new TextBox();
	private TextBox description = new TextBox();
	private DateBox publishDate = new DateBox();
	//private String pageTitlePrec = null;
	private VerticalPanel panel;
	private TinyMCE htmlEditor;
	private FlexTable tbl;
	
	public CmsPageEdition() {
		panel = new VerticalPanel();
		tbl = new FlexTable();
		panel.add(tbl);
		
		tbl.setHTML(1, 1, "Browser Title");
		tbl.setWidget(1, 2, browserTitle);

		tbl.setHTML(2, 1, "Page Title");
		tbl.setWidget(2, 2, pageTitle);
		
		tbl.setHTML(3, 1, "URL name");
		tbl.setWidget(3, 2, urlName);

		tbl.setHTML(4, 1, "Description");
		tbl.setWidget(4, 2, description);

		tbl.setHTML(5, 1, "Publish date");
		tbl.setWidget(5, 2, publishDate);
		
		tbl.setHTML(6, 1, "Content");
		htmlEditor = new TinyMCE(800,35);
		tbl.setWidget(6, 2, htmlEditor);
		
		browserTitle.setWidth("400px");
		pageTitle.setWidth("400px");
		urlName.setWidth("400px");
		description.setWidth("400px");
		
		HorizontalPanel panelButton = new HorizontalPanel();
		panel.add(panelButton);
		Button ok = new Button("OK");
		
		Button cancel = new Button("Cancel");
		panelButton.add(ok);
		panelButton.add(cancel);
		
		// All composites must call initWidget() in their constructors.
		panel.setWidth("100%");
		initWidget(panel);

	}
	
	public void initEdition() {
		initContent();
	}
	
	private void initContent() {
		htmlEditor = new TinyMCE(800,35);
		htmlEditor.setText("TinyMCE");
		tbl.setWidget(6, 2, htmlEditor);
		panel.add(this);
	}
}
