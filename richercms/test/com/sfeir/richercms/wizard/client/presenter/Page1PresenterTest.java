package com.sfeir.richercms.wizard.client.presenter;

import com.google.gwt.junit.client.GWTTestCase;

public class Page1PresenterTest extends GWTTestCase {
	
	private Page1Presenter p = null;

	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}
	
	/**
	 * Initialize presenter
	 */
	public void gwtSetUp() {
		this.p = new Page1Presenter();
	}
	
	
	public void testMyLocale() {
		
		//at begining, locale is unset
		assertEquals(this.p.myLocale(),"");
		//TODO manque un bout

	}
	
	/**
	 * TODO other test but how
	 */
}
