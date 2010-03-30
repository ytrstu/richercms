package wizardConfig.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;

import wizardConfig.client.Interface.IdisplayPage1;
import wizardConfig.client.event.GoPage2Event;
import wizardConfig.client.presenter.Presenter;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;


/**
 * First page of the configuration wizard
 * @author homberg.g
 *
 */
public class Page1Presenter implements Presenter {
	
	  private final HandlerManager eventBus;
	  private final IdisplayPage1 display;
	  
	  /**
	   * constructor 
	   * @param eventBus
	   * @param view
	   */
	  public Page1Presenter(HandlerManager eventBus, IdisplayPage1 view) {
		  
	    //this.rpcLangue= rpcService;
	    this.eventBus = eventBus;
	    this.display = view;
	  }
	  
	  /**
	   * Initialize the view
	   */
	  public void go(final HasWidgets container) {
		  
		    bind();
		    String test = this.myLocale();
		    
			if (test.equalsIgnoreCase("fr")) {
				this.display.setSelectedLanguage(1);
			} else if (test.equalsIgnoreCase("de")) {
				this.display.setSelectedLanguage(2);
			} else {
				this.display.setSelectedLanguage(0);
			}
			
		    container.clear();
		    container.add(display.asWidget());
	  }
	  
	/**
	 * Bind the different evt
	 * (lien entre l'evt d'un widget de la vue et soit le presenter, soit le controller)
	 */
	private void bind() {
		
		// changement de page (page1 -> page2)
	    display.getNextButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	          eventBus.fireEvent(new GoPage2Event());
	        }
	      });
	    
	    display.getSelectedLanguage().addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {
				changeLangueUI();		
			}
	    	});
	}
	
	/**
	 * change the UI language with language selected in the list
	 */
	public void changeLangueUI() {
		
		switch (this.display.getIndexLanguage()) {
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
	};
}
