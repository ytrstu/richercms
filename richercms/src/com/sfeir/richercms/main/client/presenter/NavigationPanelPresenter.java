package com.sfeir.richercms.main.client.presenter;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.INavigationPanel;
import com.sfeir.richercms.main.client.view.NavigationPanel;
import com.sfeir.richercms.main.client.view.custom.HorizontalEventPanel;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.main.shared.BeanTranslationPage;


@Presenter( view = NavigationPanel.class)
public class NavigationPanelPresenter extends LazyPresenter<INavigationPanel, MainEventBus>{

	private TreeItem selectedItem = null; // current selected Item in tree
	private TreeItem expandedItem = null; // current expanded Item in tree
	private ArboPageServiceAsync rpcPage = null;
	private Long rootId = null;
	//permet de savoir l'ors de l'ajout des fils dans l'arbre s'il faut sélectionner le dernier fils ou non
	private boolean selectLastChild = false; 
	
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
				eventBus.displayNormalPanel();
				//évite de recharger les donnée pour rien
				if(!event.getSelectedItem().equals(selectedItem)){
					setSelectedItem(event.getSelectedItem()); // fait des actions spécifique
					eventBus.displayPage((Long) selectedItem.getUserObject());
				}
			}
		});
		
		view.getExpandedEvtTree().addOpenHandler(new OpenHandler<TreeItem>(){
			public void onOpen(OpenEvent<TreeItem> event) {
				expandedItem = event.getTarget();
				// on ajoute les fils uniquement si sa n'a pas déjà été fait
				if(expandedItem.getChild(0).getUserObject()
						.getClass().getName().equals(new String("String")));
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
				NavigationPanelPresenter.this.eventBus.addPage((Long) selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
		
		// commande pour la modification d'une page
		this.view.getPopUpMenuBar().setModifyPageCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.eventBus.modifyPage((Long) selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
		
		// commande pour remonter une page dans la liste de fils
		this.view.getPopUpMenuBar().setUpPageCommand(new Command() {
			public void execute() {
				upPageInTree();
				view.getPopUpMenuBar().hide();
			}});
		
		// commande pour descendre une page dans la liste de fils
		this.view.getPopUpMenuBar().setDownPageCommand(new Command() {
			public void execute() {
				downPageInTree();
				view.getPopUpMenuBar().hide();
			}});
		
		this.view.getPopUpMenuBar().setReorderPagesCommand(new Command() {
			public void execute() {
				eventBus.startReorderPanel((Long)selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
	}

	/**
	 * Use this when a new Item in the tree is selected.
	 * @param selectedItem
	 */
	public void setSelectedItem(TreeItem selectedItem) {
		if (this.selectedItem!=null) {
			HorizontalPanel p =(HorizontalPanel)this.selectedItem.getWidget();
			Label l = (Label)p.getWidget(1);
			l.setStyleName("treeLabel");
		}
		this.selectedItem = selectedItem;
		HorizontalPanel p =(HorizontalPanel)selectedItem.getWidget();
		Label l = (Label)p.getWidget(1);
		l.setStyleName("treeLabelSelected");
	}
	
	/**
	 * delete page selected in the tree
	 */
	public void deletePage() {	
		view.getPopUpMenuBar().hide();
		// on ne delete pas la main Page
		if(!((Long) selectedItem.getUserObject()).equals(rootId)) {
			// on commence donc la suppression
			NavigationPanelPresenter.this.eventBus.deletePage();
			this.rpcPage.deleteArboPage((Long)selectedItem.getUserObject(), (Long)selectedItem.getParentItem().getUserObject(), new AsyncCallback<Void>() {
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
	public void setRootKey(Long id){
		this.rootId = id;
	}
	
	/**
	 * Choose the good image to display in the tree
	 * according to the BeanArboPage to display
	 * @param bean : the BeanArboPage
	 * @return : the good image according information include in the bean
	 */
	private Image chooseTheGoodImage(BeanArboPage bean){
		Image img = null;
		for(BeanTranslationPage bT : bean.getTranslation()) {
			// si certain champs d'un traduction son laissé vide alors icone = warning
			if(bT.getContent().length() == 0 || bT.getBrowserTitle().length() == 0 ||
					bT.getDescription().length() == 0 || bT.getPageTitle().length() == 0 || 
					bT.getUrlName().length() == 0) {
				
					img = new Image("tab_images/pageWarn.png");
				
				img.setTitle(view.getConstants().TreeWarnPage());
				return img;
			}
		}
		// si toute les traduction son bien remplis alors on choisis l'icone Ok
		img = new Image("tab_images/pageOk.png");
		img.setTitle(view.getConstants().TreeOkPage());
		
		return img;
	}
	
	private void upPageInTree() {
		TreeItem parent = selectedItem.getParentItem();
		
		if(parent != null && parent.getChildCount()!=1 && parent.getChildIndex(selectedItem)!=0) {
			
			TreeItem upperTreeItem = parent.getChild(parent.getChildIndex(selectedItem)-1);
			Widget display1 = selectedItem.getWidget();
			Widget display2 = upperTreeItem.getWidget();
			Long id1 = (Long)selectedItem.getUserObject();
			Long id2 = (Long)upperTreeItem.getUserObject();
			boolean expand1 = selectedItem.getState();
			boolean expand2 = upperTreeItem.getState();
			ArrayList<TreeItem> childs = new ArrayList<TreeItem>();
			
			rpcPage.moveChildPage((Long)parent.getUserObject(), (Long)selectedItem.getUserObject(), 
					parent.getChildIndex(upperTreeItem), new AsyncCallback<Void>() {
				public void onSuccess(Void result) {}
				public void onFailure(Throwable caught) {}
			});
			
			for(int i=0 ; i<selectedItem.getChildCount() ; i++) {
				childs.add(selectedItem.getChild(i));
			}
			
			selectedItem.removeItems();
			int nbChild = upperTreeItem.getChildCount();
			for(int i=0 ; i< nbChild; i++) {
				// quand le tree node est attaché dans l'autre arbre, il disparait de celui-ci
				selectedItem.addItem(upperTreeItem.getChild(0));
			}
			
			upperTreeItem.removeItems();
			for(int i=0 ; i<childs.size() ; i++) {
				upperTreeItem.addItem(childs.get(i));
			}
			
			selectedItem.setWidget(display2);
			selectedItem.setUserObject(id2);
			selectedItem.setState(expand2);
			selectedItem.setSelected(false);
			
			upperTreeItem.setWidget(display1);
			upperTreeItem.setUserObject(id1);
			upperTreeItem.setState(expand1);
			upperTreeItem.setSelected(true);
			
			selectedItem = upperTreeItem;
		}
	}
	
	
	private void downPageInTree() {
		TreeItem parent = selectedItem.getParentItem();
		
		if(parent != null && parent.getChildCount()!=1 && 
				parent.getChildIndex(selectedItem) != (parent.getChildCount()-1)) {
			
			TreeItem lowerTreeItem = parent.getChild(parent.getChildIndex(selectedItem)+1);
			Widget display1 = selectedItem.getWidget();
			Widget display2 = lowerTreeItem.getWidget();
			Long id1 = (Long)selectedItem.getUserObject();
			Long id2 = (Long)lowerTreeItem.getUserObject();
			boolean expand1 = selectedItem.getState();
			boolean expand2 = lowerTreeItem.getState();
			ArrayList<TreeItem> childs = new ArrayList<TreeItem>();
			
			rpcPage.moveChildPage((Long)parent.getUserObject(), (Long)selectedItem.getUserObject(), 
					parent.getChildIndex(lowerTreeItem), new AsyncCallback<Void>() {
				public void onSuccess(Void result) {}
				public void onFailure(Throwable caught) {}
			});
			
			for(int i=0 ; i<selectedItem.getChildCount() ; i++) {
				childs.add(selectedItem.getChild(i));
			}
			
			selectedItem.removeItems();
			int nbChild = lowerTreeItem.getChildCount();
			for(int i=0 ; i< nbChild; i++) {
				// quand le tree node est attaché dans l'autre arbre, il disparait de celui-ci
				selectedItem.addItem(lowerTreeItem.getChild(0));
			}
			
			lowerTreeItem.removeItems();
			for(int i=0 ; i<childs.size() ; i++) {
				lowerTreeItem.addItem(childs.get(i));
			}
			
			selectedItem.setWidget(display2);
			selectedItem.setUserObject(id2);
			selectedItem.setState(expand2);
			selectedItem.setSelected(false);
			
			lowerTreeItem.setWidget(display1);
			lowerTreeItem.setUserObject(id1);
			lowerTreeItem.setState(expand1);
			lowerTreeItem.setSelected(true);
			
			selectedItem = lowerTreeItem;
		}
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
		if(this.selectedItem.getState()) {
			this.rpcPage.getLastChildAdded((Long)this.selectedItem.getUserObject(), 
					new AsyncCallback<BeanArboPage>() {
				public void onSuccess(BeanArboPage result) {
					selectedItem.addItem(makeTreeNode(result));
					eventBus.displayNewPageInTree();}
				public void onFailure(Throwable caught){
					eventBus.displayNewPageInTree();//even the page are not correctly added, the "add page event" is finished
					PopUpMessage p = new PopUpMessage(view.getConstants().EAddNewChild());
					p.show();}
			});
		}else{
			 //selection du dernier fils après l'ajout dans l'arbre
			this.selectLastChild = true;
			// on étant le noeud père + send event
			this.selectedItem.setState(true, true);
			//permet de hide la State PopUp
			eventBus.displayNewPageInTree();

		}
	}

	/**
	 * Take all child node of the expandedNode and add them in the tree
	 * @param SelectLastChild : true => select the last child; false either.
	 */
	public void AddChildInTree(){
		this.rpcPage.getChildPages((Long)this.expandedItem.getUserObject(), false,
				new AsyncCallback<List<BeanArboPage>>() {
			public void onSuccess(List<BeanArboPage> result) {
				expandedItem.removeItems();//remove loading
				for(BeanArboPage subPage : result) {
					expandedItem.addItem(makeTreeNode(subPage));
				}
				if(selectLastChild){
					selectLastChild = false; //dans tout les cas on remet a false
					selectedItem.setSelected(false); //dé-selectionne le noeud courant
					selectedItem = expandedItem.getChild(expandedItem.getChildCount()-1);
					selectedItem.setSelected(true); //selectionne le dernier fils
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
	    		rootId = result.getId();
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
		Image img = this.chooseTheGoodImage(bean);
		HorizontalEventPanel p = new HorizontalEventPanel();
		p.setSpacing(0);
		p.add(img);
		Label treeLabel = new Label(bean.getTranslation().get(0).getPageTitle());
		treeLabel.setStyleName("treeLabel");
		p.add(treeLabel);
		p.add(b);
		b.setVisible(false);
		
		// The loading panel, display during all child of a node is loading
		HorizontalPanel loadingPanel = new HorizontalPanel();
		loadingPanel.add(new Image("tab_images/wait.gif"));
		loadingPanel.add(new Label(this.view.getConstants().Loading()));
		loadingPanel.setSpacing(0);
		TreeItem LoadingItem = new TreeItem();
		LoadingItem.setWidget(loadingPanel);
		LoadingItem.setUserObject(new String("Loading"));
		
		node.setUserObject(bean.getId());
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
	
	public void onReloadCurrentPageInTree(BeanArboPage modifOnPage) {
		view.setTextOfSelectedTI(modifOnPage.getTranslation().get(0).getPageTitle());
		view.setImageOfSelectedTI(this.chooseTheGoodImage(modifOnPage));
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
