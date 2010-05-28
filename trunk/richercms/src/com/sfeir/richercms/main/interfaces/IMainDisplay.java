package com.sfeir.richercms.main.interfaces;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * Allows the Main presenter to communicate with the Main view
 * @author homberg.g
 */
public interface IMainDisplay extends LazyView {

	/**
	 * Return the rootLayoutPanel, where is displayed differents view
	 * @return the rootLayoutPanel
	 */
	public Panel getBody();
	
	/**
	 * Return the Main Composite widget where the rootLayoutPanel is attached. 
	 * @return a Composite where the rootLayoutPanel is attached
	 */
	public Widget getViewWidget();
	
	public Widget asWidget();
	
	/**
	 * add a css style thanks to its name
	 * @param styleName : name of the style in css File
	 */
	public void AddStyle(String styleName);
	
	/**
	 * remove a css style thanks to its name
	 * @param styleName : name of the style in css File
	 */
	public void RemoveStyle(String styleName);
	
	/**
	 * Show the PopUpWait
	 */
	void showPopUpWait();
	
	/**
	 * Hide the PopUpWait
	 */
	void hidePopUpWait();
}
