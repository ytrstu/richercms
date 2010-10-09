package com.sfeir.richercms.main.presenter;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.sfeir.richercms.main.view.MainView;
import com.sfeir.richercms.shared.BeanUser;

/**
 * 
 * @author homberg.g
 * Presenter of the MainView
 */
@Presenter( view = MainView.class )
public class MainPresenterOldIE extends MainPresenter {

	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { }
	
	public void onStartMain(BeanUser usr){
		HTMLPanel txtBrowser = new HTMLPanel("Please install Firefox or Chrome to run this application.<br/>SVP, installez Firefox ou Chrome pour utiliser cette application.");
		RootPanel.get().add(txtBrowser);
	}
	
	public void onChangeMain(Widget widget) {
	}
	
}
