package com.sfeir.richercms.wizardConfig.client.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.wizardConfig.client.Interface.IdisplayPageLogin;

public class PageLoginView extends LayoutPanel implements IdisplayPageLogin {

	private Label loginLabel = new Label("");
	private Anchor signInLink = new Anchor();
	private VerticalPanel loginPanel = new VerticalPanel();
	
	public PageLoginView() {	
		super();
	}
	
	public Widget asWidget() {
		return this;
	}

	public void createView() {
		
		//signInLink.setHref(loginInfo.getLoginUrl());
		loginLabel.setStylePrimaryName("text-login");
		loginPanel.add(loginLabel);
		signInLink.setStylePrimaryName("anchor-login");	
		loginPanel.add(signInLink);
		this.add(loginPanel);
		
	}

	public Anchor getSignInLink() {
		return this.signInLink;
	}

}
