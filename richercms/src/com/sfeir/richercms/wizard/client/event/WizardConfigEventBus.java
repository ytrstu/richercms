package com.sfeir.richercms.wizard.client.event;


import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;

import com.mvp4g.client.event.EventBusWithLookup;
import com.sfeir.richercms.wizard.client.history.WizardHistoryConverter;
import com.sfeir.richercms.wizard.client.presenter.Page1Presenter;
import com.sfeir.richercms.wizard.client.presenter.Page2Presenter;
import com.sfeir.richercms.wizard.client.view.Page1View;


/**
 * EventBus Interface :
 * Contains the prototype of each event,
 * and annotations necessary to map events and 
 * classes that implement the function to launch
 * 
 * @author homberg.g
 */
@Events(startView = Page1View.class, module = WizardModule.class)
public interface WizardConfigEventBus extends EventBusWithLookup{

	/**
	 * Change view : Page1 => Page2
	 */
	@Event(handlers=Page2Presenter.class, historyConverter = WizardHistoryConverter.class)
	public void GoToSecondPage();
	
	
	/**
	 * Change view : Login => Page1
	 */
	@Event(handlers=Page1Presenter.class, historyConverter = WizardHistoryConverter.class)
	public void startWizard();
	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( forwardToParent = true )
	public void changeBody( Widget widget );
	
	/**
	 * forward this event to the parent eventbus
	 */
	@Event( forwardToParent = true )
	public void wizardFinished();
		
}
