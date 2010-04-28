package com.sfeir.richercms.wizard.shared;

import java.io.Serializable;


/**
 * Contain the displayable elements of a language.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class BeanLanguageDetails implements Serializable {
	
	private String key;
	private String language;
	private String tag;
	private Boolean isSelected;
	private String translationKey;
	
	public BeanLanguageDetails() {
		this.language ="";
		this.tag ="";
		this.isSelected = false;
		this.translationKey = null;
	}
	
	public BeanLanguageDetails(String key, String lg, boolean isSelected) {
		this.language = lg;
		this.tag = "";
		this.isSelected = isSelected;
		this.translationKey = null;
		this.key = key;
	}
	
	public BeanLanguageDetails(String key, String lg, String tag, boolean isSelected) {
		this.language = lg;
		this.tag = tag;
		this.isSelected = isSelected;
		this.translationKey = null;
		this.key = key;
	}
	
	public BeanLanguageDetails(String key, String lg, String tag, boolean isSelected, String translationKey) {
		this.language = lg;
		this.tag = tag;
		this.isSelected = isSelected;
		this.translationKey = translationKey;
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public String getTranslationKey() {
		return this.translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}
}
