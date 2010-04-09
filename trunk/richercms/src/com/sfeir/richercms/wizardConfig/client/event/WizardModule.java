package com.sfeir.richercms.wizardConfig.client.event;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;

@HistoryName( "wizard" )
public interface WizardModule extends Mvp4gModule {
	
	public void setParentModule( Mvp4gModule module );
}
