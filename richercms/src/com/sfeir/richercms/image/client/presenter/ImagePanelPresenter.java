package com.sfeir.richercms.image.client.presenter;


import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.image.client.event.ImageEventBus;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;
import com.sfeir.richercms.image.client.interfaces.IImagePanel;
import com.sfeir.richercms.image.client.interfaces.ILinkPanel;
import com.sfeir.richercms.image.client.view.ImagePanel;

@Presenter( view = ImagePanel.class )
public class ImagePanelPresenter extends LazyPresenter<IImagePanel, ImageEventBus>{

	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { }
	
	public void onStartImagePanel() {
		this.eventBus.startPanels();
		this.eventBus.displayImagePanel(this.view);	
	}
	
	public void onDisplayAddPanel(IAddPanel p) {
		this.view.setAddPanel(p);
	}
	
	public void onDisplayLinkPanel(ILinkPanel p) {
		this.view.setLinkPanel(p);
	}
	
}
