package com.sfeir.richercms.wizard.server.business;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Represent a language
 * @author homberg.g
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Language {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
	@Persistent
	private String langue;
	@Persistent
	private String tag;
	@Persistent
	private boolean selected;
	@Persistent
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
