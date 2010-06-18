package com.sfeir.richercms.page.client.interfaces;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IUserManager extends LazyView {

	Widget asWidget();
	
	/**
	 * Add a new user in the table but warning :
	 * You need to call addLockedPage and addAdminWidget if you need
	 * to display all information in table.
	 * And you do call this 2 method before recall addLine
	 * @param email
	 * @param nickName 
	 * @param state
	 */
	public void addLine(String email, String nickName, String state);
	
	/**
	 * add one locked page in currentLine
	 * This method can be call many times in a same line
	 * @param a locked page
	 */
	public HasClickHandlers addLockedPage(Long pageId, String pagename);
	
	/**
	 * add admin radioButton in currentLine
	 * @param admin : true if this user is an admin, false either
	 */
	public void addAdminWidget(boolean admin);
	
	/**
	 * clear user Table, and rebuild the title Line
	 */
	public void clearUserTable();
	
	/**
	 * Clear content of the new Email TextBox 
	 */
	public void clearAddUserTextBox();
	
	/**
	 * Allows the presenter to handle click event on the Add new User button
	 * @return the Event
	 */
	public HasClickHandlers onAddNewUserClick();
	
	/**
	 * Get the content of the new Email TextBox
	 * @return the content of the field
	 */
	public String getNewEmail();
}
