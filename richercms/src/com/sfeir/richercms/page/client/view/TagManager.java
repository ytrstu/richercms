package com.sfeir.richercms.page.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.ITagManager;

public class TagManager extends ResizeComposite implements ITagManager{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private DockLayoutPanel mainContainer;
	private FlexTable tagTable;
	private LayoutPanel addTagPanel;
	private Button addNewTag;
	private TextBox newTagName;
	private TextBox newShortLib;
	private TextArea newDescription;

	public Widget asWidget() {
		return this;
	}

	public void createView() {
		this.mainContainer = new DockLayoutPanel(Unit.PX);

		//title
		Label title = new Label("Gestion des tag");
		title.setStyleName("informationTitle");
		this.mainContainer.addNorth(title, 60);
		
		//tagTable
		this.tagTable = new FlexTable();
		this.tagTable.addStyleName("tagTable");
		this.tagTable.setCellPadding(5);
		this.tagTable.setBorderWidth(2);
		this.addTagTableTitle();
		
		//addTag
		this.addTagPanel = new LayoutPanel();
		this.addTagPanel();
		this.mainContainer.addSouth(addTagPanel, 300);
		
		ScrollPanel scrollUser = new ScrollPanel();
		scrollUser.setWidget(this.tagTable);
		this.mainContainer.add(scrollUser);
		
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
		this.tagTable.setText(0, 0, "Nom du tag");
		this.tagTable.setText(0, 1, "Libellé court");
		this.tagTable.setText(0, 2, "Description");
		this.tagTable.setText(0, 3, "Supression");
	}
	
	/**
	 * Initialize the "addTag".
	 */
	private void addTagPanel() {
		FlexTable newTagTable = new FlexTable();
		
		//title
		Label titleAdd = new Label("Ajouter de nouveau tag");
		titleAdd.setStyleName("informationTitle");
		
		//label
		newTagTable.setText(0, 0, "Nom du tag : ");
		newTagTable.setText(1, 0, "Libellé court : ");
		newTagTable.setText(2, 0, "description : ");
		
		//texBox
		this.newTagName = new TextBox();
		this.newShortLib = new TextBox();
		this.newDescription = new TextArea();
		this.newDescription.setVisibleLines(3);
		newTagTable.setWidget(0, 1, this.newTagName);
		newTagTable.setWidget(1, 1, this.newShortLib);
		newTagTable.setWidget(2, 1, this.newDescription);
		
		//btn
		this.addNewTag = new Button("ajouter ce tag");
		newTagTable.setWidget(1, 2, this.addNewTag);
		CellFormatter cellFormater = newTagTable.getCellFormatter();
		cellFormater.setWidth(1, 2, "200px");
		cellFormater.setAlignment(1, 2, HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		
		//attach element in layoutPanel
		this.addTagPanel.add(titleAdd);
		this.addTagPanel.setWidgetTopHeight(titleAdd, 5, Unit.PX, 20, Unit.PCT);
		this.addTagPanel.add(newTagTable);
		this.addTagPanel.setWidgetTopHeight(newTagTable, 35, Unit.PCT, 60, Unit.PCT);
		
	}
	
	public PageConstants getConstants(){
		return this.constants;
	}
	
	public HasClickHandlers addLine(String tagName, String shortLib, String description) {
		
		PushButton btnDel = new PushButton( new Image("tab_images/Delete-icon.png"));
		int numRow = this.tagTable.getRowCount();
		
		this.tagTable.setText(numRow, 0, tagName);
		this.tagTable.setText(numRow, 1, shortLib);
		this.tagTable.setText(numRow, 2, description);
		this.tagTable.setWidget(numRow, 3, btnDel);
		
		return btnDel;
	}
	
	public String getNewTagName(){
		return this.newTagName.getText();
	}

	public String getNewShortLib(){
		return this.newShortLib.getText();
	}
	
	public String getNewDescription(){
		return this.newDescription.getText();
	}
	
	public HasClickHandlers clickOnAddTag(){
		return this.addNewTag;
	}
	
	public void clearTagTable() {
		this.tagTable.removeAllRows();
		this.addTagTableTitle();
	}
	
	public void clearAddNewTagTextBox() {
		this.newTagName.setText("");
		this.newShortLib.setText("");
		this.newDescription.setText("");
	}
}
