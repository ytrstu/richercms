package com.sfeir.richercms.wizard.client.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

/**
 * Center Layout Panel with fixed size
 * @author Ensminger.f
 *
 */
public class CenterLayoutPanel extends ResizeComposite {

	private int width;
	private int height;
	private LayoutPanel mainPanel = new LayoutPanel();
	LayoutPanel borderLayout = new LayoutPanel();
	
	/**
	 * Center Layout Panel with fixed size
	 * 
	 * @param width : width in pixel from the layout
	 * @param height : height in pixel from the layout
	 * @param dialog : main child
	 */
	public CenterLayoutPanel(int width, int height, Widget title, Widget dialog) {
		super();
		title.setStyleName("wizardDialogTitle");
		this.height = height;
		this.width = width;
		mainPanel.addStyleName("wizardMain");
		borderLayout.addStyleName("wizardDialog");
		mainPanel.add(borderLayout);
		LayoutPanel paddingPanel = new LayoutPanel();
		borderLayout.add(paddingPanel);
		borderLayout.setWidgetLeftRight(paddingPanel, 5, Style.Unit.PX, 5, Style.Unit.PX);
		borderLayout.setWidgetTopBottom(paddingPanel, 5, Style.Unit.PX, 5, Style.Unit.PX);
		LayoutPanel titlePanel = new LayoutPanel();
		titlePanel.addStyleName("titlePanel");
		titlePanel.add(title);
		paddingPanel.add(titlePanel);
		paddingPanel.add(dialog);
		paddingPanel.setWidgetLeftRight(dialog, 0, Style.Unit.PX, 0, Style.Unit.PX);
		paddingPanel.setWidgetTopBottom(dialog, 30, Style.Unit.PX, 0, Style.Unit.PX);
		initWidget(mainPanel);
		
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				changeSize();
			}
		});
	}
	
	public CenterLayoutPanel(int width, int height){
		super();
		this.height = height;
		this.width = width;
		mainPanel.addStyleName("wizardMain");
		borderLayout.addStyleName("wizardDialog");
		mainPanel.add(borderLayout);
		initWidget(mainPanel);
		
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				changeSize();
			}
		});
	}
	
	public void setContent(Widget title, Widget content){
		title.setStyleName("wizardDialogTitle");
		LayoutPanel paddingPanel = new LayoutPanel();
		borderLayout.add(paddingPanel);
		borderLayout.setWidgetLeftRight(paddingPanel, 5, Style.Unit.PX, 5, Style.Unit.PX);
		borderLayout.setWidgetTopBottom(paddingPanel, 5, Style.Unit.PX, 5, Style.Unit.PX);
		LayoutPanel titlePanel = new LayoutPanel();
		titlePanel.addStyleName("titlePanel");
		titlePanel.add(title);
		paddingPanel.add(titlePanel);
		paddingPanel.add(content);
		paddingPanel.setWidgetLeftRight(content, 0, Style.Unit.PX, 0, Style.Unit.PX);
		paddingPanel.setWidgetTopBottom(content, 30, Style.Unit.PX, 0, Style.Unit.PX);
	}

	@Override
	public void onResize() {
		changeSize();
		super.onResize();
	}

	/**
	 * called in the onResize event
	 */
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

		mainPanel.setWidgetLeftWidth(borderLayout, left, Style.Unit.PX, width, Style.Unit.PX);
		mainPanel.setWidgetTopHeight(borderLayout, top, Style.Unit.PX, height, Style.Unit.PX);
		
	}
	
}
