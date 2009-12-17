package com.esial.richercms.client.view;


import java.util.HashMap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class TestTabLayoutPanelView {
	private TabLayoutPanel tabPanel;
	private HashMap<String, FlowPanel> tabsContent;

	public void MainView() {
		//Tabs creation and insertion
		tabsContent=createTabs();
		tabPanel=insertTabsInPanel();
		tabPanel.selectTab(0);
		RootLayoutPanel.get().add(tabPanel);
		
		FlowPanel siteDock = tabsContent.get("Site");
		siteDock.add(new SiteView());

		FlowPanel adminDock = tabsContent.get("Administration");
		adminDock.add(new AdminView());

	}

	private TabLayoutPanel insertTabsInPanel() {
		TabLayoutPanel tPanel=new TabLayoutPanel(2, Unit.EM);
		for(String varName : tabsContent.keySet()) {
			tPanel.add(tabsContent.get(varName), varName);	
		}
		return tPanel;
	}

	private  HashMap<String, FlowPanel> createTabs() {
		HashMap<String, FlowPanel> tContent=new HashMap<String, FlowPanel>();
		tContent.put("Site", new FlowPanel());
		tContent.put("Administration", new FlowPanel());
		return tContent;
	}

}