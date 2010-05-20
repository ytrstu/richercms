package com.sfeir.richercms.main.client.event;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.presenter.InformationPanelPresenter;
import com.sfeir.richercms.main.client.presenter.MainPagePresenter;
import com.sfeir.richercms.main.client.presenter.NavigationPanelPresenter;
import com.sfeir.richercms.main.client.presenter.ReorderPagePanelPresenter;
import com.sfeir.richercms.main.client.presenter.TinyMCEPanelPresenter;
import com.sfeir.richercms.main.client.presenter.ValidationPanelPresenter;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanArboPage;


/**
 * Event bus for the main module
 * @author homberg.g
 *
 */
@Events(startView = MainPageView.class, module = MainModule.class, debug = true)
public interface MainEventBus extends EventBus {

	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( forwardToParent = true )
	public void changeBody( Widget widget );
	
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and MainPagePresenter(first view to display)
	 */
	@Event( handlers = MainPagePresenter.class )
	public void startMain();
	
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
	@Event( handlers = MainPagePresenter.class )
	public void displayNormalPanel();
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 */
	@Event( handlers = {MainPagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void addPage(Long id);
	
	
	@Event( handlers = {MainPagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void cancelPage();
	
	/**
	 * fired by the NavigationPresenter when the addPage menu is clicked
	 */
	@Event( handlers = {MainPagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
	public void modifyPage(Long id);
	
	/**
	 * fired by the ValidationPresenter when the saveButton is clicked
	 */
	@Event( handlers =  {MainPagePresenter.class, InformationPanelPresenter.class, ValidationPanelPresenter.class, TinyMCEPanelPresenter.class} )
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
	public void displayContent(String content);
	
	/**
	 * Fired by the ValidationPresenter when the add buton is clicked
	 */
	@Event( handlers = InformationPanelPresenter.class)
	public void callInfo();
	
	/**
	 * Fired by the InformationPresenter for Sending the information to save a page (in the mainPresenter)
	 * @param translation : the list of all translation
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void sendInfo(BeanArboPage information);
	
	/**
	 * Fired by the MainPresenter After having received the data from the page to save.
	 * Request the ContentPresneter to send the content
	 */
	@Event( handlers =  TinyMCEPanelPresenter.class)
	public void callContent();
	
	/**
	 * Fired by the ContentPresenter for Sending the content to save a page (in the mainPresenter)
	 * @param content : content of the current Page
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void sendContent(String content);
	
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
	@Event( handlers =  {InformationPanelPresenter.class, MainPagePresenter.class} )
	public void deletePage();
	
	/**
	 * Fired by the NavigationPresenter when a page is delete
	 * @param state : true if the page is deleted, false etheir
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void deletingPageFinish(boolean state);
	
	/**
	 * Fired by the NavigationPanel to Load it in the right place in mainView
	 * @param navPanel : the right NavigationPanel
	 */
	@Event( handlers =  MainPagePresenter.class )	
	public void changeNavPanel(INavigationPanel navPanel);

	/**
	 * Fired by the InformationPanel to load it in the right place in mainView
	 * @param infoPanel : the right InformationPanel
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void changeInfoPanel(IInformationPanel infoPanel);
	
	/**
	 * Fired by the ReorderPagePanelPresenter to load it in the right place in mainView
	 * @param reorderPanel : the panel
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void displayReorderPage(IReorderPagePanel reorderPanel);

	/**
	 * Fired by the TinyMCEPanel to load it in the right place in mainView
	 * @param tinyMcePanel : the right TinyMCEPanel
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void changeEditorPanel(ITinyMCEPanel tinyMcePanel);
	
	/**
	 * Fired by the ValidationPanel to load the it in the right place in mainView
	 * @param validationPanel : the right ValidationPanel
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void changeValidationPanel(IValidationPanel validationPanel);
	
	/**
	 * Fired by the NavigationPanelPresenter if the translation key of the language who you need a translation
	 * are null. Andso, a new translation are created in the database and this event send this key to the presenter.
	 * The mainPresenter save this key in the selected language.
	 * @param TranslationKey
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void setTranslationKeyInLanguage(Long TranslationId);
	
	/**
	 * Fired by the MainPresenter when a new language is selected in the listBox.
	 * @param index : the index of the new translation to display
	 */
	@Event( handlers =  InformationPanelPresenter.class )
	public void changeTranslation(int index);
	
	/**
	 * Fired by the Navigation presenter, when the new page is added
	 * and allows the MainPagePresenter to hide the WaitPopUp
	 */
	@Event( handlers =  MainPagePresenter.class )
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
	@Event( handlers =  MainPagePresenter.class )
	public void showInformationPopUp();
	
	/**
	 * Hide the popUp who request the user 
	 * to waited during some action without display 
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void hideInformationPopUp();
	
	/**
	 * Add a new line in the State popUp
	 * with "Success" state
	 * @param text : the description of the new state
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void addSuccessPopUp(String text);
	
	/**
	 * Add a new line in the State popUp
	 * @param text : the description of the new state
	 * with "Wait" state
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void addWaitLinePopUp(String text);
	
	/**
	 * Add a new line in the State popUp
	 * @param text : the description of the new state
	 * with "Fail" state
	 */
	@Event( handlers =  MainPagePresenter.class )
	public void addErrorLinePopUp(String text);
}
