package com.sfeir.richercms.page.client.view.custom;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ConfirmationBox extends DialogBox {

	private Button btnOk = new Button("ok");
	private Button btncancel = new Button("cancel");

	public ConfirmationBox(String title, String text) {

		Label label = new Label(text);
		Image img = new Image("tab_images/warning.png");

		HorizontalPanel btnPanel = new HorizontalPanel();
		btnPanel.add(this.btnOk);
		btnPanel.add(this.btncancel);
		btnPanel.setSpacing(10);

		VerticalPanel container = new VerticalPanel();
		container.add(label);
		container.add(btnPanel);
		container.setCellHorizontalAlignment(btnPanel, HasHorizontalAlignment.ALIGN_CENTER);

		HorizontalPanel finalContainer = new HorizontalPanel();
		finalContainer.add(img);
		finalContainer.add(container);

		// Enable animation.
		setAnimationEnabled(true);

		// Enable glass background.
		setGlassEnabled(true);
		
		this.setText(title);
		
		this.center();
		setWidget(finalContainer);

		// handle clickEvent => hide the PopUp
		eventHide();
	}

	private void eventHide() {
		btncancel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmationBox.this.hide();
			}
		});

		btnOk.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ConfirmationBox.this.hide();
			}
		});
	}

	public HasClickHandlers getClickOkEvt() {
		return this.btnOk;
	}
}
