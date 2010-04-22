package com.sfeir.richercms.wizard.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.server.PMF;
import com.sfeir.richercms.wizard.client.ConfigurationService;
import com.sfeir.richercms.wizard.server.business.Configuration;

/**
 * Configuration Services
 * @author homberg.g
 *
 */
@SuppressWarnings("serial")
public class ServiceConfigurationImpl extends RemoteServiceServlet implements ConfigurationService{

	private static final PersistenceManagerFactory Pmf = PMF.get();
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}
	

	public Boolean SiteIsConfigured() {
		return new Boolean(this.getConfiguration().isConfigured());
		//return false;
	}
	
	@SuppressWarnings("unchecked")
	public void setIsConfigurated(boolean state) {
		
		PersistenceManager pm = getPersistenceManager();
		try {
		    Query q = pm.newQuery(Configuration.class);
		    List<Configuration> configurations = (List<Configuration>) q.execute();
		    configurations.get(0).setConfigured(state);
		    		
		} finally {
			pm.close();
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	private Configuration getConfiguration()
	{
		PersistenceManager pm = getPersistenceManager();
		try {
		    Query q = pm.newQuery(Configuration.class);
		    List<Configuration> configurations = (List<Configuration>) q.execute();
		    
		    if (configurations.size() == 0) {
		    	Configuration cf = new Configuration();
		    	pm.makePersistent(cf);
		    	return cf;
		    }
		    else
		    	return configurations.get(0);
		    		
		} finally {
			pm.close();
		}
	}
}
