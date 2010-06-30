package com.sfeir.richercms.page.client.view;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;

public class TemplateManager extends ResizeComposite implements ITemplateManager {

	private DockLayoutPanel mainContainer;
	private Button applyTag;
	private ListBox listTemplate;
	private Button addTemplate;
	private FlexTable tagTable;
	
	public Widget asWidget() {
		return this;
	}

	public void createView() {
		this.mainContainer = new DockLayoutPanel(Unit.PX);
		
		//title
		Label title = new Label("Gestion des Templates");
		title.setStyleName("informationTitle");
		this.mainContainer.addNorth(title, 60);
		
		//apply button
		this.applyTag = new Button("appliquer les changments");
		SimplePanel buttonPanel = new SimplePanel();
		buttonPanel.add(this.applyTag);
		buttonPanel.addStyleName("mainButtonPanel");
		this.mainContainer.addSouth(buttonPanel, 60);
		
		//template selection
		Label listTitle = new Label("template existant : ");
		this.listTemplate = new ListBox();
		this.addTemplate = new Button("add");
		LayoutPanel templateContainer = new LayoutPanel();
		templateContainer.add(listTitle);
		templateContainer.setWidgetLeftWidth(listTitle, 0, Unit.PX, 120, Unit.PX);
		templateContainer.setWidgetVerticalPosition(listTitle, Alignment.BEGIN);
		templateContainer.add(this.listTemplate);
		templateContainer.setWidgetLeftWidth(this.listTemplate, 120, Unit.PX, 120, Unit.PX);
		templateContainer.setWidgetVerticalPosition(this.listTemplate, Alignment.BEGIN);
		templateContainer.add(this.addTemplate);
		templateContainer.setWidgetLeftWidth(this.addTemplate, 240, Unit.PX, 70, Unit.PX);
		templateContainer.setWidgetVerticalPosition(this.addTemplate, Alignment.BEGIN);
		
		//tag Selection
		Label tagtitle = new Label("tag possible pour ce template");
		tagtitle.setStyleName("informationTitle");
		this.tagTable = new FlexTable();
		this.addTagTableTitle();
		templateContainer.add(tagtitle);
		templateContainer.setWidgetTopHeight(tagtitle, 100, Unit.PX, 160, Unit.PX);
		templateContainer.add(this.tagTable);
		templateContainer.setWidgetTopHeight(this.tagTable, 170, Unit.PX, 100, Unit.PCT);
		templateContainer.setWidgetVerticalPosition(this.tagTable, Alignment.BEGIN);
		
		this.mainContainer.add(templateContainer);
		
		this.initWidget(this.mainContainer);
	}
	
	/**
	 * add title of each columns of the tag table
	 */
	private void addTagTableTitle() {
		CellFormatter cellFormater = this.tagTable.getCellFormatter();
		cellFormater.setStyleName(0, 0, "tagTableHeader");
		cellFormater.setStyleName(0, 1, "tagTableHeader");
		cellFormater.setStyleName(0, 2, "tagTableHeader");
		cellFormater.setStyleName(0, 3, "tagTableHeader");
		cellFormater.setStyleName(0, 4, "tagTableHeader");
		this.tagTable.setText(0, 0, "sélection");
		this.tagTable.setText(0, 1, "Nom du tag");
		this.tagTable.setText(0, 2, "Libellé court");
		this.tagTable.setText(0, 3, "Description");
		this.tagTable.setText(0, 4, "tag textuel");
	}

}
