package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.tinyMCE.TinyMCE;

public class TinyMCEPanel extends ResizeComposite implements ITinyMCEPanel {

	private TinyMCE tmce = null;
	
	/**
	 * Constructor
	 * @param height of the tinyEditor in pixels
	 */
	public TinyMCEPanel(int height) {
		super();
		this.tmce = new TinyMCE((height/2-50)+"px");
		this.createView();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	private void createView() {
		LayoutPanel main = new LayoutPanel();
		main.add(this.tmce);
		this.initWidget(main);
	}

	public void ShowEditor() {
		this.tmce.setVisible(true);
	}
	
	public void HideEditor() {
		this.tmce.setVisible(false);	
	}
	
	
}
