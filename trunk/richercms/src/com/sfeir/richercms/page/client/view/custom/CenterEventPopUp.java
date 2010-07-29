package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.sfeir.richercms.wizard.client.view.CenterLayoutPanel;

/**
 * 
 * @author homberg.g
 * Panel display in center of principal view to display state of specific action
 * like delete/add/modify page
 */
public class CenterEventPopUp extends CenterLayoutPanel {

	private FlexTable displayTable = new FlexTable();
	private int previousState = -1;

	public CenterEventPopUp(int width, int height, String title) {
		super(width, height);
		this.displayTable = new FlexTable();
		this.displayTable.setCellSpacing(5);
		this.setContent(new Label(title), this.displayTable);
	}

	public void AddLine(String text, int state) {
		int index = this.displayTable.getRowCount();

		if (this.previousState == 0) {
			index--;
		}

		switch (state) {
		case 0:
			this.displayTable.setWidget(index, 0, new Image(
					"tab_images/wait.gif"));
			break;
		case 1:
			this.displayTable.setWidget(index, 0, new Image(
					"tab_images/check.png"));
			break;
		case 2:
			this.displayTable.setWidget(index, 0, new Image(
					"tab_images/fail.png"));
			break;
		}

		this.displayTable.setWidget(index, 1, new Label(text));
		this.previousState = state;
	}

	public void ClearTable() {
		this.displayTable.clear();
		this.displayTable.removeAllRows();
	}

}
