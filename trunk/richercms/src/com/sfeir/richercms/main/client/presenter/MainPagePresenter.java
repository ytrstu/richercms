package com.sfeir.richercms.main.client.presenter;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.main.client.PageServiceAsync;
import com.sfeir.richercms.main.client.event.MainEventBus;
import com.sfeir.richercms.main.client.interfaces.IdisplayMainPage;
import com.sfeir.richercms.main.client.view.MainPageView;
import com.sfeir.richercms.main.shared.BeanPage;

@Presenter( view = MainPageView.class)
public class MainPagePresenter extends LazyPresenter<IdisplayMainPage, MainEventBus> {
	
	private PageServiceAsync rpcPage = null;
	private InformationPanelPresenter infoPanelPresenter = null;
	private NavigationPanelPresenter navPanelPresenter = null;
	private TinyMCEPanelPresenter editorPanelPresenter = null;
	private ValidationPanelPresenter valPanelPresenter = null;
	
	public MainPagePresenter()
	{
		this.infoPanelPresenter = new  InformationPanelPresenter();
		this.navPanelPresenter = new NavigationPanelPresenter();
		this.editorPanelPresenter = new TinyMCEPanelPresenter();
		this.valPanelPresenter = new ValidationPanelPresenter();
	}
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
		
		view.getNavigationPanel().getSelectedEvtTree()
			.addSelectionHandler(new SelectionHandler<TreeItem>(){
				public void onSelection(SelectionEvent<TreeItem> event) {
					navPanelPresenter.setSelectedItem(event.getSelectedItem());
					displayPage();
				}
		});
		
		view.getNavigationPanel().getTreeMouseDown()
			.addMouseDownHandler(new MouseDownHandler(){
				public void onMouseDown(MouseDownEvent event) {
					//popUpAction();
					//view.getInformationPanel().deasabledWidgets();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnDelPage()
			.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					navPanelPresenter.popUpDeletePage();
					view.getInformationPanel().clearFields();
					view.getNavigationPanel().getPopUpTree().hide();
				}
		});
		
		view.getNavigationPanel().getPopUpTree().getClickBtnAddPage()
		.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.getInformationPanel().clearFields();
				view.getInformationPanel().enabledWidgets();
				view.getValidationPanel().enabledButtons();
				view.getTinyMCEPanel().enableEditor();
				view.getNavigationPanel().getPopUpTree().hide();
			}
		});
		
		view.getValidationPanel().getClicBtnAdd().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addPage();
				view.getInformationPanel().deasabledWidgets();
				view.getValidationPanel().deasableButtons();
				view.getTinyMCEPanel().disableEditor();
				displayPage();
			}
		});
	}
	
	/**
	 * Fired when the main do start
	 */
	public void onStartMain() {
		
		this.infoPanelPresenter.onStartInfoPanel(this.view.getInformationPanel());
		this.navPanelPresenter.onStartNavPanel(this.view.getNavigationPanel());
		this.editorPanelPresenter.onStartTinyMCEPanel(this.view.getTinyMCEPanel());
		this.valPanelPresenter.onStartValidationPanel(this.view.getValidationPanel());
		
		eventBus.changeBody(view.asWidget());
	}
	
	
	private void displayPage() {
		String key = this.navPanelPresenter.showPopUpAction();
		this.rpcPage.getPage(key, new AsyncCallback<BeanPage>() {
			public void onSuccess(BeanPage result) {
				infoPanelPresenter.displayPage(result);
				editorPanelPresenter.displayContent(result.getContent());
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : Get current Page");
				p.show();}
		});
		
	}
	
	private void addPage()
	{
		BeanPage newPage = this.infoPanelPresenter.addInformationInPage();
		newPage.setContent(view.getTinyMCEPanel().getContent());
		
		this.rpcPage.addPage(newPage, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				navPanelPresenter.buildTree(); //reload the new tree
			}
			public void onFailure(Throwable caught) {
				PopUpMessage p = new PopUpMessage("Error : AddPage");
				p.show();}
		});
	}
	
	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( PageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
		this.navPanelPresenter.setRpcPage(rpcPage);
	}
}
