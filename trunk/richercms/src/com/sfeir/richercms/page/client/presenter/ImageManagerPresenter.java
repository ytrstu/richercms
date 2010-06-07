package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.sfeir.richercms.page.shared.BeanFile;

@Presenter( view = ImageManager.class )
public class ImageManagerPresenter extends LazyPresenter<IImageManager, PageEventBus> {

	private FileServiceAsync rpcFile = null;
	
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { 
		this.view.onSendBtnclick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.submitForm();
				eventBus.showInformationPopUp();
				eventBus.addWaitLinePopUp("envoi de l'image en cours");
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
				System.out.println("form.addSubmitCompleteHandler");
				eventBus.addSuccessPopUp("image sauvegardée");
				displayThumbNails();
			}
		});
	}
	
	public void onStartImagePanel(String path) {
		this.view.setCurrentPath(path);
		this.displayThumbNails();
		this.eventBus.displayImageManager(this.view);
	}
	
	
	private void displayThumbNails() {
		/*eventBus.addWaitLinePopUp("refresh thumbs");*/
		this.view.clearThumbNails();
		this.rpcFile.getFile(this.view.getCurrentPath(), new AsyncCallback<List<BeanFile>>(){
			public void onFailure(Throwable caught) {
				/*eventBus.addErrorLinePopUp("ERROR during loading thumbs");
				eventBus.hideInformationPopUp();*/
			}
			public void onSuccess(List<BeanFile> result) {
				for(BeanFile bean : result){
					addThumbNail(bean);
				}
				//eventBus.addSuccessPopUp("thumbs successfuly loaded");
				//eventBus.hideInformationPopUp();
			}
		});
		eventBus.hideInformationPopUp();
	}
	
	private void addThumbNail(final BeanFile  bean) {
		this.view.addThumbnail(bean.getPath()+bean.getFileName())
			.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					rpcFile.deleteFile(bean.getId(), new AsyncCallback<Void>(){
						public void onFailure(Throwable caught) {
						}
						public void onSuccess(Void result) {
							displayThumbNails();
						}
					});
			}});
		
		this.view.onThumbClick().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				view.showPopUpImgPreview(bean.getPath()+bean.getFileName());
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
