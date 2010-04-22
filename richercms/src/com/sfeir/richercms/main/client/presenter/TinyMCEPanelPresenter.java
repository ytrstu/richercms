package com.sfeir.richercms.main.client.presenter;

import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;

public class TinyMCEPanelPresenter {

	private ITinyMCEPanel view = null;
	
	public TinyMCEPanelPresenter() {
		this.view = null;
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartTinyMCEPanel(ITinyMCEPanel editorPanel) {
		this.view = editorPanel;
		this.view.disableEditor();
	}
	
	/**
	 * Display content in the Editor
	 * @param content to display
	 */
	public void displayContent(String content) {
		
	}
	
}
