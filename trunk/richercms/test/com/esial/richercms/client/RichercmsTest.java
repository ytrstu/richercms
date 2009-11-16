package com.esial.richercms.client;

import com.esial.richercms.client.view.MainView;
import com.google.gwt.junit.client.GWTTestCase;


public class RichercmsTest extends GWTTestCase {
	
	private Richercms underTest;

	@Override
	public String getModuleName() {
		return "com.esial.richercms.Richercms";
	}
	
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
		underTest=new Richercms();
	}
	
	public void testOnModuleLoad(){
		underTest.onModuleLoad();
		MainView mainView=underTest.getMainView();
		assertNotNull(mainView);
	}
}
