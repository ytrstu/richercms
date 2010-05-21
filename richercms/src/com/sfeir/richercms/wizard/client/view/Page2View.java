package com.sfeir.richercms.wizard.client.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
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
	private Button btnNext = null;
	private Button btnPrevious = null;
	private Button btnAddLanguage = null;
	private FlexTable languageTable = null;
	private ScrollPanel scrollLanguageTable = null;
	private LayoutPanel languagePanel = null;
	private FlowPanel add_DelPanel = null;
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
	
	public HasClickHandlers getPreviousButton() {
		return this.btnPrevious;
	}
	
	public HasClickHandlers getAddButton() {	
		return this.btnAddLanguage;
	}
	
	public HasClickHandlers getPopUpBtnOk() {
		return this.popUpAddLanguage.ok;
	}
	
	public HasClickHandlers getPopUpBtnCancel() {
		return this.popUpAddLanguage.cancel;
	}
	
	public HasKeyPressHandlers kBPopUpBtnOk()
	{
		return this.popUpAddLanguage.ok;
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

	
	public String getPopUpNewLanguage(){
		return this.popUpAddLanguage.textboxLanguage.getText();
	}
	
	public String getPopUpNewTag(){
		return this.popUpAddLanguage.textboxTag.getText();
	}
	
	
	public int getSelectedLanguage() {

		for (int i = 1; i < this.languageTable.getRowCount(); ++i) {
			RadioButton radioBtn = (RadioButton)this.languageTable.getWidget(i, 0);
			
			if (radioBtn.getValue()){
				//i-1 : due to the title row
				return (i-1);
			}
		}
		return -1;
	}

	public void setSelectedLanguage(int id) {
		
		RadioButton RadioButton = (RadioButton)this.languageTable.getWidget(id, 0);
		RadioButton.setValue(true);
	}


	public HasClickHandlers addLanguage(String language, String tag, boolean checked) {
		
		RadioButton radio = new RadioButton("");
		PushButton btnDel = new PushButton( new Image("tab_images/Delete-icon.png"));
		radio.setValue(checked);
		this.languageTable.setWidget(this.languageTable.getRowCount(), 0, radio);
		// on doit faire this.LanguageTable.getRowCount()-1 car la ligne a �t� cr�er juste au dessus
		this.languageTable.setText(this.languageTable.getRowCount()-1, 1, language);
		//tag
		this.languageTable.setText(this.languageTable.getRowCount()-1, 2, tag);
		// the delBnt
		this.languageTable.setWidget(this.languageTable.getRowCount()-1, 3, btnDel);
		return btnDel;
	}


	public void clearTableLanguage() {
		this.languageTable.removeAllRows();
		this.addLanguageTableTitle();
	}

	/**
	 * call by the mvp4g framework to instantiate view (lazy method)
	 */
	public void createView() {
		
		this.popUpAddLanguage = new PopUpAddLangue();
		this.popUpWait = new PopUpWait();
		this.btnNext = new Button(constants.buttonNext());
		this.btnPrevious = new Button(constants.buttonPrevious());
		this.btnAddLanguage = new Button(constants.buttonAddLanguage());
		this.scrollLanguageTable = new ScrollPanel();
		this.languagePanel = new LayoutPanel();
		this.add_DelPanel = new FlowPanel();
		
		//Title => root 10%
		LayoutPanel mainContent = new LayoutPanel();
		Label title = new Label(this.constants.titlePage2());
		//is wrapped by the composite (super)
		CenterLayoutPanel mainPanel = new CenterLayoutPanel(800, 400, title, mainContent);
		
		//LanguageTable
		this.languageTable = new FlexTable();
		this.languageTable.addStyleName("lgTable");
		this.languageTable.setCellSpacing(0);
		this.languageTable.setCellPadding(5);
		this.addLanguageTableTitle();
		
		//this.LanguageTable.getColumnFormatter().setWidth(0, "15px");
		
		//ScrollLanguageTable
		this.scrollLanguageTable.add(this.languageTable);
		
		//add_DelPanel
		this.btnAddLanguage.addStyleName("buttonMarginRight");
		this.add_DelPanel.add(this.btnAddLanguage);
		
		//languagePanel
		this.languagePanel.add(this.scrollLanguageTable);
		this.languagePanel.setWidgetTopHeight(this.scrollLanguageTable, 0, Style.Unit.PX, 260, Style.Unit.PX);
		this.languagePanel.add(this.add_DelPanel);
		this.languagePanel.setWidgetBottomHeight(this.add_DelPanel, 50, Style.Unit.PX, 30, Style.Unit.PX);
						
		//languagePanel => root 50%
		mainContent.add(this.languagePanel);
		//mainContent.setWidgetTopHeight(this.languagePanel, 100, Style.Unit.PCT, 100, Style.Unit.PCT);
		
		// Next button
		FlowPanel buttonPanel = new FlowPanel();
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(btnPrevious);
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.add(btnNext);
		mainContent.add(buttonPanel);
		mainContent.setWidgetBottomHeight(buttonPanel, 0, Style.Unit.PX, 28, Style.Unit.PX);
		initWidget(mainPanel);
	}
	
	/**
	 * add title of each columns of the Language table
	 */
	public void addLanguageTableTitle() {
		CellFormatter cellFormater = this.languageTable.getCellFormatter();
		cellFormater.setStyleName(0, 0, "lgTableHeader");
		cellFormater.setStyleName(0, 1, "lgTableHeader");
		cellFormater.setStyleName(0, 2, "lgTableHeader");
		cellFormater.setStyleName(0, 3, "lgTableHeader");
		this.languageTable.setText(0, 0, this.constants.LanguageTitleColumn1());
		this.languageTable.setText(0, 1, this.constants.LanguageTitleColumn2());
		this.languageTable.setText(0, 2, this.constants.LanguageTitleColumn3());
		this.languageTable.setText(0, 3, this.constants.LanguageTitleColumn4());
	}
	
	public int getCurrentNumRow()
	{
		//the last line = number of row -1 - (title row)
		return this.languageTable.getRowCount() - 2;
	}
	
	public wizardConfigConstants getConstant()
	{
		return this.constants;
	}
}
