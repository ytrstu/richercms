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
	 * @return the ClickHandlers on the DeleteBtn associate with a tag
	 */
	HasClickHandlers addLine(String tagName, String shortLib, String description);
	
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
	 * Clear the tag table
	 */
	void clearTagTable();
	
	/**
	 * Clear all field needed to add a new tag
	 */
	void clearAddNewTagTextBox();
	
	PageConstants getConstants();
}
