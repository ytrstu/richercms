package com.sfeir.richercms.page.client.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
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
import com.sfeir.richercms.page.client.view.custom.PopUpAddTemplate;

public class TemplateManager extends ResizeComposite implements ITemplateManager {

	private DockLayoutPanel mainContainer;
	private Button applyTag;
	private ListBox listTemplate;
	private Button addTemplate;
	private FlexTable tagTable;
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
		this.tagTable.addStyleName("tagTable");
		this.tagTable.setCellPadding(5);
		this.tagTable.setBorderWidth(2);
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
	
	public String getSelectedTemplateId() {
		return this.listTemplate.getValue(
				this.listTemplate.getSelectedIndex());
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
}
