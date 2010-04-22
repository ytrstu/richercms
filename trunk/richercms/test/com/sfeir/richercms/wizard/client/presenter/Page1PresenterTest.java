package com.sfeir.richercms.wizard.client.presenter;

import org.junit.Before;
import org.junit.Test;
import com.google.gwt.junit.client.GWTTestCase;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage1;
import com.sfeir.richercms.wizard.client.view.MockPage1View;

public class Page1PresenterTest extends GWTTestCase {
	
	private Page1Presenter p = null;
	private IdisplayPage1 view = null;

	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}
	
	/**
	 * Initialize presenter
	 */
	@Before
	public void gwtSetUp() {
		view = new MockPage1View();
		this.p = new Page1Presenter(view);
	}
	
	@Test
	public void testChangeLangueUI()
	{
		this.p.changeLangueUI();
		this.p.changeLangueUI();
		this.p.changeLangueUI();
		this.p.changeLangueUI();
	}
	
}
