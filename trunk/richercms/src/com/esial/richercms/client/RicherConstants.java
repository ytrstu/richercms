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
	
	
}
