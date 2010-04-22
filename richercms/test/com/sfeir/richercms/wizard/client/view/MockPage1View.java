package com.sfeir.richercms.wizard.client.view;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage1;
import static junit.framework.Assert.assertEquals;


public class MockPage1View implements IdisplayPage1 {

	private int i = -1;
	public Widget asWidget() {
		return null;
	}

	public int getIndexLanguage() {
		i = i +1;
		return i;
	}


	public HasClickHandlers getNextButton() {
		return null;
	}

	public HasChangeHandlers getSelectedLanguage() {
		return null;
	}

	public void reload(String countryCode) {
		
		switch(i)
		{
			case 0 :
				assertEquals("index 0 => en",countryCode, new String("en"));
				break;
			case 1 :
				assertEquals("index 1 => fr",countryCode, new String("fr"));
				break;
			case 2 :
				assertEquals("index 2 => de",countryCode, new String("de"));
				break;
			default :
				assertEquals("default => en",countryCode, new String("en"));
				break;
		}
	}

	public void setSelectedLanguage(int idLanguage) {
	}

	public void createView() {
	}

}
