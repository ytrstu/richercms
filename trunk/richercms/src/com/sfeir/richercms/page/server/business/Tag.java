package com.sfeir.richercms.page.server.business;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity(name="Tag")
@Unindexed
public class Tag {

    @Id
    private Long id;
    
    private String tagName;
    private String shortLib;
    private String description;
    private boolean isTextual;
   
	public Tag() {
		super();
		this.tagName = "";
		this.shortLib = "";
		this.description = "";
		this.isTextual = false;
	}
    
	public Tag(String tagName, String shortLib, String description) {
		super();
		this.tagName = tagName;
		this.shortLib = shortLib;
		this.description = description;
		this.isTextual = false;
	}
	
	public Tag(String tagName, String shortLib, String description, boolean isTextual) {
		super();
		this.tagName = tagName;
		this.shortLib = shortLib;
		this.description = description;
		this.isTextual = isTextual;
	}

	
	public Tag(Long id, String tagName, String shortLib, String description, boolean isTextual) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.shortLib = shortLib;
		this.description = description;
		this.isTextual = isTextual;
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

	public boolean isTextual() {
		return isTextual;
	}

	public void setTextual(boolean isTextual) {
		this.isTextual = isTextual;
	}
}
