package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.sfeir.richercms.page.client.PageConstants;

/**
 * @author homberg.g
 * PopUp used for add
 */
public class PopUpAddTag  extends DialogBox{

	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	public final Button ok = new Button(constants.BtnAdd()); // public pour le passage des evt au presenter
	public final Button cancel = new Button(constants.BtnCancel()); // public pour le passage des evt au presenter
	
	private FlexTable mainPanel = new FlexTable();
	private FocusPanel keyBoardPanel;
	
	private TextBox newTagName;
	private TextBox newShortLib;
	private TextArea newDescription;
	private CheckBox tagTextuel;
	
	
	/**
	 * initialize all widget on the PopUp
	 */
    public PopUpAddTag() {
   
		this.mainPanel = new FlexTable();
		
		//title
		Label titleAdd = new Label(this.constants.AddNewTag());
		titleAdd.setStyleName("informationTitle");
		
		//label
		this.mainPanel.setText(0, 0, this.constants.LibTagName());
		this.mainPanel.setText(1, 0, this.constants.LibShrotLib());
		this.mainPanel.setText(2, 0, this.constants.Libdesc());
		
		//texBox
		this.newTagName = new TextBox();
		this.newShortLib = new TextBox();
		this.newDescription = new TextArea();
		this.newDescription.setVisibleLines(3);
		this.tagTextuel = new CheckBox(this.constants.TbTextual());
		this.mainPanel.setWidget(0, 1, this.newTagName);
		this.mainPanel.setWidget(1, 1, this.newShortLib);
		this.mainPanel.setWidget(2, 1, this.newDescription);
		this.mainPanel.setWidget(3, 1, this.tagTextuel);
		
    	this.mainPanel.setWidget(4, 0, this.ok);
    	this.mainPanel.setWidget(4, 1, this.cancel);
    	
    	
		this.mainPanel.setCellSpacing(3);
		this.mainPanel.setCellPadding(3);
		
		this.keyBoardPanel = new FocusPanel(this.mainPanel);
		
		// Set the dialog box's caption.
		this.setText(this.constants.AddTagTitle());  
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
		this.newTagName.setText("");
		this.newShortLib.setText("");
		this.newDescription.setText("");
		this.tagTextuel.setValue(false);
    }
    
    /**
     * show the PopUp
     */
    public void show()
    {
    	super.show();
    	this.newTagName.setFocus(true);
    }
    
    public HasKeyPressHandlers getKeyboardEvt() {
    	return this.keyBoardPanel;
    }

	public String getNewTagNameText() {
		return newTagName.getText();
	}

	public void setNewTagNameText(String newTagName) {
		this.newTagName.setText(newTagName);
	}

	public String getNewShortLibText() {
		return newShortLib.getText();
	}

	public void setNewShortLibText(String newShortLib) {
		this.newShortLib.setText(newShortLib);
	}

	public String getNewDescriptionText() {
		return newDescription.getText();
	}

	public void setNewDescriptionText(String newDescription) {
		this.newDescription.setText(newDescription);
	}

	public Boolean getTagTextuelValue() {
		return tagTextuel.getValue();
	}

	public void setTagTextuelValue(Boolean tagTextuel) {
		this.tagTextuel.setValue(tagTextuel);
	}

}
