package com.sfeir.richercms.main.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.view.TinyMCEPanel;

@Presenter( view = TinyMCEPanel.class)
public class TinyMCEPanelPresenter extends LazyPresenter<ITinyMCEPanel, MainEventBus>{

	
	public TinyMCEPanelPresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
	}
	
	/**
	 * Display content in the Editor
	 * @param content to display
	 */
	public void displayContent(String content) {
		this.view.setContent(content);
	}
	

	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onDisplayContent(String content) {
		view.disableEditor();
		this.view.setContent(content);
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.disableEditor();
		this.eventBus.changeEditorPanel(this.view);
	}
	
	public void onModifyPage(String key) {
		this.view.enableEditor();
	}
	
	public void onAddPage(String key) {
		view.enableEditor();
		this.view.setContent("");
	}
	
	public void onCancelPage() {
		view.disableEditor();
	}
	
	public void onSavePage() {
		view.disableEditor();
	}
	
	public void onCallContent() {
		eventBus.sendContent(this.view.getContent());
	}
	
}
