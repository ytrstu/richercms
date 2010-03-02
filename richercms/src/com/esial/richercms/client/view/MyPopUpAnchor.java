package com.esial.richercms.client.view;

import com.esial.richercms.client.PageService;
import com.esial.richercms.client.PageServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyPopUpAnchor extends Anchor {
	
	private final PageServiceAsync pageService = GWT.create(PageService.class);

	public MyPopUpAnchor(String text) {
		super(text);
		this.addClickHandler(new linkClickHandler(this));
	}

	private class linkClickHandler implements ClickHandler {

		MyPopUpAnchor anchor;

		public linkClickHandler(MyPopUpAnchor anchor) {
			this.anchor = anchor;
		}

		@Override
		public void onClick(ClickEvent event) {
			final PopupPanel panel = new PopupPanel(true);
			VerticalPanel vPanel=new VerticalPanel();
			vPanel.add(new Label("Choose an action for element "
					+ "\'"+this.anchor.getText()+ "\':"));
			HorizontalPanel hPanel=new HorizontalPanel();
			Button bEdit=new Button("Edit");
			hPanel.add(bEdit);
			Button bDelete=new Button("Delete");
			bDelete.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					pageService.removePage(anchor.getText(),new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							panel.hide();
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							panel.hide();
						}
					});
				}
			});
			hPanel.add(bDelete);
			vPanel.add(hPanel);
			panel.add(vPanel);
			panel.showRelativeTo(anchor);
		}

	}
}
