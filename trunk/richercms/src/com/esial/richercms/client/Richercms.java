package com.esial.richercms.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Richercms implements EntryPoint {
	
	private TabPanel tabPanel;
	private DockPanel dockPanel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		dockPanel=new DockPanel();
		tabPanel=new TabPanel();
		tabPanel.add(dockPanel, "TabTest");
		RootPanel.get().add(tabPanel);
	}
}
