package com.sfeir.richercms.page.client.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.view.custom.PopUpAddTemplate;

public class TemplateManager extends ResizeComposite implements ITemplateManager, HasKeyPressHandlers{

	private DockLayoutPanel mainContainer;
	private Button applyTag;
	private ListBox listTemplate;
	private Image addTemplate;
	private Image deleteTemplate;
	private Image modifyTemplate;
	private FlexTable tagTable;
	
	// Scroll tab if table vas too tall
	private ScrollPanel tagContainer;
	private LayoutPanel contentPanel; 
	private LayoutPanel templateContainer;
	
	private PopUpAddTemplate popUpAddTemplate;
	private HashMap<Long,CheckBox> selectedTags; // idTag, associated CheckBox
	
	public Widget asWidget() {
		return this;
	}

	public void createView() {
		this.selectedTags = new HashMap<Long,CheckBox>();
		this.mainContainer = new DockLayoutPanel(Unit.PX);
		this.popUpAddTemplate = new PopUpAddTemplate();
		
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
		this.addTemplate = new Image("tab_images/trans.png");
		this.addTemplate.setTitle("Ajouter un nouveau template");
		this.addTemplate.addStyleName("addStyle");
		this.deleteTemplate = new Image("tab_images/trans.png");
		this.deleteTemplate.setTitle("Supprimer le template sélectioné");
		this.deleteTemplate.addStyleName("deleteStyle");
		this.modifyTemplate = new Image("tab_images/trans.png");
		this.modifyTemplate.setTitle("modifier le template sélectioné");
		this.modifyTemplate.addStyleName("modifyStyle");
		
		this.templateContainer = new LayoutPanel();
		this.templateContainer.setWidth(330+"px");
		this.templateContainer.add(listTitle);
		this.templateContainer.setWidgetLeftWidth(listTitle, 0, Unit.PX, 120, Unit.PX);
		this.templateContainer.setWidgetVerticalPosition(listTitle, Alignment.BEGIN);
		this.templateContainer.add(this.listTemplate);
		this.templateContainer.setWidgetLeftWidth(this.listTemplate, 120, Unit.PX, 120, Unit.PX);
		this.templateContainer.setWidgetVerticalPosition(this.listTemplate, Alignment.BEGIN);
		this.templateContainer.add(this.addTemplate);
		this.templateContainer.setWidgetLeftWidth(this.addTemplate, 240, Unit.PX, 22, Unit.PX);
		this.templateContainer.setWidgetVerticalPosition(this.addTemplate, Alignment.BEGIN);
		this.templateContainer.add(this.modifyTemplate);
		this.templateContainer.setWidgetLeftWidth(this.modifyTemplate, 270, Unit.PX, 22, Unit.PX);
		this.templateContainer.setWidgetVerticalPosition(this.modifyTemplate, Alignment.BEGIN);
		this.templateContainer.add(this.deleteTemplate);
		this.templateContainer.setWidgetLeftWidth(this.deleteTemplate, 300, Unit.PX, 22, Unit.PX);
		this.templateContainer.setWidgetVerticalPosition(this.deleteTemplate, Alignment.BEGIN);
		
		
		
		//tag Selection
		Label tagtitle = new Label("tag possible pour ce template");
		tagtitle.setStyleName("informationTitle");
		this.tagTable = new FlexTable();
		this.tagTable.addStyleName("tagTable");
		this.tagTable.setCellPadding(5);
		this.tagTable.setBorderWidth(2);
		this.addTagTableTitle();
		
		this.tagContainer = new ScrollPanel();
		tagContainer.setWidget(this.tagTable);
		this.tagContainer.setWidth(400+"px");

		this.contentPanel = new LayoutPanel();
		this.contentPanel.add(this.templateContainer);
		this.contentPanel.add(tagtitle);
		this.contentPanel.setWidgetTopHeight(tagtitle, 70, Unit.PX, 130, Unit.PX);
		this.contentPanel.add(this.tagContainer);
		this.contentPanel.setWidgetTopBottom(this.tagContainer, 140, Unit.PX, 60, Unit.PX);
		
		this.mainContainer.add(this.contentPanel);
		
		this.initWidget(this.mainContainer);
	}
	
	public void onResize() {
		super.onResize();
		
		//////////////// RESIZE ////////////////
		if(Window.getClientWidth() < this.tagContainer.getOffsetWidth())

			this.tagContainer.setWidth(Window.getClientWidth()+"px");
			
		else if(this.tagContainer.getOffsetWidth() < Window.getClientWidth()) {
			this.tagContainer.setWidth(Window.getClientWidth()+"px");
			
			if((this.tagTable.getOffsetWidth()+20) < this.tagContainer.getOffsetWidth())
				this.tagContainer.setWidth(this.tagTable.getOffsetWidth()+20+"px");
		}
		else
			this.tagContainer.setWidth(Window.getClientWidth()+"px");
		
		//////////////// CENTER ////////////////
		int tagPadding = (Window.getClientWidth() - this.tagContainer.getOffsetWidth())/2;
		this.contentPanel.setWidgetLeftRight(tagContainer, tagPadding, Unit.PX, tagPadding, Unit.PX);
		
		int templatePadding = (Window.getClientWidth() - this.templateContainer.getOffsetWidth())/2;
		this.contentPanel.setWidgetLeftRight(this.templateContainer, templatePadding, Unit.PX, templatePadding, Unit.PX);
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
	
	public HasValueChangeHandlers<Boolean> addTagLine(String id,String TagName,
			String shortLib, String description, boolean textualTag ) {
		
		int numRow = this.tagTable.getRowCount();
		CheckBox selectTag = new CheckBox();
		selectTag.setFormValue(id);
		this.selectedTags.put(new Long(id), selectTag);
		
		this.tagTable.setWidget(numRow, 0, selectTag);
		this.tagTable.setText(numRow, 1, TagName);
		this.tagTable.setText(numRow, 2, shortLib);
		this.tagTable.setText(numRow, 3, description);
		
		if(textualTag)
			this.tagTable.setText(numRow, 4, "yes");
		else
			this.tagTable.setText(numRow, 4, "no");
		
		onResize();
		
		return selectTag;
	}
	
	public List<Long> getSelectedTagsId() {
		ArrayList<Long> lst = new ArrayList<Long>();
		
		for(CheckBox cb : this.selectedTags.values())
			if(cb.getValue())//take just checked tag
				lst.add(new Long(cb.getFormValue()));

		return lst;
	}
	
	public HasClickHandlers getBtnAddClick() {
		return this.addTemplate;
	}
	
	public HasClickHandlers getBtnDelClick() {
		return this.deleteTemplate;
	}
	
	public HasClickHandlers getBtnModifyClick() {
		return this.modifyTemplate;
	}
	
	public HasClickHandlers getBtnApplyTagClick() {
		return this.applyTag;
	}
	
	public HasClickHandlers getPopUpBtnOk() {
		return this.popUpAddTemplate.ok;
	}
	
	public HasClickHandlers getPopUpBtnCancel() {
		return this.popUpAddTemplate.cancel;
	}
	
	public HasKeyPressHandlers getPopUpKbEvent() {
		return this.popUpAddTemplate.getKeyboardEvt();
	}

	public void showPopUpAddTemplate() {
			
		// on vide les champs avant de re-afficher
		this.popUpAddTemplate.clearField();
		this.popUpAddTemplate.show();
	}

	public void hidePopUpAddTemplate() {
		this.popUpAddTemplate.hide();
	}

	
	public String getPopUpNewTempName() {
		return this.popUpAddTemplate.tbName.getText();
	}
	
	public String getPopUpNewTempDesc() {
		return this.popUpAddTemplate.taDesc.getText();
	}
	
	public void setPopUpNewTempName(String name) {
		this.popUpAddTemplate.tbName.setText(name);
	}
	
	public void setPopUpNewTempDesc(String description) {
		this.popUpAddTemplate.taDesc.setText(description);
	}
	
	public void addTemplateInList(String name, String id) {
		this.listTemplate.addItem(name, id);
	}

	public void clearTemplateList() {
		this.listTemplate.clear();
	}
	
	public void clearTagTable(){
		this.tagTable.removeAllRows();
		this.addTagTableTitle();
	}
	
	public HasChangeHandlers getTemplateLstSelection() {
		return this.listTemplate;
	}
	
	public Long getSelectedTemplateId() {
		if(this.listTemplate.getItemCount() != 0)
			return new Long(this.listTemplate.getValue(
					this.listTemplate.getSelectedIndex()));
		return null;
	}
	
	public void unCheckAllTags() {
		for(CheckBox cb : this.selectedTags.values())
			cb.setValue(false);
	}
	
	public void checktag(Long tagId) {
		this.selectedTags.get(tagId).setValue(true);
	}
	
	public void enableApplyTagBtn() {
		this.applyTag.setEnabled(true);
	}
	
	public void disableApplyTagBtn() {
		this.applyTag.setEnabled(false);
	}
	
	public void deleteSelectedTemplate() {
		this.listTemplate.removeItem(this.listTemplate.getSelectedIndex());
	}
	
	public void changeSelectedTagName(String name){
		this.listTemplate.setItemText(this.listTemplate.getSelectedIndex(), name);
	}

	public HasKeyPressHandlers getKeyPressEvent(){
		return this;
	}
	
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return addDomHandler(handler, KeyPressEvent.getType());
	}
}
