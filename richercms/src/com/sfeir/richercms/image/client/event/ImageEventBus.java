package com.sfeir.richercms.image.client.event;


import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;
import com.sfeir.richercms.image.client.interfaces.IImagePanel;
import com.sfeir.richercms.image.client.presenter.AddPanelPresenter;
import com.sfeir.richercms.image.client.presenter.ImagePanelPresenter;
import com.sfeir.richercms.image.client.view.ImagePanel;

@Events(startView = ImagePanel.class, module = ImageMobule.class, debug = true)
public interface ImageEventBus extends EventBus {

	/**
	 * Display the ImageMobule view in the PageView
	 * @param p : the moduleView
	 */
	@Event( forwardToParent = true )
	public void displayImagePanel(IImagePanel p);
	
	/**
	 * Fired by the PagePresenter, when the corresponding entryMenu is clicked
	 */
	@Event( handlers = ImagePanelPresenter.class )
	public void startImagePanel();
	
	/**
	 * Fired by the ImagePanelPresenter to display the AddPanel
	 */
	@Event( handlers = AddPanelPresenter.class )
	public void startAddPanel();
	
	/**
	 * Fired by the AddPanelPresenter when the panel is ready to display
	 * @param p : the AddPanel to display
	 */
	@Event( handlers = ImagePanelPresenter.class )
	public void displayAddPanel(IAddPanel p);
	
	
	

	@Event( forwardToParent = true  )
	public void showInformationPopUp();
	

	@Event( forwardToParent = true )
	public void hideInformationPopUp();
	

	@Event( forwardToParent = true )
	public void addSuccessPopUp(String text);
	

	@Event( forwardToParent = true )
	public void addWaitLinePopUp(String text);
	

	@Event( forwardToParent = true )
	public void addErrorLinePopUp(String text);
}
