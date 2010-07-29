package com.sfeir.richercms.page.client.tinyMCE.interfaces;


import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

/**
 * Allows the presenter to communicate with the view
 * PageViewer <=> PageViewer Presenter
 * @author homberg.g
 *
 */
public interface IPageViewer extends LazyView {

	Widget asWidget();
	
	public void addContent(String html);
}
