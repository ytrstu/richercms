package com.sfeir.richercms.wizard.client.Interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.wizard.client.wizardConfigConstants;

/**
 * Allows the presenter to communicate with the view
 * Page2View <=> Page2Presenter
 * @author homberg.g
 */
public interface IdisplayPage2 extends LazyView {
	
	/**
	 * get the handler of the button Next
	 * @return The handler of the button Next
	 */
	HasClickHandlers getNextButton();
	
	/**
	 * get the handler of the button Previous
	 * @return The handler of the button Previous
	 */
	HasClickHandlers getPreviousButton();
	
	/**
	 * get the handler of the button Add
	 * @return The handler of the button Add
	 */
	HasClickHandlers getAddButton();
	
	
	/**
	 * get the handler of the button Ok on the PopUp
	 * @return The handler of the button Ok on the PopUp
	 */
	HasClickHandlers getPopUpBtnOk();
	
	/**
	 * get the handler of the button Cancel on the PopUp
	 * @return The handler of the button Cancel on the PopUp
	 */
	HasClickHandlers getPopUpBtnCancel();
	
	
	/**
	 * Add a Language in the table
	 * @param langue 
	 * @param checked true if the language is selected.
	 * @return the delete Button for bind Event;
	 */
	HasClickHandlers addLanguage(String langue, String tag, boolean checked);
	
	/**
	 * Get the default language selected (id = position in the table)
	 * @return the default language selected
	 */
	int getSelectedLanguage();
	
	
	/**
	 * check the language with the ID
	 * @param id position in the table
	 */
	void setSelectedLanguage(int id);
	
	/**
	 * Return the name of the new language entered by the user in the popup
	 * @return The string entered in the popUp
	 */
	String getPopUpNewLanguage();
	
	/**
	 * Return the tag of the new language entered by the user in the popup
	 * @return The string entered in the popUp
	 */
	String getPopUpNewTag();
	
	/**
	 * Show the Add Language PopUp
	 */
	void showPopUpAddLanguage();
	
	/**
	 * Hide the Add Language PopUp
	 */
	void hidePopUpAddLanguage();
		
	/**
	 * Show the PopUpWait
	 */
	void showPopUpWait();
	
	/**
	 * Hide the PopUpWait
	 */
	void hidePopUpWait();
	
	/**
	 * Clear the table of languages
	 */
	void clearTableLanguage();
	
	Widget asWidget();
	
	/**
	 * the number of the last line.
	 * this method subtract the titleRow.
	 * Start at 0.
	 * -1 if any language has been added.
	 * @return the number of line : represent the id of the language in the list.
	 */
	int getCurrentNumRow();
	
	/**
	 * Needed to set messages with the needed language.
	 * @return the wizardConfigConstants for setting language
	 */
	wizardConfigConstants getConstant();
}
