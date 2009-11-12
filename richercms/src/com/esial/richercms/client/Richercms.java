package com.esial.richercms.client;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Richercms implements EntryPoint {
	
	private VerticalPanel verticalPanel;
	private TabPanel tabPanel;
	private HashMap<String, DockPanel> tabsContent;
	private HorizontalPanel horizontalPanel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//Tabs creation and insertion
		tabsContent=createTabs();
		tabPanel=insertTabsInPanel();
		tabPanel.selectTab(0);
		
		verticalPanel=new VerticalPanel();
		verticalPanel.add(tabPanel);
		horizontalPanel=new HorizontalPanel();
		
		// Create and add a tree with a few items in it.
		Tree tree = createTree();
		horizontalPanel.add(tree);
		
		//Add a text area and set size
		RichTextArea richTextArea=new RichTextArea();
		richTextArea.setSize("500px", "400px");
		horizontalPanel.add(richTextArea);
		
		verticalPanel.add(horizontalPanel);
		RootPanel.get().add(verticalPanel);
	}

	protected Tree createTree() {
		Tree tree=new Tree();
		tree.addItem("elem1");
		tree.addItem("elem2");
		tree.addItem("elem3");
		return tree;
	}

	private TabPanel insertTabsInPanel() {
		TabPanel tPanel=new TabPanel();
		Iterator<String> it=tabsContent.keySet().iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			tabPanel.add(tabsContent.get(string), string);	
		}
		return tPanel;
	}
	
	private  HashMap<String, DockPanel> createTabs() {
		HashMap<String, DockPanel> tContent=new HashMap<String, DockPanel>();
		tContent.put("Site", new DockPanel());
		tContent.put("Elements", new DockPanel());
		tContent.put("Modules", new DockPanel());
		tContent.put("Utilisateurs", new DockPanel());
		tContent.put("Rapports", new DockPanel());
		return tContent;
	}
}
