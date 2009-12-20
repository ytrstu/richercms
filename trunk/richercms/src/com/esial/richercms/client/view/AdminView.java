package com.esial.richercms.client.view;

import com.esial.richercms.client.CmsPageEdition;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminView extends FlowPanel {
	private HorizontalPanel panel;
	private VerticalPanel adminPanel;
	private VerticalPanel addPanel;
	private VerticalPanel editPanel;
	private CmsPageEdition editor;

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
					}
				});
				
			
				editPanel.add(cancelAction);
				panel.add(editPanel);
				
			}
			
		});
		adminPanel.add(addUser);
		adminPanel.add(bpEditUser);
		panel.add(adminPanel);
		this.add(panel);
	}
	
}
