package com.sfeir.richercms.wizardConfig.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.LayoutPanel;

public class CenterLayoutPanel extends LayoutPanel {

	private int width;
	private int height;
	private LayoutPanel dialog;
	
	/**
	 * Center Layout Panel with fixed size
	 * 
	 * @param width : width in pixel from the layout
	 * @param height : height in pixel from the layout
	 * @param dialog : main child
	 */
	public CenterLayoutPanel(int width, int height, LayoutPanel dialog) {
		super();
		this.height = height;
		this.width = width;
		this.dialog = dialog;
		this.add(dialog);
		
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				changeSize();
			}
		});
	}

	@Override
	public void onResize() {
		changeSize();
		super.onResize();
	}

	public void changeSize() {
		int parentHeight = getParent().getOffsetHeight();
		int parentWidth = getParent().getOffsetWidth();
		
		int left = (parentWidth - width)/2;
		if (left<0) {
			left = 0;
		}
		int top = (parentHeight - height)/2;
		if (top<0) {
			top = 0;
		}

		this.setWidgetLeftWidth(dialog, left, Style.Unit.PX, width, Style.Unit.PX);
		this.setWidgetTopHeight(dialog, top, Style.Unit.PX, height, Style.Unit.PX);
		
	}
	
}
