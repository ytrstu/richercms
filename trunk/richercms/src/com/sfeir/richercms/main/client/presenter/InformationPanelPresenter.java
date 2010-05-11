package com.sfeir.richercms.main.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
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
	private int cpt = 0;
	
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
		
		this.view.getFocusOnTB0().addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				view.showOneHelp(0);
			};
		});
		
		this.view.getFocusOnTB1().addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				view.showOneHelp(1);
			};
		});
		
		this.view.getFocusOnTB2().addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				view.showOneHelp(2);
			};
		});
		
		this.view.getFocusOnTB3().addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				view.showOneHelp(3);
			};
		});
		
		this.view.getFocusOnTB4().addFocusHandler(new FocusHandler(){
			public void onFocus(FocusEvent event) {
				view.showOneHelp(4);
			};
		});
		
		for(this.cpt=0; this.cpt<5; this.cpt++){
			this.view.getclickBtnCpy(this.cpt).addClickHandler(new ClickHandler(){
				private final int index = cpt;
				public void onClick(ClickEvent event) {
					view.cpyHelpInField(index);
				}
			});
		}
		this.cpt = 0;
	}
	
		
	/**
	 * make a Page and set all field who information is in InformationPanel
	 * @return a BeanArboPage with all information of the InformationPanel form
	 */
	public BeanArboPage addInformationInPage() {
		BeanArboPage nBaP = new BeanArboPage();
		List<BeanTranslationPage> lst = this.currentPage.getTranslation();
		
		BeanTranslationPage newTranslation = new BeanTranslationPage();
		newTranslation.setBrowserTitle(view.getBrowserTitle());
		newTranslation.setContent("");
		newTranslation.setDescription(view.getDescription());
		newTranslation.setKeyWord(view.getKeyWord());
		newTranslation.setPageTitle(view.getPageTitle());
		newTranslation.setUrlName(view.getUrlName());
		nBaP.setPublicationStart(view.getPublicationStart());
		nBaP.setPublicationFinish(view.getPublicationFinish());
		
		lst.set(this.translationIndex, newTranslation);
		nBaP.setTranslation(lst);
		
		return nBaP;
	}
	
	/**
	 * Display a BeanArboPage information in the good field
	 * @param page : the beanArboPage representing a webPage
	 */
	private void displayArboPage(BeanArboPage page){
		this.view.clearFields();
		
		//set the title of the panel
		this.view.setTitle(page.getTranslation().get(0).getUrlName());
		
		if(!isEmpty(page.getTranslation().get(this.translationIndex))) {
			view.setBrowserTitle(page.getTranslation().get(this.translationIndex).getBrowserTitle());
			view.setDescription(page.getTranslation().get(this.translationIndex).getDescription());
			view.setKeyWord(page.getTranslation().get(this.translationIndex).getKeyWord());
			view.setPageTitle(page.getTranslation().get(this.translationIndex).getPageTitle());
			view.setPublicationFinish(page.getPublicationFinish());
			view.setPublicationStart(page.getPublicationStart());
			view.setUrlName(page.getTranslation().get(this.translationIndex).getUrlName());
			eventBus.displayContent(page.getTranslation().get(this.translationIndex).getContent());
		}
		else {
			view.setBrowserTitle("");
			view.setDescription("");
			view.setKeyWord("");
			view.setPageTitle("");
			view.setPublicationFinish(page.getPublicationFinish());
			view.setPublicationStart(page.getPublicationStart());
			view.setUrlName("");
			eventBus.displayContent(page.getTranslation().get(0).getContent());
		}
		
		// on active l'aide uniquement sur les traductions et pas sur la langue par defaut
		if(this.translationIndex != 0) {
			view.enableHelp();
			view.setHelp(page.getTranslation().get(0).getBrowserTitle(),
					page.getTranslation().get(0).getDescription(), 
					page.getTranslation().get(0).getKeyWord(), 
					page.getTranslation().get(0).getPageTitle(), 
					page.getTranslation().get(0).getUrlName());
		}else {
			view.disableHelp();
		}
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
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetCurPage());
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
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetMainPage());
				p.show();}
		});	
	}
	
	
	public void onAddPage(String key) {
		this.translationIndex = 0; //on commence toujours par ajouter la langue par défaut
		view.setTitle(view.getConstants().AddPageTitleInformation()+view.getTitle());
		view.clearFields();
		view.enabledWidgets();
		view.disableHelp();
	}
	
	public void onCancelPage() {
		view.clearFields();
		view.deasabledWidgets();
		view.disableHelp();
		view.setTitle(view.getConstants().DefaultTitleInformation());
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
		BeanArboPage bean = addInformationInPage();
		this.eventBus.sendInfo(bean);
		this.view.hideAllHelpField();
		view.setTitle(bean.getTranslation().get(0).getUrlName());
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
