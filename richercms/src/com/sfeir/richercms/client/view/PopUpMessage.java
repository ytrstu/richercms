package com.sfeir.richercms.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PopUpMessage extends DialogBox {

	public PopUpMessage(String text) {
		
		Label label = new Label(text);
		Image img = new Image("tab_images/warning.png");
		VerticalPanel panel = new VerticalPanel();
		HorizontalPanel hpanel = new HorizontalPanel();
		panel.setSpacing(10);
		hpanel.setSpacing(10);
	
	    // Enable animation.
	    setAnimationEnabled(true);
	
	    // Enable glass background.
	    setGlassEnabled(true);
	
	    // DialogBox is a SimplePanel, so you have to set its widget property to
	    // whatever you want its contents to be.
	    Button ok = new Button("OK");
	    ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PopUpMessage.this.hide();	
			}
	    });
	    
	    hpanel.add(img);
	    hpanel.add(label);
	    panel.add(hpanel);
	    panel.add(ok);
	    panel.setCellHorizontalAlignment(ok, HasHorizontalAlignment.ALIGN_CENTER);
	    
		this.center();
	    setWidget(panel);
	}

}
