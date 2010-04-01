package com.sfeir.richercms.wizardConfig.client.presenter;


import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.sfeir.richercms.wizardConfig.client.Interface.IrootDisplay;
import com.sfeir.richercms.wizardConfig.client.event.WizardConfigEventBus;
import com.sfeir.richercms.wizardConfig.client.view.RootView;


/**
 * Presenter of the Main display, using to switch view
 * Replace appControler in the MVP model
 * @author homberg.g
 */
@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IrootDisplay, WizardConfigEventBus> {

	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}
	
	/**
	 * empty, wait to display a view
	 */
	public void onLogin() {}

}
