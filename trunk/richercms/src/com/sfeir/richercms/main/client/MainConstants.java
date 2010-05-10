package com.sfeir.richercms.main.client;

import com.google.gwt.i18n.client.Constants;

public interface MainConstants extends Constants {

	  @DefaultStringValue("Site Explorator")
	  String TitlePanel1();
	  
	  @DefaultStringValue("Administration")
	  String TitlePanel2();
	  
	  @DefaultStringValue("Browser Title")
	  String BrowserTitle();
	  
	  @DefaultStringValue("Page title")
	  String PageTitle();
	  
	  @DefaultStringValue("Url name")
	  String UrlName();
	  
	  @DefaultStringValue("Desciption")
	  String Description();
	  
	  @DefaultStringValue("Keyword")
	  String KeyWord();
	  
	  @DefaultStringValue("Start date of publication")
	  String PublicationStart();
	  
	  @DefaultStringValue("End date of publication")
	  String PublicationFinish();
	  
	  @DefaultStringValue("Site")
	  String MainWebSitePage();
	  
	  @DefaultStringValue("Action : ")
	  String PopUpAddDelPageTitle();
	  
	  @DefaultStringValue("Add new child Page")
	  String PopUpAddPage();
	  
	  @DefaultStringValue("Delete this page")
	  String PopUpDelPage();
	  
	  @DefaultStringValue("Add")
	  String BtnAdd();
	  
	  @DefaultStringValue("Modify")
	  String BtnModify();
	  
	  @DefaultStringValue("Cancel")
	  String BtnCancel();
	  
	  @DefaultStringValue("Unable to destroy the main page")
	  String IDeletMainPage();
	  
	  @DefaultStringValue("Error : add the new child")
	  String EAddNewChild();
	  
	  @DefaultStringValue("Error : Build tree")
	  String EBuildTree();
	  
	  @DefaultStringValue("Error : Create tree")
	  String ECreateTree();
	  
	  @DefaultStringValue("Loading")
	  String Loading();
	  
	  @DefaultStringValue("Error : Get current Page")
	  String EGetCurPage();
	  
	  @DefaultStringValue("Error : Get Main Page")
	  String EGetMainPage();
	  
	  @DefaultStringValue("Error retrieving language")
	  String ERetrievingLg();
	  
	  @DefaultStringValue("Backup in progress")
	  String PopUpSaveInProgress();
	  
	  @DefaultStringValue("Backup completed with success")
	  String PopUpSaveFinish();
	  
	  @DefaultStringValue("Backup failed, the problem with database")
	  String PopUpSaveFail();
	  
	  @DefaultStringValue("Save changes in progress")
	  String PopUpSaveModifInProg();
	  
	  @DefaultStringValue("Saving modification successfully accomplished")
	  String PopUpSaveModifFinish();
	  
	  @DefaultStringValue("Backup failed, problem with database")
	  String PopUpSaveModifFail();
	  
	  @DefaultStringValue("Retrieving information")
	  String PopUpTakeInfo();
	  
	  @DefaultStringValue("Retrieving modifications")
	  String PopUpTakeModif();
	  
	  @DefaultStringValue("Remove the page and its sub-pages in progress")
	  String PopUpDelPageInProg();
	  
	  @DefaultStringValue("Deletion succeeded!")
	  String PopUpDelPageFinish();
	  
	  @DefaultStringValue("Error during deletion!")
	  String PopUpDelPageFail();
}
