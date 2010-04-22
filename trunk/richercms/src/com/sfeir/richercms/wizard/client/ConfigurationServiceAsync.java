package com.sfeir.richercms.wizard.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * All services needed to handle configuration 
 * @author homberg.g
 *
 */
public interface ConfigurationServiceAsync {

	public void  SiteIsConfigured(AsyncCallback<Boolean> async);
	
	public void setIsConfigurated(boolean state, AsyncCallback<Void> async);
}
