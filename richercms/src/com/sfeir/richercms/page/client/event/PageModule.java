package com.sfeir.richercms.page.client.event;

import com.mvp4g.client.Mvp4gModule;
import com.sfeir.richercms.main.MainModule;

/**
 * Interface needed for the mvp4g multi-module system
 * @author homberg.g
 *
 */
public interface PageModule extends Mvp4gModule {
	public void setParentModule( MainModule parentModule );
	//public void setParentModule( Mvp4gModule module );
}