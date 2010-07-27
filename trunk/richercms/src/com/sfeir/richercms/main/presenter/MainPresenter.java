package com.sfeir.richercms.main.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.MainEventBus;
import com.sfeir.richercms.main.interfaces.IMainDisplay;
import com.sfeir.richercms.main.view.MainView;
import com.sfeir.richercms.shared.BeanUser;

/**
 * 
 * @author homberg.g
 * Presenter of the MainView
 */
@Presenter( view = MainView.class )
public class MainPresenter extends LazyPresenter<IMainDisplay, MainEventBus> {

	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { }
	
	public void onStartMain(BeanUser usr){
		eventBus.startPage(usr);
		eventBus.changeBody(this.view.asWidget());
	}
	
	public void onChangeMain(Widget widget) {
		Panel body = view.getBody();
		body.clear();
		body.add( widget );
		eventBus.changeBody(this.view.asWidget());
	}
	
}
