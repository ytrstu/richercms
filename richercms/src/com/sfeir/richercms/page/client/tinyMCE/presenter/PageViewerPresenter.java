package com.sfeir.richercms.page.client.tinyMCE.presenter;

import java.util.List;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;
import com.sfeir.richercms.page.client.tinyMCE.view.PageViewer;

@Presenter( view = PageViewer.class )
public class PageViewerPresenter extends LazyPresenter<IPageViewer,PageEventBus>{

	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
	}
	
	public void onTinyPopUpStartLinkPanels(List<Long> pathId){
		
	}
	
}
