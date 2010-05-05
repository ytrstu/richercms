package com.sfeir.richercms.main.client.presenter;

import java.util.List;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.sfeir.richercms.main.client.view.NavigationPanel;
import com.sfeir.richercms.main.shared.BeanArboPage;


@Presenter( view = NavigationPanel.class)
public class NavigationPanelPresenter extends LazyPresenter<INavigationPanel, MainEventBus>{

	private TreeItem selectedItem = null; // current selected Item in tree
	private TreeItem expandedItem = null; // current exanded Item in tree
	private ArboPageServiceAsync rpcPage = null;
	private String translationLanguageKey = null;
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
				
				// on fait la différence car le root n'est pas un arboPage
				if(((String) selectedItem.getUserObject()).equals(rootKey))
					eventBus.displayMainPage();
				else
					eventBus.displayPage((String) selectedItem.getUserObject());
				showMenuButton();
			}
		});
		
		view.getExpandedEvtTree().addOpenHandler(new OpenHandler<TreeItem>(){
			public void onOpen(OpenEvent<TreeItem> event) {
				expandedItem = event.getTarget();
				// on ajoute les fils uniquement si sa n'a pas déjà été fait
				if(expandedItem.getChild(0).getText().equals("Loading"));
					AddChildInTree();
			}
		});
		
		// commande pour la suppression d'une page
		this.view.getPopUpMenuBar().setDelPageCommand(new Command(){
			public void execute() {
				deletePage();
				NavigationPanelPresenter.this.eventBus.deletePage();
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
	 * Show button using to display menuBar
	 */
	public void showMenuButton() {
		HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
		panel.getWidget(2).setVisible(true);
	}

	/**
	 * Use this when a new Item in the tree is selected.
	 * @param selectedItem
	 */
	public void setSelectedItem(TreeItem selectedItem) {
		if(this.selectedItem!=null) {
			HorizontalPanel panel = (HorizontalPanel)this.selectedItem.getWidget();
			panel.getWidget(2).setVisible(false);
		}
		this.selectedItem = selectedItem;
	}
	
	/**
	 * delete page selected in the tree
	 */
	public void deletePage() {	
		
		view.getPopUpMenuBar().hide();
		// on ne delete pas la main Page
		if(!((String) selectedItem.getUserObject()).equals(rootKey)) {
			this.rpcPage.deleteArboPage((String)selectedItem.getUserObject(), (String)selectedItem.getParentItem().getUserObject(), new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					selectedItem = selectedItem.getParentItem();
					onReloadChildInTree();
				}
				public void onFailure(Throwable caught) {
					PopUpMessage p = new PopUpMessage("Error : DeletePage");
					p.show();}
			});
		}else {
			PopUpMessage p = new PopUpMessage("Impossible de détruire la page principal");
			p.show();
		}
	}
	
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

	/**
	 * Take all child node of the expandedNode and add them in the tree
	 */
	public void AddChildInTree(){
		expandedItem.removeItems();
		//si c'est le main qui est expand
		if(((String) expandedItem.getUserObject()).equals(rootKey)) {
			this.rpcPage.getChildPages((String)this.expandedItem.getUserObject(), true,
				new AsyncCallback<List<BeanArboPage>>() {
					public void onSuccess(List<BeanArboPage> result) {
						expandedItem.removeItems();//remove loading
						for(BeanArboPage subPage : result) {
							expandedItem.addItem(makeTreeNode(subPage));
						}
					}
					public void onFailure(Throwable caught){
						PopUpMessage p = new PopUpMessage("Error : Build tree");
						p.show();}
				});
		}else{ //si un autre noeud est expand
			this.rpcPage.getChildPages((String)this.expandedItem.getUserObject(), false,
					new AsyncCallback<List<BeanArboPage>>() {
						public void onSuccess(List<BeanArboPage> result) {
							expandedItem.removeItems();//remove loading
							for(BeanArboPage subPage : result) {
								expandedItem.addItem(makeTreeNode(subPage));
							}
						}
						public void onFailure(Throwable caught){
							PopUpMessage p = new PopUpMessage("Error : Build tree");
							p.show();}
					});
		}
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
				PopUpMessage p = new PopUpMessage("Error : Create tree");
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
		HorizontalPanel p = new HorizontalPanel();
		Image img = new Image("tab_images/subPage.JPG");
		
		p.setSpacing(5);
		p.add(img);
		p.add(new Label(bean.getTranslation().get(0).getPageTitle()));
		p.add(b);
		b.setVisible(false);
		
		node.setUserObject(bean.getEncodedKey());
		node.setWidget(p);
		node.addItem("Loading");
		
		
		b.addClickHandler(new ClickHandler() { // open the popUpMenu
			public void onClick(ClickEvent event) {
				Button b = (Button)event.getSource();
				view.getPopUpMenuBar().setPopupPosition(b.getAbsoluteLeft() + b.getOffsetWidth(),
														b.getAbsoluteTop() + b.getOffsetHeight());
				view.getPopUpMenuBar().show();
			}});
		
		return node;
	}
	
	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////

	public void onStartPanels() {		
		this.eventBus.changeNavPanel(this.view);
		//this.onBuildTree();
		this.createTree();
	}
	
	/**
	 * DEPRECATED
	 */
	public void onBuildTree() {
		
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
	    	public void onSuccess(BeanArboPage result) {
	    		view.clearTree();	 
				
				view.setTree(makeTreeNode(result));
	    		
	    		// si la clé était null alors on a créer la traduction du coup on récupère la clés
	    		/*if(translationLanguageKey == null) { 
	    			eventBus.setTranslationKeyInLanguage(result.getKey());
	    			translationLanguageKey = result.getKey();
	    		}*/
	    	}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage("Error : Build tree");
				p.show();}
			});
	}
		
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
	
	public void onChangeLanguage(String translationKey) {
		this.translationLanguageKey = translationKey;
		this.onBuildTree();
	}
}
