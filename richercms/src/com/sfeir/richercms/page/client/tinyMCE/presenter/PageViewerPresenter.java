package com.sfeir.richercms.page.client.tinyMCE.presenter;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;
import com.sfeir.richercms.page.client.tinyMCE.view.PageViewer;
import com.sfeir.richercms.page.shared.BeanTranslationPage;

/**
 * Presenter of the PageViewer panel
 * All interaction with eventBus, datastore and event handling
 * are coded here
 * @author homberg.g
 */
@Presenter( view = PageViewer.class )
public class PageViewerPresenter extends LazyPresenter<IPageViewer,PageEventBus>{

	private ArboPageServiceAsync rpcPage = null;
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
	}
	
	public void onTinyPopUpStartLinkPanels(List<Long> pathId) {
		this.eventBus.tinyPopUpDisplayContentViewer(this.view);
	}
	
	public void onDisplayContentInPopUp(Long pageId) {
		this.rpcPage.getDefaultTranslation(pageId, new AsyncCallback<BeanTranslationPage>() {
			public void onSuccess(BeanTranslationPage result) {
				view.addContent(result.getContent());
			}
			public void onFailure(Throwable caught) {}
		});
	}
	
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
	
}
