package com.sfeir.richercms.image.client.event;


import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;
import com.sfeir.richercms.image.client.interfaces.IImagePanel;
import com.sfeir.richercms.image.client.interfaces.ILinkPanel;
import com.sfeir.richercms.image.client.presenter.AddPanelPresenter;
import com.sfeir.richercms.image.client.presenter.ImagePanelPresenter;
import com.sfeir.richercms.image.client.presenter.LinkPanelPresenter;
import com.sfeir.richercms.image.client.view.ImagePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPopUpTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.presenter.PopUpTreePanelPresenter;

@Events(startView = ImagePanel.class, module = ImageMobule.class)
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
	 * Fired by the ImagePanelPresenter to display AddPanel and LinkPanel
	 */
	@Event( handlers = {AddPanelPresenter.class, LinkPanelPresenter.class} )
	public void startPanels();
	
	/**
	 * Fired by the AddPanelPresenter when the panel is ready to display
	 * @param p : the AddPanel to display
	 */
	@Event( handlers = ImagePanelPresenter.class )
	public void displayAddPanel(IAddPanel p);
	
	/**
	 * Fired by the LinkPanelPresenter when the panel is ready to display
	 * @param p : the LinkPanel to display
	 */
	@Event( handlers = ImagePanelPresenter.class )
	public void displayLinkPanel(ILinkPanel p);
	
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
	
	@Event( handlers = PopUpTreePanelPresenter.class )
	public void startLeftTreePanel();
	
	@Event( handlers = LinkPanelPresenter.class )
	public void displayLeftTree(IPopUpTreePanel p);
	
	/**
	 * Fired by the PopUpTreePanelPresenter to Display all 
	 * images linked with the selected page
	 * @param pageID : id we need to display linked Thumbs
	 */
	@Event( handlers = LinkPanelPresenter.class )
	public void displayLinkedThumbs(Long pageID);
	
	@Event( handlers = LinkPanelPresenter.class )
	public void linkAndAddSlot();
}
