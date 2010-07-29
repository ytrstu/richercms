package com.sfeir.richercms.page.client.view.custom.thumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FocusPanel;
import com.sfeir.richercms.page.client.PageConstants;

/**
 * A focusable thumb with an image and an associate path
 * @author homberg.g
 *
 */
public class ThumbFocusable extends FocusPanel{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Thumb img;
	private int width = 100;
	private int height = 100;
	
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
	
	public ThumbFocusable(String URL, Long id, String path, int width, int height){
		super();
		this.width = width;
		this.height = height;
		this.img = new Thumb(URL, id, path);
		setUp();
	}

	private void setUp() {
		this.setSize(this.width+"px", this.height+"px");
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
