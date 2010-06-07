package com.sfeir.richercms.page.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.Thumb;
import com.sfeir.richercms.page.client.interfaces.IImageManager;
import com.sfeir.richercms.page.client.view.custom.PopUpImagePreview;

/**
 * ImageManager panel in the manView, use add image using the path of Page
 * @author homberg.g
 *
 */
public class ImageManager extends ResizeComposite implements IImageManager {
	
	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	private VerticalPanel panel;
	private static final String urlActionUP = GWT.getModuleBaseURL() + "upload";
	private static final String thumbnailUrl = GWT.getModuleBaseURL() + "thumbnail";
	private static final String imageUrl = GWT.getModuleBaseURL() + "image";
	private FlowPanel thumbsPanel = null;
	private Button btnSend = null;
	private Hidden path = null;
	private FormPanel form = null;
	private PopUpImagePreview popUpImg = null;
	
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.panel = new VerticalPanel();
		VerticalPanel submitPanel = new VerticalPanel();
		this.path = new Hidden();
		this.thumbsPanel = new FlowPanel();
		this.thumbsPanel.addStyleName("thumbsPanel");
		
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
		//form.setWidget(panel);
		
		final FileUpload upload = new FileUpload();
		upload.setName("uploadFormElement");
		
		submitPanel.add(this.path);
		submitPanel.add(upload);
		form.setWidget(submitPanel);
		
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
		
		@SuppressWarnings("static-access")
		public HasClickHandlers addThumbnail(String p) {
			Thumb img = new Thumb(this.thumbnailUrl+"?path="+p,null,p);
			this.thumbsPanel.add(img);
			
			//handle the click on the delete btn
			return img.btnClickEvent();
		}
		
		public HasClickHandlers onThumbClick() {
			Thumb thumb = (Thumb)this.thumbsPanel.getWidget(this.thumbsPanel.getWidgetCount()-1);
			return thumb.imageClickEvent();
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
		
		public PageConstants getConstants() {
			return this.constants;
		}


		public String getCurrentPath() {
			return this.path.getName();
		}

		public void setCurrentPath(String path) {
			this.path.setName(path);
		}
		
		public void showPopUpImgPreview(String path){
			if(popUpImg!=null)
				popUpImg.hide();
			
			popUpImg = new PopUpImagePreview(this.imageUrl+"?path="+path);
			popUpImg.center();
		}
}
