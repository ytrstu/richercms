package com.sfeir.richercms.wizard.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * All services needed to handle configuration 
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("configurationService")
public interface ConfigurationService extends RemoteService {

	/**
	 * test if the site is configured.
	 * @return true if the site configuration is set, false either.
	 */
	public Boolean  SiteIsConfigured();
	
	/**
	 * modify the stat of the configuration
	 * @param state  true : site is configured, false either.
	 */
	public void setIsConfigurated(boolean state);
}
