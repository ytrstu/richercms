package com.sfeir.richercms.page.client.view.custom.thumb;

import com.google.gwt.user.client.ui.Image;
/**
 * Thumb is an image with an associate path
 * @author homberg.g
 *
 */
public class Thumb extends Image{

	private Long id;
	private String path;
	
	public Thumb(){
		super();
		this.id = null;
		this.path = new String("");
	}
	
	public Thumb(String URL, Long id){
		super(URL);
		this.id = id;
		this.path = new String("");
	}
	
	public Thumb(String URL, Long id, String path){
		super(URL);
		this.id = id;
		this.path = path;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
