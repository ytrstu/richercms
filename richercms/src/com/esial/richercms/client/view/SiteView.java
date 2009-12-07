package com.esial.richercms.client.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Tree;

public class SiteView extends FlowPanel {
	
	private HorizontalPanel panel;

	public SiteView() {
		super();
		panel=new HorizontalPanel();
		// Create and add a tree with a few items in it.
		Tree tree = createTree();
		panel.add(tree);
		
		//Add a text area and set size
		RichTextArea richTextArea=new RichTextArea();
		richTextArea.setSize("500px", "400px");
		panel.add(richTextArea);
		this.add(panel);
	}

	protected Tree createTree() {
		Tree tree=new Tree();
		tree.addItem("elem1");
		tree.addItem("elem2");
		tree.addItem("elem3");
		return tree;
	}

}
