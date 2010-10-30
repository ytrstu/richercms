package com.sfeir.richercms.page.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITinyMCEPanel;
import com.sfeir.richercms.page.client.state.PageState;
import com.sfeir.richercms.page.client.view.TinyMCEPanel;
import com.sfeir.richercms.page.shared.BeanTranslationPage;

/**
 * Presenter of the tinyMCE panel view
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = TinyMCEPanel.class)
public class TinyMCEPanelPresenter extends LazyPresenter<ITinyMCEPanel, PageEventBus>{

	private int indexOfTranslation = 0;
	private List<BeanTranslationPage> TranslationContents = null;
	private PageState state = PageState.display;
	
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
		// display this message only if application are in display state
		if(this.state == PageState.display)
			this.eventBus.addSuccessPopUp("Chargement terminé");
		this.state = PageState.display;
		this.eventBus.hideInformationPopUp();
	}
	
	public void onDisplayEditor(List<BeanTranslationPage>  translationContents) {
		this.TranslationContents = translationContents;
		view.displayEditor(this.TranslationContents.get(this.indexOfTranslation).getContent());
		this.state = PageState.modify;
	}
	
	/**
	 * Fired when the main do start
	 * @param navPanel 
	 */
	public void onStartPanels() {
		this.view.addEventBusInTiny(this.eventBus);
		this.view.displayViewer(this.view.getContent());
		this.eventBus.changeEditorPanel(this.view);
		this.state = PageState.display;
	}
	
	public void onModifyPage(Long id, Long parentPageId, List<Long> recPath) {
		view.displayEditor(this.view.getContent());
		this.state = PageState.modify;
	}
	
	public void onAddPage(Long id) {
		view.displayEditor("");
		this.indexOfTranslation = 0;
		this.state = PageState.add;
	}
	
	public void onCancelPage(PageState newState) {
		this.view.displayViewer(this.view.getContent());
		this.state = newState;
	}
	
	public void onDeletePage(){
		this.view.displayViewer("");
	}
	
	public void onCallContent() {
		ArrayList<String> contents = new ArrayList<String>();
		// get the last content and add it in the right Translation
		this.TranslationContents.get(this.indexOfTranslation).setContent(this.modifyImgTag(this.view.getContent()));
		
		for(BeanTranslationPage trans : this.TranslationContents) {
			contents.add(trans.getContent());
		}
		eventBus.sendContent(contents);
	}
	
	public void onChangeTranslation(int index) {
		
		if(this.state == PageState.display){
			this.view.displayViewer(this.TranslationContents.get(index).getContent());
		}else {
			// get the current content and add it in the current Translation
			this.TranslationContents.get(this.indexOfTranslation).setContent(this.modifyImgTag(this.view.getContent()));
			//display the another content
			this.view.displayEditor(this.TranslationContents.get(index).getContent());
		}
		//modify the current index
		this.indexOfTranslation = index;
	}
	
	/**
	 * add one '/' in the tag img : src="image..."  => src="/image..."
	 * @param content : tinyCME editor content.
	 * @return the content with modification
	 */
	private String modifyImgTag(String content) {
		return content.replace("src=\"image", "src=\"/image");
	}
	
}
