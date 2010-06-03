package com.sfeir.richercms.image.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.image.client.event.ImageEventBus;
import com.sfeir.richercms.image.client.interfaces.IImageTreePanel;
import com.sfeir.richercms.image.client.view.ImageTreePanel;

@Presenter( view =ImageTreePanel.class)
public class ImageTreePanelPresenter  extends LazyPresenter<IImageTreePanel,ImageEventBus>{

	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() { }
}
