package com.sfeir.richercms.page.client.tinyMCE.interfaces;


import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * popup containing a tree and an overview of selected page 
 * @author homberg.g
 *
 */
public interface IPageViewer extends LazyView {

	Widget asWidget();
	
	public void addContent(String html);
}
