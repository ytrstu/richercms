package com.sfeir.richercms.client.presenter;


import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.sfeir.richercms.client.RootEventBus;
import com.sfeir.richercms.client.interfaces.IrootDisplay;
import com.sfeir.richercms.client.view.RootView;



/**
 * Presenter of the Main display, using to switch view
 * Replace appControler in the MVP model
 * @author homberg.g
 */
@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IrootDisplay, RootEventBus> {

	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}
	
	/**
	 * empty, wait to display a view
	 */
	public void onLogin() {}
	
	public void onStartWizard() {}
	
	public void onWizardFinished(){
		eventBus.startMain();
	}

}
