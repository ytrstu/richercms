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
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.presenter.ImageManagerPresenter;
import com.sfeir.richercms.page.client.presenter.InformationPanelPresenter;
import com.sfeir.richercms.page.client.presenter.PagePresenter;
import com.sfeir.richercms.page.client.presenter.NavigationPanelPresenter;
import com.sfeir.richercms.page.client.presenter.ReorderPagePanelPresenter;
import com.sfeir.richercms.page.client.presenter.TinyMCEPanelPresenter;
import com.sfeir.richercms.page.client.presenter.ValidationPanelPresenter;
import com.sfeir.richercms.page.client.view.PageView;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;


/**
 * Event bus for the main module
 * @author homberg.g
 *
 */
@Events(startView = PageView.class, module = PageModule.class, debug = true)
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
	public void startPage();
	
	/**
	 * Started panels and integrates them into the main view
	 */
	@Event( handlers = {NavigationPanelPresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void startPanels();
	
	/**
	 * Fired by the specific Panel (ReorderPanel, ...)
	 * for re-displaying normal view with standard Panel.
	 * Warning, you can use this event after a first startPanelsEvent.
	 */
	@Event( handlers = PagePresenter.class )
	public void displayNormalPanel();
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void addPage(Long id);
	
	/**
	 * Fired by the MainPagePresenter if the user need realy to abort his work
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void cancelPage();
	
	/**
	 * Fired by the Validation Panel when the cancel button is clicked 
	 * or when a other node is selected in the tree of the NavigationPanel.
	 * This Event is catch by the MainPagePresenter who request user if he would abort
	 * his previous work.
	 */
	@Event( handlers = PagePresenter.class)
	public void confirmCancelPage();
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 */
	@Event( handlers = {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void modifyPage(Long id);
	
	/**
	 * fired by the ValidationPresenter when the saveButton is clicked
	 */
	@Event( handlers =  {PagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void savePage();
	
	/**
	 * Fired by the NavigationPresenter, for displaying the selected Page
	 * @param Key
	 */
	@Event( handlers = {InformationPanelPresenter.class, ValidationPanelPresenter.class} )
	public void displayPage(Long id);
	
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
	@Event( handlers =  PagePresenter.class )
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
	 * Fired by the NavigationPresenter when the DelPage menu is clicked
	 * * @param state : true if the page is deleted, false either
	 */
	@Event( handlers =  {InformationPanelPresenter.class, PagePresenter.class} )
	public void deletePage();
	
	/**
	 * Fired by the NavigationPresenter when a page is delete
	 * @param state : true if the page is deleted, false etheir
	 */
	@Event( handlers =  PagePresenter.class )
	public void deletingPageFinish(boolean state);
	
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
	 * Fired by the NavigationPanelPresenter if the translation key of the language who you need a translation
	 * are null. Andso, a new translation are created in the database and this event send this key to the presenter.
	 * The mainPresenter save this key in the selected language.
	 * @param TranslationKey
	 */
	@Event( handlers =  PagePresenter.class )
	public void setTranslationKeyInLanguage(Long TranslationId);
	
	/**
	 * Fired by the MainPresenter when a new language is selected in the listBox.
	 * @param index : the index of the new translation to display
	 */
	@Event( handlers =  {InformationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void changeTranslation(int index);
	
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
	 * Fired by the PagePresenter, when the corresponding entryMenu is clicked
	 */
	//@Event( modulesToLoad = ImageMobule.class )
	//public void startImagePanel();
	
	/**
	 * Fired by the NavigationPresenter, when the reorderMenu is clicked
	 * and allows the MainPagePresenter to show the ReorderPanel
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
	 * Fired by the ImagePanelPresenter, to display it in the mainPage
	 * @param p
	 */
	//@Event( handlers =  PagePresenter.class )
	//public void displayImagePanel(IImagePanel p);
}
