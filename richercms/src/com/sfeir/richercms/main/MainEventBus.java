package com.sfeir.richercms.main;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.event.EventBusWithLookup;
import com.sfeir.richercms.main.presenter.MainPresenter;
import com.sfeir.richercms.main.view.MainView;
import com.sfeir.richercms.page.client.event.PageModule;
import com.sfeir.richercms.shared.BeanUser;

@Events(startView = MainView.class, module = MainModule.class)
@ChildModules( 
		@ChildModule( moduleClass = PageModule.class, async = true, autoDisplay = false))
public interface MainEventBus extends EventBusWithLookup {
	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( forwardToParent = true )
	public void changeBody( Widget widget );

	/**
	 * Display new view in the main view
	 * use this to display view when you are a child of MainModule
	 * @param widget
	 */
	@Event( handlers = MainPresenter.class)
	public void changeMain(Widget widget);
	
	/**
	 * Start main Module
	 * Called by RootPresenter
	 * @param usr
	 */
	@Event( handlers = MainPresenter.class)
	public void startMain(BeanUser usr);
	
	/**
	 * Start the page module
	 * Fired by mainModule presenter to display pageModule
	 * @param usr
	 */
	@Event( modulesToLoad = PageModule.class)
	public void startPage(BeanUser usr);
}
