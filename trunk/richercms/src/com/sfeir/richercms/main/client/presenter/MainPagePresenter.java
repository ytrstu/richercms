package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private ArboPageServiceAsync rpcPage = null;
	private LanguageServiceAsync rpcLanguage = null;
	private BeanArboPage editingPage = null;
	private Long id = null; // field used to save the key of the current Page
	private int AddOrModify = 0; //0 => add, 1=> modify
	
	public MainPagePresenter() {
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
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onDisplayNormalPanel() {
		this.view.displayNormalPanel();
	}
	
	public void onAddPage(Long id){
		this.AddOrModify = 0;
		this.id = id;
		this.view.disableLanguageBox();
		this.view.setIndexOfLgToDefault();
	}
	
	public void onModifyPage(Long id)
	{
		this.AddOrModify = 1;
		this.id = id;
		this.view.enableLanguageBox();
	}
	
	public void onCancelPage() {
		this.view.enableLanguageBox();
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
	
	public void onDisplayReorderPage(IReorderPagePanel reorderPanel) {
		this.view.displayReorderPanel(reorderPanel);
	}
	
	public void onStartMain() {
		this.fetchLanguageListBox();
		this.eventBus.startPanels();
		eventBus.changeBody(view.asWidget());
	}
	
	public void onSavePage() {
		this.view.showWaitPopUp();
		this.eventBus.callInfo();
	}
	
	public void onDisplayNewPageInTree() {
		this.view.hideWaitPopUp();
	}

	public void onSendInfo(BeanArboPage information) {
		this.editingPage = new BeanArboPage();
		this.editingPage.setPublicationStart(information.getPublicationStart());
		this.editingPage.setPublicationFinish(information.getPublicationFinish());
		this.editingPage.setTranslation(information.getTranslation());
		this.eventBus.callContent();
	}
	
	public void onSendContent(String content) {
		this.editingPage.getTranslation().get(view.getIndexOfCurrentLg()).setContent(content);
		switch(this.AddOrModify) {
			case 0 :
				this.addPage();
				break;
			case 1 :
				this.modifyPage();
				break;
			default :
				break;
		}
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
	
	public void onSetTranslationKeyInLanguage(Long TranslationId) {
		/*this.rpcLanguage.setTranslationKey(view.getKeyOfSelectedLg(),TranslationKey, new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving on setting translationKey");
	        	p.show();}
		});*/
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
}
