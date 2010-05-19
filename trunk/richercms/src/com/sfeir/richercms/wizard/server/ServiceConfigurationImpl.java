package com.sfeir.richercms.wizard.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.wizard.client.ConfigurationService;
import com.sfeir.richercms.wizard.server.business.Configuration;

/**
 * Configuration Services
 * @author homberg.g
 *
 */
@SuppressWarnings("serial")
public class ServiceConfigurationImpl extends RemoteServiceServlet implements ConfigurationService{

	static {
        ObjectifyService.register(Configuration.class);
	}
	

	public Boolean SiteIsConfigured() {
		return new Boolean(this.getConfiguration().isConfigured());
	}
	
	public void setIsConfigurated(boolean state) {
		Objectify ofy = ObjectifyService.begin();
		Query<Configuration> configurations = ofy.query(Configuration.class);
		
		Configuration conf = configurations.get();
		conf.setConfigured(state);
		ofy.put(conf);   		
	}
	
	private Configuration getConfiguration()
	{
		Objectify ofy = ObjectifyService.begin();
		Query<Configuration> configurations = ofy.query(Configuration.class);
		    
		if (configurations.countAll() == 0) {
			Configuration cf = new Configuration();
			ofy.put(cf);
			return cf;
		}else
			return configurations.get();
	}
	
}
