package com.sfeir.richercms.main.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.view.InformationPanel;
import com.sfeir.richercms.main.shared.BeanPage;

@Presenter( view = InformationPanel.class)
public class InformationPanelPresenter extends LazyPresenter<IInformationPanel, MainEventBus>{

	private PageServiceAsync rpcPage = null;
	
	public InformationPanelPresenter() {
		super();
	}
	
	/**
	 * Fired when the main do start
	 * @param infoPanel 
	 */
	public void onStartPanels() {
		this.view.deasabledWidgets();
		this.eventBus.changeInfoPanel(this.view);
	}
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
	}
	
	
	/**
	 * display Page informations
	 * @param result the bean containing the page information
	 */
	public void onDisplayPage(String key) {
		this.view.clearFields();
		this.view.deasabledWidgets();
		
		this.rpcPage.getPage(key, new AsyncCallback<BeanPage>() {
			public void onSuccess(BeanPage result) {
				view.setBrowserTitle(result.getBrowserTitle());
				view.setDescription(result.getDescription());
				view.setKeyWord(result.getKeyWord());
				view.setPageTitle(result.getPageTitle());
				//view.setPublicationFinish(result.getPublicationFinish());
				//view.setPublicationStart(result.getPublicationStart());
				view.setUrlName(result.getUrlName());
				eventBus.displayContent(result.getContent());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get current Page");
				p.show();}
		});
	}
		
	/**
	 * make a Page and set all field who information is in InformationPanel
	 * @return a BeanPage with all information of the InformationPanel form
	 */
	public BeanPage addInformationInPage() {
		BeanPage newPage = new BeanPage();
		newPage.setBrowserTitle(view.getBrowserTitle());
		newPage.setContent("");
		newPage.setDescription(view.getDescription());
		newPage.setKeyWord(view.getKeyWord());
		newPage.setPageTitle(view.getPageTitle());
		newPage.setPublicationFinish(view.getPublicationFinish());
		newPage.setPublicationStart(view.getPublicationStart());
		newPage.setUrlName(view.getUrlName());
		
		return newPage;
	}
	
/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	
	public void onAddPage(String key) {
		view.clearFields();
		view.enabledWidgets();
	}
	
	public void onCancelPage() {
		view.clearFields();
		view.deasabledWidgets();
	}
	
	public void onModifyPage(String key) {
		view.enabledWidgets();
	}
	
	public void onSavePage() {
		view.deasabledWidgets();
	}
	
	public void onDeletePage() {
		view.clearFields();
	}
	
	public void onCallInfo() {
		this.eventBus.sendInfo(addInformationInPage());
	}
	
	

	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( PageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
