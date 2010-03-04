package com.esial.richercms.client.view;

import com.esial.richercms.client.CmsPageEdition;
import com.esial.richercms.client.PageService;
import com.esial.richercms.client.PageServiceAsync;
import com.esial.richercms.client.Richercms;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyPopUpAnchor extends Anchor {
	
	private final PageServiceAsync pageService = GWT.create(PageService.class);
	private HorizontalSplitPanel sPanel;
	private CmsPageEdition editor;

	public MyPopUpAnchor(String text, HorizontalSplitPanel panel) {
		super(text);
		this.addClickHandler(new linkClickHandler(this));
		this.sPanel=panel;
	}

	private class linkClickHandler implements ClickHandler {

		MyPopUpAnchor anchor;

		public linkClickHandler(MyPopUpAnchor anchor) {
			this.anchor = anchor;
		}

		public void onClick(ClickEvent event) {
			final PopupPanel panel = new PopupPanel(true);
			VerticalPanel vPanel=new VerticalPanel();
			vPanel.add(new Label(Richercms.getInstance().getCmsConstants().action()
					+ "\'"+this.anchor.getText()+ "\':"));
			HorizontalPanel hPanel=new HorizontalPanel();
			Button bEdit=new Button(Richercms.getInstance().getCmsConstants().editpage());
			bEdit.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					sPanel.remove(sPanel.getRightWidget());
					editor=new CmsPageEdition(sPanel,true,anchor.getText());
					sPanel.setRightWidget(editor);
					panel.hide();
				}
			});
			hPanel.add(bEdit);
			Button bDelete=new Button(Richercms.getInstance().getCmsConstants().delpage());
			bDelete.addClickHandler(new ClickHandler() {
				
				public void onClick(ClickEvent event) {
					pageService.removePage(anchor.getText(),new AsyncCallback<Void>() {
						
						public void onSuccess(Void result) {
							panel.hide();
						}
						
						public void onFailure(Throwable caught) {
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
