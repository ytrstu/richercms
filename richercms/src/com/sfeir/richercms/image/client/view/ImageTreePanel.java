package com.sfeir.richercms.image.client.view;


import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.image.client.interfaces.IImageTreePanel;
import com.sfeir.richercms.page.client.view.custom.TreePanel;

public class ImageTreePanel extends TreePanel implements IImageTreePanel{

	
	public Widget asWidget() {	
		return this;
	}

	public void createView() {
		super.createView();
	}

}
