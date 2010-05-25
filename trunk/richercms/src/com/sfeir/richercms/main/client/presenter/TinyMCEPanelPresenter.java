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
		view.displayViewer(content);
		this.view.setContent(content);
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.displayViewer(this.view.getContent());
		this.eventBus.changeEditorPanel(this.view);
	}
	
	public void onModifyPage(Long id) {
		view.displayEditor(this.view.getContent());
	}
	
	public void onAddPage(Long id) {
		view.displayEditor("");
	}
	
	public void onCancelPage() {
		this.view.displayViewer(this.view.getContent());
	}
	
	public void onSavePage() {
		this.view.displayViewer(this.view.getContent());
	}
	
	public void onCallContent() {
		eventBus.sendContent(this.view.getContent());
	}
	
}
