package com.sfeir.richercms.client;

import com.google.gwt.user.client.ui.Widget;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;

import com.mvp4g.client.event.EventBusWithLookup;

import com.sfeir.richercms.client.presenter.PageLoginPresenter;
import com.sfeir.richercms.client.presenter.RootPresenter;
import com.sfeir.richercms.client.view.RootView;
import com.sfeir.richercms.main.client.event.MainModule;
import com.sfeir.richercms.wizard.client.event.WizardModule;




@Events(startView = RootView.class, debug = true)
@ChildModules( 
		{@ChildModule( moduleClass = WizardModule.class, async = true, autoDisplay = false),
		@ChildModule( moduleClass = MainModule.class, async = true, autoDisplay = false)})
public interface RootEventBus extends EventBusWithLookup {

	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( handlers = RootPresenter.class )
	public void changeBody( Widget widget );
	
	@Event(modulesToLoad = WizardModule.class, handlers = RootPresenter.class)
	public void startWizard();
	
	@Event( modulesToLoad = MainModule.class )
	public void startMain();
	
	@Event( handlers = RootPresenter.class )
	public void wizardFinished();
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and PageLoginPresenter(first view to display)
	 */
	@Start
	@InitHistory
	@Event( handlers = {RootPresenter.class, PageLoginPresenter.class})
	public void login();

}
