package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.sfeir.richercms.page.client.PageConstants;

public class PopUpImagePreview extends PopupPanel{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private static final String previewUrl = GWT.getModuleBaseURL() + "thumbnail";
	private Image img;
	private Label title;
	private Button btn;
	private LayoutPanel container;
	
	public PopUpImagePreview(String path) {
		super(true);
		
		this.container = new LayoutPanel();
		int height = Window.getClientHeight() - 50;
		int width = Window.getClientWidth() - 50;
		
		this.title = new Label(this.ExtractTitle(path));
		this.title.setStyleName("informationTitle");
		this.btn = new Button(this.constants.BtnReturn());
		
		//size managing
		int imgWidth =  (int)(width);
		int imgHeight = (int)(height*0.8); // 80%  => 10% title and 10% btn
		this.img = new Image(previewUrl+"?width="+imgWidth+"&height="+imgHeight+"&path="+path);
		

		
		this.container.add(this.title);
		this.container.add(this.img);
		this.container.add(this.btn);
	    
	    btn.addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	            PopUpImagePreview.this.hide();
	          }});
	    
	    this.img.addLoadHandler(new LoadHandler(){
			public void onLoad(LoadEvent event) {
				resizePopUp();
			}});
	    
		 // Enable animation.
	    setAnimationEnabled(true);
	    this.setWidget(this.container);
	}
	
	private void resizePopUp(){
		int width,height;
		this.container.setWidgetTopHeight(this.title,0,Unit.PX, 50, Unit.PX);
		this.container.setWidgetTopHeight(this.img,60,Unit.PX, this.img.getHeight(), Unit.PX);
		this.container.setWidgetTopHeight(this.btn,this.img.getHeight()+70,Unit.PX, 30, Unit.PX);
		if(this.img.getWidth()<500)
			width = 500;
		else
			width = this.img.getWidth();
		
		height = 50*2+this.img.getHeight();
		this.setSize(width+"px", height+"px");
		PopUpImagePreview.this.center();
	}
	
	private String ExtractTitle(String path){
		String[] splited = path.split("/");
		return splited[splited.length-1];
	}
}
