package com.esial.richercms.client;

import com.esial.richercms.client.view.MainView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Richercms implements EntryPoint {

	private MainView mainView;
	private UserInfo loginInfo = null;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Check login status using login service.
		UserInfoServiceAsync loginService = GWT.create(UserInfoService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<UserInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(UserInfo result) {
				loginInfo = result;
				/*if(loginInfo.isLoggedIn()) {
					loadRichercms(loginInfo);
				} else {
					loadLogin(loginInfo);
				}*/
				loadRichercms(loginInfo);
			}
		});

	}

	public void loadRichercms(UserInfo loginInfo) {
		mainView = new MainView(loginInfo);
		// MainExampleView mainExampleView = new MainExampleView();
		RootLayoutPanel.get().add(mainView.getContent());
	}

		public MainView getMainView() {
			return mainView;
		}
	}
