package com.sfeir.richercms.wizard.client.presenter;



import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.wizard.client.LanguageServiceAsync;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage2;
import com.sfeir.richercms.wizard.client.event.WizardConfigEventBus;
import com.sfeir.richercms.wizard.client.view.Page2View;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Presenter of the second page of the wizard
 * @author homberg.g
 */
@Presenter( view = Page2View.class)
public class Page2Presenter extends LazyPresenter<IdisplayPage2, WizardConfigEventBus> {
  
	// Initialized by the mvp4g framework
	private LanguageServiceAsync rpcLangue = null;
		
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		// Sauvegarde des langue choisi + changement de view (page2 -> MainView)
		view.getNextButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	saveSelectedLanguage();
	        	eventBus.wizardFinished();
	        }
	      });
		
		// lancement de la popUp d'ajout d'une langue
	    view.getAddButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	view.showPopUpAddLanguage();
	        }
	      });
	    
		// Ajout d'une langue au niveau du PopUp
	    view.getPopUpBtnOk().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) 
	        {
	          if(ajouteLangue())//ajoute la langue si possible
	        	  view.hidePopUpAddLanguage();// cache le popUp uniquement si une langue a bien �t� ajout�
	        }
	      });
	    
		// Annulation du PopUp
	    view.getPopUpBtnCancel().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	view.hidePopUpAddLanguage();
	        }
	      });
	}
	  

	public void onGoToSecondPage() {
		
		eventBus.changeBody(view.asWidget());
		this.view.showPopUpWait();
	    fetchLanguageTable();
	}
	
	/**
	 * fill the LanguageArray
	 */
	private void fetchLanguageTable() {
		
	    this.rpcLangue.getLangues( new AsyncCallback<List<BeanLanguageDetails>>() {
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		view.clearTableLanguage();

	    		for(BeanLanguageDetails dLg : result)
	    		{
	    			view.addLanguage(dLg.getLangue(),dLg.getTag() ,dLg.getSelectionner()).addClickHandler(new ClickHandler() {
	    				private int pos = view.getCurrentNumRow();
	    		        public void onClick(ClickEvent event) {
	    			          deleteLanguage(pos);
	    			        }
	    		        });
	    		}
	    	    view.hidePopUpWait();
	    	}
	        public void onFailure(Throwable caught) {
	            Window.alert("Error retrieving language");}
	    });
	}
	
	/**
	 * Add a new language if all conditions are verify
	 * @return true : new language Added ; false else
	 */
	private boolean ajouteLangue() {
		
		if(this.view.getPopUpNewLanguage().length() == 0) {
			Window.alert("No Language Entered");
			return false;
		}
		else if (this.view.getPopUpNewTag().length() == 0){
			Window.alert("please, enter the tag");
			return false;
		}else {
			this.rpcLangue.addLanguage(this.view.getPopUpNewLanguage(), this.view.getPopUpNewTag(),new AsyncCallback<Void>() {
				    	public void onSuccess(Void result){
				    		fetchLanguageTable();}
				        public void onFailure(Throwable caught) {
				            Window.alert("Error : addLanguage");}
				    });
			return true;
		}
			
	}
	
	/**
	 * Save selected Languages in datastore
	 */
	private void saveSelectedLanguage() {
		
		this.rpcLangue.selectLanguage(this.view.getSelectedLanguage(), new AsyncCallback<Void>() {
						public void onSuccess(Void result){}
						public void onFailure(Throwable caught) {
							Window.alert("Error : SelectLanguage");}
				});
	}

	/**
	 * Delete selected Languages in datastore
	 */
	private void deleteLanguage(int id) {
		this.rpcLangue.deleteLanguage(id, new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							fetchLanguageTable();
						}
						public void onFailure(Throwable caught) {
							Window.alert("Error : DeleteLanguage");}
				});
	}
	
	/**
	 * used by the framework to instantiate rpcLangue 
	 * @param rpcLangue
	 */
	@InjectService
	public void setUserService( LanguageServiceAsync rpcLangue ) {
		this.rpcLangue = rpcLangue;
	}
}
