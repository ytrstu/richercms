package com.sfeir.richercms.main.client.view;



import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.layout.client.Layout.Alignment;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.interfaces.IValidationPanel;

public class ValidationPanel extends ResizeComposite implements IValidationPanel {
	
	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	
	private Button btnAdd = new Button(constants.BtnAdd());
	private Button btnCancel = new Button(constants.BtnCancel());
	
	public ValidationPanel() {
		super();
		createView();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	private void createView() {
		
		FlexTable tab = new FlexTable();
		tab.setCellSpacing(10);
		tab.setWidget(0,0,this.btnAdd);
		tab.setWidget(0,1,this.btnCancel);
		
		LayoutPanel p = new LayoutPanel();
		p.add(tab);
		this.initWidget(p);
	}

	public void enabledButtons() {
		this.btnAdd.setEnabled(true);
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
	
	
}
