package wizardConfig.client.Interface;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

/**
 * Allows the presenter to communicate with the view
 * Page2View <=> Page2Presenter
 * @author homberg.g
 */
public interface IdisplayPage2 {
	
	/**
	 * get the handler of the button Next
	 * @return The handler of the button Next
	 */
	HasClickHandlers getNextButton();
	
	/**
	 * get the handler of the button Add
	 * @return The handler of the button Add
	 */
	HasClickHandlers getAddButton();
	
	/**
	 * get the handler of the button Delete
	 * @return The handler of the button Delete
	 */
	HasClickHandlers getDelButton();
	
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
	

	void setLanguageList(ArrayList<String> langues);
	void setLanguageTable(ArrayList<String> langues);
	
	/**
	 * Add a Language in the table
	 * @param langue 
	 * @param checked true if the language is selected.
	 */
	void addLanguage(String langue, boolean checked);
	
	/**
	 * Get the List of selected IDs (id = position in the table)
	 * @return List of selected IDs in the table
	 */
	List<Integer> getSelectedLanguage();
	
	
	/**
	 * check the language with the ID
	 * @param id position in the table
	 */
	void setSelectedLanguage(int id);
	
	/**
	 * Return the string entered by the user in the popup
	 * @return The string entered in the popUp
	 */
	String getPopUpNewLanguage();
	
	
	/**
	 * Show the Add Language PopUp
	 */
	void showPopUpAddLanguage();
	
	/**
	 * Hide the Add Language PopUp
	 */
	void hidePopUpAddLanguage();
	
	/**
	 * Clear the table of languages
	 */
	void clearTableLanguage();
	
	Widget asWidget();
}
