package com.sfeir.richercms.wizardConfig.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
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

		this.signInLink.setStylePrimaryName("anchor-login");
		this.add(this.signInLink);
		this.setWidgetTopHeight(this.signInLink, 55, Style.Unit.PCT, 169, Style.Unit.PX);	
	}

	public Anchor getSignInLink() {
		return this.signInLink;
	}

}