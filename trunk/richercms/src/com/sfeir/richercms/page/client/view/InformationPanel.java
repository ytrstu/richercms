package com.sfeir.richercms.page.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;

/**
 * Information panel in the PageView, use to create a new page
 * @author homberg.g
 *
 */
public class InformationPanel extends ResizeComposite implements IInformationPanel {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private boolean help = false; // savoir si on active ou non l'aide pour la traduction
	
	private ArrayList<Label> cpyLabelLst = null;
	private ArrayList<Button> cpyButtonLst = null;

	private VerticalPanel verticalContainer = null;
	private ScrollPanel scroll = null;
	private LayoutPanel root;
	// infoTab contain : information fields and tags
	private FlexTable infoTab = null;
	private FlexTable noLgeInfoTab = null;
    private DisclosurePanel advancedDisclosure = null;

	
	private Label Title = null;
	private Label lock = null;
	private Label helpUrlName = null;
	private Label helpPageTitle = null;
	private TextBox tBrowserTitle = null;
	private TextBox tPageTitle = null;
	private TextBox tUrlName = null;
	private TextBox tDescription = null;
	private TextBox tKeyWord = null;
	private DateBox dPublicationStart = null;
	private DateBox dPublicationFinish = null;
	private final static String textBoxWidth = "250px";
	private final int firstTagInTable = 6;
	//parse this to know what tag are selected
	
	private HashMap<Long,Widget> checkORTextBox; // idTag, associated CheckBox or textBox
	private ListBox listTemplate = null;
	
	private boolean EnableCheckBox = false;
	
	
	public InformationPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {

		this.checkORTextBox = new HashMap<Long,Widget>();
		this.lock = new Label("");
		this.lock.setStyleName("lockLabel");
		
		//attach content
		this.verticalContainer = new VerticalPanel();
		//this.verticalContainer.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		// layoutPanel attach content + lockDisplay
		this.root = new LayoutPanel();

		//create the first part : information
		this.createInfoPanel();

		this.deasabledWidgets();
		this.hideAllHelpField();
		
		//masterPanel is scrollable
		this.scroll = new ScrollPanel();
		this.scroll.add(this.verticalContainer);
		
		
		this.root.add(this.scroll);
		this.root.setWidgetTopBottom(this.scroll, 20, Unit.PX, 0, Unit.PX);
		this.root.add(this.lock);
		this.root.setWidgetRightWidth(this.lock, 10, Unit.PX, 300, Unit.PX);
		this.root.setWidgetTopHeight(this.lock, 10, Unit.PX, 20, Unit.PX);
		
		this.initWidget(this.root);
	}
	
	
	private void createInfoPanel(){
		Image img;
		this.cpyLabelLst = new ArrayList<Label>();
		this.cpyButtonLst = new ArrayList<Button>();
		
		this.Title = new Label(this.constants.DefaultTitleInformation());
		this.Title.setStyleName("informationTitle");
		

		this.tBrowserTitle = new TextBox();
		this.tBrowserTitle.setWidth(textBoxWidth);
		this.tPageTitle = new TextBox();
		this.tPageTitle.setWidth(textBoxWidth);
		this.tUrlName = new TextBox();
		this.tUrlName.setWidth(textBoxWidth);
		this.tDescription = new TextBox();
		this.tDescription.setWidth(textBoxWidth);
		this.tKeyWord = new TextBox();
		this.tKeyWord.setWidth(textBoxWidth);
		this.dPublicationStart = new DateBox();
		this.dPublicationStart.setWidth(textBoxWidth);
		this.dPublicationFinish = new DateBox();
		this.dPublicationFinish.setWidth(textBoxWidth);
		
		//display required if user miss them
		this.helpUrlName = new Label(this.constants.ObligationMsg());
		this.helpUrlName.setVisible(false);
		this.helpUrlName.addStyleName("requiredField");
		this.helpPageTitle = new Label(this.constants.ObligationMsg());
		this.helpPageTitle.setVisible(false);
		this.helpPageTitle.addStyleName("requiredField");
		
		for(int i=0; i<4; i++) {
			Button b = new Button();
			b.setText("<");
			this.cpyButtonLst.add(b);
		}
		
		for(int i=0; i<4; i++) {
			this.cpyLabelLst.add(new Label(""));
		}
		
		HorizontalPanel p;
		
		// info in several language
		this.infoTab = new FlexTable();
		// same info for all language
		this.noLgeInfoTab = new FlexTable();
		this.advancedDisclosure = new DisclosurePanel(this.constants.disclosureTitle());
		this.advancedDisclosure.setAnimationEnabled(true);
		this.advancedDisclosure.setContent(this.noLgeInfoTab);

		
		this.infoTab.setWidget(0,0, new Label(constants.BrowserTitle()));
		this.infoTab.setWidget(0,1,this.tBrowserTitle);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessBrowserTitle());
		this.infoTab.setWidget(0,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(0));p.add(this.cpyLabelLst.get(0));
		this.infoTab.setWidget(0,3,p);
		
		this.infoTab.setWidget(1,0, new Label(constants.PageTitle()));
		this.infoTab.setWidget(1,1,this.tPageTitle);
		this.infoTab.setWidget(1,3,this.helpPageTitle);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessPageTitle());
		this.infoTab.setWidget(1,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(1));p.add(this.cpyLabelLst.get(1));
		this.infoTab.setWidget(1,4,p);
		
		this.infoTab.setWidget(2,0, new Label(constants.Description()));
		this.infoTab.setWidget(2,1,this.tDescription);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDescription());
		this.infoTab.setWidget(2,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(2));p.add(this.cpyLabelLst.get(2));
		this.infoTab.setWidget(2,3,p);
		
		this.infoTab.setWidget(3,0, new Label(constants.KeyWord()));
		this.infoTab.setWidget(3,1,this.tKeyWord);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessKeyWord());
		this.infoTab.setWidget(3,2,img);
		p = new HorizontalPanel();
		p.add(this.cpyButtonLst.get(3));p.add(this.cpyLabelLst.get(3));
		this.infoTab.setWidget(3,3,p);
		
		this.noLgeInfoTab.setWidget(0,0, new Label(constants.UrlName()));
		this.noLgeInfoTab.setWidget(0,1,this.tUrlName);
		this.noLgeInfoTab.setWidget(0,3,this.helpUrlName);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessUrlName());
		this.noLgeInfoTab.setWidget(0,2,img);
		
		this.noLgeInfoTab.setWidget(1,0, new Label(constants.PublicationStart()));
		this.noLgeInfoTab.setWidget(1,1,this.dPublicationStart);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDateStart());
		this.noLgeInfoTab.setWidget(1,2,img);
		
		this.noLgeInfoTab.setWidget(2,0, new Label(constants.PublicationFinish()));
		this.noLgeInfoTab.setWidget(2,1,this.dPublicationFinish);
		img = new Image("/tab_images/infoBulle.png");
		img.setTitle(this.constants.infoMessDateStop());
		this.noLgeInfoTab.setWidget(2,2,img);
		
		//template selection
		this.listTemplate = new ListBox();
		this.noLgeInfoTab.setText(3,0,this.constants.ExistingTemplate());
		this.noLgeInfoTab.setWidget(3,1,this.listTemplate);	
		
		//obligation
		this.noLgeInfoTab.setText(4, 0, this.constants.Obligation());
		//tag title
		this.noLgeInfoTab.setText(5, 0, this.constants.PossibleTag());
		
		SimplePanel spTitle = new SimplePanel();
		SimplePanel spTable = new SimplePanel();
		SimplePanel spTable2 = new SimplePanel();
		spTitle.setWidget(this.Title);
		spTitle.addStyleName("informationPanel-Vertical-margin");
		// add in a simple panel for the scrollBar
		spTable.setWidget(this.infoTab);
		spTable.addStyleName("informationPanel-Vertical-margin");
		// add in a simple panel for the scrollBar
		spTable2.setWidget(this.advancedDisclosure);
		spTable2.addStyleName("informationPanel-Vertical-margin");
		
		this.verticalContainer.add(spTitle);
		this.verticalContainer.add(spTable);
		this.verticalContainer.add(spTable2);
		
	}
	
	
	public void addTemplateInList(String name, String id) {
		this.listTemplate.addItem(name, id);
	}
	
	
	public void addTagLine(String id,String TagName,
			String shortLib, String description, boolean textualTag ) {
		
		//tag are added in infoTab
		int numRow = this.noLgeInfoTab.getRowCount();
		HorizontalPanel checkOrTextAndHelp = new HorizontalPanel();
		checkOrTextAndHelp.setSpacing(10);
		
		Image img = new Image("/tab_images/infoBulle.png");
		img.setTitle(description);
		
		
		this.noLgeInfoTab.setText(numRow, 0, shortLib);
		this.noLgeInfoTab.setWidget(numRow, 1, checkOrTextAndHelp);
		
		if(textualTag){
			TextBox tb = new TextBox(); 
			tb.setEnabled(this.EnableCheckBox);
			tb.setName(id);
			tb.setWidth(textBoxWidth);
			this.checkORTextBox.put(new Long(id), tb);
			checkOrTextAndHelp.add(tb);
		}else{
			CheckBox selectTag = new CheckBox();
			selectTag.setEnabled(this.EnableCheckBox);
			selectTag.setName(id);
			this.checkORTextBox.put(new Long(id), selectTag);
			checkOrTextAndHelp.add(selectTag);
		}
		
		checkOrTextAndHelp.add(img);
	}
	
	public List<Long> getSelectedTagsId() {
		ArrayList<Long> lst = new ArrayList<Long>();
		
		for(Widget cb : this.checkORTextBox.values()){
			//checkBox and are checked
			if(cb.getClass().getName().contains("CheckBox") && ((CheckBox)cb).getValue()){
				lst.add(new Long(((CheckBox)cb).getName()));
			} // textbox and are filled
			else if (cb.getClass().getName().contains("TextBox") && ((TextBox)cb).getValue().length() != 0){
				lst.add(new Long(((TextBox)cb).getName()));
			}
		}

		return lst;
	}
	
	public void clearTemplateList() {
		this.listTemplate.clear();
	}
	
	public void clearTagTable() {
		//tag and information are included in same table
		//we remove just tag
		for(int i = (this.noLgeInfoTab.getRowCount()-1); i> (this.firstTagInTable-1) ; i--){
			this.noLgeInfoTab.removeRow(i);
		}
		this.checkORTextBox.clear();
	}
	
	public HasChangeHandlers getTemplateLstSelection() {
		return this.listTemplate;
	}
	
	public String getSelectedTemplateId() {
		if(this.listTemplate.getItemCount() != 0)
			return this.listTemplate.getValue(
					this.listTemplate.getSelectedIndex());
		return null;
	}
	
	public String getCustomValue(Long id) {
		Widget custom = this.checkORTextBox.get(id);
		if(custom.getClass().getName().contains("TextBox"))
			return ((TextBox)custom).getText();
		return null;
	}
	
	public void setCustom(Long tagId, String customName){
		TextBox custom = (TextBox)this.checkORTextBox.get(tagId);
		if(custom != null)
			custom.setText(customName);
	}
	
	public void unCheckAllTags() {
		for(Widget cb : this.checkORTextBox.values())
			if(cb.getClass().getName().contains("CheckBox"))
				((CheckBox)cb).setValue(false);
	}
	
	public void checktag(Long tagId) {
		Widget widget = this.checkORTextBox.get(tagId);
		if(widget.getClass().getName().contains("CheckBox"))
			((CheckBox)widget).setValue(true);
	}
	
	public void enabledWidgets() {
		this.tBrowserTitle.setEnabled(true);
		this.tPageTitle.setEnabled(true);
		this.tUrlName.setEnabled(true);
		this.tDescription.setEnabled(true);
		this.tKeyWord.setEnabled(true);
		this.dPublicationStart.setEnabled(true);
		this.dPublicationFinish.setEnabled(true);
		this.listTemplate.setEnabled(true);
		
		this.EnableCheckBox = true;
		for(Widget cb : this.checkORTextBox.values()){
			if(cb.getClass().getName().contains("CheckBox"))
				((CheckBox)cb).setEnabled(true);
			else // textBox
				((TextBox)cb).setEnabled(true);
			
		}
	}
	
	public void deasabledWidgets() {
		this.tBrowserTitle.setEnabled(false);
		this.tPageTitle.setEnabled(false);
		this.tUrlName.setEnabled(false);
		this.tDescription.setEnabled(false);
		this.tKeyWord.setEnabled(false);
		this.dPublicationStart.setEnabled(false);
		this.dPublicationFinish.setEnabled(false);
		this.listTemplate.setEnabled(false);
		
		this.EnableCheckBox = false;
		for(Widget cb : this.checkORTextBox.values()){
			if(cb.getClass().getName().contains("CheckBox"))
				((CheckBox)cb).setEnabled(false);
			else // textBox
				((TextBox)cb).setEnabled(false);
		}

	}
	
	public void clearFields() {
		this.tBrowserTitle.setText("");
		this.tDescription.setText("");
		this.tKeyWord.setText("");
		this.tPageTitle.setText("");
		this.tUrlName.setText("");
		this.dPublicationFinish.setValue(null);
		this.dPublicationStart.setValue(null);
	}
	
	public void clearTags() {
		for(Widget cb : this.checkORTextBox.values()){
			if(cb.getClass().getName().contains("CheckBox"))
				((CheckBox)cb).setValue(false);
			else // textBox
				((TextBox)cb).setText("");
		}
	}

	public String getBrowserTitle() {
		return tBrowserTitle.getText();
	}


	public String getPageTitle() {
		return tPageTitle.getText();
	}

	public String getUrlName() {
		return tUrlName.getText();
	}

	public String getDescription() {
		return tDescription.getText();
	}

	public String getKeyWord() {
		return tKeyWord.getText();
	}

	public Date getPublicationStart() {
		return dPublicationStart.getValue();
	}

	public Date getPublicationFinish() {
		return dPublicationFinish.getValue();
	}

	public void setBrowserTitle(String browserTitle) {
		this.tBrowserTitle.setText(browserTitle);	
	}

	public void setDescription(String description) {
		this.tDescription.setText(description);
	}

	public void setKeyWord(String keyWord) {
		this.tKeyWord.setText(keyWord);
	}

	public void setPageTitle(String pageTitle) {
		this.tPageTitle.setText(pageTitle);
	}
	
	public void setlockInfo(String pseudo) {
		if(pseudo.length() == 0)
			this.lock.setText("");
		else
			this.lock.setText(this.constants.LockMessage()+pseudo);
	}

	public void setPublicationFinish(Date publicationFinish) {
		this.dPublicationFinish.setValue(publicationFinish);
	}

	public void setPublicationStart(Date publicationStart) {
		this.dPublicationStart.setValue(publicationStart);
	}

	public void setUrlName(String urlName) {
		this.tUrlName.setText(urlName);
	}

	public Widget asWidget() {
		return this;
	}
	
	public void setHelp(String browserTitle, String description, String keyWord, 
			String pageTitle) {
		this.cpyLabelLst.get(0).setText(browserTitle);
		this.cpyLabelLst.get(2).setText(description);
		this.cpyLabelLst.get(3).setText(keyWord);
		this.cpyLabelLst.get(1).setText(pageTitle);
	}
	
	public void hideAllHelpField() {
		for(int i = 0 ; i<4 ; i++) {
			this.cpyButtonLst.get(i).setVisible(false);
			this.cpyLabelLst.get(i).setVisible(false);
		}
	}
	
	public void showOneHelp(int number) {
		if(this.help) {
			this.hideAllHelpField();
			this.cpyButtonLst.get(number).setVisible(true);
			this.cpyLabelLst.get(number).setVisible(true);
		}
	}
	
	public void hideOneHelp(int number) {
		this.cpyButtonLst.get(number).setVisible(false);
		this.cpyLabelLst.get(number).setVisible(false);
	}
	

	
	public void showRequiredTitle() {
		this.helpPageTitle.setVisible(true);
	}
	
	public void showRequiredUrl() {
		this.helpUrlName.setText(this.constants.ObligationMsg());
		this.helpUrlName.setVisible(true);
		this.advancedDisclosure.setOpen(true);
	}
	
	public void hideRequiredField(){
		this.helpPageTitle.setVisible(false);
		this.helpUrlName.setVisible(false);
	}
	
	public void showErrorInUrl(){
		this.helpUrlName.setText(this.constants.ErrorInUrl());
		this.helpUrlName.setVisible(true);
	}
	
	public void hideErrorInUrl(){
		this.helpUrlName.setVisible(false);
	}
	
	public void enableHelp(){
		this.help = true;
	}
	
	public void disableHelp(){
		this.help = false;
		this.hideAllHelpField();
	}
	
	public HasFocusHandlers getFocusOnTB0() {
		return this.tBrowserTitle;
	}
	
	public HasFocusHandlers getFocusOnTB1() {
		return this.tPageTitle;
	}
	
	public HasFocusHandlers getFocusOnTB2() {
		return this.tDescription;
	}
	
	public HasFocusHandlers getFocusOnTB3() {
		return this.tKeyWord;
	}

	
	public HasClickHandlers getclickBtnCpy(int number) {
		return this.cpyButtonLst.get(number);
	}
	
	public void setTitle(String title) {
		this.Title.setText(title);
	}
	
	public String getTitle() {
		return this.Title.getText();
	}
	
	public void cpyHelpInField(int number) {
		switch(number) {
		case 0 :
			this.tBrowserTitle.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 1 :
			this.tPageTitle.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 2 :
			this.tDescription.setText(this.cpyLabelLst.get(number).getText());
			break;
		case 3 :
			this.tKeyWord.setText(this.cpyLabelLst.get(number).getText());
			break;
		}
	}
	
	public PageConstants getConstants() {
		return this.constants;
	}
	
	public void selectTemplate(String id){
		int nbTemplate = this.listTemplate.getItemCount();
		
		for(int i = 0 ; i< nbTemplate; i++){
			if(this.listTemplate.getValue(i).equals(id)){
				this.listTemplate.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void openEnclosurePanel(){
		this.advancedDisclosure.setOpen(true);
	}
	
	public void closeEnclosurePanel(){
		this.advancedDisclosure.setOpen(false);
	}
}
