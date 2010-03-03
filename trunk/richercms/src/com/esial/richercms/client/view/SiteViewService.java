package com.esial.richercms.client.view;


import com.esial.richercms.client.CmsPageEdition;
import com.esial.richercms.client.StatService;
import com.esial.richercms.client.StatServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;


public class SiteViewService {
	
	//Singleton pattern
	private static SiteViewService INSTANCE=null;
	private CmsPageEdition editor;
	private final StatServiceAsync statService = GWT.create(StatService.class);
	
	public SiteViewService(){
	}
	
	public HorizontalSplitPanel setUpSplitPanel(){
		HorizontalSplitPanel result=new HorizontalSplitPanel();
		result.setSize("1300px", "650px");
		return result;
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
	
	//Singleton Pattern
	public final synchronized static SiteViewService getInstance(){
		if(INSTANCE==null) INSTANCE=new SiteViewService();
		return INSTANCE;
	}

	public VerticalPanel setUpVPanel(HorizontalSplitPanel splitPanel) {
		final VerticalPanel panel=new VerticalPanel();
		PushButton pushButton = new PushButton(new Image("tab_images/article-add.png"));
		pushButton.addClickHandler(new AddPageHandler(splitPanel));
		panel.add(pushButton);
		statService.getPagesCount(new AsyncCallback<Integer>() {
			
			@Override
			public void onSuccess(Integer result) {
				// TODO Auto-generated method stub
				panel.add(new Label("Page Count: "+result.toString()));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
		return panel;
	}

}
