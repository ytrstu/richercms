package com.sfeir.richercms.page.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BeanTag  implements Serializable{

    private Long id;
    
    private String tagName;
    private String shortLib;
    private String description;
   
	public BeanTag() {
		super();
		this.tagName = "";
		this.shortLib = "";
		this.description = "";
	}
    
	public BeanTag(String tagName, String shortLib, String description) {
		super();
		this.tagName = tagName;
		this.shortLib = shortLib;
		this.description = description;
	}
	
	

	public BeanTag(Long id, String tagName, String shortLib, String description) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.shortLib = shortLib;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getShortLib() {
		return shortLib;
	}

	public void setShortLib(String shortLib) {
		this.shortLib = shortLib;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
