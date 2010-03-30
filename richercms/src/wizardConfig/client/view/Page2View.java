package wizardConfig.client.view;

import java.util.ArrayList;
import java.util.List;

import wizardConfig.client.wizardConfigConstants;
import wizardConfig.client.Interface.IdisplayPage2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * view of the second page of the wizard
 * @author homberg.g
 *
 */
public class Page2View extends DockLayoutPanel implements IdisplayPage2 {
	
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnNext = new Button(constants.buttonNext());
	private Button btnAddLanguage = new Button(constants.buttonAddLanguage());
	private Button btnDeleteLanguage = new Button(constants.buttonDelLanguage());
	private ListBox languageList = new ListBox();
	private FlexTable LanguageTable;
	private VerticalPanel languagePanel = new VerticalPanel();
	private HorizontalPanel add_DelPanel = new HorizontalPanel();
	private PopUpAddLangue popUpAddLanguage = new PopUpAddLangue();
	
	
	public Page2View() {
		
		super(Unit.PX);
		
	    this.popUpAddLanguage.hide();
	    
	    this.LanguageTable = new FlexTable();
	    this.LanguageTable.setCellSpacing(0);
	    this.LanguageTable.setCellPadding(0);
	    this.LanguageTable.setWidth("100%");
	    this.LanguageTable.addStyleName("contacts-ListContents");
	    this.LanguageTable.getColumnFormatter().setWidth(0, "15px");
	    
	    this.add_DelPanel.add(this.btnAddLanguage);
	    this.add_DelPanel.add(this.btnDeleteLanguage);
	    
	    this.languagePanel.add(this.LanguageTable);
	    this.languagePanel.add(this.add_DelPanel);

	    this.addSouth(this.btnNext, 30);
	    this.addNorth(new HTML(this.constants.titlePage2()),30);
	    this.add(this.languagePanel);

	    //hauteur de la page
	    this.setHeight("200px");
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
	

	public void hidePopUpAddLanguage() {
		this.popUpAddLanguage.hide();
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
		
		CheckBox checkBox = (CheckBox)this.LanguageTable.getWidget(id, 0);
		checkBox.setValue(true);
	}


	public void addLanguage(String language, boolean checked) {
		
		CheckBox box = new CheckBox();
		box.setValue(checked);
		this.LanguageTable.setWidget(this.LanguageTable.getRowCount(), 0, box);
		// on doit faire this.LanguageTable.getRowCount()-1 car la ligne a �t� cr�er juste au dessus
		this.LanguageTable.setText(this.LanguageTable.getRowCount()-1, 1, language);
	}

	public void clearTableLanguage() {
		this.LanguageTable.removeAllRows();
	}
	
}
