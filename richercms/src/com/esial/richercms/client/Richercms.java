package com.esial.richercms.client;


import com.esial.richercms.client.view.MainView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Richercms implements EntryPoint {
	
	private MainView mainView;
	private DeckPanel mainContent;
	private DecoratedTabPanel tabPanel;
	private final HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
	private final CmsPageEdition cmsPageEdition = new CmsPageEdition(splitPanel);
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainView=new MainView();
		//MainExampleView mainExampleView = new MainExampleView();
		RootPanel.get().add(mainView.getContent());
		
int heightMainContent = Window.getClientHeight() - 50;
		
		splitPanel.setRightWidget(new Label("Select a page on the left tree."));
		splitPanel.setSplitPosition("200px");
		
		tabPanel = new DecoratedTabPanel();
		tabPanel.add(splitPanel, "Configuration");
		tabPanel.add(new HTML("Admin"), "Administration");
		tabPanel.selectTab(0);
		tabPanel.setWidth("100%");

		mainContent = tabPanel.getDeckPanel();
		mainContent.setHeight(heightMainContent+"px");

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				int heightMainContent = event.getHeight() - 50;
				mainContent.setHeight(heightMainContent+"px");
			}
			
		});
		cmsPageEdition.initEdition();
		
		RootPanel.get().add(tabPanel);
	
	}

	public MainView getMainView() {
		return mainView;
	}
}
