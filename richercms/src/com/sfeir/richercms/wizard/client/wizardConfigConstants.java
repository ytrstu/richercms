package com.sfeir.richercms.wizard.client;

import com.google.gwt.i18n.client.Constants;

public interface wizardConfigConstants extends Constants {
	
	  @DefaultStringValue("Title")
	  String titlePage1();
	  
	  @DefaultStringValue("Title")
	  String titlePage2();
	  
	  @DefaultStringValue("Summary")
	  String summary();
	  
	  @DefaultStringValue("Next")
	  String buttonNext();
	  
	  @DefaultStringValue("Previous")
	  String buttonPrevious();
	  
	  @DefaultStringValue("Language : ")
	  String LabelLangue();
	  
	  @DefaultStringValue(" add Language ")
	  String buttonAddLanguage();
	  
	  @DefaultStringValue(" Add Language ")
	  String TextPopUp();
	  
	  @DefaultStringValue(" Add ")
	  String buttonAddLanguagePopUp();
	  
	  @DefaultStringValue(" Cancel ")
	  String buttonCancelPopUp();
	  
	  @DefaultStringValue(" Language : ")
	  String labelLanguagePopUp();
	  
	  @DefaultStringValue("Tag (fr, en_US) : ")
	  String labelTagPopUp();
	  
	  @DefaultStringValue("Default language")
	  String LanguageTitleColumn1();
	  
	  @DefaultStringValue("Language")
	  String LanguageTitleColumn2();
	  
	  @DefaultStringValue("tag")
	  String LanguageTitleColumn3();
	  
	  @DefaultStringValue("Delete")
	  String LanguageTitleColumn4();
	  
	  @DefaultStringValue("It takes at least one language, please add one")
	  String AlertNoLanguage();
	  
	  @DefaultStringValue("It takes a default language, please select a default language")
	  String AlertNoLanguageSelected();
	  
	  @DefaultStringValue("No Language Entered, please add the name of the language")
	  String AlertNoLanguageEntered();
	  
	  @DefaultStringValue("No Language tag Entered, please add the tag of the language")
	  String AlertNoTagLanguageEntered();
}
