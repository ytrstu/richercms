package com.sfeir.richercms.page.client.event;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.interfaces.ITagManager;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.presenter.ImageManagerPresenter;
import com.sfeir.richercms.page.client.presenter.InformationPanelPresenter;
import com.sfeir.richercms.page.client.presenter.PagePresenter;
import com.sfeir.richercms.page.client.presenter.NavigationPanelPresenter;
import com.sfeir.richercms.page.client.presenter.ReorderPagePanelPresenter;
import com.sfeir.richercms.page.client.presenter.TagManagerPresenter;
import com.sfeir.richercms.page.client.presenter.TemplateManagerPresenter;
import com.sfeir.richercms.page.client.presenter.TinyMCEPanelPresenter;
import com.sfeir.richercms.page.client.presenter.UserManagerPresenter;
import com.sfeir.richercms.page.client.presenter.ValidationPanelPresenter;
import com.sfeir.richercms.page.client.state.LockState;
import com.sfeir.richercms.page.client.state.PageState;
import com.sfeir.richercms.page.client.state.PopUpState;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPopUpTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.presenter.FileMBoxPresenter;
import com.sfeir.richercms.page.client.tinyMCE.presenter.PopUpTreePanelPresenter;
import com.sfeir.richercms.page.client.tinyMCE.presenter.PageViewerPresenter;
import com.sfeir.richercms.page.client.tinyMCE.presenter.ThumbsPanelPresenter;
import com.sfeir.richercms.page.client.view.PageView;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;
import com.sfeir.richercms.shared.BeanUser;


/**
 * Event bus for the main module
 * @author homberg.g
 *
 */
@Events(startView = PageView.class, module = PageModule.class)
public interface PageEventBus extends EventBus {

	/**
	 * Display the new view in the mainLayout
	 * @param widget : the new view
	 */
	@Event( forwardToParent = true )
	public void changeMain( Widget widget );
	
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and MainPagePresenter(first view to display)
	 */
	@Event( handlers = PagePresenter.class )
	public void startPage(BeanUser usr);
	
	/**
	 * Started panels and integrates them into the main view
	 */
	@Event( handlers = {NavigationPanelPresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void startPanels();
	
	/**
	 * Fired by the specific Panel (ReorderPanel, ...)
	 * for re-displaying view correponding to the current state.
	 * Warning, you can use this event only after a first startPanelsEvent.
	 */
	@Event( handlers = PagePresenter.class )
	public void displayCurrentStatePanel();
	
	/**
	 * Fired by the PagePresenter to allows the navigation panel to display
	 * the last page selected after an DisplayNormalPanel event
	 * @param state : State of the application
	 */
	@Event( handlers = NavigationPanelPresenter.class )
	public void displayCurrentPage(PageState state);
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void addPage(Long id);
	
	/**
	 * Fired by the MainPagePresenter if the user need realy to abort his work
	 * Don't use this event to handle a cancelEvent. use confirmCancelPage.
	 * @param newState : the state you need to place application
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void cancelPage(PageState newState);
	
	/**
	 * Fired by the Validation Panel when the cancel button is clicked 
	 * or when a other node is selected in the tree of the NavigationPanel.
	 * This Event is catch by the MainPagePresenter who request user if he would abort
	 * his previous work.
	 * 
	 * @param newState : the state you need to place application
	 * @param withMsg : true if you need a confirmationPopUp, false either
	 */
	@Event( handlers = PagePresenter.class)
	public void confirmCancelPage(PageState newState, boolean withMsg);
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 * @param pageId : id of page in modification
	 * @param parentId : id of parentPage
	 * @param path : path in a recursive order
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void modifyPage(Long pageId, Long parentId, List<Long> path);
	
	/**
	 * fired by the ValidationPresenter when the saveButton is clicked
	 */
	@Event( handlers =  PagePresenter.class )
	public void savePage();
	
	/**
	 * Fired by the InformationPanelPresenter if all fields are filled properly 
	 */
	@Event( handlers =  ValidationPanelPresenter.class )
	public void rightInformation();
	
	/**
	 * Fired by the NavigationPresenter, for displaying the selected Page
	 * @param id : pageId
	 */
	@Event( handlers = {InformationPanelPresenter.class, ValidationPanelPresenter.class} )
	public void displayPage(Long pageId);
	
	/**
	 * Fired by the NavigationPresenter, for displaying the root Page
	 */
	@Event( handlers = {InformationPanelPresenter.class, ValidationPanelPresenter.class} )
	public void displayMainPage();
	
	/**
	 * Fired by the InformationPanelPresenter, for displaying the content of the selected Page
	 * @param Key
	 */
	@Event( handlers = {TinyMCEPanelPresenter.class} )
	public void displayContent(List<BeanTranslationPage>  translationContents);
	
	/**
	 * Fired by the ValidationPresenter when the add buton is clicked
	 */
	@Event( handlers = InformationPanelPresenter.class)
	public void callInfo();
	
	/**
	 * Fired by the InformationPresenter for Sending the information to save a page (in the mainPresenter)
	 * @param translation : the list of all translation
	 */
	@Event( handlers =  PagePresenter.class )
	public void sendInfo(BeanArboPage information);
	
	/**
	 * Fired by the MainPresenter After having received the data from the page to save.
	 * Request the ContentPresneter to send the content
	 */
	@Event( handlers =  TinyMCEPanelPresenter.class)
	public void callContent();
	
	/**
	 * Fired by the ContentPresenter for Sending the content to save a page (in the mainPresenter)
	 * @param translationsContent : content of all Page (modify or not)
	 */
	@Event( handlers =  {PagePresenter.class, ValidationPanelPresenter.class})
	public void sendContent(List<String> translationsContent);
	
	/**
	 * reload all child of a selected ArboPage
	 * This event allows NavigationPanel to reload his child
	 */
	@Event( handlers = NavigationPanelPresenter.class)
	public void reloadChildInTree();
	
	/**
	 * Fired by the MainPagePresenter, when a new page is added.
	 * This method allows the NavigationPresenter to add the new ArboPage in the tree
	 */
	@Event( handlers = NavigationPanelPresenter.class)
	public void AddNewChildInTree();
	
	/**
	 * Fired by the MainPagePresenter, after a ArboPage modification
	 * This method allows the NavigationPresenter to modify the field display in the tree.
	 * @param newTitle : the new URLName, display in the tree
	 */
	@Event( handlers = NavigationPanelPresenter.class)
	public void reloadCurrentPageInTree(BeanArboPage modifOnPage);
	
	/**
	 * Fired by the NavigationPresenter when a page is deleted
	 * Allows the InformationPanelPresenter and the TinyMCEPanelPresenter to clear her field.
	 */
	@Event( handlers = {InformationPanelPresenter.class, TinyMCEPanelPresenter.class})
	public void deletePage();
	
	
	/**
	 * Fired by the NavigationPanel to Load it in the right place in mainView
	 * @param navPanel : the right NavigationPanel
	 */
	@Event( handlers =  PagePresenter.class )	
	public void changeNavPanel(INavigationPanel navPanel);

	/**
	 * Fired by the InformationPanel to load it in the right place in mainView
	 * @param infoPanel : the right InformationPanel
	 */
	@Event( handlers =  PagePresenter.class )
	public void changeInfoPanel(IInformationPanel infoPanel);
	
	/**
	 * Fired by the ReorderPagePanelPresenter to load it in the right place in mainView
	 * @param reorderPanel : the panel
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayReorderPage(IReorderPagePanel reorderPanel);

	/**
	 * Fired by the TinyMCEPanel to load it in the right place in mainView
	 * @param tinyMcePanel : the right TinyMCEPanel
	 */
	@Event( handlers =  PagePresenter.class )
	public void changeEditorPanel(ITinyMCEPanel tinyMcePanel);
	
	/**
	 * Fired by the ValidationPanel to load the it in the right place in mainView
	 * @param validationPanel : the right ValidationPanel
	 */
	@Event( handlers =  PagePresenter.class )
	public void changeValidationPanel(IValidationPanel validationPanel);
	
	/**
	 * Fired by the MainPresenter when a new language is selected in the listBox.
	 * @param index : the index of the new translation to display
	 */
	@Event( handlers =  {InformationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void changeTranslation(int index);
	
	/**
	 * Fired by the InformationPresenter if all field are not correctly feeled and
	 * force user to modify his fault before change language. (return to the last language
	 * in the list).
	 * @param index : index of the new language to display in the list
	 */
	@Event( handlers =  PagePresenter.class )
	public void changeLanguageInList(int index);
	
	/**
	 * Fired by the Navigation presenter, when the new page is added
	 * and allows the MainPagePresenter to hide the WaitPopUp
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayNewPageInTree();

	/**
	 * Fired by the Navigation presenter, when the reorderMenu is clicked
	 * and allows the MainPagePresenter to show the ReorderPanel
	 */
	@Event( handlers =  ReorderPagePanelPresenter.class )
	public void startReorderPanel(Long parentId);
	
	/**
	 * Show the popUp who request the user 
	 * to waited during some action without display 
	 */
	@Event( handlers =  PagePresenter.class )
	public void showInformationPopUp();
	
	/**
	 * Hide the popUp who request the user 
	 * to waited during some action without display 
	 */
	@Event( handlers =  PagePresenter.class )
	public void hideInformationPopUp();
	
	/**
	 * Add a new line in the State popUp
	 * with "Success" state
	 * @param text : the description of the new state
	 */
	@Event( handlers =  PagePresenter.class )
	public void addSuccessPopUp(String text);
	
	/**
	 * Add a new line in the State popUp
	 * @param text : the description of the new state
	 * with "Wait" state
	 */
	@Event( handlers =  PagePresenter.class )
	public void addWaitLinePopUp(String text);
	
	/**
	 * Add a new line in the State popUp
	 * @param text : the description of the new state
	 * with "Fail" state
	 */
	@Event( handlers =  PagePresenter.class )
	public void addErrorLinePopUp(String text);
	
	/**
	 * Fired by the NavigationPresenter, when the manage image entryMenu is clicked.
	 */
	@Event( handlers =  PagePresenter.class )
	public void menuImageManager();
	
	/**
	 * Fired by the NavigationPresenter to allows the ImageManagerPresenter to send his view to the 
	 * PagePrestenter.
	 * @param path : the virtual path, use to load image
	 */
	@Event( handlers =  ImageManagerPresenter.class )
	public void startImagePanel(String path);
	
	/**
	 * Fired by the ImageManagerPresenter, to display it in the mainPage
	 * @param p : the ImageManager's panel
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayImageManager(IImageManager p);
	
	
	/**
	 * Fired by the FileManager to display the ImageFileMBox
	 * But the FileMbox need to know what page is selected to open the right node
	 * Andso the NavigationPanelPresenter catch this event end fired a startTinyPopUp with 
	 * the id of the selectedPage
	 */
	@Event( handlers =  NavigationPanelPresenter.class )
	public void loadFileManager();
	
	
	/**
	 * Fired by the FileManager to display the LinkFileMBox
	 * But the FileMbox need to know what page is selected to open the right node
	 * Andso the NavigationPanelPresenter catch this event end fired a startTinyPopUp with 
	 * the id of the selectedPage
	 */
	@Event( handlers =  NavigationPanelPresenter.class )
	public void loadLinkManager();
	
	/**
	 * Fired by the NavigationPanelPresenter with the id of the selected page
	 * in order to open the corresponding node in the popUp
	 * @param pathId : the recusive path ids
	 * @param state : linkManager or imageManager
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void startTinyPopUp(List<Long> pathId, PopUpState state);
	
	/**
	 * Fired by the FileManager to create his TreePanel and the thumbsView
	 * @param pathId : the recusive path ids
	 */
	@Event( handlers = {PopUpTreePanelPresenter.class, ThumbsPanelPresenter.class} )
	public void tinyPopUpStartImgPanels(List<Long> pathId);
	
	/**
	 * Fired by the FileManager to create his TreePanel and the PageViewer
	 * @param pathId : the recusive path ids
	 */
	@Event( handlers = {PopUpTreePanelPresenter.class, PageViewerPresenter.class} )
	public void tinyPopUpStartLinkPanels(List<Long> pathId);
	
	/**
	 * Fired by the PopUpTreePanelPresenter to display the image tree view
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void tinyPopUpDisplayTreePanel(IPopUpTreePanel p);
	
	/**
	 * Fired by the ThumbsPanelPresenter to display the thumbPanel view
	 * @param p
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void tinyPopUpDisplayThumbsPanel(IThumbsPanel p);
	
	/**
	 * Fired by the PageViewerPresenter to display the PageViewer.
	 * @param p
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void tinyPopUpDisplayContentViewer(IPageViewer p);
	
	/**
	 * Fired by the PopUpTreePanelPresenter when a node is selected in the tree
	 * Allows the ThumbsPanelPresenter to display thumbnails include in this page
	 * @param path : Path of the page : /page1/page1.1/page1.1.3
	 */
	@Event( handlers =  ThumbsPanelPresenter.class )
	public void displayThumbsInPopUp(String path);
	
	/**
	 * Fired by the PopUpTreePanelPresenter when a node is selected in the tree
	 * Allows the PageViewerPresenter to display content include in this page
	 * @param pageId : the id of the selected page
	 */
	@Event( handlers =  PageViewerPresenter.class )
	public void displayContentInPopUp(Long pageId);
	
	/**
	 * Fired by the ThumbsPanelPresenter to send the path of the selected thumb
	 * @param path : Image's path : /page1/page1.1/image3.png
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void selectThumbs(String path);
	
	/**
	 * Fired by the FileMBoxPresenter, when the ok btn was clicked.
	 */
	@Event( handlers = PopUpTreePanelPresenter.class)
	public void callLinkPath();
	
	/**
	 * Fired by the PopUpsPanelPresenter to send the
	 * selected page path and allows the FileMBoxPresenter to add it in tinyMCE
	 * @param path : Page's path : /page1/page1.1
	 */
	@Event( handlers =  FileMBoxPresenter.class )
	public void sendLinkPath(String path);
	
	/**
	 * Fired by the PagePresenter, to enable return button of the validationPanel
	 */
	@Event( handlers =  ValidationPanelPresenter.class )
	public void enableReturnBtn();
	
	/**
	 * Fired by the PagePresenter, when the userSettings entry menu is clicked
	 * Allows the UserManagerPresenter to display his view.
	 */
	@Event( handlers =  UserManagerPresenter.class )
	public void startUserManager();
	
	/**
	 * Fired by the UserManagerPresenter, to display it in the mainPage
	 * @param p : the displayUser's panel
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayUserManager(IUserManager p);
	
	/**
	 * Fired by a panel to know if this page is locked
	 * PagePresenter Handle this event, because, some panels 
	 * must be called if this page was locked
	 * @param pageId : id of the page to test
	 * @param state : the new state needed by the user
	 */
	@Event( handlers =  PagePresenter.class )
	public void verifyPageLock(Long pageId, LockState state);
	
	/**
	 * event Fired by the PagePresenter to manage correctly
	 * the lock event : InformationPage display the user who lock
	 * and the NavigationPanelPresenter thorws different action or not.
	 * @param userName : the nickName of the user
	 */
	@Event( handlers =  {InformationPanelPresenter.class})
	public void pageLockState(String userName);
	
	/**
	 * Fired by the pagePresenter to disable modify Button
	 * if the current page is locked
	 */
	@Event( handlers =  {ValidationPanelPresenter.class})
	public void disableModifyBtn();
	
	/**
	 * Fired by the pagePresenter to enable modify Button
	 * if the current page is unlocked
	 */
	@Event( handlers =  {ValidationPanelPresenter.class})
	public void enableModifyBtn();
	
	/**
	 * Fired by the ValidationPanelPresenter when you click on the modify btn
	 * this function test if the page was not locked and lock the page if its possible
	 */
	@Event( handlers =  NavigationPanelPresenter.class )
	public void goInModification();
	
	/**
	 * Fired by the NavigationPanelPresenter to start 
	 * the tagManagerView
	 */
	@Event( handlers =  TagManagerPresenter.class )
	public void startTagManager();
	
	/**
	 * Fired by the TagManagerPresenter to display 
	 * tagManager view in the Page container
	 * @param tagManager : view
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayTagManager(ITagManager tagManager);
	
	/**
	 * Fired by the NavigationPanelPresenter to start 
	 * the templateManagerView
	 */
	@Event( handlers =  TemplateManagerPresenter.class )
	public void startTemplateManager();
	
	/**
 	 * Fired by the TemplateManagerPresenter to display 
	 * templateManager view in the Page container 
	 * @param templateManager : view
	 */
	@Event( handlers =  PagePresenter.class )
	public void displayTemplateManager(ITemplateManager templateManager);
}
