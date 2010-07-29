package com.sfeir.richercms.page.client.tinyMCE.view;


import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPopUpTreePanel;
import com.sfeir.richercms.page.client.view.custom.TreePanel;

/**
 * 
 * @author homberg.g
 * a View which containing a tree panel
 * this view are added in FileMbox view
 */
public class PopUpTreePanel extends TreePanel implements IPopUpTreePanel{

	
	public Widget asWidget() {	
		return this;
	}

	public void createView() {
		super.createView();
	}

}
