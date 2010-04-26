package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.tinyMCE.TinyMCE;

/**
 * @author homberg.g
 */
public class TinyMCEPanel extends ResizeComposite implements ITinyMCEPanel {

	private TinyMCE tmce = null;
	private final int height = Window.getClientHeight()-30;
	
	/**
	 * Constructor
	 * @param height of the tinyEditor in pixels
	 */
	public TinyMCEPanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.tmce = new TinyMCE((height/2-50)+"px");
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
	
	public String getContent() {
		return this.tmce.getText();
	}
	
	public void setContent(String content) {
		this.tmce = new TinyMCE((this.height/2-50)+"px");
		this.tmce.setText(content);
	}
	
	public void enableEditor() {
		this.tmce.setReadOnly(true);
	}
	
	public void disableEditor() {
		this.tmce.setReadOnly(false);
	}
}
