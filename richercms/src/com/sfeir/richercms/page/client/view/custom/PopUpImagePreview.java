package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class PopUpImagePreview extends PopupPanel{

	private Image img;
	
	public PopUpImagePreview(String URL) {
		super(true);
		this.img = new Image(URL);
		
		 // Enable animation.
	    setAnimationEnabled(true);
	    this.setWidget(this.img);
	    
	    this.img.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	            PopUpImagePreview.this.hide();
	          }});
	    
	    this.img.addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent event) {
				PopUpImagePreview.this.center();
			}});
	    
	    this.setTitle("click to close");
	    
	}
}
