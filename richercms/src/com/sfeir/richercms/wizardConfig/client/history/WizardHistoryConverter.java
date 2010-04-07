package com.sfeir.richercms.wizardConfig.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.sfeir.richercms.wizardConfig.client.event.WizardConfigEventBus;

@History
public class WizardHistoryConverter implements HistoryConverter<Void, WizardConfigEventBus> {

	public WizardHistoryConverter(){}
	
	public void convertFromToken(String eventType, String param,
			WizardConfigEventBus eventBus) {
		eventBus.dispatch( eventType );
		
	}

	public String convertToToken(String eventType, Void form) {
		return null;
	}

}
