package com.sfeir.richercms.page.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IValidationPanel;

/**
 * Validation panel in the PageView
 * This panel contain button to apply or cancel modification/add information
 * It was displayed in the bottom of the PageView
 * @author homberg.g
 *
 */
public class ValidationPanel extends ResizeComposite implements IValidationPanel {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	private Button btnAdd = new Button(constants.BtnAdd());
	private Button btnCancel = new Button(constants.BtnReturn());
	private Button modifyAndRetrun = new Button(constants.BtnModifyAndRetrun());
	private Button modify = new Button(constants.BtnModify());
	
	public ValidationPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("mainButtonPanel");
		buttonPanel.add(this.btnAdd);
		buttonPanel.add(this.modifyAndRetrun);
		buttonPanel.add(this.modify);
		buttonPanel.add(this.btnCancel);

		LayoutPanel p = new LayoutPanel();
		p.add(buttonPanel);

		this.initWidget(p);
	}

	public void showModifyButtons() {
		this.btnAdd.setVisible(true);
		this.modifyAndRetrun.setVisible(true);
		this.btnCancel.setVisible(true);
		this.modify.setVisible(false);
	}
	
	public void showJustModifyBtn() {
		this.btnAdd.setVisible(false);
		this.modifyAndRetrun.setVisible(false);
		this.btnCancel.setVisible(false);
		this.modify.setVisible(true);
	}
	
	public void showAddButtons() {
		this.btnAdd.setVisible(true);
		this.modifyAndRetrun.setVisible(false);
		this.btnCancel.setVisible(true);
		this.modify.setVisible(false);
	}
	
	public void showReturnBtn(){
		this.modify.setEnabled(true);
		this.btnCancel.setVisible(true);
		this.modify.setVisible(false);
	}
	
	public void hideButtons() {
		this.btnAdd.setVisible(false);
		this.modifyAndRetrun.setVisible(false);
		this.btnCancel.setVisible(false);
		this.modify.setVisible(false);
	}
	
	public void enableModifyBtn() {
		this.modify.setEnabled(true);
	}

	public void disableModifyBtn() {
		this.modify.setEnabled(false);
	}
	
	public HasClickHandlers getClicBtnAdd() {
		return this.btnAdd;
	}

	public HasClickHandlers getClicBtnCancel() {
		return this.btnCancel;
	}
	
	public HasClickHandlers getClicBtnSaveAndQ() {
		return this.modifyAndRetrun;
	}
	
	public HasClickHandlers getClicBtnModify() {
		return this.modify;
	}
	
	public void setBtnAddText(String text) {
		this.btnAdd.setText(text);
	}

	public PageConstants getConstants() {
		return this.constants;
	}
}
