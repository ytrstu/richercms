package wizardConfig.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootLayoutPanel;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class WizardConfig implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		LanguageServiceAsync rpcLangue = GWT.create(LanguageService.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    
	    AppController appViewer = new AppController(rpcLangue, eventBus);
	    appViewer.go(RootLayoutPanel.get());
	}
	
}
