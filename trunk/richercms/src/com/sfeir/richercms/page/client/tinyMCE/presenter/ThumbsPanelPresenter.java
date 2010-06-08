package com.sfeir.richercms.page.client.tinyMCE.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.FileServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.view.ThumbsPanel;
import com.sfeir.richercms.page.shared.BeanFile;

@Presenter( view = ThumbsPanel.class )
public class ThumbsPanelPresenter extends LazyPresenter<IThumbsPanel,PageEventBus> {
	
	private FileServiceAsync rpcFile = null;
	
	public void bindView() {
	}
	
	public void onTinyPopUpStartPanels(){
		this.eventBus.tinyPopUpDisplayThumbsPanel(this.view);
	}
	
	public void onDisplayThumbsInPopUp(String path){
		this.view.setCurrentPath(path);
		this.displayThumbNails();
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
		//eventBus.hideInformationPopUp();
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
