package wizardConfig.client.view;

import java.util.ArrayList;
import java.util.List;

import wizardConfig.client.wizardConfigConstants;
import wizardConfig.client.Interface.IdisplayPage2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * view of the second page of the wizard
 * @author homberg.g
 *
 */
public class Page2View extends LayoutPanel implements IdisplayPage2 {
	
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
	private HorizontalPanel add_DelPanel = new HorizontalPanel();
	private PopUpAddLangue popUpAddLanguage = null;
	
	
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

	/**
	 * call by the mvp4g framework to instantiate view (lazy method)
	 */
	public void createView() {
		
		this.popUpAddLanguage = new PopUpAddLangue();
		
		//Title => root 10%
		HTML title = new HTML(this.constants.titlePage2());
		this.add(title);
		this.setWidgetTopHeight(title, 0, Style.Unit.PCT, 10, Style.Unit.PCT);
		
		//LanguageTable
		this.LanguageTable = new FlexTable();
		this.LanguageTable.setCellSpacing(3);
		this.LanguageTable.setCellPadding(3);
		this.LanguageTable.setBorderWidth(1);
		//this.LanguageTable.getColumnFormatter().setWidth(0, "15px");
		
		//ScrollLanguageTable
		this.ScrollLanguageTable.add(this.LanguageTable);
		this.ScrollLanguageTable.setWidth("10%");
		
		//add_DelPanel
		this.add_DelPanel.add(this.btnAddLanguage);
		this.add_DelPanel.add(this.btnDeleteLanguage);
		
		//languagePanel
		this.languagePanel.add(this.ScrollLanguageTable);
		this.languagePanel.setWidgetTopHeight(this.ScrollLanguageTable, 0, Style.Unit.PCT, 80, Style.Unit.PCT);
		this.languagePanel.add(this.add_DelPanel);
		this.languagePanel.setWidgetTopHeight(this.add_DelPanel, 85, Style.Unit.PCT, 10, Style.Unit.PCT);
						
		//languagePanel => root 50%
		this.add(this.languagePanel);
		this.setWidgetTopHeight(this.languagePanel, 10, Style.Unit.PCT, 50, Style.Unit.PCT);
		
		//btnNext => root 3%
		btnNext.setWidth("10%");
		this.add(this.btnNext);
		this.setWidgetTopHeight(this.btnNext, 85, Style.Unit.PCT, 3, Style.Unit.PCT);
		
		//hauteur de la page
		//this.setHeight("200px");
	}
	
	
}
