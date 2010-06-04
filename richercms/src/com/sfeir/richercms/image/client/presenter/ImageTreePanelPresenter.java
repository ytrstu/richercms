package com.sfeir.richercms.image.client.presenter;

import java.util.List;

import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.image.client.event.ImageEventBus;
import com.sfeir.richercms.image.client.interfaces.IImageTreePanel;
import com.sfeir.richercms.image.client.view.ImageTreePanel;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.view.custom.HorizontalEventPanel;
import com.sfeir.richercms.page.shared.BeanArboPage;

@Presenter( view =ImageTreePanel.class)
public class ImageTreePanelPresenter  extends LazyPresenter<IImageTreePanel,ImageEventBus>{

	private TreeItem expandedItem = null; // current expanded Item in tree
	private TreeItem selectedItem = null; // current selected Item in tree
	private ArboPageServiceAsync rpcPage = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { 
		
		view.getSelectedEvtTree()
		.addSelectionHandler(new SelectionHandler<TreeItem>(){
			public void onSelection(SelectionEvent<TreeItem> event) {
				setSelectedItem(event.getSelectedItem()); // fait des actions spécifique (surtout css)
				eventBus.displayLinkedThumbs((Long)selectedItem.getUserObject());
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
	}
	
	public void onStartLeftTreePanel(){
		this.createTree();
		this.eventBus.displayLeftTree(this.view);
	}
	
	/**
	 * Create the tree at the begin of the application
	 */
	public void createTree() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
	    	public void onSuccess(BeanArboPage result) {
	    		view.clearTree();
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

		Image img = new Image("tab_images/imagePage.jpg");
		HorizontalEventPanel p = new HorizontalEventPanel();
		p.setSpacing(0);
		p.add(img);
		Label treeLabel = new Label(bean.getTranslation().get(0).getPageTitle());
		treeLabel.setStyleName("treeLabel");
		p.add(treeLabel);
		
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
		
		return node;
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
			}
			public void onFailure(Throwable caught){
				PopUpMessage p = new PopUpMessage(view.getConstants().EBuildTree());
				p.show();}
		});
		
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
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
