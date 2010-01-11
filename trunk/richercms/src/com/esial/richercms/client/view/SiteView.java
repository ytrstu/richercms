package com.esial.richercms.client.view;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SiteView extends FlowPanel {

	private HorizontalPanel panel;
	private HorizontalPanel buttonPanel;
	private HorizontalSplitPanel splitPanel;
	private Tree tree;
	private TextBox titleBox;
	private TextBox nameBox;
	private ListBox listBox;

	public SiteView() {
		super();
		panel = new HorizontalPanel();
		buttonPanel = new HorizontalPanel();
		splitPanel = new HorizontalSplitPanel();
		splitPanel.setSize("900px", "600px");
		// Create and add a tree with a few items in it.
		tree = createTree();
		tree.setSize("300px", "600px");
		splitPanel.add(tree);

		// Add buttons
		createButtonsForStartScreen();
		splitPanel.setRightWidget(buttonPanel);
		panel.add(splitPanel);

		this.add(panel);
	}

	private void createButtonsForStartScreen() {
		Button addPageButton = new Button("Créer Page");
		buttonPanel.add(addPageButton);
		Button modifPageButton = new Button("Modifier Page");
		buttonPanel.add(modifPageButton);
		Button deletePageButton = new Button("Supprimer Page");
		buttonPanel.add(deletePageButton);
		addPageButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				splitPanel.remove(splitPanel.getRightWidget());
				VerticalPanel vPanel=new VerticalPanel();
				HorizontalPanel hChoicePanel = createChoicePanel();
				vPanel.add(hChoicePanel);
				HorizontalPanel hTitlePanel = createTitlePanel();
				vPanel.add(hTitlePanel);
				HorizontalPanel hNamePanel = createNamePanel();
				vPanel.add(hNamePanel);
				updateButtonPanel();
				vPanel.add(buttonPanel);
				splitPanel.setRightWidget(vPanel);
			}
		});
	}

	protected Tree createTree() {
		Tree tree = new Tree();
		TreeItem pere1 = new TreeItem("Catégorie 1");
		pere1.addItem("Elem 1.1");
		pere1.addItem("Elem 1.2");
		tree.addItem(pere1);
		TreeItem pere2 = new TreeItem("Catégorie 2");
		pere2.addItem("Elem 2.1");
		pere2.addItem("Elem 2.2");
		tree.addItem(pere2);
		return tree;
	}

	protected ArrayList<String> getTreeElements(Tree tree) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < tree.getItemCount(); i++) {
			result.add(tree.getItem(i).getText());
			if (tree.getItem(i).getChildCount() != 0)
				result.addAll(getTreeItemElements(tree.getItem(i)));
		}
		return result;
	}

	protected ArrayList<String> getTreeItemElements(TreeItem item) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < item.getChildCount(); i++) {
			result.add(item.getChild(i).getText());
			if (item.getChild(i).getChildCount() != 0)
				result.addAll(getTreeItemElements(item.getChild(i)));
		}
		return result;
	}
	
	protected void addNewItemInTree(String name, String parent){
		if(parent.equals("Nouvelle catégorie")){
			tree.addItem(name);
			return;
		}
		for (int i = 0; i < tree.getItemCount(); i++) {
			String text=tree.getItem(i).getText();
			if(text.equals(parent)) tree.getItem(i).addItem(name);
			else lookupTreeItem(tree.getItem(i),parent,name);
		}
	}

	protected void lookupTreeItem(TreeItem item,String parent,String name) {
		for (int i = 0; i < item.getChildCount(); i++) {
			String text=item.getChild(i).getText();
			if(text.equals(parent)) item.addItem(name);
			else if(item.getChild(i).getChildCount()==0) return;
			else lookupTreeItem(item.getChild(i), parent, name);
		}
	}
	
	private HorizontalPanel createTitlePanel() {
		HorizontalPanel hNamePanel = new HorizontalPanel();
		hNamePanel.add(new Label("Titre de la page:"));
		titleBox = new TextBox();
		titleBox.setSize("100px", "25px");
		hNamePanel.add(titleBox);
		return hNamePanel;
	}

	private HorizontalPanel createChoicePanel() {
		HorizontalPanel hChoicePanel = new HorizontalPanel();
		hChoicePanel.add(new Label("Choix du noeud parent:"));
		listBox = new ListBox(false);
		ArrayList<String> names = getTreeElements(tree);
		Iterator<String> it = names.iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			listBox.addItem(string);
		}
		listBox.addItem("Nouvelle catégorie");
		hChoicePanel.add(listBox);
		return hChoicePanel;
	}
	
	private void updateButtonPanel() {
		Button createButton = new Button("Créer");
		createButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				splitPanel.remove(splitPanel.getRightWidget());
				splitPanel.setRightWidget(new Label("Editeur html"));
				addNewItemInTree(titleBox.getText(),listBox.getItemText(listBox.getSelectedIndex()));
			}
		});
		Button cancelButton = new Button("Annuler");
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				splitPanel.remove(splitPanel.getRightWidget());
				buttonPanel.clear();
				createButtonsForStartScreen();
				splitPanel.setRightWidget(buttonPanel);
			}
		});
		buttonPanel.clear();
		buttonPanel.add(createButton);
		buttonPanel.add(cancelButton);
	}
	
	private HorizontalPanel createNamePanel() {
		HorizontalPanel hNamePanel = new HorizontalPanel();
		hNamePanel.add(new Label("Nom du fichier HMTL: "));
		nameBox = new TextBox();
		nameBox.setSize("100px", "25px");
		hNamePanel.add(nameBox);
		return hNamePanel;
	}

}
