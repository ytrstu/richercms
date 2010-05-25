package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
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
	private HTMLPanel  displayPanel = null;
	private LayoutPanel main = null;
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
		this.main = new LayoutPanel();
		this.displayEditor("");
		this.initWidget(main);
	}

	public void ShowEditor() {
		this.tmce.enable();
	}
	
	public void HideEditor() {
		this.tmce.disable();
	}
	
	public void clear() {
		this.tmce.setVisible(true);
	}
	
	public String getContent() {
		if(this.main.getWidget(0).equals(this.displayPanel))
			return this.displayPanel.getWidget(0).toString();
		else
			return this.tmce.getText();
	}
	
	public void setContent(String content) {
		this.tmce.setText(content);
	}
	
	public void enableEditor() {
		this.tmce.enable();
	}
	
	public void disableEditor() {
		this.tmce.disable();
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void displayEditor(String html) {
		this.main.clear();
		this.tmce = new TinyMCE((height/2-50)+"px");
		this.main.add(this.tmce);
		this.setContent(html);
	}
	
	public void displayViewer(String html) {
		this.main.clear();
		this.displayPanel = new HTMLPanel(html);
		this.main.add(this.displayPanel);
	}
}
