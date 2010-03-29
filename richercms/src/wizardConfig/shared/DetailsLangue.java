package wizardConfig.shared;

import java.io.Serializable;



public class DetailsLangue implements Serializable
{
	private String langue;
	private Boolean selectionner;
	
	
	public DetailsLangue()
	{
		this.langue ="";
		this.selectionner = false;
	}
	
	public DetailsLangue(String lg, boolean isSelected)
	{
		this.langue = lg;
		this.selectionner = isSelected;
	}
	
	public String getLangue() {
		return this.langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public Boolean getSelectionner() {
		return this.selectionner;
	}
	public void setSelectionner(Boolean selectionner) {
		this.selectionner = selectionner;
	}
	
}
