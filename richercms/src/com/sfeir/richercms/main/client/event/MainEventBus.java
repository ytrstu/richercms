package com.sfeir.richercms.main.client.event;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.main.client.presenter.MainPagePresenter;
import com.sfeir.richercms.main.client.view.MainPageView;


/**
 * Event bus for the main module
 * @author homberg.g
 *
 */
@Events(startView = MainPageView.class, module = MainModule.class)
public interface MainEventBus extends EventBus {

	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( forwardToParent = true )
	public void changeBody( Widget widget );
	
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and MainPagePresenter(first view to display)
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void startMain();
}
