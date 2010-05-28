package com.sfeir.richercms.main.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.MainState;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.main.client.view.TinyMCEPanel;
import com.sfeir.richercms.main.shared.BeanTranslationPage;

@Presenter( view = TinyMCEPanel.class)
public class TinyMCEPanelPresenter extends LazyPresenter<ITinyMCEPanel, MainEventBus>{

	private int indexOfTranslation = 0;
	private List<BeanTranslationPage> TranslationContents = null;
	private MainState state = MainState.display;
	
	public TinyMCEPanelPresenter() {
		super();
	}
	
	/**
	 * Bind the various evt
	 */ 
	public void bindView() {
		
	}
	

	/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onDisplayContent(List<BeanTranslationPage>  translationContents) {
		this.TranslationContents = translationContents;
		this.view.displayViewer(this.TranslationContents.get(this.indexOfTranslation).getContent());
		this.state = MainState.display;
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.displayViewer(this.view.getContent());
		this.eventBus.changeEditorPanel(this.view);
		this.state = MainState.display;
	}
	
	public void onModifyPage(Long id) {
		view.displayEditor(this.view.getContent());
		this.state = MainState.modify;
	}
	
	public void onAddPage(Long id) {
		view.displayEditor("");
		this.indexOfTranslation = 0;
		this.state = MainState.add;
	}
	
	public void onCancelPage() {
		this.view.displayViewer(this.view.getContent());
		this.indexOfTranslation = 0;
		this.state = MainState.display;
	}
	
	public void onSavePage() {
		//this.view.displayViewer(this.view.getContent());
		//this.indexOfTranslation = 0;
		//this.state = MainState.display;
	}
	
	public void onCallContent() {
		ArrayList<String> contents = new ArrayList<String>();
		// get the last content and add it in the right Translation
		this.TranslationContents.get(this.indexOfTranslation).setContent(this.view.getContent());
		
		for(BeanTranslationPage trans : this.TranslationContents) {
			contents.add(trans.getContent());
		}
		eventBus.sendContent(contents);
	}
	
	public void onChangeTranslation(int index) {
		
		if(this.state == MainState.display){
			this.view.displayViewer(this.TranslationContents.get(index).getContent());
		}else {
			// get the current content and add it in the current Translation
			this.TranslationContents.get(this.indexOfTranslation).setContent(this.view.getContent());
			//display the another content
			this.view.displayEditor(this.TranslationContents.get(index).getContent());
		}
		//modify the current index
		this.indexOfTranslation = index;
	}
	
}
