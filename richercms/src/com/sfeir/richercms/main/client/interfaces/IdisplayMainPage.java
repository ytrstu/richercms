package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;


/**
 * Allows the presenter to communicate with the view
 * MainPage <=> MainPagePresenter
 * @author homberg.g
 *
 */
public interface IdisplayMainPage extends LazyView {

	Widget asWidget();
	
	/**
	 * Load the NavigationPanel in the right place Panel
	 * @param navPanel
	 */
	public void setNavPanel(INavigationPanel navPanel);

	/**
	 * Load the InformationPanel in the right place Panel
	 * @param listPanel
	 */
	public void setInfoPanel(IInformationPanel listPanel);

	/**
	 * Load the TinyMCEPanel in the right place Panel
	 * @param tinyMcePanel
	 */
	public void setTinyMcePanel(ITinyMCEPanel tinyMcePanel);

	/**
	 * Load the ValidationPanel in the right place Panel
	 * @param validationPanel
	 */
	public void setValidationPanel(IValidationPanel validationPanel);
	
	/**
	 * Add a language in the listBox
	 * @param name : the display name of the language 
	 * @param key : the key, needed to retrive the page translation in the dataBase
	 * @param defaultLg : if is set : the language was select by default and a string is added in the name to specify it.
	 */
	public void addLanguageInListBox(String name, String key, boolean defaultLg);
	
	/**
	 * When a new language is selected in the listBox
	 * @return the Event
	 */
	public HasChangeHandlers onChangeSelectedLg();
	
	/**
	 * Return the key of the language, its the key of the associated language in the datastore.
	 * @return the key of the selected Language
	 */
	public String getKeyOfSelectedLg();

}
