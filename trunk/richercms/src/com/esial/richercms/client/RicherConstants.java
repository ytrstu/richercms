package com.esial.richercms.client;

import com.google.gwt.i18n.client.Constants;

public interface RicherConstants extends Constants{
	
	@DefaultStringValue("RicherCMS Project")
	String appTitle();
	
	/*Administration part*/
	@DefaultStringValue("Administration")
	String admin();
	@DefaultStringValue("Add new user")
	String bpadd();
	@DefaultStringValue("Edit an existing user")
	String bpedit();
	
}
