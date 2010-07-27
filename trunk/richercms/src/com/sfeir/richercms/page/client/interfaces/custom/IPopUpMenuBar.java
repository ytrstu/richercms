package com.sfeir.richercms.page.client.interfaces.custom;

import com.google.gwt.user.client.Command;

/**
 * Allows a presenter to communicate with the popUp
 * @author homberg.g
 *
 */
public interface IPopUpMenuBar {

	/**
	 * Set the popUp position
	 * @param left : range in px
	 * @param top : range in px
	 */
	void setPopupPosition(int left, int top);
	
	/**
	 * Set the add page command
	 * @param cmd : the command
	 */
	void setAddPageCommand(Command cmd);

	/**
	 * Set the delete page command
	 * @param cmd : the command
	 */
	void setDelPageCommand(Command cmd);
	
	/**
	 * Set the modify page command
	 * @param cmd : the command
	 */
	void setModifyPageCommand(Command cmd);
	
	/**
	 * Set the up page command
	 * @param cmd : the command
	 */
	void setUpPageCommand(Command cmd);
	
	/**
	 * Set the down page command
	 * @param cmd : the command
	 */
	void setDownPageCommand(Command cmd);
	
	/**
	 * Set the reorder page command
	 * @param cmd : the command
	 */
	void setReorderPagesCommand(Command cmd);
	
	/**
	 * Set the Image manager command
	 * @param cmd : the command
	 */
	void setManageImagesCommand(Command cmd);
	
	/**
	 * show the popUp
	 */
	void show();
	
	/**
	 * hide the popUp
	 */
	void hide();
}
