package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;


/**
 * Allows the presenter to communicate with the view
 * MainPage <=> MainPagePresenter
 * @author homberg.g
 *
 */
public interface IdisplayMainPage extends LazyView {

	Widget asWidget();
	
	/**
	 * Modify the view and add a new node in the tree
	 * @param name name of the node
	 */
	void addPageInTree(String name);
	
	/**
	 * clear the webPage tree
	 */
	void clearTree();
}
