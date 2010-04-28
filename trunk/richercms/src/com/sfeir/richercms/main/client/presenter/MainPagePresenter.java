package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanPage;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private PageServiceAsync rpcPage = null;
	private LanguageServiceAsync rpcLanguage = null;
	private BeanPage editingPage = null;
	private String key = null; // field used to save the key of the current Page
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
				changeTranslation(view.getKeyOfSelectedLg());
			}
	    	});
	}
	
	
	private void addPage() {
		this.editingPage.setKey(this.key);
		this.rpcPage.addPage(this.editingPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				eventBus.buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : AddPage");
				p.show();}
		});
	}
	
	private void modifyPage() {
		
		this.editingPage.setKey(this.key);
		this.rpcPage.updatePage(this.editingPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				eventBus.buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Modify Page");
				p.show();}
		});
	}
	
	private void fetchLanguageListBox() {
		
		this.rpcLanguage.getLangues( new AsyncCallback<List<BeanLanguageDetails>>() {
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		for(BeanLanguageDetails lg : result) {
	    			if(lg.getSelectionner())
	    			{
	    				view.addLanguageInListBox(lg.getLangue(), lg.getKey(), true);
	    				changeTranslation(lg.getKey());
	    			}
	    			else
	    				view.addLanguageInListBox(lg.getLangue(), lg.getKey(), false);
	    		}
	    	}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving language");
	        	p.show();}
		});
	}
	
	
	private void changeTranslation(String languageSelectedKey) {
		this.rpcLanguage.isAlreadyTranslated(languageSelectedKey, new AsyncCallback<String>() {
	    	public void onSuccess(String result) {
	    		eventBus.changeLanguage(result);
	    	}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving language by key");
	        	p.show();}
		});
	}
	
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(String key){
		this.AddOrModify = 0;
		this.key = key;
	}
	
	public void onModifyPage(String key)
	{
		this.AddOrModify = 1;
		this.key = key;
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
	
	public void onStartMain() {
		this.fetchLanguageListBox();
		this.eventBus.startPanels();
		eventBus.changeBody(view.asWidget());
	}
	
	public void onSavePage() {
		this.eventBus.callInfo();
	}

	public void onSendInfo(BeanPage info) {
		this.editingPage = info;
		this.eventBus.callContent();
	}
	
	public void onSendContent(String content) {
		this.editingPage.setContent(content);

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
	
	public void onSetTranslationKeyInLanguage(String TranslationKey) {
		this.rpcLanguage.setTranslationKey(view.getKeyOfSelectedLg(),TranslationKey, new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {}
			public void onFailure(Throwable caught) {
	        	PopUpMessage p = new PopUpMessage("Error retrieving on setting translationKey");
	        	p.show();}
		});
	}
	
	
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( PageServiceAsync rpcPage ) {
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
