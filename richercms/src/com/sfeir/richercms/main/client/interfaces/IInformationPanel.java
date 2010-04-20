package com.sfeir.richercms.main.client.interfaces;

public interface IInformationPanel {

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
	String getPublicationStart();

	/**
	 * Return the date of the Last publication of this new Page
	 * @return content of the field PublicationFinish
	 */
	String getPublicationFinish();
	
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
	void setPublicationStart(String publicationStart);

	/**
	 * Set the date of the Last publication of this new Page
	 * @param content of the field PublicationFinish
	 */
	void setPublicationFinish(String publicationFinish);
}
