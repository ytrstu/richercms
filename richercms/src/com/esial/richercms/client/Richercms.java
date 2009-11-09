package com.esial.richercms.client;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
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
		tabsContent=new HashMap<String, DockPanel>();
		tabsContent.put("Site", new DockPanel());
		tabsContent.put("Elements", new DockPanel());
		tabsContent.put("Modules", new DockPanel());
		tabsContent.put("Utilisateurs", new DockPanel());
		tabsContent.put("Rapports", new DockPanel());
		tabPanel=new TabPanel();
		Iterator<String> it=tabsContent.keySet().iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			tabPanel.add(tabsContent.get(string), string);
		}
		verticalPanel=new VerticalPanel();
		verticalPanel.add(tabPanel);
		horizontalPanel=new HorizontalPanel();
		Tree tree=new Tree();
		tree.addItem("elem1");
		tree.addItem("elem2");
		tree.addItem("elem3");
		horizontalPanel.add(tree);
		RichTextArea richTextArea=new RichTextArea();
		richTextArea.setSize("500px", "400px");
		horizontalPanel.add(richTextArea);
		verticalPanel.add(horizontalPanel);
		RootPanel.get().add(verticalPanel);
	}
}
