package com.esial.richercms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
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
	private final PageServiceAsync pageService = GWT.create(PageService.class);

	private FlexTable tbl;
	
	private final HorizontalSplitPanel splitPanel;
	
	public CmsPageEdition(HorizontalSplitPanel split) {
		this.splitPanel=split;
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
		ok.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				pageService.addPage(browserTitle.getText(), pageTitle.getText(),
						urlName.getText(), description.getText(),
						htmlEditor.getText(), new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						splitPanel.remove(splitPanel.getRightWidget());
						splitPanel.setRightWidget(new Label("Ok"));
					}
					
					@Override
					public void onFailure(Throwable caught) {
						splitPanel.remove(splitPanel.getRightWidget());
						splitPanel.setRightWidget(new Label("Echec"));
					}
				});
				pageService.getAllPages(new AsyncCallback<String[]>() {
					
					@Override
					public void onSuccess(String[] result) {
						splitPanel.remove(splitPanel.getLeftWidget());
						Tree tree=new Tree();
						for(String s : result){
							tree.addItem(s);
						}
						splitPanel.setLeftWidget(tree);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						splitPanel.remove(splitPanel.getLeftWidget());
						splitPanel.setLeftWidget(new Label("TreeUpdateError"));
					}
				});
			}
		});
		
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
	
	public TinyMCE getHtmlEditor() {
		return htmlEditor;
	}
}
