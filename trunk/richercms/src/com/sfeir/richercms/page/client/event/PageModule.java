package com.sfeir.richercms.page.client.event;

import com.mvp4g.client.Mvp4gModule;

/**
 * Interface needed for the mvp4g multi-module system
 * @author homberg.g
 *
 */
public interface PageModule extends Mvp4gModule {
	
	public void setParentModule( Mvp4gModule module );
}