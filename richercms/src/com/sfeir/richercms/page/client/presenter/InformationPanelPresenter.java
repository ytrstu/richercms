package com.sfeir.richercms.page.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.sfeir.richercms.page.client.PageState;
import com.sfeir.richercms.page.client.TemplateServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IInformationPanel;
import com.sfeir.richercms.page.client.view.InformationPanel;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;
import com.sfeir.richercms.page.shared.BeanTranslationPage;


@Presenter( view = InformationPanel.class)
public class InformationPanelPresenter extends LazyPresenter<IInformationPanel, PageEventBus>{

	private ArboPageServiceAsync rpcPage = null;
	private TemplateServiceAsync rpcTemplate = null;
	private BeanArboPage currentPage = null;
	private int translationIndex = 0;
	private int cpt = 0;
	private PageState state = PageState.display;;
	
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
		
		this.view.getTemplateLstSelection().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				fetchTagTable();
			}
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
		
		List<BeanTranslationPage> lst = null;
		BeanTranslationPage newTranslation = new BeanTranslationPage();
		
		if(this.state == PageState.add) { 
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
		
		lst.set(this.translationIndex, newTranslation);
		BeanArboPage nBaP = new BeanArboPage(view.getPublicationStart(),
				view.getPublicationFinish(),
				lst);
		
		// tag Handle
		if(view.getSelectedTemplateId() != null) {
			nBaP.setTemplateId(new Long(view.getSelectedTemplateId()));
		nBaP.setTagsId(view.getSelectedTagsId());
		}
		
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
				fetchTemplateList();
				eventBus.displayContent(result.getTranslation());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetCurPage());
				p.show();}
		});
		this.state = PageState.display;
	}
	
	public void onDisplayMainPage() {
		this.rpcPage.getMainArboPage(new AsyncCallback<BeanArboPage>() {
			public void onSuccess(BeanArboPage result) {
				view.deasabledWidgets();
				currentPage = result;
				displayArboPage(result);
				fetchTemplateList();
				eventBus.displayContent(result.getTranslation());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage(view.getConstants().EGetMainPage());
				p.show();}
		});	
		this.state = PageState.display;
	}
	
	
	public void onAddPage(Long id) {
		this.translationIndex = 0; //on commence toujours par ajouter la langue par défaut
		this.view.setTitle(this.view.getConstants().AddPageTitleInformation()+this.view.getTitle());
		this.view.clearFields();
		this.view.enabledWidgets();
		this.view.disableHelp();
		this.state = PageState.add;
		//make a clean BeanArboPage
		this.currentPage = addInformationInPage();
		//send clean translation to add new content
		this.eventBus.displayContent(this.currentPage.getTranslation());
	}
	
	public void onCancelPage(PageState newState) {
		this.view.hideRequiredField();
		this.view.deasabledWidgets();
		this.view.disableHelp();
		this.view.setTitle(this.view.getConstants().DefaultTitleInformation());
		this.state = newState;
	}
	
	public void onModifyPage(Long id) {
		view.enabledWidgets();
		this.state = PageState.modify;
	}
	
	public void onDeletePage() {
		view.setTitle(this.view.getConstants().DefaultTitleInformation());
		view.clearFields();
		this.state = PageState.display;
	}
	
	public void onCallInfo() {
		if(testField()) {
			this.eventBus.showInformationPopUp();
			//need the current state to restore them later
			PageState currentState = this.state;
			//modifymode : modify the translation in the current page
			this.state = PageState.modify;
			//add the translation in the current page
			this.currentPage = this.addInformationInPage();
			//reconfigure the state
			this.state = currentState;
			//send info
			this.eventBus.sendInfo(this.currentPage);
			//hide all help field
			this.view.hideAllHelpField();
			//set title
			view.setTitle(this.currentPage.getTranslation().get(0).getUrlName());
			//this event is send if all information entered by user are right
			this.eventBus.rightInformation();
		}
		
	}
	
	private boolean testField() {
		boolean ret = true;
		//hide all field and display just the necessary
		this.view.hideErrorInUrl();
		this.view.hideRequiredField();
		
		if(this.view.getUrlName().length() == 0){
			this.view.showRequiredUrl();
			ret = false;
	
		}
		if(this.view.getPageTitle().length() == 0){
			this.view.showRequiredTitle();
			ret = false;

		}
		if(!validateURL(this.view.getUrlName())){
			this.view.showErrorInUrl();
			ret = false;
		}
		
		return ret;
	}
	
	public void onChangeTranslation(int index) {
		if(testField()){
			if(this.state == PageState.modify){//modifyMode
				//add the current translation in the page
				this.currentPage = this.addInformationInPage();
			}else if(this.state == PageState.add){
				//modifymode : modify a translation in the current page
				this.state = PageState.modify;
				//add the translation in the current page
				this.currentPage = this.addInformationInPage();
				//addMode : necessary to add a page in the datastore
				this.state = PageState.add;
			}
			// new index selected in the Language list
			this.translationIndex = index;
			// display the new translation
			this.displayArboPage(this.currentPage);
		}else{
			this.eventBus.changeLanguageInList(this.translationIndex);
		}
	}
	
	public void onPageLockState(String userName) {
		if(userName == null)
			this.view.setlockInfo("");
		else
			this.view.setlockInfo(userName);
	}
	
	private boolean validateURL(String url) {
		if(url.contains(" "))
			return false;
		if(url.matches(".*[!\"#$%&'()*+,./:;<=>?@[\\\\]^`{|}~].*"))
				return false;
		
		if(url.matches(".*[^\\p{Alpha}\\p{Digit}-_].*"))
			return false;
		return true;
	}
	
	/**
	 * This function clear TemplateList and add
	 * all template into the list (use an rpc call to take all template)
	 * function call
	 */
	private void fetchTemplateList() {
		this.view.clearTemplateList();
		this.rpcTemplate.getAllTemplate(new AsyncCallback<List<BeanTemplate>>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(List<BeanTemplate> result) {
				for(BeanTemplate template : result){
					view.addTemplateInList(template.getName(), 
							template.getId().toString());
				}
				//select the good template
				if(currentPage.getTemplateId()!=null)
					view.selectTemplate(currentPage.getTemplateId().toString());
				fetchTagTable();
			}
		});
	}
	
	/**
	 * Clear the tag table and and all tag in it
	 * (use an RPC call to take all tag)
	 */
	private void fetchTagTable(){
		this.view.clearTagTable();
		//if template != null (null => any template available
		if(this.view.getSelectedTemplateId()!= null){
			this.rpcTemplate.getTemplate(new Long(this.view.getSelectedTemplateId()),
					new AsyncCallback<BeanTemplate>() {
				public void onFailure(Throwable caught) {}
				public void onSuccess(BeanTemplate result) {
					for(BeanTag tag : result.getAssociatedTags()){
						view.addTagLine(tag.getId().toString(),
								tag.getTagName(),
								tag.getShortLib(),
								tag.getDescription(),
								tag.isTextual());
					}
					checkTag(); //check tag if its necessary
				}
			});
		}
	}
	
	private void checkTag() {
		
		if(this.currentPage.getTemplateId()!= null && 
			this.currentPage.getTemplateId().equals(new Long(view.getSelectedTemplateId())))
			for (Long tagId : this.currentPage.getTagsId()){
				this.view.checktag(tagId);
			}
		else
			this.view.unCheckAllTags();
	}

	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
	}
	
	/**
	 * used by the framework to instantiate rpcTag
	 * @param rpcTag
	 */
	@InjectService
	public void setTemplateService( TemplateServiceAsync rpcTemplate ) {
		this.rpcTemplate = rpcTemplate;
	}
}
