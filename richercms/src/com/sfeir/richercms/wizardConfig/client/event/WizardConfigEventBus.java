package com.sfeir.richercms.wizardConfig.client.event;


import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.wizardConfig.client.presenter.Page1Presenter;
import com.sfeir.richercms.wizardConfig.client.presenter.Page2Presenter;
import com.sfeir.richercms.wizardConfig.client.presenter.PageLoginPresenter;
import com.sfeir.richercms.wizardConfig.client.presenter.RootPresenter;
import com.sfeir.richercms.wizardConfig.client.view.RootView;


/**
 * EventBus Interface :
 * Contains the prototype of each event,
 * and annotations necessary to map events and 
 * classes that implement the function to launch
 * 
 * @author homberg.g
 */
@Events(startView = RootView.class)
public interface WizardConfigEventBus extends EventBus{

	/**
	 * Change view : Page1 => Page2
	 */
	@Event(handlers=Page2Presenter.class)
	public void GoToSecondPage();
	
	
	/**
	 * Change view : Login => Page1
	 */
	@Event(handlers=Page1Presenter.class)
	public void startWizard();
	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( handlers = RootPresenter.class )
	public void changeBody( Widget widget );
	
	
	
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and PageLoginPresenter(first view to display)
	 */
	@Start
	@Event( handlers = {RootPresenter.class, PageLoginPresenter.class} )
	public void login();
}
