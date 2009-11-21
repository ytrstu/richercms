package com.esial.richercms.client.examples.view;

import com.esial.richercms.client.view.MainView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Point d'entrée permettant d'accéder aux développements de chaque membre de l'équipe.
 * Ce point d'entrée pourra être supprimé lorsque tous les développements seront intégrés.
 * 
 * @author fen
 *
 */
public class MainExampleView {

	private Panel panel = null;
	
	public MainExampleView() {
		VerticalPanel mainPanel = new VerticalPanel();
		panel = mainPanel;
		
		Label title = new Label();
		title.setHeight("50px");
		title.setText("Main menu");
		title.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(title);
		
		Button mainViewBtn = new Button("Main Application", new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				MainView mainView=new MainView();
				RootPanel.get().clear();
				RootPanel.get().add(mainView.getContent());
			}
		});
		panel.add(mainViewBtn);
		
		HTMLPanel demoJspAuthentication = new HTMLPanel("<a href=\"/examples/authentication.jsp\">Sample of JSP Authentication</a>");
		panel.add(demoJspAuthentication);
	}
	
	public Panel getPanel() {
		return panel;
	}
	
}
