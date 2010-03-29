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
public class Page2View extends DockLayoutPanel implements IdisplayPage2
{
	//gestion des langues
	private wizardConfigConstants constants = GWT.create(wizardConfigConstants.class);
	
	//widget de la fenetre
	private Button btnSuivant = new Button(constants.buttonNext());
	private Button btnAjoutLangue = new Button(constants.buttonAddLanguage());
	private Button btnDeleteLangue = new Button(constants.buttonDelLanguage());
	private ListBox listeLangue = new ListBox();
	private FlexTable LanguageTable;
	private VerticalPanel PanelLangue = new VerticalPanel();
	private HorizontalPanel PanelAdd_Del = new HorizontalPanel();
	private PopUpAddLangue popUpAddLangue = new PopUpAddLangue();
	
	
	public Page2View()
	{

		super(Unit.PX);
		
	    this.popUpAddLangue.hide();
	    
	    this.LanguageTable = new FlexTable();
	    this.LanguageTable.setCellSpacing(0);
	    this.LanguageTable.setCellPadding(0);
	    this.LanguageTable.setWidth("100%");
	    this.LanguageTable.addStyleName("contacts-ListContents");
	    this.LanguageTable.getColumnFormatter().setWidth(0, "15px");
	    
	    this.PanelAdd_Del.add(this.btnAjoutLangue);
	    this.PanelAdd_Del.add(this.btnDeleteLangue);
	    
	    this.PanelLangue.add(this.LanguageTable);
	    this.PanelLangue.add(this.PanelAdd_Del);

	    this.addSouth(this.btnSuivant, 30);
	    this.addNorth(new HTML(this.constants.titlePage2()),30);
	    this.add(this.PanelLangue);

	    //hauteur de la page
	    this.setHeight("200px");
	}
	

	public Widget asWidget() 
	{
		return this;
	}

	public HasClickHandlers getNextButton() 
	{
		return this.btnSuivant;
	}
	
	public HasClickHandlers getAddButton() 
	{
		return this.btnAjoutLangue;
	}
	
	public HasClickHandlers getDelButton() 
	{
		return this.btnDeleteLangue;
	}
	
	public HasClickHandlers getPopUpBtnOk() 
	{
		return this.popUpAddLangue.ok;
	}
	
	public HasClickHandlers getPopUpBtnCancel() 
	{
		return this.popUpAddLangue.cancel;
	}

	public void showPopUpAddLangue() 
	{
		// on vide les champs avant de re-afficher
		this.popUpAddLangue.clearField();
		this.popUpAddLangue.show();
	}
	

	public void hidePopUpAddLangue() 
	{
		this.popUpAddLangue.hide();
	}

	public void setListeLangue(ArrayList<String> langues) 
	{
		this.listeLangue.clear();
		for (int i = 0; i < langues.size(); ++i) 
		{
			this.listeLangue.addItem(langues.get(i));
		}
	}
	
	public String getPopUpNewLanguage()
	{
		return this.popUpAddLangue.textbox.getText();
	}
	
	public void setTableLangue(ArrayList<String> langues) 
	{
		this.LanguageTable.removeAllRows();
		
		for (int i = 0; i < langues.size(); ++i) 
		{
			this.LanguageTable.setWidget(i, 0, new CheckBox());
			this.LanguageTable.setText(i, 1, langues.get(i));
		}
	}
	
	public List<Integer> getLangueSelectionner() 
	{
		List<Integer> selectedRows = new ArrayList<Integer>();
		   
		for (int i = 0; i < this.LanguageTable.getRowCount(); ++i) 
		{
			CheckBox checkBox = (CheckBox)this.LanguageTable.getWidget(i, 0);
			
			if (checkBox.getValue())
			{
				selectedRows.add(i);
			}
		}
		return selectedRows;
	}

	public void setLangueSelectionner(int id) 
	{
		CheckBox checkBox = (CheckBox)this.LanguageTable.getWidget(id, 0);
		checkBox.setValue(true);
	}


	public void addLangue(String langue, boolean checked) 
	{
		CheckBox box = new CheckBox();
		box.setValue(checked);
		this.LanguageTable.setWidget(this.LanguageTable.getRowCount(), 0, box);
		// on doit faire this.LanguageTable.getRowCount()-1 car la ligne a été créer juste au dessus
		this.LanguageTable.setText(this.LanguageTable.getRowCount()-1, 1, langue);
	}

	public void clearTableLanguage()
	{
		this.LanguageTable.removeAllRows();
	}
	
}
