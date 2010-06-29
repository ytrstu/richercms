package com.sfeir.richercms.wizard.server.business;


import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;


/**
 * Represent a language
 * @author homberg.g
 */
@Cached
@Entity(name="Language")
@Unindexed// or @Unindexed sets default for fields in class; if neither specified, assume @Indexed
public class Language {
	
	@Id
    private Long id;
	
	private String langue;
	
	private String tag;
	
	private boolean selected;
	
	private int translationID; //permet savoir la position de la traduction associé
	
	public Language() {
		this.langue = "";
		this.selected = false;
		this.tag  = "";
		this.translationID = 0;
	}

	public Language(String langue) {
		this.langue = langue;
		this.selected = false;
		this.tag  = "";
		this.translationID = 0;
	}
	
	public Language(String langue, String tag) {
		this.langue = langue;
		this.tag = tag;
		this.selected = false;
		this.translationID = 0;
	}
	
	public Language(String langue, String tag, boolean selected) {
		this.langue = langue;
		this.tag = tag;
		this.selected = selected;
		this.translationID = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLangue() {
		return this.langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getTranslationID() {
		return this.translationID;
	}

	public void setTranslationID(int translationID) {
		this.translationID = translationID;
	}
}
