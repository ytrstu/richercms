package com.sfeir.richercms.client.view;


import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * popUp display during loading elements
 * @author homberg.g
 */
public class PopUpWait extends DialogBox {

	private HorizontalPanel valuePanel = new HorizontalPanel();
	private Label label = new Label("wait a momment");
	private Image img = new Image("tab_images/loading29.gif");
	
	public PopUpWait()
	{
		this.valuePanel.add(img);
		this.setText("Loading");
		this.valuePanel.add(this.label);
		this.valuePanel.setSpacing(10);
		this.center();
		this.setWidget(this.valuePanel);
		
		//hide directly the popUp
		this.hide();
		
		// Enable animation.
		this.setAnimationEnabled(true);
		  
		// Enable glass background.
		this.setGlassEnabled(true);
	
	}

}
