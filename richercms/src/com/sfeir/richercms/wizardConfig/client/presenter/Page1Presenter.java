package com.sfeir.richercms.wizardConfig.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;



import com.google.gwt.user.client.Window;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.wizardConfig.client.Interface.IdisplayPage1;
import com.sfeir.richercms.wizardConfig.client.event.WizardConfigEventBus;
import com.sfeir.richercms.wizardConfig.client.view.Page1View;


/**
 * First page of the configuration wizard
 * @author homberg.g
 */
@Presenter( view = Page1View.class)
public class Page1Presenter extends LazyPresenter<IdisplayPage1, WizardConfigEventBus> {
	

	public void onStartWizard() {
		
		String test = this.myLocale();
		
		if (test.equalsIgnoreCase("fr")) {
			this.view.setSelectedLanguage(1);
		} else if (test.equalsIgnoreCase("de")) {
			this.view.setSelectedLanguage(2);
		} else {
			this.view.setSelectedLanguage(0);
		}
		
		eventBus.changeBody(view.asWidget());
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
		case 0: {
			myReload("en");
		}break;
		case 1: {
			myReload("fr");
		}break;
		case 2: {
			myReload("de");
		}break;
		default:{
			myReload("en");
		}break;
		}
	}
	

	/**
	 * use the URL to take the language identifier
	 * @return the language identifier : en,fr,de,...
	 */
	 public String myLocale() {
		 
		String url = Window.Location.getHref();
		if (url.contains("locale")) 
		{
			String[] splitted = url.split("locale=");
			
			if (splitted.length < 2)
				return "en";
			
			return splitted[1];
		}
		return "en";
	}
	
	/**
	 * Modify the "Locale" variable with the new countryCode
	 * @param countryCode
	 */
	public void myReload(String countryCode) {
		
		String url = Window.Location.getHref();
		System.out.println(url);
		StringBuffer buf = new StringBuffer();
		if (url.contains("locale")) {
			String[] splitted = url.split("locale");
			buf.append(splitted[0]);
			buf.append("locale=");
			buf.append(countryCode);
			System.out.println("Avec locale : "+buf);
		} else {
			buf.append(url);
			buf.append("?locale=");
			buf.append(countryCode);
			System.out.println("Sans locale : "+buf);
		}
	
		Window.Location.replace(buf.toString());
	}
}
