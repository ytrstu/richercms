package com.sfeir.richercms.page.client.presenter;

import java.util.ArrayList;
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
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.MainState;
import com.sfeir.richercms.page.client.event.MainEventBus;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.view.InformationPanel;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTranslationPage;


@Presenter( view = InformationPanel.class)
public class InformationPanelPresenter extends LazyPresenter<IInformationPanel, MainEventBus>{

	private ArboPageServiceAsync rpcPage = null;
	private BeanArboPage currentPage = null;
	private int translationIndex = 0;
	private int cpt = 0;
	private MainState state = MainState.display;;
	
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
	@SuppressWarnings("unused")
	public BeanArboPage addInformationInPage() {
		BeanArboPage nBaP = new BeanArboPage();
		List<BeanTranslationPage> lst = null;
		BeanTranslationPage newTranslation = new BeanTranslationPage();
		
		if(this.state == MainState.add) { 
			// on met toutes les translations a vide
			lst = new ArrayList<BeanTranslationPage>();
			for(BeanTranslationPage bTp : this.currentPage.getTranslation()) {
				lst.add(new BeanTranslationPage());
			}
		}else { 
			// 0 => modify; on garde toute les translations
			lst = this.currentPage.getTranslation();
			// il nous faut garder l'id de l'anciennen traduction 
			// pour pouvoir la mettre dans la nouvelle traduction pour l'écrasé
			// dans la base de donnée
			newTranslation.setId(this.currentPage.getTranslation()
					.get(this.translationIndex).getId());
		}
		

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
		this.view.setTitle(page.getTranslation().get(0).getPageTitle());
		
		if(!isEmpty(page.getTranslation().get(this.translationIndex))) {
			view.setBrowserTitle(page.getTranslation().get(this.translationIndex).getBrowserTitle());
			view.setDescription(page.getTranslation().get(this.translationIndex).getDescription());
			view.setKeyWord(page.getTranslation().get(this.translationIndex).getKeyWord());
			view.setPageTitle(page.getTranslation().get(this.translationIndex).getPageTitle());
			view.setPublicationFinish(page.getPublicationFinish());
			view.setPublicationStart(page.getPublicationStart());
			view.setUrlName(page.getTranslation().get(this.translationIndex).getUrlName());
		}
		else {
			view.setBrowserTitle("");
			view.setDescription("");
			view.setKeyWord("");
			view.setPageTitle("");
			view.setPublicationFinish(page.getPublicationFinish());
			view.setPublicationStart(page.getPublicationStart());
			view.setUrlName("");
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
	

	public void onDisplayPage(Long id) {
		this.rpcPage.getArboPage(id, new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				view.deasabledWidgets();
				currentPage = result;
				displayArboPage(result);
				eventBus.displayContent(result.getTranslation());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetCurPage());
				p.show();}
		});
		this.state = MainState.display;
	}
	
	public void onDisplayMainPage() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				view.deasabledWidgets();
				currentPage = result;
				displayArboPage(result);
				eventBus.displayContent(result.getTranslation());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetMainPage());
				p.show();}
		});	
		this.state = MainState.display;
	}
	
	
	public void onAddPage(Long id) {
		this.translationIndex = 0; //on commence toujours par ajouter la langue par défaut
		view.setTitle(view.getConstants().AddPageTitleInformation()+view.getTitle());
		view.clearFields();
		view.enabledWidgets();
		view.disableHelp();
		this.state = MainState.add;
		//make a clean BeanArboPage
		this.currentPage = addInformationInPage();
		//send clean translation to add new content
		eventBus.displayContent(this.currentPage.getTranslation());
	}
	
	public void onCancelPage() {
		view.deasabledWidgets();
		view.disableHelp();
		view.setTitle(view.getConstants().DefaultTitleInformation());
		this.state = MainState.display;
	}
	
	public void onModifyPage(Long id) {
		view.enabledWidgets();
		this.state = MainState.modify;
	}
	
	public void onSavePage() {
		//view.deasabledWidgets();
		//this.state = MainState.display;
	}
	
	public void onDeletePage() {
		view.clearFields();
		this.state = MainState.display;
	}
	
	public void onCallInfo() {
		//need the current state to restore them later
		MainState currentState = this.state;
		//modifymode : modify the translation in the current page
		this.state = MainState.modify;
		//add the translation in the current page
		this.currentPage = this.addInformationInPage();
		//reconfigure the state
		this.state = currentState;
		//send info
		this.eventBus.sendInfo(this.currentPage);
		this.view.hideAllHelpField();
		view.setTitle(this.currentPage.getTranslation().get(0).getUrlName());
	}
	
	public void onChangeTranslation(int index) {
		if(this.state == MainState.modify){//modifyMode
			//add the current translation in the page
			this.currentPage = this.addInformationInPage();
		}else if(this.state == MainState.add){
			//modifymode : modify a translation in the current page
			this.state = MainState.modify;
			//add the translation in the current page
			this.currentPage = this.addInformationInPage();
			//addMode : necessary to add a page in the datastore
			this.state = MainState.add;
		}
		// new index selected in the Language list
		this.translationIndex = index;
		// display the new translation
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
