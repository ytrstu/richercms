package com.sfeir.richercms.wizard.client.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.sfeir.richercms.wizard.client.event.WizardConfigEventBus;

//public class WizardHistoryConverter implements HistoryConverter<WizardConfigEventBus, Void> {

@History
public class WizardHistoryConverter implements HistoryConverter<WizardConfigEventBus> {

	public WizardHistoryConverter(){}
	
	public void convertFromToken(String eventType, String param,
			WizardConfigEventBus eventBus) {
		eventBus.dispatch( eventType );
		
	}

	public String convertToToken(String eventType, Void form) {
		return null;
	}
	
	public void onGoToSecondPage(){}
	public void onStartWizard(){}

	@Override
	public boolean isCrawlable() {
		return false;
	}
}
