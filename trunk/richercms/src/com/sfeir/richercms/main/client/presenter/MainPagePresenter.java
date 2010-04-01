package com.sfeir.richercms.main.client.presenter;


import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.main.client.Interface.IdisplayMainPage;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.view.MainPageView;


@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
	}

	public void onStart() {
		eventBus.changeBody(view.asWidget());
	}
}
