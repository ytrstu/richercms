package com.sfeir.richercms.page.client.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

public interface ITagManager extends LazyView {
	
	Widget asWidget();
	
	/**
	 * Add one tage in the display Table
	 * @param tagName : Name of the tag.
	 * @param shortLib : the short name of the tag
	 * @param description : little description of the tag and his impact
	 * @param isTextual : tag can be textual or not
	 * @return the ClickHandlers on the DeleteBtn associate with a tag
	 */
	HasClickHandlers addLine(String tagName, String shortLib, String description, boolean isTextual);
	
	/**
	 * Handle clic on "add new tag" button.
	 * @return EventHandler
	 */
	HasClickHandlers clickOnAddTag();
	
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
	 * Clear the tag table
	 */
	void clearTagTable();
	
	/**
	 * Clear all field needed to add a new tag
	 */
	void clearAddNewTagTextBox();
	
	PageConstants getConstants();
}
