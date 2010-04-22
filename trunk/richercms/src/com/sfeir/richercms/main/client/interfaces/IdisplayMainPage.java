package com.sfeir.richercms.main.client.interfaces;

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
	 * Allows the presenter to communicate with the NavigationPanel
	 * @return NavigationPanel
	 */
	INavigationPanel getNavigationPanel();
	
	/**
	 * Allows the presenter to communicate with the InformationPanel
	 * @return InformationPanel
	 */
	IInformationPanel getInformationPanel();
	
	/**
	 * Allows the presenter to communicate with the ValidationPanel
	 * Containing the add button and the cancel button.
	 * @return ValidationPanel
	 */
	IValidationPanel getValidationPanel();
	
	/**
	 * Allows the presenter to communicate with the TinyEditor
	 * @return TinyMCEPanel
	 */
	ITinyMCEPanel getTinyMCEPanel();
}
