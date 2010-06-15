package com.sfeir.richercms.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.interfaces.IdisplayPageLogin;
import com.sfeir.richercms.wizard.client.view.CenterLayoutPanel;


/**
 * view of the homePage Allows you to log-in
 * @author homberg.g
 *
 */
public class PageLoginView extends ResizeComposite implements IdisplayPageLogin {

	private Label loginLabel = new Label("");
	private Anchor signInLink = new Anchor();
	private PopUpWait popUpWait;
	private CenterLayoutPanel recoPopUp;
	private LayoutPanel mainContainter;
	
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
		this.mainContainter = new LayoutPanel();
		this.popUpWait = new PopUpWait();
		this.loginLabel.setStylePrimaryName("text-login");

		this.signInLink.setStylePrimaryName("anchor-login");
		this.mainContainter.add(this.signInLink);
		this.mainContainter.setWidgetTopHeight(this.signInLink, 55, Style.Unit.PCT, 169, Style.Unit.PX);
		
		this.initWidget(this.mainContainter);
	}

	public Anchor getSignInLink() {
		return this.signInLink;
	}

	public void notAuthorized(String email, String urlLogIn) {
		LayoutPanel panel = new LayoutPanel();
		Anchor accountLink = new Anchor();
		accountLink.setHref(urlLogIn);
		accountLink.setStylePrimaryName("anchor-account");
		Label text = new Label("l'accès a ce site vous est refusé avec le compte google :"+
				email+". Si vous possèdez un autre compte veuillez vous reconnecter "+
				"en cliquant sous sur l'image ci-dessous");
		panel.add(text);
		panel.setWidgetTopHeight(text, 0, Unit.PX, 40, Unit.PCT);
		panel.add(accountLink);
		panel.setWidgetTopHeight(accountLink, 50, Unit.PCT, 40, Unit.PCT);
		
		recoPopUp = new CenterLayoutPanel(500, 200, new Label("non Autorisé"), panel);
		this.mainContainter.add(recoPopUp);
		
	}
	
	public void destroyRecoPopUp(){
		this.mainContainter.remove(this.recoPopUp);
	}

}
