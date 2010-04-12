package com.sfeir.richercms.wizard.shared;

import java.io.Serializable;


/**
 * Contain the displayable elements of a language.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class BeanLanguageDetails implements Serializable {
	
	private String language;
	private String tag;
	private Boolean isSelected;
	
	
	public BeanLanguageDetails() {
		this.language ="";
		this.tag ="";
		this.isSelected = false;
	}
	
	public BeanLanguageDetails(String lg, boolean isSelected) {
		this.language = lg;
		this.tag = "";
		this.isSelected = isSelected;
	}
	
	public BeanLanguageDetails(String lg, String tag, boolean isSelected) {
		this.language = lg;
		this.tag = tag;
		this.isSelected = isSelected;
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
	
	
}
