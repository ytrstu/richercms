package com.sfeir.richercms.wizard.client.presenter;

import com.google.gwt.junit.client.GWTTestCase;

public class Page2PresenterTest extends GWTTestCase {
	
	private Page2Presenter p = null;
	
	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}

	/**
	 * Initialize presenter
	 */
	public void gwtSetUp() {
		this.p = new Page2Presenter();
	}
}
