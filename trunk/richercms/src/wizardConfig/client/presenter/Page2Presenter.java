package wizardConfig.client.presenter;



import java.util.List;

import wizardConfig.client.ServiceLangueAsync;
import wizardConfig.client.Interface.IdisplayPage2;
import wizardConfig.shared.DetailsLangue;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

/**
 * Presenter of the second page of the wizard
 * @author homberg.g
 *
 */
public class Page2Presenter implements Presenter
{
  
	private final ServiceLangueAsync rpcLangue;
	@SuppressWarnings("unused")
	private final HandlerManager eventBus;
	private final IdisplayPage2 display;
	  
	/**
	* constructor 
	* @param eventBus
	* @param view
	*/
	public Page2Presenter(ServiceLangueAsync rpcService, HandlerManager eventBus, IdisplayPage2 view) 
	{
		this.rpcLangue= rpcService;
		this.eventBus = eventBus;
		this.display = view;
	}
	
	/**
	 * Bind the different evt
	 * (lien entre l'evt d'un widget de la vue et soit le presenter, soit le controller)
	 */	  
	private void bind() 
	{
		// Sauvegarde des langue choisi + changement de view (page2 -> MainView)
	    display.getNextButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	        	saveSelectedLanguage();
	        }
	      });
		
		// lancement de la popUp d'ajout d'une langue
	    display.getAddButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	          display.showPopUpAddLangue();
	        }
	      });
	    
		// lancement de la popUp d'ajout d'une langue
	    display.getDelButton().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	          deleteLanguage();
	        }
	      });
	    
		// Ajout d'une langue au niveau du PopUp
	    display.getPopUpBtnOk().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) 
	        {
	          if(ajouteLangue())//ajoute la langue si possible
	        	  display.hidePopUpAddLangue();// cache le popUp uniquement si une langue a bien été ajouté
	        }
	      });
	    
		// Annulation du PopUp
	    display.getPopUpBtnCancel().addClickHandler(new ClickHandler() {   
	        public void onClick(ClickEvent event) {
	          display.hidePopUpAddLangue();
	        }
	      });
	}
	  
	/**
	 * Start the view
	 */
	public void go(HasWidgets container) 
	{
	    bind();
	    container.clear();
	    fetchLanguageTable();
	    container.add(this.display.asWidget());
	}
	
	/**
	 * fill the LanguageArray
	 */
	private void fetchLanguageTable()
	{
	    this.rpcLangue.getLangues( new AsyncCallback<List<DetailsLangue>>()
	    {
	    	public void onSuccess(List<DetailsLangue> result)
	    	{
	    		display.clearTableLanguage();	    		
	    		for(DetailsLangue dLg : result)
	    			display.addLangue(dLg.getLangue(),dLg.getSelectionner());
	    	}
	        public void onFailure(Throwable caught) {
	            Window.alert("Error retrieving language");}
	    });
	}
	
	/**
	 * Add a new language if all conditions are verify
	 * @return true : new language Added ; false else
	 */
	private boolean ajouteLangue()
	{
		if(this.display.getPopUpNewLanguage().length() == 0)
		{
			Window.alert("No Language Entered");
			return false;
		}
		else
		{
			this.rpcLangue.addLanguage(this.display.getPopUpNewLanguage(),new AsyncCallback<Void>()
				    {
				    	public void onSuccess(Void result){
				    		fetchLanguageTable();}
				        public void onFailure(Throwable caught) {
				            Window.alert("Error : addLanguage");}
				    });
			return true;
		}
			
	}
	
	private void saveSelectedLanguage()
	{
		
		this.rpcLangue.SelectionneLangue(this.display.getLangueSelectionner(), new AsyncCallback<Void>()
				{
						public void onSuccess(Void result){}
						public void onFailure(Throwable caught) {
							Window.alert("Error : SelectLanguage");}
				});
	}

	private void deleteLanguage()
	{
		this.rpcLangue.DeleteLanguage(this.display.getLangueSelectionner(), new AsyncCallback<Void>()
				{
						public void onSuccess(Void result)
						{
							fetchLanguageTable();
						}
						public void onFailure(Throwable caught) {
							Window.alert("Error : DeleteLanguage");}
				});
	}
}
