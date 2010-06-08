package com.sfeir.richercms.page.client.tinyMCE.interfaces;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IFileMBox extends LazyView{

	Widget asWidget();
	
	void center();
	
	void displayLeftTree(IImageTreePanel p);
	
	void displayThumbs(IThumbsPanel p);
}