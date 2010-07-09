package com.sfeir.richercms.page.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Panel;
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
	private int rowInModification = -1;
	
	//add tag
	private Button addTag;
	
	//modify tag
	private TextBox modifyTagName;
	private TextBox modifyShortLib;
	private TextArea modifyDescription;
	private CheckBox modifyTagTextuel;
	private Image applyModification;
	private Image cancelModification;
	private FlowPanel btnModifyPanel;
	
	// add tag
	private TextBox newTagName;
	private TextBox newShortLib;
	private TextArea newDescription;
	private CheckBox newtagTextuel;
	private Image saveNewTag;
	private Image cancelNewTag;
	private FlowPanel btnAddPanel;
	private boolean inAddState;
	
	private Panel currentBtnPanel; // save the current BtnPanel during the modification step
	private Image currentModifyBtn;
	private Image currentDeleteBtn;
	private String currentTagName;
	private String currentShortLib;
	private String currentDescription;
	private boolean currentTagTextuel;
	
	// Scroll tab if table was too tall
	private ScrollPanel scrollTag;
	// contain scrollTag : allow to place scrollTag in center
	private LayoutPanel tagContainer;
	

	public Widget asWidget() {
		return this;
	}

	public void createView() {
		this.mainContainer = new DockLayoutPanel(Unit.PX);

		//title
		Label title = new Label("Gestion des tag");
		title.setStyleName("informationTitle");
		this.mainContainer.addNorth(title, 60);
		
		this.addTag = new Button("Ajouter un nouveau tag");
		this.addTag.setWidth("170px");
		this.addTag.setHeight("30px");
		
		//tagTable
		this.tagTable = new FlexTable();
		this.tagTable.addStyleName("tagTable");
		this.tagTable.setCellPadding(5);
		this.tagTable.setBorderWidth(2);
		this.addTagTableTitle();
		
		//modify field
		this.modifyTagName = new TextBox();
		this.modifyShortLib = new TextBox();
		this.modifyDescription = new TextArea();
		this.modifyDescription.setVisibleLines(3);
		this.modifyTagTextuel = new CheckBox(this.constants.TbTextual());
		this.applyModification = new Image("tab_images/trans.png");
		this.applyModification.addStyleName("ApplyStyle");
		this.cancelModification = new Image("tab_images/trans.png");
		this.cancelModification.addStyleName("deleteStyle");
		this.btnModifyPanel = new FlowPanel();
		this.btnModifyPanel.add(applyModification);
		this.btnModifyPanel.add(cancelModification);
		
		//add field
		this.newTagName = new TextBox();
		this.newShortLib = new TextBox();
		this.newDescription = new TextArea();
		this.newDescription.setVisibleLines(3);
		this.newtagTextuel = new CheckBox(this.constants.TbTextual());
		this.saveNewTag = new Image("tab_images/trans.png");
		this.saveNewTag.addStyleName("ApplyStyle");
		this.cancelNewTag = new Image("tab_images/trans.png");
		this.cancelNewTag.addStyleName("deleteStyle");
		this.btnAddPanel = new FlowPanel();
		this.btnAddPanel.add(this.saveNewTag);
		this.btnAddPanel.add(this.cancelNewTag);
		this.inAddState = false;
		
		
		this.scrollTag = new ScrollPanel();
		this.scrollTag.setWidget(this.tagTable);
		this.scrollTag.setWidth(400+"px");
		
		this.tagContainer = new LayoutPanel();
		this.tagContainer.add(this.addTag);
		this.tagContainer.add(this.scrollTag );
		this.tagContainer.setWidgetTopBottom(this.scrollTag, 50, Unit.PX, 10, Unit.PX);
		
		
		this.mainContainer.add(this.tagContainer);
		
		this.initWidget(this.mainContainer);
	}
	
	public void onResize() {
		super.onResize();
		
		//////////////// RESIZE ////////////////
		if(Window.getClientWidth() < this.scrollTag.getOffsetWidth())

			this.scrollTag.setWidth(Window.getClientWidth()+"px");
			
		else if(this.scrollTag.getOffsetWidth() < Window.getClientWidth()) {
			this.scrollTag.setWidth(Window.getClientWidth()+"px");
			
			if((this.tagTable.getOffsetWidth()+20) < this.scrollTag.getOffsetWidth())
				this.scrollTag.setWidth(this.tagTable.getOffsetWidth()+20+"px");
		}
		else
			this.scrollTag.setWidth(Window.getClientWidth()+"px");
		
		//////////////// CENTER ////////////////
		int tagPadding = (Window.getClientWidth() - this.scrollTag.getOffsetWidth())/2;
		this.tagContainer.setWidgetLeftRight(scrollTag, tagPadding, Unit.PX, tagPadding, Unit.PX);
		int buttonPopUpPadding = (Window.getClientWidth() - this.addTag.getOffsetWidth())/2;
		this.tagContainer.setWidgetLeftRight(this.addTag, buttonPopUpPadding, Unit.PX, buttonPopUpPadding, Unit.PX);
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
		this.tagTable.setText(0, 0, this.constants.TagTableName());
		this.tagTable.setText(0, 1, this.constants.TagTableLibe());
		this.tagTable.setText(0, 2, this.constants.TagTableDesc());
		this.tagTable.setText(0, 3, this.constants.TagTabelTextual());
		this.tagTable.setText(0, 4, this.constants.Action());
	}
	
	
	public PageConstants getConstants(){
		return this.constants;
	}
	
	public int addLine(String tagName, String shortLib,
			String description, boolean isTextual) {
		
		this.currentDeleteBtn = new Image("tab_images/trans.png");
		this.currentDeleteBtn.addStyleName("deleteStyle");
		this.currentModifyBtn = new Image("tab_images/trans.png");
		this.currentModifyBtn.addStyleName("modifyStyle");
		
		FlowPanel btnPanel = new FlowPanel();
		btnPanel.add(this.currentModifyBtn);
		btnPanel.add(this.currentDeleteBtn);
		
		int numRow = this.tagTable.getRowCount();
		
		this.tagTable.setText(numRow, 0, tagName);
		this.tagTable.setText(numRow, 1, shortLib);
		this.tagTable.setText(numRow, 2, description);
		this.tagTable.setWidget(numRow, 4, btnPanel);
		
		if(isTextual)
			this.tagTable.setText(numRow, 3, this.constants.Yes());
		else
			this.tagTable.setText(numRow, 3, this.constants.No());
			this.onResize();
		return numRow;
	}
	
	public HasClickHandlers getCurModifyClick() {
		return this.currentModifyBtn;
	}
	
	public HasClickHandlers getCurDeleteClick() {
		return this.currentDeleteBtn;
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
	
	public boolean isTextual(){
		return this.newtagTextuel.getValue();
	}
	
	public HasClickHandlers clickOnAddTag(){
		return this.saveNewTag;
	}
	
	public HasClickHandlers clickOnCancelAddTag(){
		return this.cancelNewTag;
	}
	
	public void clearTagTable() {
		this.tagTable.removeAllRows();
		this.addTagTableTitle();
	}
	
	public void clearModifyFields() {
		this.modifyTagName.setText("");
		this.modifyShortLib.setText("");
		this.modifyDescription.setText("");
		this.modifyTagTextuel.setValue(false);
	}
	
	public String getModifyTagName(){
		return this.modifyTagName.getText();
	}

	public String getModifyShortLib(){
		return this.modifyShortLib.getText();
	}
	
	public String getModifyDescription(){
		return this.modifyDescription.getText();
	}
	
	public boolean isModifyTextual(){
		return this.modifyTagTextuel.getValue();
	}
	
	public void hideModifyFields(boolean restoreValue) {
	
		if(this.rowInModification != -1) {
			if(restoreValue){
				this.tagTable.setText(this.rowInModification, 0, 
						this.currentTagName);
				this.tagTable.setText(this.rowInModification, 1,
						this.currentShortLib);
				this.tagTable.setText(this.rowInModification, 2, 
						this.currentDescription);
				//text instead checkBox value
				if(this.currentTagTextuel)
					this.tagTable.setText(this.rowInModification, 3, 
							this.constants.Yes());
				else
					this.tagTable.setText(this.rowInModification, 3, 
							this.constants.No());
				
				this.tagTable.setWidget(this.rowInModification, 4, 
						this.currentBtnPanel);
			}else{
				this.tagTable.setText(this.rowInModification, 0, 
						this.getModifyTagName());
				this.tagTable.setText(this.rowInModification, 1,
						this.getModifyShortLib());
				this.tagTable.setText(this.rowInModification, 2, 
						this.getModifyDescription());
				//text instead checkBox value
				if(this.isModifyTextual())
					this.tagTable.setText(this.rowInModification, 3, this.constants.Yes());
				else
					this.tagTable.setText(this.rowInModification, 3, this.constants.No());
				
				this.tagTable.setWidget(this.rowInModification, 4, 
						this.currentBtnPanel);
			}
		}
		
		this.rowInModification = -1;
		this.currentBtnPanel= null;
		
		this.onResize();
	}
	
	public void DisplayModifyFields(int row) {
		boolean textualTest;
		
		//hide modify fields if it was use in an other line
		this.hideModifyFields(false);
		//display in the good line
		this.rowInModification = row;
		
		if(this.tagTable.getText(row, 3).equals(this.constants.Yes()))
			textualTest = true;
		else
			textualTest = false;
		
		setModifyFields(this.tagTable.getText(row, 0),
				this.tagTable.getText(row, 1),
				this.tagTable.getText(row, 2),
				textualTest);
		
		this.tagTable.setWidget(row, 0, this.modifyTagName);
		this.tagTable.setWidget(row, 1, this.modifyShortLib);
		this.tagTable.setWidget(row, 2, this.modifyDescription);
		this.tagTable.setWidget(row, 3, this.modifyTagTextuel);
		
		this.currentBtnPanel = (FlowPanel)this.tagTable.getWidget(row, 4);
		this.tagTable.setWidget(this.rowInModification, 4, 
				this.btnModifyPanel);
		
		
		this.onResize();
	}
	
	/**
	 * Set different modify fields with specific value
	 * @param tagName : Name of the tag.
	 * @param shortLib : the short name of the tag
	 * @param description : little description of the tag and his impact
	 * @param isTextual : tag can be textual or not
	 */
	private void setModifyFields(String tagName, String ShortLib,
			String description, boolean textual) {
		//save alt vamue
		this.currentTagName = tagName;
		this.currentShortLib = ShortLib;
		this.currentDescription = description;
		this.currentTagTextuel = textual;
		
		this.modifyTagName.setText(tagName);
		this.modifyShortLib.setText(ShortLib);
		this.modifyDescription.setText(description);
		this.modifyTagTextuel.setValue(textual);
	}
	
	public HasClickHandlers clickOnApplyModif() {
		return this.applyModification;
	}
	
	public HasClickHandlers clickOnCancelModif() {
		return this.cancelModification;
	}
	
	public HasClickHandlers clickOnAddNewTag() {
		return this.addTag;
	}
	
	public void deleteLine(Element clicSrc){
		clicSrc.getParentNode().getParentNode().getParentNode().removeFromParent();
		this.onUnload();
		this.onResize();
	}
	
	public void showAddLine(){
		// if whe are in add state, field are already displayed
		if(!this.inAddState) {
			this.clearAddField();
			if(tagTable.getRowCount() != 1)
				this.tagTable.insertRow(1);
			this.tagTable.setWidget(1, 0, this.newTagName);
			this.tagTable.setWidget(1, 1, this.newShortLib);
			this.tagTable.setWidget(1, 2, this.newDescription);
			this.tagTable.setWidget(1, 3, this.newtagTextuel);
			this.tagTable.setWidget(1, 4, this.btnAddPanel);
			
			this.inAddState = true;
			this.onResize();
		}
	}
	
	public void hideAddLine(){
		if(this.inAddState){
			this.tagTable.removeRow(1);
			this.inAddState = false;
			this.onResize();
		}
	}
	
	private void clearAddField(){
		this.newTagName.setText("");
		this.newShortLib.setText("");
		this.newDescription.setText("");
		this.newtagTextuel.setValue(false);
	}
}
