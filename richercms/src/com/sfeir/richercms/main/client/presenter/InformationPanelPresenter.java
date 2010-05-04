package com.sfeir.richercms.main.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.ArboPageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.client.view.InformationPanel;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.main.shared.BeanTranslationPage;

@Presenter( view = InformationPanel.class)
public class InformationPanelPresenter extends LazyPresenter<IInformationPanel, MainEventBus>{

	private ArboPageServiceAsync rpcPage = null;
	
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
	 * make a Page and set all field who information is in InformationPanel
	 * @return a BeanTranslationPage with all information of the InformationPanel form
	 */
	public BeanTranslationPage addInformationInPage() {
		BeanTranslationPage newTranslation = new BeanTranslationPage();
		newTranslation.setBrowserTitle(view.getBrowserTitle());
		newTranslation.setContent("");
		newTranslation.setDescription(view.getDescription());
		newTranslation.setKeyWord(view.getKeyWord());
		newTranslation.setPageTitle(view.getPageTitle());
		newTranslation.setPublicationFinish(view.getPublicationFinish());
		newTranslation.setPublicationStart(view.getPublicationStart());
		newTranslation.setUrlName(view.getUrlName());
		
		return newTranslation;
	}
	
	/**
	 * Display a BeanArboPage information in the good field
	 * @param page : the beanArboPage representing a webPage
	 */
	private void displayArboPage(BeanArboPage page){
		this.view.clearFields();
		this.view.deasabledWidgets();
		
		view.setBrowserTitle(page.getTranslation().get(0).getBrowserTitle());
		view.setDescription(page.getTranslation().get(0).getDescription());
		view.setKeyWord(page.getTranslation().get(0).getKeyWord());
		view.setPageTitle(page.getTranslation().get(0).getPageTitle());
		//view.setPublicationFinish(page.getPublicationFinish());
		//view.setPublicationStart(page.getPublicationStart());
		view.setUrlName(page.getTranslation().get(0).getUrlName());
		eventBus.displayContent(page.getTranslation().get(0).getContent());
	}
	
/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	

	public void onDisplayPage(String key) {
		this.rpcPage.getArboPage(key, new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				displayArboPage(result);
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get current Page");
				p.show();}
		});
	}
	
	public void onDisplayMainPage() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				displayArboPage(result);
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get Main Page");
				p.show();}
		});	
	}
	
	
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
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
}
