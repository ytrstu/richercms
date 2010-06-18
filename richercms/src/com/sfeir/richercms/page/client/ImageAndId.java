package com.sfeir.richercms.page.client;

import com.google.gwt.user.client.ui.Image;

/**
 * Very useFul if you need to use an image
 * like a button and if you would pass an Id to you presenter
 * when the img is clicked
 * @author homberg.g
 *
 */
public class ImageAndId extends Image{

	private Long id;
	
	public ImageAndId(){
		super();
		this.id = null;
	}
	
	public ImageAndId(String URL, Long id){
		super(URL);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
