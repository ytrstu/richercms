package com.sfeir.richercms.wizard.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;



import com.google.gwt.user.client.Window;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage1;
import com.sfeir.richercms.wizard.client.event.WizardConfigEventBus;
import com.sfeir.richercms.wizard.client.view.Page1View;


/**
 * First page of the configuration wizard
 * @author homberg.g
 */
@Presenter( view = Page1View.class)
public class Page1Presenter extends LazyPresenter<IdisplayPage1, WizardConfigEventBus> {
	

	/**
	 * when the wizard start, the language is tested and the view was displaying
	 */
	public void onStartWizard() {
		changeViewLocale(LocaleInfo.getCurrentLocale().getLocaleName());
		eventBus.changeBody(view.asWidget());	
	}

	/**
	 * Initialize the current view with the language passed in parameter
	 * @param currentLocale : language of the view
	 */
	private void changeViewLocale(String currentLocale) {
		if (currentLocale.equals("fr")) {
			this.view.setSelectedLanguage(1);
		} else if (currentLocale.equals("de")) {
			this.view.setSelectedLanguage(2);
		} else {
			this.view.setSelectedLanguage(0);
		}
	}
	  
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */
	public void bindView() {
		
		// changement de page (page1 -> page2)
		view.getNextButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	          eventBus.GoToSecondPage();
	        }
	      });
	    
		view.getSelectedLanguage().addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {
				changeLangueUI();		
			}
	    	});
	}
	
	/**
	 * change the UI language with language selected in the list
	 */
	public void changeLangueUI() {
		
		switch (this.view.getIndexLanguage()) {
		case 0: 
			myReload("en");
			break;
		case 1: 
			myReload("fr");
			break;
		case 2: 
			myReload("de");
			break;
		default:
			myReload("en");
			break;
		}
	}
	

	/**
	 * Modify the "Locale" variable with the new countryCode
	 * @param countryCode
	 */
	public void myReload(String countryCode) {
		
		UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
		urlBuilder.setParameter("locale", countryCode);
		Window.Location.replace(urlBuilder.buildString());

		changeViewLocale(countryCode);
	}
}
