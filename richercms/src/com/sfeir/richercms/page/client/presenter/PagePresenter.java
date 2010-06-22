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
import com.sfeir.richercms.page.client.LockState;
import com.sfeir.richercms.page.client.PageState;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;
import com.sfeir.richercms.page.client.interfaces.IdisplayPage;
import com.sfeir.richercms.page.client.view.PageView;
import com.sfeir.richercms.page.client.view.custom.ConfirmationBox;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.shared.BeanUser;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

@Presenter( view = PageView.class)
public class PagePresenter extends LazyPresenter<IdisplayPage, PageEventBus> {
	
	private ArboPageServiceAsync rpcPage = null;
	private UserInfoServiceAsync rpcUser = null;
	private LanguageServiceAsync rpcLanguage = null;
	private BeanUser usr;
	private BeanArboPage editingPage = null;
	private Long id = null; // field used to save the key of the current Page
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
	}
	
	
	private void addPage() {
		// show the popUp to the user
		this.view.addLineInPopUp(view.getConstants().PopUpTakeInfo(), 1);
		this.view.addLineInPopUp(view.getConstants().PopUpSaveInProgress(), 0);
		this.rpcPage.addArboPage(this.editingPage, this.id, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				eventBus.AddNewChildInTree(); //reload the new tree
				view.addLineInPopUp(view.getConstants().PopUpSaveFinish(), 1);
				
				//on redonne la possibilité de changer de traduction
				view.enableLanguageBox();
			}
			public void onFailure(Throwable caught) {
				view.addLineInPopUp(view.getConstants().PopUpSaveFail(), 2);}
		});
	}
	
	private void modifyPage() {
		// show the popUp to the user
		this.view.addLineInPopUp(view.getConstants().PopUpTakeModif(), 1);
		this.view.addLineInPopUp(view.getConstants().PopUpSaveModifInProg(), 0);
		this.editingPage.setId(this.id);
		this.rpcPage.updateArboPage(this.editingPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				view.addLineInPopUp(view.getConstants().PopUpSaveModifFinish(), 1);
				//reload the current treeNode
				eventBus.reloadCurrentPageInTree(editingPage);
				view.hideWaitPopUp();
			}
			public void onFailure(Throwable caught) {
				view.addLineInPopUp(view.getConstants().PopUpSaveModifFail(), 2);
				view.hideWaitPopUp();}
		});
	}
	
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
	
	private void disconnectUser() {
		// unlock page if it was in modify mode
		if(this.state.equals(PageState.modify))
			this.rpcPage.unlockThisPage(this.id, new AsyncCallback<Void>() {
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
		case manageUser:
		case manageImage :
			view.disableLanguageBox();
		case display :
			this.state = newState;
			view.displayNormalPanel();
			eventBus.displayCurrentPage(state);
		}
	}
	
	public void onAddPage(Long id){
		this.state = PageState.add;
		this.id = id;
		//this.view.disableLanguageBox();
		this.view.setIndexOfLgToDefault();
	}
	
	public void onModifyPage(Long id)
	{
		this.state = PageState.modify;
		this.id = id;
		this.view.enableLanguageBox();
	}
	
	public void onCancelPage(PageState newState) {		
		this.view.enableLanguageBox();
		this.state = newState;
		this.changeState(newState);
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
							rpcPage.unlockThisPage(id, new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {}
								public void onSuccess(Void result) {
									// avertit tout les presenter qu'il faut cancel
									eventBus.cancelPage(newState);
								}
							});
						}		
					});
				}else{// just unlock and send the cancelPage event
					rpcPage.unlockThisPage(id, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {}
						public void onSuccess(Void result) {
							// avertit tout les presenter qu'il faut cancel
							eventBus.cancelPage(newState);
						}
					});
				}
				break;
			case manageImage:
			case manageUser:
			case display:
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
		// we d'ont display de right part of the adress mail (only if the user does not fil the nickName field)
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
		this.editingPage = new BeanArboPage();
		this.editingPage.setPublicationStart(information.getPublicationStart());
		this.editingPage.setPublicationFinish(information.getPublicationFinish());
		this.editingPage.setTranslation(information.getTranslation());
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
	
	public void onDeletePage() {
		this.view.showWaitPopUp();
		this.view.addLineInPopUp(view.getConstants().PopUpDelPage(), 0);
	}
	
	public void onDeletingPageFinish(boolean state) {
		if(state)
			this.view.addLineInPopUp(view.getConstants().PopUpDelPageFinish(), 1);
		else
			this.view.addLineInPopUp(view.getConstants().PopUpDelPageFail(), 2);
		this.view.hideWaitPopUp();
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
								eventBus.pageLockState(result.getNickname());
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
