package com.sfeir.richercms.page.client.interfaces;

import java.util.Date;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.PageConstants;

public interface IInformationPanel extends LazyView {
	
	Widget asWidget();
	
	/**
	 * Deasable all widgets includes in the Panel
	 */
	void deasabledWidgets();
	
	/**
	 * Enable all widgets includes in the Panel
	 */
	void enabledWidgets();
	
	/**
	 * Clear all field in the display
	 */
	void clearFields();

	/**
	 * Return the Browser Title of the new webPage
	 * @return content of the field browserTitle
	 */
	String getBrowserTitle();

	/**
	 * Return the Page Title of the new webPage
	 * @return content of the field pageTitle
	 */
	String getPageTitle();

	/**
	 * Return the Url Name of the new webPage
	 * @return content of the field urlName
	 */
	String getUrlName();

	/**
	 * Return the new WebPage description
	 * @return content of the field description
	 */
	String getDescription();

	/**
	 * Return the new WebPage Key word
	 * @return content of the field keyWord
	 */
	String getKeyWord();

	/**
	 * Return the date of the first publication of this new Page
	 * @return content of the field PublicationStart
	 */
	Date getPublicationStart();

	/**
	 * Return the date of the Last publication of this new Page
	 * @return content of the field PublicationFinish
	 */
	Date getPublicationFinish();
	
	/**
	 * Set the Browser Title of the new webPage
	 */
	void setBrowserTitle(String browserTitle);

	/**
	 * Set the Page Title of the new webPage
	 * @param content of the field pageTitle
	 */
	void setPageTitle(String pageTitle);

	/**
	 * Set the Url Name of the new webPage
	 * @param content of the field urlName
	 */
	void setUrlName(String urlName);

	/**
	 * Set the new WebPage description
	 * @param content of the field description
	 */
	void setDescription(String description);

	/**
	 * Set the new WebPage Key word
	 * @param content of the field keyWord
	 */
	void setKeyWord(String keyWord);

	/**
	 * Set the date of the first publication of this new Page
	 * @param content of the field PublicationStart
	 */
	void setPublicationStart(Date publicationStart);

	/**
	 * Set the date of the Last publication of this new Page
	 * @param content of the field PublicationFinish
	 */
	void setPublicationFinish(Date publicationFinish);
	
	/**
	 * Set the content of each label using for help during a translation
	 * @param browserTitle : content of default translation
	 * @param description : content of default translation
	 * @param keyWord : content of default translation
	 * @param pageTitle : content of default translation
	 * @param urlName : content of default translation
	 */
	public void setHelp(String browserTitle, String description, String keyWord, 
			String pageTitle, String urlName);
	
	/**
	 * Set the title of the InformationPanel
	 * @param title : the new title
	 */
	public void setTitle(String title);
	
	/**
	 * Get the title of the InformationPanel
	 * @return the current title
	 */
	public String getTitle();
	
	
	/**
	 * hide all widget used for display help
	 */
	public void hideAllHelpField();
	
	/**
	 * display once widget needed for help
	 * @param number : the number associated to the position of the textBox who need helpDisplay
	 * (0 to 4)
	 */
	public void showOneHelp(int number);
	
	/**
	 * hide once widget needed for help
	 * @param number : the number associated to the position of the textBox who need helpDisplay
	 * (0 to 4)
	 */
	public void hideOneHelp(int number);
	
	/**
	 * handle focus event on the 0 of 4 textBox
	 * @return Event
	 */
	public HasFocusHandlers getFocusOnTB0();
	
	/**
	 * handle focus event on the 1 of 4 textBox
	 * @return Event
	 */
	public HasFocusHandlers getFocusOnTB1();
	
	/**
	 * handle focus event on the 2 of 4 textBox
	 * @return Event
	 */
	public HasFocusHandlers getFocusOnTB2();
	
	/**
	 * handle focus event on the 3 of 4 textBox
	 * @return Event
	 */
	public HasFocusHandlers getFocusOnTB3();
	
	/**
	 * handle focus event on the 4 of 4 textBox
	 * @return Event
	 */
	public HasFocusHandlers getFocusOnTB4();
	
	/**
	 * Enable the Translation help 
	 */
	public void enableHelp();
	
	/**
	 * disable the Translation help 
	 */
	public void disableHelp();
	
	/**
	 * Handle click event on one of the 5th helpsButton 
	 * @param number : ID of the corresponding button : 0 to 4
	 * @return
	 */
	public HasClickHandlers getclickBtnCpy(int number);
	
	/**
	 * action after a click on the Helpbutton
	 * copy the content of the help field in the texbox
	 * @param number : ID of the corresponding help : 0 to 4
	 */
	public void cpyHelpInField(int number);
	
	/**
	 * Return the MainConstants. Use this for use translationSystem
	 * @return the MainConstants
	 */
	public PageConstants getConstants();
}
