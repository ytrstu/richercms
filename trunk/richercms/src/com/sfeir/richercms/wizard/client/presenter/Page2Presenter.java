package com.sfeir.richercms.wizard.client.presenter;



import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.wizard.client.ConfigurationServiceAsync;
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
	private ConfigurationServiceAsync rpcConfigurationService = null;
		
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		// Sauvegarde des langue choisi + changement de view (page2 -> MainView)
		view.getNextButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	saveSelectedLanguage();
	        	if(check())
	        		eventBus.wizardFinished();
	        }
	      });
		
		// Sauvegarde des langue choisi + changement de view (page2 -> Page1)
		view.getPreviousButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	eventBus.startWizard();
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
	  
	/**
	 * fired when the next button is clicked
	 */
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
	        	PopUpMessage p = new PopUpMessage("Error retrieving language");
	        	p.show();}
	    });
	}
	
	/**
	 * Add a new language if all conditions are verify
	 * @return true : new language Added ; false else
	 */
	private boolean ajouteLangue() {
		
		if(this.view.getPopUpNewLanguage().length() == 0) {
			PopUpMessage p = new PopUpMessage(view.getConstant().AlertNoLanguageEntered());
			p.show();
			return false;
		}
		else if (this.view.getPopUpNewTag().length() == 0){
			PopUpMessage p = new PopUpMessage(view.getConstant().AlertNoTagLanguageEntered());
			p.show();
			return false;
		}else {
			this.rpcLangue.addLanguage(this.view.getPopUpNewLanguage(), this.view.getPopUpNewTag(),new AsyncCallback<Void>() {
				    	public void onSuccess(Void result){
				    		fetchLanguageTable();}
				        public void onFailure(Throwable caught) {
				        	PopUpMessage p = new PopUpMessage("Error : addLanguage");
				        	p.show();}
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
							PopUpMessage p = new PopUpMessage("Error : SelectLanguage");
							p.show();}
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
							PopUpMessage p = new PopUpMessage("Error : DeleteLanguage");
							p.show();}
				});
	}
	
	/**
	 * Check if minimum information are set : a default Language, etc ...
	 * And send PopUp to help user to set informations.
	 * if all is good, set the configuration state with true.
	 * @return true if minimum informations are set
	 */
	private boolean check()
	{
		if(view.getCurrentNumRow() == -1) {
			PopUpMessage p = new PopUpMessage(view.getConstant().AlertNoLanguage());
			p.show();
			return false;
		}
		if(view.getSelectedLanguage() == -1) {
			PopUpMessage p = new PopUpMessage(view.getConstant().AlertNoLanguageSelected());
			p.show();
			return false;
		}
		
		rpcConfigurationService.setIsConfigurated(true, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {;}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Configuration");
				p.show();}});
		
		rpcLangue.setAllTranslationID(new AsyncCallback<Void>() {
			public void onSuccess(Void result) {;}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : set translation ID");
				p.show();}});
		
		return true;
	}
	
	/**
	 * used by the framework to instantiate rpcLangue 
	 * @param rpcLangue
	 */
	@InjectService
	public void setUserService( LanguageServiceAsync rpcLangue ) {
		this.rpcLangue = rpcLangue;
	}
	
	@InjectService
	public void setConfigurationService( ConfigurationServiceAsync rpcConfigurationService ) {
		this.rpcConfigurationService = rpcConfigurationService;
	}
}
