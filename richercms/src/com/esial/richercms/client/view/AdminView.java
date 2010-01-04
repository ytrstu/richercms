package com.esial.richercms.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminView extends FlowPanel {
	private HorizontalPanel panel;
	private VerticalPanel adminPanel;
	private VerticalPanel languagePanel;
	private VerticalPanel addPanel;
	private VerticalPanel editPanel;
	//private CmsPageEdition editor;

	public AdminView() {
		super();
		panel = new HorizontalPanel();
		adminPanel = new VerticalPanel();
		adminPanel.setSize("500px", "400px");

		Button addUser = new Button("Add a new user");		
		addUser.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				panel.remove(adminPanel);
				panel.remove(languagePanel);
				addPanel=new VerticalPanel();
				addPanel.setSize("500px", "400px");

				Label lbllogin = new Label("Login - Gmail valid e-mail adress :");
				TextBox login = new TextBox();
				addPanel.add(lbllogin);
				addPanel.add(login);

				Button bpAddUser =new Button("Add new user");
				Button cancelAction =new Button("Cancel");
				cancelAction.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						panel.remove(addPanel);
						panel.add(adminPanel);	
						panel.add(languagePanel);
					}
				});
				addPanel.add(bpAddUser);
				addPanel.add(cancelAction);
				panel.add(addPanel);
			}
		});

		Button bpEditUser = new Button("Edit an existing user");
		bpEditUser.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				panel.remove(adminPanel);
				panel.remove(languagePanel);
				/*editor = new CmsPageEdition();
				panel.add(editor);*/		

				editPanel = new VerticalPanel();
				editPanel.setSize("500px", "400px");


				Button cancelAction =new Button("Cancel");
				cancelAction.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						panel.remove(editPanel);
						panel.add(adminPanel);		
						panel.add(languagePanel);
					}
				});


				editPanel.add(cancelAction);
				panel.add(editPanel);

			}

		});
		adminPanel.add(addUser);
		adminPanel.add(bpEditUser);

		//Language Settings part
		languagePanel = new VerticalPanel();
		languagePanel.setSize("500px", "400px");

		Label lblCmsLg = new Label("Editor Language :");
		ListBox cmsLg = new ListBox();
		cmsLg.addItem("English");
		cmsLg.addItem("Francais");
		cmsLg.addItem("Deutsch");
		cmsLg.setVisibleItemCount(1);
		languagePanel.add(lblCmsLg);
		languagePanel.add(cmsLg);
		
		Label lblSiteLg = new Label("Website Created default language :");
		ListBox siteLg = new ListBox();
		siteLg.addItem("English");
		siteLg.addItem("Francais");
		siteLg.addItem("Deutsch");
		siteLg.setVisibleItemCount(1);
		languagePanel.add(lblSiteLg);
		languagePanel.add(siteLg);

		panel.add(adminPanel);
		panel.add(languagePanel);
		this.add(panel);
	}

}
