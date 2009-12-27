package com.esial.richercms.client.view;

import java.util.HashMap;
import java.util.Iterator;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class MainView {
	
	private FlowPanel content;
	private TabPanel tabPanel;
	private HashMap<String, FlowPanel> tabsContent;
	
	//private String email;
	
	public MainView() {
		super();
		//Tabs creation and insertion
		tabsContent=createTabs();
		tabPanel=insertTabsInPanel();
		tabPanel.selectTab(0);
		
		content=new FlowPanel();
		content.add(tabPanel);
		
		FlowPanel siteDock = tabsContent.get("Site");
		siteDock.add(new SiteView());
		FlowPanel adminDock = tabsContent.get("Administration");
		adminDock.add(new AdminView());
		content.insert(new UserView(), 0);

		tabPanel.selectTab(0);
	}

	private TabPanel insertTabsInPanel() {
		TabPanel tPanel=new TabPanel();
		Iterator<String> it=tabsContent.keySet().iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			tPanel.add(tabsContent.get(string), string);	
		}
		return tPanel;
	}
	
	private  HashMap<String, FlowPanel> createTabs() {
		HashMap<String, FlowPanel> tContent=new HashMap<String, FlowPanel>();
		tContent.put("Site", new FlowPanel());
		tContent.put("Administration", new FlowPanel());
		return tContent;
	}

	public FlowPanel getContent() {
		return content;
	}

	public void setContent(FlowPanel content) {
		this.content = content;
	}

}
