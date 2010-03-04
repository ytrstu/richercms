package com.esial.richercms.client;

import com.google.gwt.i18n.client.Constants;

public interface RicherConstants extends Constants{
	
	
	@DefaultStringValue("RicherCMS Project")
	String appTitle();
	
	@DefaultStringValue("Please sign into your Google Account to access the RicherCMS application")
	String signInto();
	
	@DefaultStringValue("Sign in")
	String signIn();
	
	@DefaultStringValue("Sign out")
	String signOut();
	
	@DefaultStringValue("Not logged in")
	String notLogged();
	
	/*Generic parts*/
	@DefaultStringValue("Cancel")
	String bpcancel();
	
	@DefaultStringValue("Site")
	String site();
	
	/*Administration part*/
	@DefaultStringValue("Administration")
	String admin();
	
	@DefaultStringValue("Add new user")
	String bpaddu();
	
	@DefaultStringValue("Login - Gmail valid e-mail adress :")
	String login();
	
	@DefaultStringValue("Edit an existing user")
	String bpeditu();
	
	@DefaultStringValue("Editor Language :")
	String lblCmslang();
	
	@DefaultStringValue("Website Created default language :")
	String lblSitelang();
	
	@DefaultStringValue("Create page")
	String createpage();
	
	@DefaultStringValue("Choose an action for element :")
	String action();
	
	@DefaultStringValue("Edit")
	String editpage();
	
	@DefaultStringValue("Delete")
	String delpage();
	
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
	
	/*Error part*/
	@DefaultStringValue("Delete error")
	String delError();
	
	@DefaultStringValue("Delete Ok")
	String delOk();
	
	@DefaultStringValue("Email Adress")
	String email();
	
	@DefaultStringValue("Nickname")
	String nickname();
	
	@DefaultStringValue("Is admin")
	String isadmin();
}
