package com.sfeir.richercms.wizardConfig.shared;

import java.io.Serializable;


/**
 * Contain the displayable elements of a language.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class BeanLanguageDetails implements Serializable {
	
	private String language;
	private Boolean isSelected;
	
	
	public BeanLanguageDetails() {
		this.language ="";
		this.isSelected = false;
	}
	
	public BeanLanguageDetails(String lg, boolean isSelected) {
		this.language = lg;
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
	
}
