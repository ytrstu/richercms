package com.esial.richercms.client.view;

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
	//private FlowPanel editPanel;

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
				
				Label lbllogin = new Label("Login (e-mail adress) :");
				TextBox login = new TextBox();
				Label lblpwd1 = new Label("Password :");
				PasswordTextBox pwd1 = new PasswordTextBox();
				Label lblpwd2 = new Label("Type again your password :");
				PasswordTextBox pwd2 = new PasswordTextBox();
				
				addPanel.add(lbllogin);
				addPanel.add(login);
				addPanel.add(lblpwd1);
				addPanel.add(pwd1);
				addPanel.add(lblpwd2);
				addPanel.add(pwd2);
				
				Button createUser =new Button("Create user");
				Button cancelAction =new Button("Cancel");
				cancelAction.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						panel.remove(addPanel);
						panel.add(adminPanel);				
					}
				});
				
			
				addPanel.add(createUser);
				addPanel.add(cancelAction);
				panel.add(addPanel);
				
			}
			
		});
		Button editUser = new Button("Edit an existing user");
		
		adminPanel.add(addUser);
		adminPanel.add(editUser);
		panel.add(adminPanel);
		this.add(panel);
	}
	
}
