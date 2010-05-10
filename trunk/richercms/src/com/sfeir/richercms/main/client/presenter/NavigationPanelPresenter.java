package com.sfeir.richercms.main.client.presenter;

import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.view.HorizontalEventPanel;
import com.sfeir.richercms.main.client.view.NavigationPanel;
import com.sfeir.richercms.main.shared.BeanArboPage;


@Presenter( view = NavigationPanel.class)
public class NavigationPanelPresenter extends LazyPresenter<INavigationPanel, MainEventBus>{

	private TreeItem selectedItem = null; // current selected Item in tree
	private TreeItem expandedItem = null; // current exanded Item in tree
	private ArboPageServiceAsync rpcPage = null;
	private String rootKey = null;
	
	public NavigationPanelPresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
		view.getSelectedEvtTree()
		.addSelectionHandler(new SelectionHandler<TreeItem>(){
			public void onSelection(SelectionEvent<TreeItem> event) {
				setSelectedItem(event.getSelectedItem()); // fait des actions spécifique	
				eventBus.displayPage((String) selectedItem.getUserObject());
			}
		});
		
		view.getExpandedEvtTree().addOpenHandler(new OpenHandler<TreeItem>(){
			public void onOpen(OpenEvent<TreeItem> event) {
				expandedItem = event.getTarget();
				// on ajoute les fils uniquement si sa n'a pas déjà été fait
				if(((String)(expandedItem.getChild(0).getUserObject())).equals("Loading"));
					AddChildInTree();
			}
		});
		
		// commande pour la suppression d'une page
		this.view.getPopUpMenuBar().setDelPageCommand(new Command(){
			public void execute() {
				deletePage();
			}});
		// commande pour l'ajout d'une sous-page
		this.view.getPopUpMenuBar().setAddPageCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.eventBus.addPage((String) selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
		
		// commande pour la modification d'une page
		this.view.getPopUpMenuBar().setModifyPageCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.eventBus.modifyPage((String) selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
	}

	/**
	 * Use this when a new Item in the tree is selected.
	 * @param selectedItem
	 */
	public void setSelectedItem(TreeItem selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	/**
	 * delete page selected in the tree
	 */
	public void deletePage() {	
		view.getPopUpMenuBar().hide();
		// on ne delete pas la main Page
		if(!((String) selectedItem.getUserObject()).equals(rootKey)) {
			// on commence donc la suppression
			NavigationPanelPresenter.this.eventBus.deletePage();
			this.rpcPage.deleteArboPage((String)selectedItem.getUserObject(), (String)selectedItem.getParentItem().getUserObject(), new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					view.deleteSelectedTI();
					selectedItem = selectedItem.getParentItem();
					eventBus.deletingPageFinish(true); // suppression finis : on peut hide la popUp
				}
				public void onFailure(Throwable caught) {
					eventBus.deletingPageFinish(false);}
			});
		}else {
			PopUpMessage p = new PopUpMessage(this.view.getConstants().IDeletMainPage());
			p.show();
		}
	}
	
	/**
	 * set the key of the root Page
	 * @param key
	 */
	public void setRootKey(String key){
		this.rootKey = key;
	}
	
	///////////////////////////////////////// ACTION ON THE TREE /////////////////////////////////////////
	
	/**
	 * Reload the selected item and his child
	 * this function doesn't expand the reloaded Node
	 */
	public void onReloadChildInTree(){
		this.expandedItem = this.selectedItem;
		this.AddChildInTree();
	}
	

	public void onAddNewChildInTree() {
		this.rpcPage.getLastChildAdded((String)this.selectedItem.getUserObject(), 
				new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				selectedItem.addItem(makeTreeNode(result));
				eventBus.displayNewPageInTree();}
			public void onFailure(Throwable caught){
				eventBus.displayNewPageInTree();//even the page are not correctly added, the "add page event" is finished
				PopUpMessage p = new PopUpMessage(view.getConstants().EAddNewChild());
				p.show();}
		});
	}
	
	public void onReloadCurrentPageInTree(String newTitle) {
		view.setTextOfSelectedTI(newTitle);
	}

	/**
	 * Take all child node of the expandedNode and add them in the tree
	 */
	public void AddChildInTree(){
		this.rpcPage.getChildPages((String)this.expandedItem.getUserObject(), false,
				new AsyncCallback<List<BeanArboPage>>() {
			public void onSuccess(List<BeanArboPage> result) {
				expandedItem.removeItems();//remove loading
				for(BeanArboPage subPage : result) {
					expandedItem.addItem(makeTreeNode(subPage));
				}
			}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage(view.getConstants().EBuildTree());
				p.show();}
		});
		
	}
	
	/**
	 * Create the tree at the begin of the application
	 */
	public void createTree() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
	    	public void onSuccess(BeanArboPage result) {
	    		view.clearTree();
	    		rootKey = result.getEncodedKey();
	    		view.setTree(makeTreeNode(result));

	    	}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage(view.getConstants().ECreateTree());
				p.show();}
			});
	}
	
	/**
	 * Make a tree node containing the key of the page who was represented and display the title
	 * of the page in the default language.
	 * @param bean : Bean representing the corresponding page
	 * @return the tree node.
	 */
	private TreeItem makeTreeNode(BeanArboPage bean){
		TreeItem node = new TreeItem();
		Button b = new Button(">");
		HorizontalEventPanel p = new HorizontalEventPanel();
		Image img = new Image("tab_images/subPage.JPG");
		
		p.setSpacing(5);
		p.add(img);
		p.add(new Label(bean.getTranslation().get(0).getUrlName()));
		p.add(b);
		b.setVisible(false);
		
		// The loading panel, display during all child of a node is loading
		HorizontalPanel loadingPanel = new HorizontalPanel();
		loadingPanel.add(new Image("tab_images/wait.gif"));
		loadingPanel.add(new Label(this.view.getConstants().Loading()));
		loadingPanel.setSpacing(3);
		TreeItem LoadingItem = new TreeItem();
		LoadingItem.setWidget(loadingPanel);
		LoadingItem.setUserObject(new String("Loading"));
		
		node.setUserObject(bean.getEncodedKey());
		node.setWidget(p);
		node.addItem(LoadingItem);
		// utile pour pouvoir enlever le loading au chagement de l'arbre
		 
		
		// EVENT on treeItem
		
		b.addClickHandler(new ClickHandler() { // open the popUpMenu
			public void onClick(ClickEvent event) {
				Button b = (Button)event.getSource();
				view.getPopUpMenuBar().setPopupPosition(b.getAbsoluteLeft() + b.getOffsetWidth(),
														b.getAbsoluteTop() + b.getOffsetHeight());
				view.getPopUpMenuBar().show();
			}});
		
		p.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				HorizontalEventPanel p = (HorizontalEventPanel)event.getSource();
				Button b = (Button)p.getWidget(2); // img in 0, the label in 0, and the button in 1
				b.setVisible(true);
			}
		});
		
		p.addMouseOutHandler(new MouseOutHandler () {
			public void onMouseOut(MouseOutEvent event) {
				HorizontalEventPanel p = (HorizontalEventPanel)event.getSource();
				Button b = (Button)p.getWidget(2); // img in 0, the label in 0, and the button in 1
				b.setVisible(false);
			}
		});
		
		return node;
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////

	public void onStartPanels() {		
		this.eventBus.changeNavPanel(this.view);
		this.createTree();
	}
		
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
