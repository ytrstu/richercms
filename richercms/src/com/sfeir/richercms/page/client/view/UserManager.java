package com.sfeir.richercms.page.client.view;



import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.sfeir.richercms.page.client.ImageAndId;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.IUserManager;

public class UserManager  extends ResizeComposite implements IUserManager {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private DockLayoutPanel mainContainer;
	private FlexTable userTable;
	private Button addNewUser;
	private TextBox newEmailAdress;
	
	private VerticalPanel currentLockedPage;
	private VerticalPanel currentAdminPanel;
	
	public Widget asWidget() {
		return this;
	}

	public void createView() {
		this.mainContainer = new DockLayoutPanel(Unit.PX);
		
		//title
		Label title = new Label("Gestion des utilisateur");
		title.setStyleName("informationTitle");
		this.mainContainer.addNorth(title, 60);
		
		//userTable
		this.userTable = new FlexTable();
		this.userTable.addStyleName("userTable");
		this.userTable.setCellPadding(5);
		this.userTable.setBorderWidth(2);
		this.addUserTableTitle();

		//AddUserPanel
		LayoutPanel addUserPanel = new LayoutPanel();
		Label titleAdd = new Label("Ajouter un nouvelle utilisateur");
		titleAdd.setStyleName("informationTitle");
		Label intitule = new Label("Nouvelle adresse email : ");
		this.newEmailAdress = new TextBox();
		this.addNewUser = new Button("Ajouté cette utilisateur");
		
		//title : add new User
		addUserPanel.add(titleAdd);
		addUserPanel.setWidgetTopHeight(titleAdd, 5, Unit.PX, 20, Unit.PCT);
		
		//label for the textBox
		addUserPanel.add(intitule);
		addUserPanel.setWidgetTopHeight(intitule, 51, Unit.PCT, 30, Unit.PCT);
		addUserPanel.setWidgetLeftWidth(intitule, 20, Unit.PX, 150, Unit.PX);
		
		addUserPanel.add(this.newEmailAdress);
		addUserPanel.setWidgetTopHeight(this.newEmailAdress, 50, Unit.PCT, 30, Unit.PCT);
		addUserPanel.setWidgetLeftWidth(this.newEmailAdress, 170, Unit.PX, 200, Unit.PX);
		addUserPanel.add(this.addNewUser);
		addUserPanel.setWidgetTopHeight(this.addNewUser, 45, Unit.PCT, 20, Unit.PCT);
		addUserPanel.setWidgetLeftWidth(this.addNewUser, 380, Unit.PX, 100, Unit.PX);
		
		this.mainContainer.addSouth(addUserPanel, 200);
		
		ScrollPanel scrollUser = new ScrollPanel();
		scrollUser.setWidget(this.userTable);
		this.mainContainer.add(scrollUser);
		
		this.initWidget(this.mainContainer);
	}
	
	/**
	 * add title of each columns of the Language table
	 */
	private void addUserTableTitle() {
		CellFormatter cellFormater = this.userTable.getCellFormatter();
		cellFormater.setStyleName(0, 0, "userTableHeader");
		cellFormater.setStyleName(0, 1, "userTableHeader");
		cellFormater.setStyleName(0, 2, "userTableHeader");
		cellFormater.setStyleName(0, 3, "userTableHeader");
		cellFormater.setStyleName(0, 4, "userTableHeader");
		cellFormater.setStyleName(0, 5, "userTableHeader");
		this.userTable.setText(0, 0, this.constants.UserTitleColumn1());
		this.userTable.setText(0, 1, this.constants.UserTitleColumn2());
		this.userTable.setText(0, 2, this.constants.UserTitleColumn3());
		this.userTable.setText(0, 3, this.constants.UserTitleColumn4());
		this.userTable.setText(0, 4, this.constants.UserTitleColumn5());
		this.userTable.setText(0, 5, this.constants.UserTitleColumn6());
	}
	
	public void clearUserTable() {
		this.userTable.removeAllRows();
		this.addUserTableTitle();
	}
	
	public void clearAddUserTextBox() {
		this.addNewUser.setText("");
	}
	
	public HasClickHandlers addLine(String email, String nickName, String state) {
		// j'instancie les 2 panneaux et je les add vide dans la table
		// c'est les 2 fonction : addAdminWidget et addLockedPage qui les remplirons
		// c'est aussi c'est fonction qui vont laisser la main au presenter pour gèrer les events
		this.currentLockedPage = new VerticalPanel();
		this.currentAdminPanel = new VerticalPanel();
		
		Image btnDel = new Image("tab_images/trans.png");
		btnDel.addStyleName("deleteStyle");
		
		int numRow = this.userTable.getRowCount();
		Label labelState = new Label(state);
		
		if(state.equals("OnLine"))
			labelState.addStyleName("onLineText");
		else
			labelState.addStyleName("offLineText");
		
		
		this.userTable.setText(numRow, 0, email);
		this.userTable.setText(numRow, 1, nickName);
		this.userTable.setWidget(numRow, 2, labelState);
		this.userTable.setWidget(numRow, 3, this.currentLockedPage);
		this.userTable.setWidget(numRow, 4, this.currentAdminPanel);
		this.userTable.setWidget(numRow, 5, btnDel);
		
		return btnDel;
	}

	public HasValueChangeHandlers<Boolean> addAdminWidgetYes(boolean admin) {
		int numRow = this.userTable.getRowCount()-1;
		RadioButton yes = new RadioButton("row"+numRow,"yes");
		
		this.currentAdminPanel.add(yes);
		
		if(admin)
			yes.setValue(true);

		return yes;
	}
	
	public HasValueChangeHandlers<Boolean> addAdminWidgetNo(boolean admin) {
		int numRow = this.userTable.getRowCount()-1;
		RadioButton no = new RadioButton("row"+numRow,"no");
		
		this.currentAdminPanel.add(no);
		
		if(!admin)
			no.setValue(true);
		return no;
	}
	
	public HasClickHandlers addLockedPage(Long pageId, String pagename) {
		HorizontalPanel p = new HorizontalPanel();
		p.add(new Label(pagename));
		ImageAndId img = new ImageAndId("/tab_images/trans.png",pageId);
		img.addStyleName("lockedStyle");
		p.add(img);
		
		this.currentLockedPage.add(p);
		return img;
	}

	public HasClickHandlers onAddNewUserClick() {
		return this.addNewUser;
	}
	
	public String getNewEmail() {
		return this.newEmailAdress.getText();
	}
	
	public PageConstants getConstants(){
		return this.constants;
	}
}
