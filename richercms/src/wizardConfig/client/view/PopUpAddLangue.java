package wizardConfig.client.view;

import wizardConfig.client.wizardConfigConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PopUpAddLangue extends DialogBox {
	
	public final Button ok = new Button("OK"); // public pour le passage des evt au presenter
	public final Button cancel = new Button("Cancel"); // public pour le passage des evt au presenter
	private final Label label = new Label("Langue : ");
	public final TextBox textbox = new TextBox(); // public pour le passage des evt au presenter
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel valuePanel = new HorizontalPanel();
	private HorizontalPanel buttonPanel = new HorizontalPanel();
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
    public PopUpAddLangue() {
    	this.valuePanel.add(this.label);
    	this.valuePanel.add(this.textbox);
    	
    	this.buttonPanel.add(this.ok);
    	this.buttonPanel.add(this.cancel);
    	
    	this.mainPanel.add(this.valuePanel);
    	this.mainPanel.add(this.buttonPanel);
    	
      // Set the dialog box's caption.
      this.setText(this.constants.TextPopUp());

      // Enable animation.
      this.setAnimationEnabled(true);
      
      // Enable glass background.
      this.setGlassEnabled(true);
      
      this.center();

      this.setWidget(this.mainPanel);
    }
    
	/**
	 * Clear all field in the PopUp
	 */
    public void clearField() {
    	this.textbox.setText("");
    }
    
  }
