package com.sfeir.richercms.image.client.presenter;

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
import com.sfeir.richercms.image.client.event.ImageEventBus;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;
import com.sfeir.richercms.image.client.view.AddPanel;
import com.sfeir.richercms.page.client.FileServiceAsync;

@Presenter( view = AddPanel.class )
public class AddPanelPresenter extends LazyPresenter<IAddPanel, ImageEventBus> {
	
	
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
	
	public void onStartPanels() {
		eventBus.showInformationPopUp();
		displayThumbNails();
		this.eventBus.displayAddPanel(this.view);
	}
	
	private void displayThumbNails() {
		eventBus.addWaitLinePopUp("refresh thumbs");
		this.view.clearThumbNails();
		this.rpcFile.getUnlikedFile(new AsyncCallback<List<Long>>(){
			public void onFailure(Throwable caught) {
				eventBus.addErrorLinePopUp("ERROR during loading thumbs");
				eventBus.hideInformationPopUp();
			}
			public void onSuccess(List<Long> result) {
				for(Long id : result){
					view.addThumbnail(id);
				}
				eventBus.addSuccessPopUp("thumbs successfuly loaded");
				eventBus.hideInformationPopUp();
			}
		});
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
