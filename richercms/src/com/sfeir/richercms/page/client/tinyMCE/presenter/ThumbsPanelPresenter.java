package com.sfeir.richercms.page.client.tinyMCE.presenter;

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
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.tinyMCE.view.ThumbsPanel;
import com.sfeir.richercms.page.client.view.custom.thumb.ThumbFocusable;
import com.sfeir.richercms.page.shared.BeanFile;

/**
 * Presenter of the ThumbsPanel view
 * @author homberg.g
 *
 */
@Presenter( view = ThumbsPanel.class )
public class ThumbsPanelPresenter extends LazyPresenter<IThumbsPanel,PageEventBus> {
	
	private FileServiceAsync rpcFile = null;
	
	public void bindView() {
		this.view.onSendBtnclick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.submitForm();
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
				displayThumbNails(true);
			}
		});
	}
	
	public void onTinyPopUpStartImgPanels(List<Long> pathId){
		this.view.clearThumbNails();
		this.eventBus.tinyPopUpDisplayThumbsPanel(this.view);
		
	}
	
	public void onDisplayThumbsInPopUp(String path){
		this.view.setCurrentPath(path);
		//this.eventBus.addWaitLinePopUp("chargement des miniatures");
		this.displayThumbNails(false);
		
		//no thumbs was selected
		this.eventBus.selectThumbs("");
	}
	
	/**
	 * Clear thumbnail panel and display all thumbnail.
	 * If the justLast are set with true, just the last thumb are
	 * added and the panel are not cleared.
	 * @param justLast
	 */
	private void displayThumbNails(final boolean justLast) {
		if(!justLast)
			this.view.clearThumbNails();
		this.rpcFile.getFile(this.view.getCurrentPath(), new AsyncCallback<List<BeanFile>>(){
			public void onFailure(Throwable caught) {
				//eventBus.addErrorLinePopUp("Impossible d'ajouter les miniatures");
			}
			public void onSuccess(List<BeanFile> result) {
				if(!justLast){
					for(BeanFile bean : result){
						addThumbNail(bean);
					}
					//eventBus.addSuccessPopUp("Miniature chargé avec succès");
					//eventBus.hideInformationPopUp();
				}else {
					addThumbNail(result.get(result.size()-1));
				}
			}
		});
	}
	
	/**
	 * add One thumbNail in view.
	 * this method use an RPC call
	 * @param bean
	 */
	private void addThumbNail(final BeanFile  bean) {
		this.view.addThumbnail(bean.getPath()+bean.getFileName())
			.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					ThumbFocusable thumb = (ThumbFocusable)event.getSource();
					setSelectedImgPath(thumb.getImg().getPath());
			}});
	}
	
	private void setSelectedImgPath(String path){
		this.eventBus.selectThumbs(path);
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
