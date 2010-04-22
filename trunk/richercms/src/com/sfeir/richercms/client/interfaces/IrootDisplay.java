package com.sfeir.richercms.client.interfaces;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Allows the Root presenter to communicate with the Root view
 * @author homberg.g
 */
public interface IrootDisplay 
{
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
