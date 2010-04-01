package com.sfeir.richercms.main.client.presenter;


import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.sfeir.richercms.main.client.Interface.IrootDisplay;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.view.RootView;


/**
 * Presenter of the Main display, using to switch view
 * Replace appControler in the MVP model
 * @author homberg.g
 */
@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IrootDisplay, MainEventBus> {

	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}
	
	/**
	 * empty, wait to display a view
	 */
	public void onStart() {}

}
