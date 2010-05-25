package com.sfeir.richercms.wizard.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.richercms.wizard.client.wizardConfigConstants;

/**
 * PopUp using to add a new Language
 * @author homberg.g
 *
 */
public class PopUpAddLangue extends DialogBox {
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	public final Button ok = new Button(constants.buttonAddLanguagePopUp()); // public pour le passage des evt au presenter
	public final Button cancel = new Button(constants.buttonCancelPopUp()); // public pour le passage des evt au presenter
	private final Label labelLanguage = new Label(constants.labelLanguagePopUp());
	public final TextBox textboxLanguage = new TextBox(); // public pour le passage des evt au presenter
	private final Label labelTag = new Label(constants.labelTagPopUp());
	public final TextBox textboxTag = new TextBox(); // public pour le passage des evt au presenter
	private FlexTable mainPanel = new FlexTable();
	private FocusPanel keyBoardPanel;
	
	
	/**
	 * initialize all widget on the PopUp
	 */
    public PopUpAddLangue() {
    	
    	this.mainPanel.setWidget(0, 0, this.labelLanguage);
    	this.mainPanel.setWidget(0, 1, this.textboxLanguage);
			
    	this.mainPanel.setWidget(1, 0, this.labelTag);
    	this.mainPanel.setWidget(1, 1, this.textboxTag);
		
    	this.mainPanel.setWidget(2, 0, this.ok);
    	this.mainPanel.setWidget(2, 1, this.cancel);
    	
    	
		this.mainPanel.setCellSpacing(3);
		this.mainPanel.setCellPadding(3);
		
		this.keyBoardPanel = new FocusPanel(this.mainPanel);
		
		// Set the dialog box's caption.
		this.setText(this.constants.TextPopUp());  
		this.center();
		this.setWidget(this.keyBoardPanel);
		
		//hide directly the popUp
		this.hide();
		
		// Enable animation.
		this.setAnimationEnabled(true);
		  
		// Enable glass background.
		this.setGlassEnabled(true);
    }
    
	/**
	 * Clear all field in the PopUp
	 */
    public void clearField() {
    	this.textboxLanguage.setText("");
    	this.textboxTag.setText("");
    }
    
    /**
     * show the PopUp
     */
    public void show()
    {
    	super.show();
    	this.textboxLanguage.setFocus(true);
    }
    
    public HasKeyPressHandlers getKeyboardEvt() {
    	return this.keyBoardPanel;
    }
    
  }
