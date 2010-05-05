package com.sfeir.richercms.main.client.presenter;

import java.util.List;

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
	private BeanArboPage currentPage = null;
	private int translationIndex = 0;
	
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
	public List<BeanTranslationPage> addInformationInPage() {
		
		List<BeanTranslationPage> lst = this.currentPage.getTranslation();
		
		BeanTranslationPage newTranslation = new BeanTranslationPage();
		newTranslation.setBrowserTitle(view.getBrowserTitle());
		newTranslation.setContent("");
		newTranslation.setDescription(view.getDescription());
		newTranslation.setKeyWord(view.getKeyWord());
		newTranslation.setPageTitle(view.getPageTitle());
		newTranslation.setPublicationFinish(view.getPublicationFinish());
		newTranslation.setPublicationStart(view.getPublicationStart());
		newTranslation.setUrlName(view.getUrlName());
		
		lst.set(this.translationIndex, newTranslation);
		
		return lst;
	}
	
	/**
	 * Display a BeanArboPage information in the good field
	 * @param page : the beanArboPage representing a webPage
	 */
	private void displayArboPage(BeanArboPage page){
		this.view.clearFields();
		int indexTransToDisp = 0; // si la traduction n'existe pas encore alors on affiche celle de base
		
		// si la traduction est vide alors on affiche la traduction par défaut
		if(!isEmpty(page.getTranslation().get(this.translationIndex)))
			indexTransToDisp = this.translationIndex;
		
		view.setBrowserTitle(page.getTranslation().get(indexTransToDisp).getBrowserTitle());
		view.setDescription(page.getTranslation().get(indexTransToDisp).getDescription());
		view.setKeyWord(page.getTranslation().get(indexTransToDisp).getKeyWord());
		view.setPageTitle(page.getTranslation().get(indexTransToDisp).getPageTitle());
		//view.setPublicationFinish(page.getPublicationFinish());
		//view.setPublicationStart(page.getPublicationStart());
		view.setUrlName(page.getTranslation().get(indexTransToDisp).getUrlName());
		eventBus.displayContent(page.getTranslation().get(indexTransToDisp).getContent());
	}
	
	private boolean isEmpty(BeanTranslationPage bean){
		
		if((bean.getBrowserTitle() != null ) && (!bean.getBrowserTitle().equals(""))) return false;
		
		if((bean.getContent() != null ) && (!bean.getContent().equals(""))) return false;
		
		if((bean.getDescription() != null ) && (!bean.getDescription().equals(""))) return false;
		
		if((bean.getPageTitle() != null ) && (!bean.getPageTitle().equals(""))) return false;
		
		if((bean.getUrlName() != null ) && (!bean.getUrlName().equals(""))) return false;
			
		return true;
	}
	
/////////////////////////////////////////////// EVENT ///////////////////////////////////////////////
	

	public void onDisplayPage(String key) {
		this.rpcPage.getArboPage(key, new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				view.deasabledWidgets();
				displayArboPage(result);
				currentPage = result;
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get current Page");
				p.show();}
		});
	}
	
	public void onDisplayMainPage() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				view.deasabledWidgets();
				currentPage = result;
				displayArboPage(result);
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get Main Page");
				p.show();}
		});	
	}
	
	
	public void onAddPage(String key) {
		this.translationIndex = 0; //on commence toujours par ajouter la langue par défaut
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
	
	public void onChangeTranslation(int index) {
		this.translationIndex = index;
		this.displayArboPage(this.currentPage);
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
