package com.sfeir.richercms.page.client.tinyMCE.presenter;


import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.state.PopUpState;
import com.sfeir.richercms.page.client.tinyMCE.FileManager;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IFileMBox;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPopUpTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.view.FileMbox;

/**
 * Presenter of the FileMbox view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 *
 */
@Presenter( view = FileMbox.class )
public class FileMBoxPresenter extends LazyPresenter<IFileMBox,PageEventBus>{

	private static final String imageUrl = "/image/";
	private String selectedPath = "";
	private PopUpState state;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		this.view.onOkClick().addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		switch(state){
        			case imageManager :
        				addPathInTiny();
        				break;
        			case linkManager :
        				eventBus.callLinkPath();
        		}
        		view.hide();
        	}
        });
		
		this.view.onCancelClick().addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		view.hide();
        	}
        });
	}
	
	public void onStartTinyPopUp(List<Long> pathId, PopUpState state) {
		this.state = state;
		//this.eventBus.showInformationPopUp();
		switch(this.state)
		{
		case imageManager :
			this.eventBus.tinyPopUpStartImgPanels(pathId);
			this.view.setImgDefaultTitle();
			break;
		case linkManager :
			this.eventBus.tinyPopUpStartLinkPanels(pathId);
			this.view.setLinkDefaultTitle();
			break;
		}
	}
	
	public void onTinyPopUpDisplayTreePanel(IPopUpTreePanel p) {
		this.view.displayLeftTree(p);
		this.view.center();
	}
	
	public void onTinyPopUpDisplayThumbsPanel(IThumbsPanel p) {
		this.view.displayThumbs(p);
		this.view.center();
	}
	
	public void onTinyPopUpDisplayContentViewer(IPageViewer p) {
		this.view.displayViewer(p);
		this.view.center();
	}
	
	public void onSelectThumbs(String path){
		this.selectedPath = path;
		
		// if path == "" no image was selected
		if(path == "")
			this.view.setImgDefaultTitle();
		else
			this.view.setTitle(this.extractTitle(path));
	}
	
	public void onSendLinkPath(String path){
		this.selectedPath = path;
		addPathInTiny();
	}
	
	/**
	 * Add the current path in tinyMCE
	 */
	private void addPathInTiny() {
		
		switch(this.state){
		case imageManager :
			FileManager.setTinyMceUrl(imageUrl+selectedPath);
			break;
		case linkManager :
			FileManager.setTinyMceUrl(selectedPath);
			break;
		}
		this.selectedPath = "";
	}
	
	/**
	 * Extract the last element in a path
	 * You can use this for make title
	 * @param path : path
	 * @return last page name in the path
	 */
	private String extractTitle(String path){
		String[] splited = path.split("/");
		return splited[splited.length-1];
	}
}
