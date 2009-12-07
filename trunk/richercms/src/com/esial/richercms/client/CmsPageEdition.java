package com.esial.richercms.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
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
	private HorizontalSplitPanel splitPanel;
	private Ckeditor htmlEditor;
	private FlexTable tbl;
	
	public CmsPageEdition(HorizontalSplitPanel splitPanel) {
		this.splitPanel = splitPanel;
		VerticalPanel panel = new VerticalPanel();
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
		htmlEditor = new Ckeditor(800,35);
		tbl.setWidget(6, 2, htmlEditor);
		
		browserTitle.setWidth("400px");
		pageTitle.setWidth("400px");
		urlName.setWidth("400px");
		description.setWidth("400px");
		
		pageTitle.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				//onChangeTitle();
			}
			
		});
		
		HorizontalPanel panelButton = new HorizontalPanel();
		panel.add(panelButton);
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//onOk();
			}
			
		});
		
		Button cancel = new Button("Cancel");
		cancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onCancel();
			}
			
		});
		panelButton.add(ok);
		panelButton.add(cancel);
		
		// All composites must call initWidget() in their constructors.
		panel.setWidth("100%");
		initWidget(panel);

	}
	
	
	private void onCancel() {
		//item.setLabelText(pageTitlePrec);
		splitPanel.setRightWidget(new Label("Select a page on the left tree."));
	}
	
	public void initEdition() {
		initContent();
	}
	
	private void initContent() {
		/*browserTitle.setText(cmsTreeNode.getTitleBrowser());
		pageTitle.setText(cmsTreeNode.getTitle());
		urlName.setText(cmsTreeNode.getPageName());
		description.setText(cmsTreeNode.getDescription());
		publishDate.setValue(cmsTreeNode.getPublishDate());
		pageTitlePrec = cmsTreeNode.getTitle();*/
		htmlEditor = new Ckeditor(800,35);
		htmlEditor.setText("Ckeditor");
		tbl.setWidget(6, 2, htmlEditor);
		splitPanel.setRightWidget(this);
	}
}
