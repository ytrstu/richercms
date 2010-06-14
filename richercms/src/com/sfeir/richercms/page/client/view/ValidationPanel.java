package com.sfeir.richercms.page.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;

public class ValidationPanel extends ResizeComposite implements IValidationPanel {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	private Button btnAdd = new Button(constants.BtnAdd());
	private Button btnCancel = new Button(constants.BtnReturn());
	
	public ValidationPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("mainButtonPanel");
		buttonPanel.add(btnAdd);
		buttonPanel.add(btnCancel);

		LayoutPanel p = new LayoutPanel();
		p.add(buttonPanel);

		this.initWidget(p);
	}

	public void enabledButtons() {
		this.btnAdd.setEnabled(true);
		this.btnCancel.setEnabled(true);
	}
	
	public void enabledReturnBtn(){
		this.btnCancel.setEnabled(true);
	}
	
	public void deasableButtons() {
		this.btnAdd.setEnabled(false);
		this.btnCancel.setEnabled(false);
	}

	public HasClickHandlers getClicBtnAdd() {
		return this.btnAdd;
	}

	public HasClickHandlers getClicBtnCancel() {
		return this.btnCancel;
	}
	
	public void setBtnAddText(String text) {
		this.btnAdd.setText(text);
	}

}
