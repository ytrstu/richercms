package com.esial.richercms.client.view;


import com.esial.richercms.client.CmsPageEdition;
import com.esial.richercms.client.PageService;
import com.esial.richercms.client.PageServiceAsync;
import com.esial.richercms.client.Richercms;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;


public class SiteViewService {
	
	//Singleton pattern
	private static SiteViewService INSTANCE=null;
	private CmsPageEdition editor;
	private final PageServiceAsync pageService = GWT.create(PageService.class);
	
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
		buttonPanel.add(setUpDeletePageButton(splitPanel));
		return buttonPanel;
	}

	private Button setUpDeletePageButton(HorizontalSplitPanel splitPanel) {
		Button deletePageButton = new Button(Richercms.getInstance().getCmsConstants().delpage());
		deletePageButton.addClickHandler(new DeletePageListener(splitPanel));
		return deletePageButton;
	}

	private Button setUpModifPageButton() {
		Button modifPageButton = new Button(Richercms.getInstance().getCmsConstants().editpage());
		return modifPageButton;
	}

	private Button setUpAddPageButton(HorizontalSplitPanel splitPanel) {
		Button addPageButton = new Button(Richercms.getInstance().getCmsConstants().createpage());
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
			splitPanel.remove(splitPanel.getRightWidget());
			editor=new CmsPageEdition(splitPanel);
			splitPanel.setRightWidget(editor);
		}
		
	}
	
	private class DeletePageListener implements ClickHandler{
		
		private HorizontalSplitPanel splitPanel;
		
		public DeletePageListener(HorizontalSplitPanel splitPanel){
			this.splitPanel=splitPanel;
		}

		@Override
		public void onClick(ClickEvent event) {
			pageService.getAllPages(new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					ListBox listBox=new ListBox();
					for(String s : result){
						listBox.addItem(s);
					}
					listBox.addChangeHandler(new DeleteHandler(splitPanel));
					splitPanel.remove(splitPanel.getRightWidget());
					splitPanel.setRightWidget(listBox);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					splitPanel.remove(splitPanel.getRightWidget());
					splitPanel.setRightWidget(new Label(Richercms.getInstance().getCmsConstants().delError()));
				}
			});
		}
		
	}
	
	private class DeleteHandler implements ChangeHandler{
		
		private HorizontalSplitPanel splitPanel;
		
		public DeleteHandler(HorizontalSplitPanel splitPanel){
			this.splitPanel=splitPanel;
		}

		@Override
		public void onChange(ChangeEvent event) {
			ListBox listBox=(ListBox) event.getSource();
			pageService.removePage(listBox.getItemText(listBox.getSelectedIndex())
					, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							splitPanel.remove(splitPanel.getRightWidget());
							splitPanel.setRightWidget(new Label(Richercms.getInstance().getCmsConstants().delOk()));
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							splitPanel.remove(splitPanel.getRightWidget());
							splitPanel.setRightWidget(new Label(Richercms.getInstance().getCmsConstants().delError()));
						}
					});
		}
		
	}
	
	//Singleton Pattern
	public final synchronized static SiteViewService getInstance(){
		if(INSTANCE==null) INSTANCE=new SiteViewService();
		return INSTANCE;
	}

}
