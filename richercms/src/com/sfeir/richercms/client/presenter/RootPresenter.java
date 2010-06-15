package com.sfeir.richercms.client.presenter;


import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.sfeir.richercms.client.RootEventBus;
import com.sfeir.richercms.client.interfaces.IrootDisplay;
import com.sfeir.richercms.client.view.RootView;
import com.sfeir.richercms.shared.BeanUser;



/**
 * Presenter of the Main display, using to switch view
 * Replace appControler in the MVP model
 * @author homberg.g
 */
@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IrootDisplay, RootEventBus> {

	private BeanUser usr;
	
	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}
	
	/**
	 * empty, wait to display a view
	 */
	public void onLogin() {}
	
	/**
	 * fired by the wizardModule when configuration is finished
	 */
	public void onWizardFinished(){
		eventBus.startMain(this.usr);
	}

	public void onBeforeLoadWizard() {
		view.showPopUpWait();
	}
	
	public void onAfterLoadWizard() {
		view.hidePopUpWait();
	}
	
	public void onSetUsr(BeanUser usr) {
		this.usr = usr;
	}
}
