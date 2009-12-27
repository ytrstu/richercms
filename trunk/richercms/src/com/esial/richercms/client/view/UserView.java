package com.esial.richercms.client.view;

import com.esial.richercms.client.UserInfoService;
import com.esial.richercms.client.UserInfoServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;

public class UserView extends HorizontalPanel {

	private TextArea email;
	private TextArea logout;

	public UserView() {
		super();
		email = new TextArea();
		logout = new TextArea();
		email.setText("plopTest");

		final UserInfoServiceAsync user = GWT.create(UserInfoService.class);
		((ServiceDefTarget) user).setServiceEntryPoint (GWT.getModuleBaseURL () + "user");
		user.getEmail(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				email.setText(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				email.setText("error");
			}
		});
/*		user.logout(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				logout.setText("error");
			}

			@Override
			public void onSuccess(String result) {
				logout.setText(result);
			}
			
		});*/

		this.add(email);
		this.add(logout);
	}

}
