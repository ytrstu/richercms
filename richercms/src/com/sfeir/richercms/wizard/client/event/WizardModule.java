package com.sfeir.richercms.wizard.client.event;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;

/**
 * Interface needed for the mvp4g multi-module system
 * @author homberg.g
 *
 */
@HistoryName( "wizard" )
public interface WizardModule extends Mvp4gModule {
	
	public void setParentModule( Mvp4gModule module );
}
