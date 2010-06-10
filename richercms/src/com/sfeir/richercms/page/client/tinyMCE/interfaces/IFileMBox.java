package com.sfeir.richercms.page.client.tinyMCE.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * popup containing a tree and an overview of image 
 * content in a certain page of the tree.
 * @author homberg.g
 *
 */
public interface IFileMBox extends LazyView{

	Widget asWidget();
	
	/**
	 * Function inherit to DialogBox.
	 * Display the popUp in a center position
	 * and reset all component
	 */
	void center();
	
	/**
	 * Function inherit to DialogBox.
	 * hide the PopUp
	 */
	void hide();
	
	/**
	 * Add the ImageTreePanel into the view
	 * @param p : the ImageTreePanel
	 */
	void displayLeftTree(IImageTreePanel p);
	
	/**
	 * Add the ThumbsPanel into the view
	 * @param p : the ThumbsPanel
	 */
	void displayThumbs(IThumbsPanel p);
	
	/**
	 * Return the click handler on the Ok button.
	 * @return EVENT
	 */
	HasClickHandlers onOkClick();
}