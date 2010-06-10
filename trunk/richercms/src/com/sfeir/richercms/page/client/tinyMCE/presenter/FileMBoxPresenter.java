package com.sfeir.richercms.page.client.tinyMCE.presenter;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.tinyMCE.FileManager;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IFileMBox;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IImageTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.view.FileMbox;

/**
 * Presenter of the FileMbox view
 * @author homberg.g
 *
 */
@Presenter( view = FileMbox.class )
public class FileMBoxPresenter extends LazyPresenter<IFileMBox,PageEventBus>{

	private static final String imageUrl = GWT.getModuleBaseURL() + "image?path=";
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		this.view.onOkClick().addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		eventBus.callPath();
        		view.hide();
        	}
        });
	}
	
	public void onSendPath(String path){
		FileManager.setTinyMceUrl(imageUrl+path);
	}
	
	public void onStartTinyPopUp(){
		this.eventBus.tinyPopUpStartPanels();
	}
	
	public void onTinyPopUpDisplayTreePanel(IImageTreePanel p){
		this.view.displayLeftTree(p);
		this.view.center();
	}
	
	public void onTinyPopUpDisplayThumbsPanel(IThumbsPanel p){
		this.view.displayThumbs(p);
		this.view.center();
	}
}
