package com.sfeir.richercms.wizard.shared;

import java.io.Serializable;


/**
 * Contain the displayable elements of a language.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class BeanLanguageDetails implements Serializable {
	
	private Long id;
	private String language;
	private String tag;
	private Boolean isSelected;
	private int translationID;
	
	public BeanLanguageDetails() {
		this.language ="";
		this.tag ="";
		this.isSelected = false;
		this.translationID = 0;
	}
	
	public BeanLanguageDetails(Long id, String lg, boolean isSelected) {
		this.language = lg;
		this.tag = "";
		this.isSelected = isSelected;
		this.translationID = 0;
		this.id = id;
	}
	
	public BeanLanguageDetails(Long id, String lg, String tag, boolean isSelected) {
		this.language = lg;
		this.tag = tag;
		this.isSelected = isSelected;
		this.translationID = 0;
		this.id = id;
	}
	
	public BeanLanguageDetails(Long id, String lg, String tag, boolean isSelected, int translationID) {
		this.language = lg;
		this.tag = tag;
		this.isSelected = isSelected;
		this.translationID = translationID;
		this.id = id;
	}
	
	public Long getKey() {
		return id;
	}

	public void setKey(Long id) {
		this.id = id;
	}

	public String getLangue() {
		return this.language;
	}
	
	public void setLangue(String langue) {
		this.language = langue;
	}
	
	public Boolean getSelectionner() {
		return this.isSelected;
	}
	
	public void setSelectionner(Boolean selectionner) {
		this.isSelected = selectionner;
	}

	public String getTag() {
		return this.tag;
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
