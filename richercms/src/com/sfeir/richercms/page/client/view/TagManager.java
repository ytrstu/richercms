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
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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
	private LayoutPanel addTagPanel;
	private Button addNewTag;
	private int rowInModification = -1;
	
	//add tag
	private TextBox newTagName;
	private TextBox newShortLib;
	private TextArea newDescription;
	private CheckBox tagTextuel;
	
	//modify tag
	private TextBox modifyTagName;
	private TextBox modifyShortLib;
	private TextArea modifyDescription;
	private CheckBox modifyTagTextuel;
	private Image applyModification;
	private Image cancelModification;
	private FlowPanel btnModifyPanel;
	
	private Panel currentBtnPanel; // save the current BtnPanel during the modification step
	private Image currentModifyBtn;
	private Image currentDeleteBtn;
	private String currentTagName;
	private String currentShortLib;
	private String currentDescription;
	private boolean currentTagTextuel;
	
	// Scroll tab if table vas too tall
	private ScrollPanel verticalScrollTag;
	// contain verticalScrollTag : allow to place verticalScrollTag in center
	private LayoutPanel TagContainer; 

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
		
		//modify field
		this.modifyTagName = new TextBox();
		this.modifyShortLib = new TextBox();
		this.modifyDescription = new TextArea();
		this.modifyDescription.setVisibleLines(3);
		this.modifyTagTextuel = new CheckBox(" tag textuel");
		this.applyModification = new Image("tab_images/trans.png");
		this.applyModification.addStyleName("ApplyStyle");
		this.cancelModification = new Image("tab_images/trans.png");
		this.cancelModification.addStyleName("deleteStyle");
		this.btnModifyPanel = new FlowPanel();
		this.btnModifyPanel.add(applyModification);
		this.btnModifyPanel.add(cancelModification);
		
		//addTag
		this.addTagPanel = new LayoutPanel();
		this.addTagPanel();
		this.mainContainer.addSouth(this.addTagPanel, 300);
		
		this.verticalScrollTag = new ScrollPanel();
		verticalScrollTag.setWidget(this.tagTable);
		this.verticalScrollTag.setWidth(this.tagTable.getOffsetWidth()+20+"px");
		
		this.TagContainer = new LayoutPanel();
		this.TagContainer.add(this.verticalScrollTag );
		
		this.mainContainer.add(this.TagContainer);
		
		this.initWidget(this.mainContainer);
	}
	
	public void onResize() {
		super.onResize();
		
		//////////////// RESIZE ////////////////
		if(Window.getClientWidth() < this.verticalScrollTag.getOffsetWidth())

			this.verticalScrollTag.setWidth(Window.getClientWidth()+"px");
			
		else if(this.verticalScrollTag.getOffsetWidth() < Window.getClientWidth()) {
			this.verticalScrollTag.setWidth(Window.getClientWidth()+"px");
			
			if((this.tagTable.getOffsetWidth()+20) < this.verticalScrollTag.getOffsetWidth())
				this.verticalScrollTag.setWidth(this.tagTable.getOffsetWidth()+20+"px");
		}
		else
			this.verticalScrollTag.setWidth(Window.getClientWidth()+"px");
		
		//////////////// CENTER ////////////////
		int tagPadding = (Window.getClientWidth() - this.verticalScrollTag.getOffsetWidth())/2;
		this.TagContainer.setWidgetLeftRight(verticalScrollTag, tagPadding, Unit.PX, tagPadding, Unit.PX);
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
		this.tagTable.setText(0, 0, "Nom du tag");
		this.tagTable.setText(0, 1, "Libellé court");
		this.tagTable.setText(0, 2, "Description");
		this.tagTable.setText(0, 3, "tag textuel");
		this.tagTable.setText(0, 4, "Action");
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
		this.tagTextuel = new CheckBox(" tag textuel");
		newTagTable.setWidget(0, 1, this.newTagName);
		newTagTable.setWidget(1, 1, this.newShortLib);
		newTagTable.setWidget(2, 1, this.newDescription);
		newTagTable.setWidget(3, 1, this.tagTextuel);
		
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
			this.tagTable.setText(numRow, 3, "oui");
		else
			this.tagTable.setText(numRow, 3, "non");
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
		return this.tagTextuel.getValue();
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
		this.tagTextuel.setValue(false);
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
				this.tagTable.setWidget(this.rowInModification, 4, 
						this.currentBtnPanel);
			}else{
				this.tagTable.setText(this.rowInModification, 0, 
						this.getModifyTagName());
				this.tagTable.setText(this.rowInModification, 1,
						this.getModifyShortLib());
				this.tagTable.setText(this.rowInModification, 2, 
						this.getModifyDescription());
				this.tagTable.setWidget(this.rowInModification, 4, 
						this.currentBtnPanel);
			}
		}
		
		this.rowInModification = -1;
		this.currentBtnPanel= null;
		
		this.onResize();
	}
	
	public void DisplayModifyFields(int row) {
		//hide modify fields if it was use in an other line
		this.hideModifyFields(false);
		//display in the good line
		this.rowInModification = row;
		
		setModifyFields(this.tagTable.getText(row, 0),
				this.tagTable.getText(row, 1),
				this.tagTable.getText(row, 2),
				false);
		
		this.tagTable.setWidget(row, 0, this.modifyTagName);
		this.tagTable.setWidget(row, 1, this.modifyShortLib);
		this.tagTable.setWidget(row, 2, this.modifyDescription);
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
	
	public void deleteLine(Element clicSrc){
		clicSrc.getParentNode().getParentNode().getParentNode().removeFromParent();
	}
}
