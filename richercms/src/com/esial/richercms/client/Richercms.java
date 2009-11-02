package com.esial.richercms.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

public class Richercms implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Button b=new Button("Test");
		RootPanel.get().add(b);
	}

}
