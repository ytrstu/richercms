package com.sfeir.richercms.image.client.presenter;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SimplePanel;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.image.client.event.ImageEventBus;
import com.sfeir.richercms.image.client.event.SetWidgetDropController;
import com.sfeir.richercms.image.client.interfaces.ILinkPanel;
import com.sfeir.richercms.image.client.view.LinkPanel;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.FileServiceAsync;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IImageTreePanel;

@Presenter( view = LinkPanel.class)
public class LinkPanelPresenter extends LazyPresenter<ILinkPanel,ImageEventBus> {
	
	private FileServiceAsync rpcFile = null;
	private ArboPageServiceAsync rpcPage = null;
	private PickupDragController dragController;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { 
	    // initialize our drag controller
		dragController = new PickupDragController(view.getBoundaryPanel(), false);
	    dragController.setBehaviorMultipleSelection(false);
	}
	
	public void onStartPanels() {
		displayThumbNails();
		this.eventBus.startLeftTreePanel();
		this.eventBus.displayLinkPanel(this.view);
	}
	
	public void onDisplayLeftTree(IImageTreePanel p){
		this.view.displayLeftTree(p);
	}
	
	public void onDisplayLinkedThumbs(Long pageID) {
		/*this.rpcPage.getLinkedImage(pageID, new AsyncCallback<List<Long>>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(List<Long> result) {
				for (Long id : result) {
					addLeftLinkThumbs(id);
				}
				addLeftLinkThumbs(null);
			}

		});*/
	}
	
	public void onLinkAndAddSlot() {
		addLeftLinkThumbs(null);
	}
	
	private void displayThumbNails() {
		this.view.clearElement();
		this.rpcFile.getUnlikedFile(new AsyncCallback<List<Long>>(){
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(List<Long> result) {
				for(Long id : result){
					addUnlinkThumbs(id);
				}
			}
		});
	}
	
	private void addUnlinkThumbs(Long id){
		SimplePanel thumbcontainer = view.addUnlinkThumbnail(id);
		//if it contain a Thumb or not
		if(thumbcontainer.getWidget() != null)
			dragController.makeDraggable(thumbcontainer.getWidget());
		
        // instantiate a drop controller of the panel in the current cell
        SetWidgetDropController dropController = new SetWidgetDropController(thumbcontainer,this.eventBus);
        dragController.registerDropController(dropController);
	}
	
	private void addLeftLinkThumbs(Long id){
		SimplePanel thumbcontainer = view.addThumbnail(id);
		//if it contain a Thumb or not
		if(thumbcontainer.getWidget() != null)
			dragController.makeDraggable(thumbcontainer.getWidget());
		
        SetWidgetDropController dropController = new SetWidgetDropController(thumbcontainer, this.eventBus);
        dragController.registerDropController(dropController);
	}
	
	/**
	 * used by the framework to instantiate rpcFile 
	 * @param rpcFile
	 */
	@InjectService
	public void setFileService(FileServiceAsync rpcFile) {
		this.rpcFile = rpcFile;
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
