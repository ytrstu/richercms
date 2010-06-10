package com.sfeir.richercms.page.client.view.custom.thumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FocusPanel;
import com.sfeir.richercms.page.client.PageConstants;

public class ThumbFocusable extends FocusPanel{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Thumb img;
	
	public ThumbFocusable(){
		super();
		this.img = new Thumb();
		setUp();
	}
	
	public ThumbFocusable(String URL, Long id){
		super();
		this.img = new Thumb(URL, id);
		setUp();
	}
	
	public ThumbFocusable(String URL, Long id, String path){
		super();
		this.img = new Thumb(URL, id, path);
		setUp();
	}

	private void setUp() {
		this.img.setTitle(this.constants.MsgClick2Select());
		this.addStyleName("thumb");
		this.setWidget(img);
	}

	public Thumb getImg() {
		return img;
	}

	public void setImg(Thumb img) {
		this.img = img;
	}
}
