package com.sfeir.richercms.page.client.interfaces;

import java.util.List;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface ITemplateManager  extends LazyView {

	Widget asWidget();
	
	/**
	 * Handle clickEvent on the add
	 * new template Button
	 * @return Event
	 */
	HasClickHandlers getBtnAddClick();
	
	/**
	 * Handle clickEvent on the delete
	 * selected template button
	 * @return Event
	 */
	HasClickHandlers getBtnDelClick();
	
	/**
	 * Handle clickEvent on the modify
	 * selected template button
	 * @return Event
	 */
	HasClickHandlers getBtnModifyClick();
	
	/**
	 * Handle ClickEvent on the tag modification apply button
	 * @return Event
	 */
	HasClickHandlers getBtnApplyTagClick();
	
	/**
	 * Handle ClickEvent on ok Button 
	 * in the Add template Popup
	 * @return Event
	 */
	HasClickHandlers getPopUpBtnOk();
	
	/**
	 * Handle ClickEvent on  cancel button
	 * in the add template popUp
	 * @return Event
	 */
	HasClickHandlers getPopUpBtnCancel();
	
	/**
	 * Handle KeyPressEvent on the PopUp
	 * @return Event
	 */
	HasKeyPressHandlers getPopUpKbEvent();
	
	/**
	 * Handle the Change event on the Template list
	 * @return Event
	 */
	HasChangeHandlers getTemplateLstSelection();
	
	/**
	 * Handle the KeyPress event
	 * @return Event
	 */
	HasKeyPressHandlers getKeyPressEvent();

	/**
	 * Show the add template PopUp
	 */
	void showPopUpAddTemplate();

	/**
	 * Hide the add template PopUp
	 */
	void hidePopUpAddTemplate();
	
	/**
	 * Get the Template's name,
	 * entered in the popUp
	 * @return template's name
	 */
	String getPopUpNewTempName();
	
	/**
	 * Get the Template's description
	 * entered in the popUp
	 * @return template's Description
	 */
	String getPopUpNewTempDesc();
	
	/**
	 * Set the Template's name,
	 * entered in the popUp
	 * @param name : template's name
	 */
	void setPopUpNewTempName(String name);
	
	/**
	 * Set the Template's description
	 * entered in the popUp
	 * @param description : template's Description
	 */
	void setPopUpNewTempDesc(String description);
	
	/**
	 * Add a new line into the TemplateList
	 * @param name : Template's name
	 * @param id : template's id
	 */
	void addTemplateInList(String name, String id);
	
	/**
	 * Clear template list
	 */
	void clearTemplateList();
	
	/**
	 * Clear tag table
	 */
	void clearTagTable();
	
	/**
	 * Get template selected in the list.
	 * @return template's id 
	 */
	Long getSelectedTemplateId();
	
	/**
	 * Add a tag in table.
	 * @param id : tag's id
	 * @param TagName : tag's name
	 * @param shortLib : tag's short libelle
	 * @param description : tag's decription
	 * @param textualTag : if its a textual tag or not
	 * @return ValueChange handler on the checkBox. If you want handle this event
	 * (for example detect modification)
	 */
	HasValueChangeHandlers<Boolean> addTagLine(String id,String TagName, String shortLib,
			String description, boolean textualTag );
	
	/**
	 * Get all tag selected in Tag's table
	 * @return a list who contain all selected tags
	 */
	List<Long> getSelectedTagsId();
	
	/**
	 * Uncheck all tag in table
	 */
	void unCheckAllTags();
	
	/**
	 * Check a specific tag into table
	 * @param tagId
	 */
	void checktag(Long tagId);
	
	/**
	 * Enable the tag selection apply button
	 */
	void enableApplyTagBtn();
	
	/**
	 * Disable the tag selection apply button
	 */
	void disableApplyTagBtn();
	
	/**
	 * Delete selected template into the list
	 */
	void deleteSelectedTemplate();
	
	/**
	 * Change selected template's name into the list
	 */
	void changeSelectedTagName(String name);
}
