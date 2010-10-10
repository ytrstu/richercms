package com.sfeir.richercms.page.client.presenter;

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
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.state.LockState;
import com.sfeir.richercms.page.client.state.PageState;
import com.sfeir.richercms.page.client.state.PopUpState;
import com.sfeir.richercms.page.client.view.NavigationPanel;
import com.sfeir.richercms.page.client.view.custom.ConfirmationBox;
import com.sfeir.richercms.page.client.view.custom.HorizontalEventPanel;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;

/**
 * Presenter of the Navigation panel view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = NavigationPanel.class)
public class NavigationPanelPresenter extends LazyPresenter<INavigationPanel, PageEventBus>{

	private TreeItem selectedItem = null; // current selected Item in tree
	private TreeItem expandedItem = null; // current expanded Item in tree
	private TreeItem rootItem = null;
	private ArboPageServiceAsync rpcPage = null;
	// permet de savoir si on doit affiché les btn au survol dans l'arbo
	private boolean dispBtnInTree = true;
	//permet de savoir l'ors de l'ajout des fils dans l'arbre s'il faut sélectionner le dernier fils ou non
	private boolean selectLastChild = false;
	//permet de savoir si c'est un nouvelle objet qui est selectionné ou non
	private boolean sameItemSelected = false;
	//dernière état affiché (vars uniquement changé dans la fonction onDisplayCurrentPage
	private PageState dispState;
	
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
				//évite de recharger les donnée pour rien
				if(!event.getSelectedItem().equals(selectedItem)){
					sameItemSelected = false;
				}else{
					sameItemSelected = true;
				}
				
				setSelectedItem(event.getSelectedItem()); // fait des actions spécifique
				
				// l'arbo est aussi utilisé dans le userManger, mais il ne doit pas réagir à sa 
				if(dispState != PageState.manageImage){
					//vérification des locks puis affichage dans l'event retour
					eventBus.verifyPageLock((Long)event.getSelectedItem().getUserObject(), LockState.display);
					view.getPopUpMenuBar().hide();
				}else {
					createPath();
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
					
				view.getPopUpMenuBar().hide();
			}
		});
		
		// commande pour la suppression d'une page
		this.view.getPopUpMenuBar().setDelPageCommand(new Command(){
			public void execute() {
				view.getPopUpMenuBar().hide();
				ConfirmationBox confirmPopUp = new ConfirmationBox("ATTENTION", view.getConstants().MsgDelPageAndChild());
				confirmPopUp.getClickOkEvt().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						deletePage();
						view.getPopUpMenuBar().hide();
					}		
				});	
			}});
		
		// commande pour l'ajout d'une sous-page
		this.view.getPopUpMenuBar().setRefreshCommand(new Command(){
			public void execute() {
				NavigationPanelPresenter.this.refreshChild();
				view.getPopUpMenuBar().hide();
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
				// demande de savoir l'état de la page et donc si on peut la modifié
				NavigationPanelPresenter.this.eventBus.
					verifyPageLock((Long) selectedItem.getUserObject(), LockState.modify);
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
		// commande pour afficher l'outil de reorganisation des fils
		this.view.getPopUpMenuBar().setReorderPagesCommand(new Command() {
			public void execute() {
				eventBus.startReorderPanel((Long)selectedItem.getUserObject());
				view.getPopUpMenuBar().hide();
			}});
		// commande pour afficher l'outil de gestion des images
		this.view.getPopUpMenuBar().setManageImagesCommand(new Command() {
			public void execute() {
				//fired this event to modify state into the pagePresenter
				//and make all test necessary before display imageManager
				eventBus.menuImageManager();
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
		// on ne delete pas la main Page
		if(!((Long) selectedItem.getUserObject()).equals((Long)rootItem.getUserObject())) {
			// affichage de la popUp lors du deleteing
			this.eventBus.showInformationPopUp();
			this.eventBus.addWaitLinePopUp(view.getConstants().PopUpDelPage());
			this.rpcPage.deleteArboPage((Long)selectedItem.getUserObject(), (Long)selectedItem.getParentItem().getUserObject(), new AsyncCallback<Boolean>() {
				public void onSuccess(Boolean result) {
					if(result){
						view.deleteSelectedTI();
						selectedItem = selectedItem.getParentItem();
						eventBus.deletePage();
						eventBus.addSuccessPopUp(view.getConstants().PopUpDelPageFinish()); // suppression finis : on peut hide la popUp
					} else {
						eventBus.addErrorLinePopUp(view.getConstants().PopUpDelPageLock());
						// popUp for display the UI error
						PopUpMessage popUp = new PopUpMessage(view.getConstants().PopUpDelPageLock());
						popUp.show();
					}
					eventBus.hideInformationPopUp();
				}
				public void onFailure(Throwable caught) {
					eventBus.addErrorLinePopUp(view.getConstants().PopUpDelPageFail());
					eventBus.hideInformationPopUp();}
			});
		}else {
			PopUpMessage p = new PopUpMessage(this.view.getConstants().IDeletMainPage());
			p.show();
		}
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
					bean.getUrlName().length() == 0) {
				
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
	
	/**
	 * Up selected page in tree and his child
	 * (up page but at the same degree in the tree)
	 */
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
	
	/**
	 * Down selected page in tree and his child
	 * (up page but at the same degree in the tree)
	 */
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
	
	/**
	 * Create path with an RPC call
	 * and sart the imagePanelPresenter
	 */
	private void createPath() {
		//get the id Path : ids[0] = selected Node ... ids[max] : root
		List<Long> ids = this.getIdPath();
		this.rpcPage.getPath(ids, new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(String result) {
				eventBus.startImagePanel(result);
				}
			
		});
	}
	
	/**
	 * Create a list containing the path of the selectedNode
	 * this list contains id in a recursive order.
	 * @return ids : ids[0] = selected Node => ids[max] : root of the tree ('=>' represent intermediate nodes)
	 */
	private List<Long> getIdPath() {
		ArrayList<Long> ids = new ArrayList<Long>();
		ids.add((Long)this.selectedItem.getUserObject());
		TreeItem currentItem = this.selectedItem.getParentItem();
		
		while(currentItem != null) {
			ids.add((Long)currentItem.getUserObject());
			currentItem = currentItem.getParentItem();
		}
		
		return ids;
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
					TreeItem newSelected = makeTreeNode(result);
					selectedItem.addItem(newSelected);
					eventBus.displayNewPageInTree();
					view.setSelectedItem(newSelected);
				}
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
	
	private void refreshChild(){
		//delete all child of current node
		this.selectedItem.removeItems();
		// add all child in selected node
		this.AddChildInTree();
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
					TreeItem newSelectedItem = expandedItem.getChild(expandedItem.getChildCount()-1);
					view.setSelectedItem(newSelectedItem);
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
	    		rootItem = makeTreeNode(result);
	    		view.setTree(rootItem);

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
		Image b = new Image("tab_images/leftBtn.png");
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
				Image b = (Image)event.getSource();
				view.getPopUpMenuBar().setPopupPosition(b.getAbsoluteLeft() + b.getOffsetWidth(),
														b.getAbsoluteTop() + b.getOffsetHeight());
				view.getPopUpMenuBar().show();
			}});
		
		p.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				HorizontalEventPanel p = (HorizontalEventPanel)event.getSource();
				Image b = (Image)p.getWidget(2); // img in 0, the label in 0, and the button in 1
				if(dispBtnInTree)
					b.setVisible(true);
			}
		});
		
		p.addMouseOutHandler(new MouseOutHandler () {
			public void onMouseOut(MouseOutEvent event) {
				HorizontalEventPanel p = (HorizontalEventPanel)event.getSource();
				Image b = (Image)p.getWidget(2); // img in 0, the label in 0, and the button in 1
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
	 * Function call by the Validation presenter when you click on the modify btn
	 */
	public void onGoInModification(){
		// if no page selected, select the root by default
		if(this.selectedItem == null)
			this.setSelectedItem(this.rootItem);
		
		this.eventBus.verifyPageLock((Long) selectedItem.getUserObject(), LockState.modify);
	}
	
	public void onCancelPage(PageState newState){
		eventBus.showInformationPopUp();
		eventBus.addWaitLinePopUp(this.view.getConstants().PageLoading());
		eventBus.displayPage((Long) selectedItem.getUserObject());
	}
	
	public void onDisplayCurrentPage(PageState state) {
		
		// if no page selected, select the root by default
		if(this.selectedItem == null)
			this.setSelectedItem(this.rootItem);
		dispState = state;
		
		switch(state) {
		case manageImage:
			this.dispBtnInTree = false;
			this.createPath();//display new thumbs
			break;
		case manageUser:
			this.eventBus.startUserManager();
			break;
		case manageTag:
			this.eventBus.startTagManager();
			break;
		case manageTemplate:
			this.eventBus.startTemplateManager();
			break;
		case display:
			this.dispBtnInTree = true;
			if(!sameItemSelected){
				eventBus.showInformationPopUp();
				eventBus.addWaitLinePopUp(this.view.getConstants().PageLoading());
				eventBus.displayPage((Long) selectedItem.getUserObject());
			}

			break;
		case modify:
			this.dispBtnInTree = true;
			
			//if null => rootPage
			if(selectedItem.getParentItem() == null){
				NavigationPanelPresenter.this.eventBus.modifyPage(
						(Long) selectedItem.getUserObject(),
						null,
						getIdPath());
			}else{
				NavigationPanelPresenter.this.eventBus.modifyPage(
						(Long) selectedItem.getUserObject(),
						(Long) selectedItem.getParentItem().getUserObject(),
						getIdPath());
			}
			break;
		}
	}
	
	//display the FileMbox PopUp with the good node opened
	public void onLoadFileManager() {
		this.eventBus.startTinyPopUp(this.getIdPath(),PopUpState.imageManager);
	}
	
	public void onLoadLinkManager(String url) {
		this.rpcPage.loadPathIdFromRealPath(url, new AsyncCallback<List<Long>>() {

			@Override
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage(view.getConstants().ECreateTree());
				p.show();
			}

			@Override
			public void onSuccess(List<Long> result) {
				startTynyPopUp(result);
			}
		});
		
	}
	
	private void startTynyPopUp(List<Long> pathList) {
		this.eventBus.startTinyPopUp(pathList,PopUpState.linkManager);
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
