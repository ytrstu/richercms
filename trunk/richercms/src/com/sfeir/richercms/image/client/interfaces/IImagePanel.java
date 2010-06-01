package com.sfeir.richercms.image.client.interfaces;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface IImagePanel extends LazyView {
	
	Widget asWidget();
	void setAddPanel(IAddPanel p);
}
