package com.sfeir.richercms.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.interfaces.IdisplayPageLogin;


/**
 * view of the homePage Allows you to log-in
 * @author homberg.g
 *
 */
public class PageLoginView extends LayoutPanel implements IdisplayPageLogin {

	private Label loginLabel = new Label("");
	private Anchor signInLink = new Anchor();
	private PopUpWait popUpWait;
	
	public PageLoginView() {	
		super();
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public void hidePopUpWait() {
		this.popUpWait.hide();
	}
	
	public void showPopUpWait() {
		
		this.popUpWait.show();
	}

	public void createView() {
		this.popUpWait = new PopUpWait();
		this.loginLabel.setStylePrimaryName("text-login");

		this.signInLink.setStylePrimaryName("anchor-login");
		this.add(this.signInLink);
		this.setWidgetTopHeight(this.signInLink, 55, Style.Unit.PCT, 169, Style.Unit.PX);	
	}

	public Anchor getSignInLink() {
		return this.signInLink;
	}

}
