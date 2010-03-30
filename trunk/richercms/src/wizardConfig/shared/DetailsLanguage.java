package wizardConfig.shared;

import java.io.Serializable;



public class DetailsLanguage implements Serializable {
	
	private String language;
	private Boolean isSelected;
	
	
	public DetailsLanguage() {
		this.language ="";
		this.isSelected = false;
	}
	
	public DetailsLanguage(String lg, boolean isSelected) {
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
