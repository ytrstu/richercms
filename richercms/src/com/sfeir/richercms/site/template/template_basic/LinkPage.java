package com.sfeir.richercms.site.template.template_basic;

/**
 * 
 * @author homberg.g
 * Define a page by his name and his path
 */
public class LinkPage {

	private String name;
	private String path;
	
	public LinkPage(String name, String path){
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
