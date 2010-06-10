package com.sfeir.richercms.page.client.tinyMCE.view;


import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.view.custom.thumb.ThumbFocusable;

/**
 * 
 * @author homberg.g
 *
 */
public class ThumbsPanel extends ResizeComposite implements IThumbsPanel{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	
	private LayoutPanel panel;
	private static final String urlActionUP = GWT.getModuleBaseURL() + "upload"; // up a file
	private static final String thumbnailUrl = GWT.getModuleBaseURL() + "thumbnail"; // display a thumbnail
	private FlowPanel thumbsPanel = null;
	private Button btnSend = null; // send the form
	private Hidden path = null;
	private FormPanel form = null;
	
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		this.panel = new LayoutPanel();
		VerticalPanel submitPanel = new VerticalPanel();
		this.path = new Hidden();
		this.thumbsPanel = new FlowPanel();
		//this.thumbsPanel.addStyleName("thumbsPanel");
		
		ScrollPanel thumbScroll = new ScrollPanel();
		thumbScroll.add(this.thumbsPanel);
		this.panel.add(thumbScroll);
		this.panel.setWidgetTopHeight(thumbScroll,0,Unit.PX,300,Unit.PX);
		this.panel.setWidgetLeftRight(thumbScroll, 10, Unit.PCT, 10, Unit.PCT);
		
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
		
		this.panel.add(this.form);
		this.panel.setWidgetTopHeight(this.form,300,Unit.PX,50,Unit.PX);
		this.panel.setWidgetLeftRight(this.form, 10, Unit.PCT, 10, Unit.PCT);
		
		// Ajout d'un bouton de soumission pour le formulaire
		this.panel.add(this.btnSend);
		this.panel.setWidgetTopHeight(this.btnSend,350,Unit.PX,30,Unit.PX);
		this.panel.setWidgetLeftRight(this.btnSend, 40, Unit.PCT, 40, Unit.PCT);
		
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
			
		this.initWidget(this.panel);
	}
		
	@SuppressWarnings("static-access")
	public HasClickHandlers addThumbnail(String p) {
		ThumbFocusable thumb = new ThumbFocusable(this.thumbnailUrl+"?path="+p,null,p);
		this.thumbsPanel.add(thumb);
		return thumb;
	}
	
	public HasClickHandlers onLastThumbClick() {
		ThumbFocusable thumb = (ThumbFocusable)this.thumbsPanel.getWidget(this.thumbsPanel.getWidgetCount()-1);
		return thumb;
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
}
