package com.sfeir.richercms.wizard.client.view;

import java.util.ArrayList;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.client.view.PopUpWait;
import com.sfeir.richercms.wizard.client.wizardConfigConstants;
import com.sfeir.richercms.wizard.client.Interface.IdisplayPage2;

/**
 * view of the second page of the wizard
 * @author homberg.g
 *
 */
public class Page2View extends ResizeComposite implements IdisplayPage2 {
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnNext = new Button(constants.buttonNext());
	private Button btnAddLanguage = new Button(constants.buttonAddLanguage());
	private Button btnDeleteLanguage = new Button(constants.buttonDelLanguage());
	private ListBox languageList = new ListBox();
	private FlexTable LanguageTable;
	private ScrollPanel ScrollLanguageTable = new ScrollPanel();
	private LayoutPanel languagePanel = new LayoutPanel();
	private FlowPanel add_DelPanel = new FlowPanel();
	private PopUpAddLangue popUpAddLanguage = null;
	private PopUpWait popUpWait = null;
	
	
	public Page2View() {	
		super();
	}
	

	public Widget asWidget() {	
		return this;
	}

	public HasClickHandlers getNextButton() {	
		return this.btnNext;
	}
	
	public HasClickHandlers getAddButton() {	
		return this.btnAddLanguage;
	}
	
	public HasClickHandlers getDelButton() {
		return this.btnDeleteLanguage;
	}
	
	public HasClickHandlers getPopUpBtnOk() {
		return this.popUpAddLanguage.ok;
	}
	
	public HasClickHandlers getPopUpBtnCancel() {
		return this.popUpAddLanguage.cancel;
	}

	public void showPopUpAddLanguage() {
			
		// on vide les champs avant de re-afficher
		this.popUpAddLanguage.clearField();
		this.popUpAddLanguage.show();
	}
	
	public void showPopUpWait() {
		
		this.popUpWait.show();
	}

	public void hidePopUpAddLanguage() {
		this.popUpAddLanguage.hide();
	}
	
	public void hidePopUpWait() {
		this.popUpWait.hide();
	}

	public void setLanguageList(ArrayList<String> languages) {
		this.languageList.clear();
		for (int i = 0; i < languages.size(); ++i) 
		{
			this.languageList.addItem(languages.get(i));
		}
	}
	
	public String getPopUpNewLanguage(){
		return this.popUpAddLanguage.textbox.getText();
	}
	
	public void setLanguageTable(ArrayList<String> languages) {
		
		this.LanguageTable.removeAllRows();
		
		for (int i = 0; i < languages.size(); ++i) {
			this.LanguageTable.setWidget(i, 0, new CheckBox());
			this.LanguageTable.setText(i, 1, languages.get(i));
		}
	}
	
	public List<Integer> getSelectedLanguage() {
		
		List<Integer> selectedRows = new ArrayList<Integer>();
		   
		for (int i = 0; i < this.LanguageTable.getRowCount(); ++i) {
			CheckBox checkBox = (CheckBox)this.LanguageTable.getWidget(i, 0);
			
			if (checkBox.getValue()){
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	public void setSelectedLanguage(int id) {
		
		RadioButton RadioButton = (RadioButton)this.LanguageTable.getWidget(id, 0);
		RadioButton.setValue(true);
	}


	public void addLanguage(String language, boolean checked) {
		
		RadioButton radio = new RadioButton("");
		radio.setValue(checked);
		this.LanguageTable.setWidget(this.LanguageTable.getRowCount(), 0, radio);
		// on doit faire this.LanguageTable.getRowCount()-1 car la ligne a �t� cr�er juste au dessus
		this.LanguageTable.setText(this.LanguageTable.getRowCount()-1, 1, language);
	}

	public void clearTableLanguage() {
		this.LanguageTable.removeAllRows();
	}

	/**
	 * call by the mvp4g framework to instantiate view (lazy method)
	 */
	public void createView() {
		
		this.popUpAddLanguage = new PopUpAddLangue();
		this.popUpWait = new PopUpWait();
		
		//Title => root 10%
		LayoutPanel mainContent = new LayoutPanel();
		Label title = new Label(this.constants.titlePage2());
		//is wrapped by the composite (super)
		CenterLayoutPanel mainPanel = new CenterLayoutPanel(800, 400, title, mainContent);
		
		//LanguageTable
		this.LanguageTable = new FlexTable();
		this.LanguageTable.setCellSpacing(3);
		this.LanguageTable.setCellPadding(3);
		//this.LanguageTable.getColumnFormatter().setWidth(0, "15px");
		
		//ScrollLanguageTable
		this.ScrollLanguageTable.add(this.LanguageTable);
		
		//add_DelPanel
		this.btnAddLanguage.addStyleName("buttonMarginRight");
		this.add_DelPanel.add(this.btnAddLanguage);
		this.add_DelPanel.add(this.btnDeleteLanguage);
		
		//languagePanel
		this.languagePanel.add(this.ScrollLanguageTable);
		this.languagePanel.setWidgetTopHeight(this.ScrollLanguageTable, 0, Style.Unit.PX, 260, Style.Unit.PX);
		this.languagePanel.add(this.add_DelPanel);
		this.languagePanel.setWidgetBottomHeight(this.add_DelPanel, 50, Style.Unit.PX, 30, Style.Unit.PX);
						
		//languagePanel => root 50%
		mainContent.add(this.languagePanel);
		//mainContent.setWidgetTopHeight(this.languagePanel, 100, Style.Unit.PCT, 100, Style.Unit.PCT);
		
		// Next button
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(btnNext);
		mainContent.add(buttonPanel);
		mainContent.setWidgetBottomHeight(buttonPanel, 0, Style.Unit.PX, 28, Style.Unit.PX);
		
		
		initWidget(mainPanel);
	}
	
	
}
