package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

/**
 * Allows the presenter to communicate with the view
 * TagManager <=> TagManagerPresenter
 * @author homberg.g
 *
 */
public interface ITagManager extends LazyView {
	
	Widget asWidget();
	
	/**
	 * Add one tage in the display Table
	 * @param tagName : Name of the tag.
	 * @param shortLib : the short name of the tag
	 * @param description : little description of the tag and his impact
	 * @param isTextual : tag can be textual or not
	 * @return the line number
	 */
	int addLine(String tagName, String shortLib, String description, boolean isTextual);
	
	/**
	 * Handle click on the modify Btn in the current line
	 * call this method after an addline to handle click on this button
	 * @return Event
	 */
	HasClickHandlers getCurModifyClick();
	
	/**
	 * Handle click on the delete Btn in the current line
	 * call this method after an addline to handle click on this button
	 * @return Event
	 */
	HasClickHandlers getCurDeleteClick();
	
	/**
	 * Handle click on the applyModification
	 * @return Event
	 */
	HasClickHandlers clickOnApplyModif();
	
	/**
	 * Handle click on the CancelModification
	 * @return Event
	 */
	HasClickHandlers clickOnCancelModif();
	
	/**
	 * Handle clic on "add new tag" button.
	 * @return EventHandler
	 */
	HasClickHandlers clickOnAddTag();
	
	/**
	 * Handle clic on "cancel" button.
	 * @return EventHandler
	 */
	HasClickHandlers clickOnCancelAddTag();
	
	/**
	 * Handle click on the add new tag button
	 * After this click we can display de addTagpopup
	 * @return
	 */
	HasClickHandlers clickOnAddNewTag();
	
	/**
	 * Return value of the newTagName field
	 * @return value
	 */
	String getNewTagName();

	/**
	 * Return value of the newShortLib field
	 * @return value
	 */
	String getNewShortLib();
	
	/**
	 * Return value of the newDescription field
	 * @return value
	 */
	String getNewDescription();
	
	/**
	 * @return true if the tag is textual, false either
	 */
	boolean isTextual();
	
	/**
	 * Return value of the modify TagName field
	 * @return value
	 */
	String getModifyTagName();

	/**
	 * Return value of the modify ShortLib field
	 * @return value
	 */
	String getModifyShortLib();
	
	/**
	 * Return value of the modify Description field
	 * @return value
	 */
	String getModifyDescription();
	
	/**
	 * Modify value of the textual fields
	 * @return true if the tag is textual, false either
	 */
	boolean isModifyTextual();
	
	/**
	 * Clear the tag table
	 */
	void clearTagTable();
	
	/**
	 * Clear all modify fields
	 */
	void clearModifyFields();
	
	/**
	 * hide modification fields and display normal fields
	 * @param restoreValue : true : restore alt value, false : apply modification
	 */
	void hideModifyFields(boolean restoreValue);
	
	/**
	 * display modify field in a specific line
	 * @param row : line number
	 */
	void DisplayModifyFields(int row);
	
	/**
	 * Remove one line in the tagTable
	 * @param clicSrc : the widget source of the clickEvent.
	 */
	void deleteLine(Element clicSrc);
	
	/**
	 * Show the add line in tagTable
	 */
	void showAddLine();
	
	/**
	 * Hide the add line in tagTable
	 */
	void hideAddLine();
	
	PageConstants getConstants();
}
