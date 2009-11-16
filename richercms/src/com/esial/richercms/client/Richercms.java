package com.esial.richercms.client;

import com.esial.richercms.client.view.MainView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Richercms implements EntryPoint {
	
	private MainView mainView;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainView=new MainView();
		RootPanel.get().add(mainView.getContent());
	}

	public MainView getMainView() {
		return mainView;
	}
}
