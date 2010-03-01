package com.esial.richercms.client;

import com.google.gwt.i18n.client.Constants;

public interface RicherConstants extends Constants{
	
	
	@DefaultStringValue("RicherCMS Project")
	String appTitle();
	
	/*Generic parts*/
	@DefaultStringValue("Cancel")
	String bpcancel();
	
	/*Administration part*/
	@DefaultStringValue("Administration")
	String admin();
	
	@DefaultStringValue("Add new user")
	String bpaddu();
	
	@DefaultStringValue("Edit an existing user")
	String bpeditu();
	
	@DefaultStringValue("Editor Language :")
	String lblCmslang();
	
	@DefaultStringValue("Website Created default language :")
	String lblSitelang();
	
	@DefaultStringValue("Create page")
	String bpcreatepage();
	
	@DefaultStringValue("Edit page")
	String bpeditpage();
	
	@DefaultStringValue("Delete page")
	String bpdelpage();
	
	/*Editor part*/
	
	@DefaultStringValue("Browser Title")
	String browserTitle();

	@DefaultStringValue("Page Title")
	String pageTitle();
	
	@DefaultStringValue("URL name")
	String urlName();

	@DefaultStringValue("Description")
	String description();

	@DefaultStringValue("Publish date")
	String publishDate();
	
	@DefaultStringValue("Content")
	String content();
	
	@DefaultStringValue("Upload a picture")
	String uploadPic();
	
	@DefaultStringValue("Picture name")
	String picName();
	
	
}
