package com.sfeir.richercms.main.client.presenter;


import com.sfeir.richercms.main.client.interfaces.IInformationPanel;
import com.sfeir.richercms.main.shared.BeanPage;

public class InformationPanelPresenter {

	private IInformationPanel view = null;
	
	public InformationPanelPresenter() {
		this.view = null;
	}
	
	/**
	 * Fired when the main do start
	 * @param infoPanel 
	 */
	public void onStartInfoPanel(IInformationPanel infoPanel) {
		this.view = infoPanel;
		this.view.deasabledWidgets();
	}
	
	
	/**
	 * display Page informations
	 * @param result the bean containing the page information
	 */
	public void displayPage(BeanPage result) {
		
		view.setBrowserTitle(result.getBrowserTitle());
		view.setDescription(result.getDescription());
		view.setKeyWord(result.getKeyWord());
		view.setPageTitle(result.getPageTitle());
		//view.setPublicationFinish(result.getPublicationFinish());
		//view.setPublicationStart(result.getPublicationStart());
		view.setUrlName(result.getUrlName());
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
}
