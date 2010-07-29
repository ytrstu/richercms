package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.richercms.page.client.PageConstants;

/**

 * @author homberg.g
 * PopUp use to add/modify template
 */
public class PopUpAddTemplate extends DialogBox {

	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	public final Button ok = new Button(constants.BtnAdd()); // public pour le passage des evt au presenter
	public final Button cancel = new Button(constants.BtnCancel()); // public pour le passage des evt au presenter
	private final Label lname = new Label("template Name : ");
	public final TextBox tbName = new TextBox(); // public pour le passage des evt au presenter
	private final Label lshortLib = new Label("Short libelle : ");
	public final TextBox tbshortLib = new TextBox(); // public pour le passage des evt au presenter
	private final Label lDesc = new Label("Description : ");
	public final TextArea taDesc = new TextArea(); // public pour le passage des evt au presenter
	private FlexTable mainPanel = new FlexTable();
	private FocusPanel keyBoardPanel;
	
	
	/**
	 * initialize all widget on the PopUp
	 */
    public PopUpAddTemplate() {
    	
    	this.mainPanel.setWidget(0, 0, this.lname);
    	this.mainPanel.setWidget(0, 1, this.tbName);
    	
    	this.mainPanel.setWidget(1, 0, this.lshortLib);
    	this.mainPanel.setWidget(1, 1, this.tbshortLib);
			
    	this.mainPanel.setWidget(2, 0, this.lDesc);
    	this.mainPanel.setWidget(2, 1, this.taDesc);
		
    	this.mainPanel.setWidget(3, 0, this.ok);
    	this.mainPanel.setWidget(3, 1, this.cancel);
    	
    	
		this.mainPanel.setCellSpacing(3);
		this.mainPanel.setCellPadding(3);
		
		this.keyBoardPanel = new FocusPanel(this.mainPanel);
		
		// Set the dialog box's caption.
		this.setText(this.constants.AddTemplateTitle());  
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
    	this.tbName.setText("");
    	this.tbshortLib.setText("");
    	this.taDesc.setText("");
    }
    
    /**
     * show the PopUp
     */
    public void show()
    {
    	super.show();
    	this.tbName.setFocus(true);
    }
    
    public HasKeyPressHandlers getKeyboardEvt() {
    	return this.keyBoardPanel;
    }
    
}

