package com.esial.richercms.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SiteView extends FlowPanel {
	
	private HorizontalPanel panel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel verticalPanel;

	public SiteView() {
		super();
		panel=new HorizontalPanel();
		buttonPanel=new HorizontalPanel();
		verticalPanel=new VerticalPanel();
		verticalPanel.setSize("500px", "600px");
		// Create and add a tree with a few items in it.
		Tree tree = createTree();
		tree.setSize("300px", "600px");
		panel.add(tree);
		
		//Add buttons
		Button addPageButton=new Button("Créer Page");
		addPageButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				verticalPanel.clear();
				verticalPanel.add(new Label("Choix de la racine:"));
			}
		});
		buttonPanel.add(addPageButton);
		Button modifPageButton=new Button("Modifie Page");
		buttonPanel.add(modifPageButton);
		Button deletePageButton=new Button("Supprimer Page");
		buttonPanel.add(deletePageButton);
		verticalPanel.add(buttonPanel);
		panel.add(verticalPanel);
		
		this.add(panel);
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
