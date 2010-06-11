package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.FileServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.view.ImageManager;
import com.sfeir.richercms.page.client.view.custom.ConfirmationBox;
import com.sfeir.richercms.page.shared.BeanFile;

@Presenter( view = ImageManager.class )
public class ImageManagerPresenter extends LazyPresenter<IImageManager, PageEventBus> {

	private FileServiceAsync rpcFile = null;
	private Element deletedThumb; // node dans le dom du thumb dont l'image vien d'être effacé
	
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { 
		this.view.onSendBtnclick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.submitForm();
				eventBus.showInformationPopUp();
				eventBus.addWaitLinePopUp(view.getConstants().MsgSendImage());
			}
		});
		
		//fired when submitting the form begins
		this.view.getFormEvent().addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				System.out.println("form.addSubmitHandler");
			}
		});
		
		//fired when the form is submitted
		this.view.getFormEvent().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String test = event.getResults();
				System.out.println(view.getConstants().MsgImageSaved());
				
				//ERROR 413 : SC_REQUEST_ENTITY_TOO_LARGE
				if(test.contains("HTTP ERROR 413")){
					eventBus.addErrorLinePopUp("Image not stored, there is to large, maximum size : 1MO");
					eventBus.hideInformationPopUp();
				}else if (test.contains("HTTP ERROR 415")){
					eventBus.addErrorLinePopUp("Just image can be stored here (png, jpg, tif, ...)");
					eventBus.hideInformationPopUp();
				}else{
					eventBus.addSuccessPopUp(view.getConstants().MsgImageSaved());
					displayThumbNails(true);
				}
			}
		});
	}
	
	public void onStartImagePanel(String path) {
		this.view.setCurrentPath(path);
		this.displayThumbNails(false);
		this.eventBus.displayImageManager(this.view);
	}
	
	
	private void displayThumbNails(final boolean justLast) {
		eventBus.showInformationPopUp();
		eventBus.addWaitLinePopUp(this.view.getConstants().MsgRefreshThumb());
		if(!justLast)
			this.view.clearThumbNails();
		
		this.rpcFile.getFile(this.view.getCurrentPath(), new AsyncCallback<List<BeanFile>>(){
			public void onFailure(Throwable caught) {
				eventBus.addErrorLinePopUp(view.getConstants().MsgErrorLoadThumbs());
				eventBus.hideInformationPopUp();
			}
			public void onSuccess(List<BeanFile> result) {
				if(!justLast){
					for(BeanFile bean : result){
						addThumbNail(bean);
					}
				}else {
					addThumbNail(result.get(result.size()-1));
				}
				
				if(result.size() != 0)
					eventBus.addSuccessPopUp(view.getConstants().MsgSuccessLoadThumbs());
				else
					eventBus.addSuccessPopUp(view.getConstants().MsgErrorImgNotFound());
				
				eventBus.hideInformationPopUp();
			}
		});
	}
	
	

	private void addThumbNail(final BeanFile  bean) {
		this.view.addThumbnail(bean.getPath()+bean.getFileName())
			.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					// récupération de la case contenant le bouton + l'image (le tout inclus dans un panel)
					ImageManagerPresenter.this.deletedThumb = event.getRelativeElement();
					
					deleteImg(bean);
			}});
		
		this.view.onThumbClick().addMouseDownHandler(new MouseDownHandler(){
			public void onMouseDown(MouseDownEvent event) {
				view.showPopUpImgPreview(bean.getPath()+bean.getFileName());
			}});
	}
	
	/**
	 * Delete a file into the dataStore
	 * @param bean : file to delete
	 */
	private void deleteImg(final BeanFile bean) {
		ConfirmationBox confirmPopUp = new ConfirmationBox("ATTENTION", this.view.getConstants().MsgSuppImg());
		confirmPopUp.getClickOkEvt().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rpcFile.deleteFile(bean.getId(), new AsyncCallback<Void>(){
					public void onFailure(Throwable caught) {}
					public void onSuccess(Void result) {
						view.deleteThumb(deletedThumb);
					}
				});
			}});
	}
	
	
	/**
	 * used by the framework to instantiate rpcFile 
	 * @param rpcFile
	 */
	@InjectService
	public void setFileService(FileServiceAsync rpcFile) {
		this.rpcFile = rpcFile;
	}
	
}
