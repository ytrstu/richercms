package wizardConfig.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WizardConfig implements EntryPoint 
{

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		ServiceLangueAsync rpcLangue = GWT.create(ServiceLangue.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    
	    AppController appViewer = new AppController(rpcLangue, eventBus);
	    appViewer.go(RootPanel.get());
	}
	
}
