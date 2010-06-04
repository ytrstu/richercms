package com.sfeir.richercms.image.client.interfaces;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface ILinkPanel extends LazyView{

	Widget asWidget();
	SimplePanel addUnlinkThumbnail(Long id);
	SimplePanel addThumbnail(Long id);
	AbsolutePanel getBoundaryPanel();
	void clearElement();
	
	void displayLeftTree(IImageTreePanel p);
	FlexTable getUnlinkTable();
}
