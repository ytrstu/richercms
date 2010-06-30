package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.TagServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITagManager;
import com.sfeir.richercms.page.client.view.TagManager;
import com.sfeir.richercms.page.shared.BeanTag;


@Presenter( view = TagManager.class)
public class TagManagerPresenter extends LazyPresenter<ITagManager, PageEventBus>{

	private TagServiceAsync rpcTag = null;
	
	public void bindView() {
		this.view.clickOnAddTag().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				BeanTag bean = new BeanTag(view.getNewTagName(),
						view.getNewShortLib(),
						view.getNewDescription());
				rpcTag.addTag(bean, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {}
					public void onSuccess(Void result) {
						view.addLine(view.getNewTagName(),
								view.getNewShortLib(), 
								view.getNewDescription());
						view.clearAddNewTagTextBox();
					}
				});
			}
		});
	}
	
	public void onStartTagManager() {
		this.fetchTagTable();
		eventBus.displayTagManager(this.view);
	}
	
	private void fetchTagTable(){
		this.view.clearTagTable();
		this.rpcTag.getAllTags(new AsyncCallback<List<BeanTag>>() {
			public void onSuccess(List<BeanTag> result) {
				for(final BeanTag bean : result){
					
					view.addLine(bean.getTagName(),
							bean.getShortLib(),
							bean.getDescription()).addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {
									deleteTag(bean.getId());
								}
							});
				}
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void deleteTag(Long id){
		this.rpcTag.deleteTag(id, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(Void result) {}
		});
	}
	
	/**
	 * used by the framework to instantiate rpcTag
	 * @param rpcUser
	 */
	@InjectService
	public void setTagService( TagServiceAsync rpcTag ) {
		this.rpcTag = rpcTag;
	}
}
