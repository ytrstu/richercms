package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.interfaces.custom.IPopUpTree;

/**
 * 
 * @author homberg.g
 * 
 *        Old PopUp System
 * 
 */
public class PopUpTree extends PopupPanel implements IPopUpTree {

	// gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Button btnAddPage = new Button(constants.PopUpAddPage());
	private Button btnDelPage = new Button(constants.PopUpDelPage());

	public PopUpTree() {
		super();
		this.setAutoHideEnabled(true);
		this.setAnimationEnabled(true);
	}

	public void show(int type) {

		switch (type) {
		case 1:
			addPage();// addPage
		}
		this.setAnimationEnabled(true);
		super.show();
		this.setAnimationEnabled(false);
	}

	private void addPage() {
		this.clear();
		this.setTitle(constants.PopUpAddDelPageTitle());
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(this.btnAddPage);
		hPanel.add(this.btnDelPage);
		hPanel.setSpacing(5);

		this.add(hPanel);
	}

	public HasClickHandlers getClickBtnAddPage() {
		return this.btnAddPage;
	}

	public HasClickHandlers getClickBtnDelPage() {
		return this.btnDelPage;
	}

	public void setPopupPosition(int left, int top) {
		super.setPopupPosition(left + 10, top + 10);
	}
}
