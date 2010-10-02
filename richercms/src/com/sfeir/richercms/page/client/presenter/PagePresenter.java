package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.UserInfoServiceAsync;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.interfaces.ITagManager;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.interfaces.IdisplayPage;
import com.sfeir.richercms.page.client.state.LockState;
import com.sfeir.richercms.page.client.state.PageState;
import com.sfeir.richercms.page.client.view.PageView;
import com.sfeir.richercms.page.client.view.custom.ConfirmationBox;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.shared.BeanUser;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Presenter of the page panel view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = PageView.class)
public class PagePresenter extends LazyPresenter<IdisplayPage, PageEventBus> {
	
	private ArboPageServiceAsync rpcPage = null;
	private UserInfoServiceAsync rpcUser = null;
	private LanguageServiceAsync rpcLanguage = null;
	private BeanUser usr;
	private BeanArboPage editingPage = null;
	private Long pageId = null; // field used to save the key of the current Page
	private Long parentPageId = null; // field used to save the key of the current Page
	private List<Long> recPath; // need this to update imagePath
	private PageState state = PageState.display;
	
	public PagePresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		view.onChangeSelectedLg().addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {	
				eventBus.changeTranslation(view.getIndexOfCurrentLg());
			}
	    	});
		
		view.setImageToolCommand(new Command(){
			public void execute() {
				changeState(PageState.manageImage);
		}});
		
		view.setPageToolCommand(new Command(){
			public void execute() {
				changeState(PageState.display);
		}});
		
		view.setUserSettingsCommand(new Command(){
			public void execute() {
				changeState(PageState.manageUser);
		}});
		
		view.setTagSettingsCommand(new Command(){
			public void execute() {
				changeState(PageState.manageTag);
		}});
		
		view.setTemplateSettingsCommand(new Command(){
			public void execute() {
				changeState(PageState.manageTemplate);
		}});
		
		Window.addCloseHandler(new CloseHandler<Window>(){
			public void onClose(CloseEvent<Window> event) {
				disconnectUser();
			}
		});
		
		this.view.onLogOutClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {	
				disconnectUser();
			}
		});
		
		this.view.upSpliterEvent().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.upRightSpliter();
			}
		});
		
		this.view.downSpliterEvent().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.downRightSpliter();
			}
		});
	}
	
	/**
	 * Add current page in datastore
	 */
	private void addPage() {
		// show the popUp to the user
		this.view.addLineInPopUp(view.getConstants().PopUpTakeInfo(), 1);
		this.view.addLineInPopUp(view.getConstants().PopUpSaveInProgress(), 0);
		this.editingPage.setParentId(this.parentPageId);
		this.rpcPage.addArboPage(this.editingPage, this.parentPageId, new AsyncCallback<Long>() {
			public void onSuccess(Long result) {
				eventBus.AddNewChildInTree(); //reload the new tree
				view.addLineInPopUp(view.getConstants().PopUpSaveFinish(), 1);
				//on redonne la possibilité de changer de traduction
				view.enableLanguageBox();
				//allows informationPresenter to save customTag with right pageId
				eventBus.saveCustomTag(result);
				//this event is send if all information entered by user are right
				eventBus.rightInformation();
			}
			public void onFailure(Throwable caught) {
				view.addLineInPopUp(view.getConstants().PopUpSaveFail(), 2);}
		});
	}
	
	/**
	 * modify page in datastore
	 */
	private void modifyPage() {
		// show the popUp to the user
		this.view.addLineInPopUp(view.getConstants().PopUpTakeModif(), 1);
		this.view.addLineInPopUp(view.getConstants().PopUpSaveModifInProg(), 0);
		this.editingPage.setId(this.pageId);
		this.rpcPage.updateArboPage(this.editingPage, this.recPath, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				view.addLineInPopUp(view.getConstants().PopUpSaveModifFinish(), 1);
				//reload the current treeNode
				eventBus.reloadCurrentPageInTree(editingPage);
				view.hideWaitPopUp();
				//allows informationPresenter to save customTag with right pageId
				eventBus.saveCustomTag(editingPage.getId());
				//this event is send if all information entered by user are right
				eventBus.rightInformation();
			}
			public void onFailure(Throwable caught) {
				view.addLineInPopUp(view.getConstants().PopUpSaveModifFail(), 2);
				view.hideWaitPopUp();}
		});
	}
	
	/**
	 * Fill the language list in the view
	 * with language take in datastore
	 */
	private void fetchLanguageListBox() {
		this.rpcLanguage.getLangues( new AsyncCallback<List<BeanLanguageDetails>>() {
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		for(int i = 0 ; i<result.size(); i++) 
		    		for(BeanLanguageDetails lg : result) {
		    			if(lg.getTranslationID() == i) // sort language by her translationID
		    			{
		    				if( i == 0) // add the default language
		    					view.addLanguageInListBox(lg.getLangue(), "", true);
		    				else //other language
		    					view.addLanguageInListBox(lg.getLangue(), "", false);
		    				break;
		    			}
		    				
		    		}
	    	}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage(view.getConstants().ERetrievingLg());
	        	p.show();}
		});
	}
	
	/**
	 * Disconnect an user to modify its status in datastore
	 */
	private void disconnectUser() {
		// unlock page if it was in modify mode
		if(this.state.equals(PageState.modify))
			this.rpcPage.unlockThisPage(this.pageId, new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {;}
				public void onSuccess(Void result) {;}
			});
		
		this.rpcUser.logOutUser(this.usr.getId(), new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {;}
			public void onSuccess(Void result) {;}
		});
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onDisplayCurrentStatePanel() {
		changeState(this.state);
	}
	
	/**
	 * Allows you to change State.
	 * If you would just display the currentState panel, 
	 * add the current state into the newState var.
	 * @param newState : new State.
	 */
	private void changeState(PageState newState){
		
		switch(this.state) {
		case modify :
		case add :
			eventBus.confirmCancelPage(newState,true);
			break;
		case manageTag:
		case manageUser:
		case manageImage:
		case manageTemplate:
			view.disableLanguageBox();
		case display :
			this.state = newState;
			view.displayNormalPanel();
			eventBus.displayCurrentPage(state);	
		}
	}
	
	public void onAddPage(Long id){
		this.state = PageState.add;
		this.parentPageId = id;
		this.pageId = null;
		//this.view.disableLanguageBox();
		this.view.setIndexOfLgToDefault();
	}
	
	public void onModifyPage(Long id, Long parentpageId, List<Long> path)
	{
		this.state = PageState.modify;
		this.recPath = path;
		this.pageId = id;
		this.parentPageId = parentpageId;
		this.view.enableLanguageBox();
	}
	
	public void onCancelPage(PageState newState) {		
		this.view.enableLanguageBox();
		this.state = newState;
		//this.changeState(newState);
	}
	
	public void onConfirmCancelPage(final PageState newState, boolean withMsg) {
		switch(this.state){
			case modify:
			case add :
				if(withMsg){//display a popUp to confirm cancel
					ConfirmationBox confirmPopUp = new ConfirmationBox("ATTENTION",
													this.view.getConstants().ConfirmCancelMsg());
					
					confirmPopUp.getClickOkEvt().addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							// unlock page if its necessary
							unLockPage(pageId, newState);
						}		
					});
				}else{// just unlock and send the cancelPage event
					this.unLockPage(pageId, newState);
				}
				break;
			case manageImage:
			case manageUser:
			case manageTag:
			case manageTemplate:
			case display:
				eventBus.cancelPage(newState);
		}

	}

	/**
	 * Unlock specific page in datastore
	 * and fired the cancelPage event with good state
	 * @param idOfPage : page id to unlock
	 * @param newState : new state
	 */
	private void unLockPage(final Long idOfPage, final PageState newState) {
		// pageId == null if its a new page 
		if(pageId != null){
			rpcPage.unlockThisPage(pageId, new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {}
				public void onSuccess(Void result) {
					// avertit tout les presenter qu'il faut cancel
					eventBus.cancelPage(newState);
				}
			});
		}else {
			eventBus.cancelPage(newState);
		}
	}
	
	public void onChangeNavPanel(INavigationPanel navPanel) {
		this.view.setNavPanel(navPanel);
	}
	
	public void onChangeInfoPanel(IInformationPanel infoPanel) {
		this.view.setInfoPanel(infoPanel);
	}
	
	public void onChangeEditorPanel(ITinyMCEPanel tinyMcePanel) {
		this.view.setTinyMcePanel(tinyMcePanel);
	}
	
	public void onChangeValidationPanel(IValidationPanel validationPanel) {
		this.view.setValidationPanel(validationPanel);
	}
	
	public void onChangeLanguageInList(int index){
		this.view.setLanguageListIndex(index);
	}
	
	//NavigationPresenter Menu Event
	public void onMenuImageManager(){
		changeState(PageState.manageImage);
	}
	
	public void onDisplayReorderPage(IReorderPagePanel reorderPanel) {
		this.view.displayReorderPanel(reorderPanel);
		this.state = PageState.display;
	}
	
	public void onDisplayImageManager(IImageManager p) {
		this.view.displayImagePanel(p);
		this.state = PageState.manageImage;
		this.view.disableLanguageBox();
		this.eventBus.enableReturnBtn(); // enable the return button of the ValidationPanel
	}
	
	public void onStartPage(BeanUser usr) {
		//initialize state
		this.state = PageState.display;
		this.usr = usr;
		this.view.initAdminMenu(usr.isAdmin());
		// we don't display the right part of the address mail (only if the user does not fill the nickName field)
		if(this.usr.getNickname().contains("@"))
			this.view.setPseudo(this.usr.getNickname().split("@")[0]);
		else
			this.view.setPseudo(this.usr.getNickname());
			
		this.view.setLogOutAnchor(this.usr.getLogoutUrl());
		this.fetchLanguageListBox();
		this.eventBus.startPanels();
		eventBus.changeMain(view.asWidget());
	}
	
	public void onSavePage() {
		this.eventBus.callInfo();
	}
	
	public void onDisplayNewPageInTree() {
		this.view.hideWaitPopUp();
		this.state = PageState.display;
	}

	public void onSendInfo(BeanArboPage information) {
		this.editingPage = information;
		this.eventBus.callContent();
	}
	
	public void onSendContent(List<String> translationsContent) {
		int i = 0;
		for(String content : translationsContent) {
			// insert all content in new TranslationPages
			this.editingPage.getTranslation().get(i).setContent(content);
			i++;
		}
		
		if(this.state.equals(PageState.add ))
			this.addPage();
		else if(this.state.equals(PageState.modify ))
			this.modifyPage();
	}
	
	public void onShowInformationPopUp() {
		this.view.showWaitPopUp();
	}
	
	public void onHideInformationPopUp() {
		this.view.hideWaitPopUp();
	}

	public void onAddSuccessPopUp(String text) {
		this.view.addLineInPopUp(text, 1);
	}
	
	public void onAddWaitLinePopUp(String text) {
		this.view.addLineInPopUp(text, 0);
	}
	
	public void onAddErrorLinePopUp(String text) {
		this.view.addLineInPopUp(text, 2);
	}
	
	public void onDisplayUserManager(IUserManager p) {
		this.view.displayUserManager(p);
	}
	
	public void onDisplayTagManager(ITagManager tagManager) {
		this.view.displayTagManager(tagManager);
	}
	
	public void onDisplayTemplateManager(ITemplateManager templateManager){
		this.view.displayTemplateManager(templateManager);
	}
	
	public void onVerifyPageLock(Long pageId, final LockState lockState) {
		switch(lockState){
		case delete :
		case modify :

			// if any user block this page, this page was locked
			this.rpcPage.lockThisPage(pageId, this.usr.getId(), new AsyncCallback<Long>() {
				public void onFailure(Throwable caught){}
				public void onSuccess(Long result) {
					// null => page unlock
					if(result == null){
						eventBus.pageLockState(null);
						changeState(PageState.modify);
					}else
						rpcUser.getUser(result, new AsyncCallback<BeanUser>() {
							public void onFailure(Throwable caught) {}
							public void onSuccess(BeanUser result) {
								//display lock
								eventBus.pageLockState(result.getNickname());
								PopUpMessage popUp = new PopUpMessage("Impossible de modifier cette page, car elle est déjà en cours de modification par : "+result.getNickname());
								popUp.show();
							}
						});
				}
			});
			break;
		case display :
			// just display if an user lock this page
			this.rpcPage.lockPageInfo(pageId, new AsyncCallback<Long>() {
				public void onFailure(Throwable caught){}
				public void onSuccess(Long result) {
					// null => page unlock
					if(result == null){
						eventBus.pageLockState(null);
						// we can modify !!
						eventBus.enableModifyBtn();
					}else{
						// we cannot modify !!
						eventBus.disableModifyBtn();
						rpcUser.getUser(result, new AsyncCallback<BeanUser>() {
							public void onFailure(Throwable caught) {}
							public void onSuccess(BeanUser result) {
								eventBus.pageLockState(result.getNickname());
							}
						});
					}
						changeState(PageState.display);
				}
			});
		}
	}
	

	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
	
	/**
	 * used by the framework to instantiate rpcLanguage
	 * @param rpcLanguage
	 */
	@InjectService
	public void setLanguageService( LanguageServiceAsync rpcLanguage ) {
		this.rpcLanguage = rpcLanguage;
	}
	
	/**
	 * used by the framework to instantiate rpcLanguage
	 * @param rpcUser
	 */
	@InjectService
	public void setUserInfoService( UserInfoServiceAsync rpcUser ) {
		this.rpcUser = rpcUser;
	}
}
