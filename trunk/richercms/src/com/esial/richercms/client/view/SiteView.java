package com.esial.richercms.client.view;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SiteView extends FlowPanel {
	
	private HorizontalPanel panel;
	private HorizontalPanel buttonPanel;
	private VerticalPanel verticalPanel;
	private Tree tree;
	private TextBox nameBox;

	public SiteView() {
		super();
		panel=new HorizontalPanel();
		buttonPanel=new HorizontalPanel();
		verticalPanel=new VerticalPanel();
		verticalPanel.setSize("500px", "600px");
		// Create and add a tree with a few items in it.
		tree = createTree();
		tree.setSize("300px", "600px");
		panel.add(tree);
		
		//Add buttons
		Button addPageButton=new Button("Créer Page");
		addPageButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				verticalPanel.clear();
				HorizontalPanel hChoicePanel=new HorizontalPanel();
				hChoicePanel.add(new Label("Choix du noeud parent:"));
				ListBox listBox=new ListBox(false);
				ArrayList<String> names=getTreeElements(tree);
				Iterator<String> it=names.iterator();
				while (it.hasNext()) {
					String string = (String) it.next();
					listBox.addItem(string);
				}
				hChoicePanel.add(listBox);
				verticalPanel.add(hChoicePanel);
				HorizontalPanel hNamePanel=new HorizontalPanel();
				hNamePanel.add(new Label("Nom de la page:"));
				nameBox=new TextBox();
				nameBox.setSize("100px", "25px");
				hNamePanel.add(nameBox);
				verticalPanel.add(hNamePanel);
				Button createButton=new Button("Créer");
				createButton.addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						verticalPanel.clear();
						verticalPanel.add(new Label("Editeur html"));
						tree.addItem(nameBox.getText());
					}
				});
				Button cancelButton=new Button("Annuler");
				buttonPanel.clear();
				buttonPanel.add(createButton);
				buttonPanel.add(cancelButton);
				verticalPanel.add(buttonPanel);
			}
		});
		buttonPanel.add(addPageButton);
		Button modifPageButton=new Button("Modifier Page");
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
	
	protected ArrayList<String> getTreeElements(Tree tree){
		ArrayList<String> result=new ArrayList<String>();
		for(int i=0;i<tree.getItemCount();i++){
			result.add(tree.getItem(i).getText());
		}
		return result;
	}

}
