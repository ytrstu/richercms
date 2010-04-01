package com.sfeir.richercms.wizardConfig.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.wizardConfig.client.Interface.IdisplayPageLogin;

/**
 * view of the homePage Allows you to log-in
 * @author homberg.g
 *
 */
public class PageLoginView extends LayoutPanel implements IdisplayPageLogin {

	private Label loginLabel = new Label("");
	private Anchor signInLink = new Anchor();
	
	public PageLoginView() {	
		super();
	}
	
	public Widget asWidget() {
		return this;
	}

	public void createView() {
		
		this.loginLabel.setStylePrimaryName("text-login");

		this.add(loginLabel);
		this.setWidgetTopHeight(this.loginLabel, 10, Style.Unit.PCT, 50, Style.Unit.PCT);
		this.signInLink.setStylePrimaryName("anchor-login");
		this.add(this.signInLink);
		this.setWidgetTopHeight(this.signInLink, 50, Style.Unit.PCT, 22, Style.Unit.PCT);	
	}

	public Anchor getSignInLink() {
		return this.signInLink;
	}

}
