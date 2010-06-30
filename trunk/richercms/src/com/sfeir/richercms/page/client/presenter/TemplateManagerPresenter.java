package com.sfeir.richercms.page.client.presenter;

import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.TagServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.view.TemplateManager;

@Presenter( view = TemplateManager.class)
public class TemplateManagerPresenter extends LazyPresenter<ITemplateManager, PageEventBus>{

	private TagServiceAsync rpcTag = null;
	
	public void bindView() {
		
	}
	
	public void onStartTemplateManager() {
		eventBus.displayTemplateManager(this.view);
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
