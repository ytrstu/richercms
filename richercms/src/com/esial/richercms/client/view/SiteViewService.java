package com.esial.richercms.client.view;

import com.esial.richercms.client.CmsPageEdition;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;


public class SiteViewService {
	
	//Singleton pattern
	private static SiteViewService INSTANCE=null;
	private CmsPageEdition editor;
	
	public SiteViewService(){
	}
	
	public HorizontalSplitPanel setUpSplitPanel(){
		HorizontalSplitPanel result=new HorizontalSplitPanel();
		result.setSize("1300px", "650px");
		return result;
	}
	
	public HorizontalPanel createButtonsForStartScreen(HorizontalSplitPanel splitPanel) {
		HorizontalPanel buttonPanel=new HorizontalPanel();
		buttonPanel.add(setUpAddPageButton(splitPanel));
		buttonPanel.add(setUpModifPageButton());
		buttonPanel.add(setUpDeletePageButton());
		return buttonPanel;
	}

	private Button setUpDeletePageButton() {
		Button deletePageButton = new Button("Supprimer Page");
		return deletePageButton;
	}

	private Button setUpModifPageButton() {
		Button modifPageButton = new Button("Modifier Page");
		return modifPageButton;
	}

	private Button setUpAddPageButton(HorizontalSplitPanel splitPanel) {
		Button addPageButton = new Button("Créer Page");
		addPageButton.addClickHandler(new AddPageHandler(splitPanel));
		return addPageButton;
	}
	
	private class AddPageHandler implements ClickHandler{
		
		private HorizontalSplitPanel splitPanel;
		
		public AddPageHandler(HorizontalSplitPanel splitPanel){
			this.splitPanel=splitPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			splitPanel.remove(splitPanel.getRightWidget());
			editor=new CmsPageEdition(splitPanel);
			splitPanel.setRightWidget(editor);
		}
		
	}
	
	//Singleton Pattern
	public final synchronized static SiteViewService getInstance(){
		if(INSTANCE==null) INSTANCE=new SiteViewService();
		return INSTANCE;
	}

}
