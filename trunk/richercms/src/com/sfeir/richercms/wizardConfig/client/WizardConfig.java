package com.sfeir.richercms.wizardConfig.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WizardConfig implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		// the only way to display layoutPanel
	    Mvp4gModule module = (Mvp4gModule)GWT.create( Mvp4gModule.class );
	    module.createAndStartModule();
	    RootLayoutPanel.get().add( (Widget)module.getStartView() );
	}
	
}
