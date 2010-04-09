package com.sfeir.richercms.main.client.interfaces;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;


public interface IdisplayMainPage extends LazyView {

	Widget asWidget();
	void addPageInTree(String name);
	void clearTree();
}
