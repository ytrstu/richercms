package com.esial.richercms.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RichTextArea.BasicFormatter;
import com.google.gwt.user.client.ui.RichTextArea.ExtendedFormatter;

public class SiteView extends FlowPanel {
	
	private HorizontalPanel panel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel verticalPanel;

	public SiteView() {
		super();
		panel=new HorizontalPanel();
		buttonPanel=new HorizontalPanel();
		verticalPanel=new VerticalPanel();
		// Create and add a tree with a few items in it.
		Tree tree = createTree();
		panel.add(tree);
		
		//Add a text area and set size
		RichTextArea richTextArea=createRichTextArea();
		verticalPanel.add(buttonPanel);
		verticalPanel.add(richTextArea);
		panel.add(verticalPanel);
		
		this.add(panel);
	}
	
	protected RichTextArea createRichTextArea(){
		RichTextArea richTextArea=new RichTextArea();
		richTextArea.setSize("500px", "400px");
		final BasicFormatter basicFormatter=richTextArea.getBasicFormatter();
		if(basicFormatter!=null){
			Button gras=new Button("Gras");
			buttonPanel.add(gras);
			gras.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					basicFormatter.toggleBold();
				}
			});
		}
		final ExtendedFormatter extendedFormatter=richTextArea.getExtendedFormatter();
		if(extendedFormatter!=null){
			Button barre=new Button("Barré");
			buttonPanel.add(barre);
			barre.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					extendedFormatter.toggleStrikethrough();
				}
			});
		}
		return richTextArea;
	}

	protected Tree createTree() {
		Tree tree=new Tree();
		TreeItem pere1=new TreeItem("Catégorie 1");
		pere1.addItem("Sous-Catégorie 1.1");
		pere1.addItem("Sous-Catégorie 1.2");
		tree.addItem(pere1);
		TreeItem pere2=new TreeItem("Catégorie 2");
		pere2.addItem("Sous-Catégorie 2.1");
		pere2.addItem("Sous-Catégorie 2.2");
		tree.addItem(pere2);
		return tree;
	}

}
