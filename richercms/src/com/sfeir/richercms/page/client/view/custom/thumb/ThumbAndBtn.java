package com.sfeir.richercms.page.client.view.custom.thumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.view.custom.HorizontalEventPanel;

public class ThumbAndBtn extends HorizontalEventPanel {

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Thumb img;
	private Button btn;
	
	public ThumbAndBtn(){
		super();
		this.img = new Thumb();
		setUp();
	}
	
	public ThumbAndBtn(String URL, Long id){
		super();
		this.img = new Thumb(URL, id);
		setUp();
	}
	
	public ThumbAndBtn(String URL, Long id, String path){
		super();
		this.img = new Thumb(URL, id, path);
		setUp();
	}
	
	private void setUp() {
		this.img.setTitle(this.constants.MsgClick2DisplayIRS());
		this.addStyleName("thumb");
		this.btn = new Button("-");
		this.btn.setTitle(this.constants.MsgClick2DeleteImg());
		this.btn.setVisible(false);
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				if(!btn.isVisible())
					btn.setVisible(true);
			}
		});
		this.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if(btn.isVisible())
					btn.setVisible(false);
			}
		});
		
		this.add(this.img);
		this.add(this.btn);
		
	}
	
	public HasClickHandlers btnClickEvent() {
		return this.btn;
	}
	
	public HasClickHandlers imageClickEvent() {
		return this.img;
	}
	
}
