package com.sfeir.richercms.image.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.sfeir.richercms.image.client.interfaces.IAddPanel;

public class AddPanel extends ResizeComposite implements IAddPanel {

	private VerticalPanel panel;
	private static final String urlActionUP = GWT.getModuleBaseURL() + "upload";
	private static final String thumbnailUrl = GWT.getModuleBaseURL() + "thumbnail";
	private FlowPanel thumbsPanel = null;
	private Button btnSend = null;
	private FormPanel form = null;
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.panel = new VerticalPanel();
		this.thumbsPanel = new FlowPanel();
		this.panel.add(this.thumbsPanel);
		this.btnSend = new Button("envoi");
		
		// Creation du panel contenant le formulaire
		this.form = new FormPanel();
		// Definition de l'action du formulaire (url du servlet)

		form.setAction(urlActionUP);
		// encodage pour envoi de fichier
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		// definition de la methode POST pour l'envoi
		form.setMethod(FormPanel.METHOD_POST);
		// Creation du panel contenant tous les element du formulaire
		form.setWidget(panel);

		final FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		form.setWidget(upload);
		
		panel.add(form);
		
		// Ajout d'un bouton de soumission pour le formulaire
		this.panel.add(this.btnSend);

		// Ajout d'un evenement declenche pendant la soumission du formulaire
		form.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				System.out.println("form.addSubmitHandler");
			}
		});

		// Ajout d'un evenement declenche a la fin de l'upload
		form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				System.out.println("form.addSubmitCompleteHandler");
			}
		});
		
		
		
		LayoutPanel p = new LayoutPanel();
		p.add(this.panel);
		this.initWidget(p);
	}
	
	public AddPanel getPanel() {
		return this;
	}
	
	@SuppressWarnings("static-access")
	public void addThumbnail(Long id){
		this.thumbsPanel.add(new Image(
				this.thumbnailUrl+"?id="+id.toString()));
	}
	
	public HasClickHandlers onSendBtnclick() {
		return this.btnSend;
	}
	
	public FormPanel getFormEvent() {
		return this.form;
	}
	
	public void submitForm() {
		System.out.println("form.toString() : " + form.toString());
		this.form.submit();
	}
	
	public void clearThumbNails() {
		this.thumbsPanel.clear();
	}
}
