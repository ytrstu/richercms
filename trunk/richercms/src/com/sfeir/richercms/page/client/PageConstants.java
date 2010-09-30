package com.sfeir.richercms.page.client;

import com.google.gwt.i18n.client.Constants;

public interface PageConstants extends Constants {

	  @DefaultStringValue("Site Explorator")
	  String TitlePanel1();
	  
	  @DefaultStringValue("Administration")
	  String TitlePanel2();
	  
	  @DefaultStringValue("Browser Title")
	  String BrowserTitle();
	  
	  @DefaultStringValue("Page title (*)")
	  String PageTitle();
	  
	  @DefaultStringValue("Url name (*)")
	  String UrlName();
	  
	  @DefaultStringValue("Desciption")
	  String Description();
	  
	  @DefaultStringValue("Keyword")
	  String KeyWord();
	  
	  @DefaultStringValue("Start date of publication")
	  String PublicationStart();
	  
	  @DefaultStringValue("End date of publication")
	  String PublicationFinish();
	  
	  @DefaultStringValue("(*) = required field")
	  String Obligation();
	  
	  @DefaultStringValue("<== Do not be empty")
	  String ObligationMsg();
	  
	  @DefaultStringValue("Site")
	  String MainWebSitePage();
	  
	  @DefaultStringValue("Action : ")
	  String PopUpAddDelPageTitle();
	  
	  @DefaultStringValue("Add new child Page")
	  String PopUpAddPage();
	  
	  @DefaultStringValue("Delete this page")
	  String PopUpDelPage();
	  
	  @DefaultStringValue("Initial position")
	  String InitPosition();
	  
	  @DefaultStringValue("Creation Date")
	  String CreationDate();
	  
	  @DefaultStringValue("Add")
	  String BtnAdd();
	  
	  @DefaultStringValue("Modify")
	  String BtnModify();
	  
	  @DefaultStringValue("Save")
	  String BtnSave();
	  
	  @DefaultStringValue("return")
	  String BtnReturn();
	  
	  @DefaultStringValue("Save and return")
	  String BtnModifyAndRetrun();
	  
	  @DefaultStringValue("Reset position")
	  String BtnResetPos();
	  
	  @DefaultStringValue("Apply")
	  String BtnApply();
	  
	  @DefaultStringValue("Upload !")
	  String BtnUpload();
	  
	  @DefaultStringValue("Ok")
	  String BtnOk();
	  
	  @DefaultStringValue("Cancel")
	  String BtnCancel();
	  
	  @DefaultStringValue("Send")
	  String BtnSend();
	  
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
	  
	  @DefaultStringValue("Error : Build List")
	  String EBuildList();
	  
	  @DefaultStringValue("Backup in progress")
	  String PopUpSaveInProgress();
	  
	  @DefaultStringValue("Please Wait")
	  String PopUpWait();
	  
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
	  
	  @DefaultStringValue("Can not delete this page because it or one of his son is being modified")
	  String PopUpDelPageLock();

	  @DefaultStringValue("page is complete")
	  String TreeOkPage();

	  @DefaultStringValue("Some fields or translation are not filled")
	  String TreeWarnPage();
	  
	  @DefaultStringValue("Information")
	  String DefaultTitleInformation();
	  
	  @DefaultStringValue("Add a child in : ")
	  String AddPageTitleInformation();
	  
	  @DefaultStringValue("Image in : ")
	  String ThumbTitle();
	  
	  @DefaultStringValue("Upload a new image with a maximum size of 1 Mb (png, jpg or gif)")	  
	  String UploadTitle();
	  
	  @DefaultStringValue("Click to Close")
	  String MsgClick2Close();
	  
	  @DefaultStringValue("click to delete this image")
	  String MsgClick2DeleteImg();
	  
	  @DefaultStringValue("Click to display in real size")
	  String MsgClick2DisplayIRS();
	  
	  @DefaultStringValue("click to select this image")
	  String MsgClick2Select();
	  
	  @DefaultStringValue("Select an image")
	  String PopUpImgTitle();
	  
	  @DefaultStringValue("Select page to link it")
	  String PopUpLinkTitle();
	  
	  @DefaultStringValue("sending the current image")
	  String MsgSendImage();
	  
	  @DefaultStringValue("saved image !")
	  String MsgImageSaved();
	  
	  @DefaultStringValue("refresh thumbs")
	  String MsgRefreshThumb();
	  
	  @DefaultStringValue("ERROR during loading thumbs")
	  String MsgErrorLoadThumbs();
	  
	  @DefaultStringValue("thumbs successfuly loaded")
	  String MsgSuccessLoadThumbs();
	  
	  @DefaultStringValue("No image found")
	  String MsgErrorImgNotFound();
	  
	  @DefaultStringValue("Are you sure you want to delete this image?")
	  String MsgSuppImg();
	  
	  @DefaultStringValue("Are you sure, you want to leave the current task ?")
	  String ConfirmCancelMsg();
	  
	  @DefaultStringValue("logOut")
	  String LogOutAnchor();
	  
	  @DefaultStringValue("Current language : ")
	  String LanguageListBox();
	  
	  @DefaultStringValue("Email adress")
	  String UserTitleColumn1();
	  
	  @DefaultStringValue("NickName")
	  String UserTitleColumn2();
	  
	  @DefaultStringValue("State")
	  String UserTitleColumn3();
	  
	  @DefaultStringValue("Locked page")
	  String UserTitleColumn4();
	  
	  @DefaultStringValue("administrator")
	  String UserTitleColumn5();
	  
	  @DefaultStringValue("delete user")
	  String UserTitleColumn6();
	  
	  @DefaultStringValue("Page title, will be displayed in the web page.")
	  String infoMessPageTitle();
	  
	  @DefaultStringValue("appearing in the browser")
	  String infoMessBrowserTitle();
	  
	  @DefaultStringValue("The name in the url. Must not contain spaces, special or accentuated character. ('_' Allowed as a separato")
	  String infoMessUrlName();
	  
	  @DefaultStringValue("Quick and brief desciption of page content")
	  String infoMessDescription();
	  
	  @DefaultStringValue("Keywords page, separated by spaces")
	  String infoMessKeyWord();
	  
	  @DefaultStringValue("Date which the page is available")
	  String infoMessDateStart();
	  
	  @DefaultStringValue("Date which the page isn't available")
	  String infoMessDateStop();
	  
	  @DefaultStringValue("Do not use illegal character ")
	  String ErrorInUrl();
	  @DefaultStringValue("Existing template : ")
	  String ExistingTemplate();
	  @DefaultStringValue("Possible tag for this template :")
	  String PossibleTag();
	  @DefaultStringValue("Selection")
	  String TagTableSelect();
	  @DefaultStringValue("tag name")
	  String TagTableName();
	  @DefaultStringValue("Short text")
	  String TagTableLibe();
	  @DefaultStringValue("Description")
	  String TagTableDesc();
	  @DefaultStringValue("textual tag")
	  String TagTabelTextual();
	  @DefaultStringValue("Yes")
	  String Yes();
	  @DefaultStringValue("No")
	  String No();
	  @DefaultStringValue("Being edited by : ")
	  String LockMessage();
	  @DefaultStringValue(" Textual tag")
	  String TbTextual();
	  @DefaultStringValue("Action")
	  String Action();
	  @DefaultStringValue("Add new tag")
	  String AddNewTag();
	  @DefaultStringValue("Tag name : ")
	  String LibTagName();
	  @DefaultStringValue("Short text : ")
	  String LibShrotLib();
	  @DefaultStringValue("description : ")
	  String Libdesc();
	  @DefaultStringValue("add this tag")
	  String addThisTag();
	  @DefaultStringValue("Managing templates")
	  String TemplatesHandle();
	  @DefaultStringValue("apply changments")
	  String ApplyChange();
	  @DefaultStringValue("Add new Template")
	  String AddTemplate();
	  @DefaultStringValue("Delete selected template")
	  String DeleteTemplate();
	  @DefaultStringValue("Modify selected template")
	  String ModifyTemplate();
	  @DefaultStringValue("Add new template")
	  String AddTemplateTitle();
	  @DefaultStringValue("Add new tag")
	  String AddTagTitle();
	  @DefaultStringValue("Unable to add tag because this name or short text are already used by another tag")
	  String msgErrorAddTag();
	  @DefaultStringValue("Unable to modify tag because this name or short text are already used by another tag")
	  String msgErrorModifyTag();
	  @DefaultStringValue("A tag must contain at least a name and short text")
	  String msgErrorEmptyTag();
	  @DefaultStringValue("Unable to add template because this name or short text are already used by another template")
	  String msgErrorAddTemplate();
	  @DefaultStringValue("Unable to modify template because this name or short text are already used by another template")
	  String msgErrorModifyTemplate();
	  @DefaultStringValue("A template must contain at least a name and a short text")
	  String msgErrorEmptyTemplate();
	  @DefaultStringValue("Image not stored, there is to large, maximum size : 1MO")
	  String msgErrorImgSize();
	  @DefaultStringValue("Just image can be stored here (png, jpg, tif, ...)")
	  String msgErrorImgFormat();
	  @DefaultStringValue("Are you sure you want to delete this page and all its sub-pages")
	  String MsgDelPageAndChild();
	  @DefaultStringValue("Delete in progress")
	  String MsgDelInProgress();
	  @DefaultStringValue("Delete template sucessfuly")
	  String MsgDelTemplateSucess();
	  @DefaultStringValue("impossible to delete the template")
	  String MsgErrorDelTemplate();
	  @DefaultStringValue("Warning email field is empty")
	  String MsgWarnMailEmpty();
	  @DefaultStringValue("Adding user ...")
	  String MsgAddUserInProg();
	  @DefaultStringValue("Warning, unable to add")
	  String MsgErrorAddUser();
	  @DefaultStringValue("User are already added")
	  String MsgUserAlreadyInDB();
	  @DefaultStringValue("User are added successfuly")
	  String MsgUserAddSuccess();
	  @DefaultStringValue("Online")
	  String onLine();
	  @DefaultStringValue("Offline")
	  String offLine();
	  @DefaultStringValue("Store only image like png, jpg, tif, ... With a maximum size of 1 MB")
	  String imageTitle();
	  @DefaultStringValue("Tag managment")
	  String tagTitle();
	  @DefaultStringValue("Add new user")
	  String addNewUser();
	  @DefaultStringValue("Description : ")
	  String TemplateDesc();
	  @DefaultStringValue("Page loading")
	  String PageLoading();
	  @DefaultStringValue("Load all User")
	  String MsgLoadUser();
	  @DefaultStringValue("Users successfully loaded")
	  String MsgLoadUserSuccess();
	  @DefaultStringValue("Error during users loading")
	  String MsgLoadUserError();
	  @DefaultStringValue("Load templates")
	  String MsgLoadTemplate();
	  @DefaultStringValue("Templates seccessfully loaded")
	  String MsgLoadTempSuccess();
	  @DefaultStringValue("Error during templates loading")
	  String MsgLoadTempFail();
	  @DefaultStringValue("Check good tags")
	  String MsgCheckTag();
	  @DefaultStringValue("Good tags are Selected")
	  String MsgGoodTagSelected();
	  @DefaultStringValue("Load tag table")
	  String MsgLoadTagTable();
	  @DefaultStringValue("Tags successfully loaded")
	  String MsgLoadTagTableSuccess();
	  @DefaultStringValue("Tag table are filled")
	  String MsgLoadTagTableOk();
	  @DefaultStringValue("Error during tag loading")
	  String MsgLoadTagTableFail();
	  @DefaultStringValue("Loading all tags")
	  String MsgLoadTag();
	  @DefaultStringValue("Tags successfully loaded")
	  String MsgLoadTagSuccess();
	  @DefaultStringValue("Error during loading tags")
	  String MsgLoadTagFail();
	  
	  @DefaultStringValue("Tools")
	  String menuTools();
	  @DefaultStringValue("Page")
	  String menuToolsPage();
	  @DefaultStringValue("Image")
	  String menuToolsImage();
	  @DefaultStringValue("Settings")
	  String menuSettings();
	  @DefaultStringValue("User")
	  String menuSettingsUser();
	  @DefaultStringValue("Tag")
	  String menuSettingsTag();
	  @DefaultStringValue("Template")
	  String menuSettingsTemplate();
	  @DefaultStringValue("Help")
	  String menuHelp();
	  @DefaultStringValue("About richerCMS...")
	  String menuHelpAbout();
	  
}
