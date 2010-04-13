package com.sfeir.richercms.client.interfaces;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * Allows the presenter to communicate with the view
 * PageLogin <=> PageLoginPresenter
 * @author homberg.g
 *
 */
public interface IdisplayPageLogin extends LazyView {

	Widget asWidget();
	
	/**
	 * Return the Anchor widget
	 * => Uses this for change the target URL
	 * @return the login anchor
	 */
	Anchor getSignInLink();
	
	/**
	 * Show a little popUp during loading
	 */
	public void showPopUpWait();
	
	/**
	 * Hide the popUp use during loading
	 */
	public void hidePopUpWait();
}
