package com.sfeir.richercms.page.client.tinyMCE.presenter;


import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IFileMBox;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IImageTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.view.FileMbox;

@Presenter( view = FileMbox.class )
public class FileMBoxPresenter extends LazyPresenter<IFileMBox,PageEventBus>{

	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
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
