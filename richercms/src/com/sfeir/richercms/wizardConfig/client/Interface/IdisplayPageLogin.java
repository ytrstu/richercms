package com.sfeir.richercms.wizardConfig.client.Interface;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IdisplayPageLogin extends LazyView {

	Widget asWidget();
	
	/**
	 * Return the Anchor widget
	 * => Uses this for change the target URL
	 * @return the login anchor
	 */
	Anchor getSignInLink();
}
