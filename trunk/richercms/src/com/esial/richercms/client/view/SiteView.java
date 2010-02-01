package com.esial.richercms.client.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;

public class SiteView extends FlowPanel {

	private HorizontalSplitPanel splitPanel;
	private HorizontalPanel buttonPanel;

	public SiteView() {
		super();
		splitPanel = SiteViewService.getInstance().setUpSplitPanel();
		buttonPanel = SiteViewService.getInstance().createButtonsForStartScreen(splitPanel);
		splitPanel.setRightWidget(buttonPanel);
		this.add(splitPanel);
	}

}
