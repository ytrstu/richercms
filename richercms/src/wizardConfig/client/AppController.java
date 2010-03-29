package wizardConfig.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;

import wizardConfig.client.event.GoPage2Event;
import wizardConfig.client.event.GoPage2EventHandler;
import wizardConfig.client.presenter.Page1Presenter;
import wizardConfig.client.presenter.Page2Presenter;
import wizardConfig.client.presenter.Presenter;
import wizardConfig.client.view.Page1View;
import wizardConfig.client.view.Page2View;

public class AppController implements Presenter, ValueChangeHandler<String>
{
	private final HandlerManager eventBus;
	private final ServiceLangueAsync rpcServiceLangue;
	private HasWidgets container;

	public AppController(ServiceLangueAsync rpc, HandlerManager eventBus) 
	{
		super();
		this.rpcServiceLangue = rpc;
		this.eventBus = eventBus;
		bind(); // evt binding
	}

	/**
	 * bind the events that turns the interface view
	 */
	private void bind() 
	{
		//History.addValueChangeHandler(this);
		
	    eventBus.addHandler(GoPage2Event.TYPE,
	            new GoPage2EventHandler() {
	              public void onGoPage2(GoPage2Event event) {
	                GoPage2();
	              }
	            });  
	}
	
	/**
	 * Display the first view
	 */
	public void go(final HasWidgets container) 
	{
		this.container = container;
		Presenter presenter = new Page1Presenter(this.eventBus, new Page1View());
		presenter.go(container);
	}

	/**
	 * Display the second view
	 */
	private void GoPage2() 
	{
		Presenter presenter = new Page2Presenter(this.rpcServiceLangue, this.eventBus, new Page2View());
		presenter.go(container);
	}
	
	
	/**
	 * TODO a voir si c vmt necessaire
	 */
	public void onValueChange(ValueChangeEvent<String> event) {
		/*String token = event.getValue();
	    
	    if (token != null) 
	    {
	      Presenter presenter = null;

	      if (token.equals("page1")) {
	          presenter = new Page1Presenter(this.eventBus, new Page1View());
	        }
	      else if (token.equals("page2")) {
	          presenter = new Page2Presenter(this.rpcServiceLangue, this.eventBus, new Page2View());
	        }
	      
	      if (presenter != null) {
	        presenter.go(container);
	      }
	    }*/
	  }
	

}
