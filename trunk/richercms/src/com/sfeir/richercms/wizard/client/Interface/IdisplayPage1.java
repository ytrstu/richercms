package com.sfeir.richercms.wizard.client.Interface;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * Allows the presenter to communicate with the view
 * Page1View <=> Page1Presenter
 * @author homberg.g
 */
public interface IdisplayPage1 extends LazyView{
	
	/**
	 * get the handler of the button Next
	 * @return The handler of the button Next
	 */
    HasClickHandlers getNextButton();
    
	/**
	 * get the handler of the List SelectedLanguage
	 * @return The handler of List SelectedLanguage
	 */
    HasChangeHandlers getSelectedLanguage();
    
    /***
     * return the Index of the selected Language
     * @return Index of the selected Language
     */
    int getIndexLanguage();
    
    /**
     * Modify the selected Language by his index
     * @param idLanguage : New Index
     */
    void setSelectedLanguage(int idLanguage);
    
    Widget asWidget();
   
}
