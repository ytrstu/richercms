package com.esial.richercms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;


public class CmsPageEdition extends Composite {


	
	
	private TextBox browserTitle = new TextBox();
	private TextBox pageTitle = new TextBox();
	private TextBox urlName = new TextBox();
	private TextBox description = new TextBox();
	private DateBox publishDate = new DateBox();
	//private TextBox urlimage = new TextBox();
	//private String pageTitlePrec = null;
	private VerticalPanel panel;
	private TinyMCE htmlEditor;
	private final PageServiceAsync pageService = GWT.create(PageService.class);

	private FlexTable tbl;
	
	private final HorizontalSplitPanel splitPanel;
	
	public CmsPageEdition(HorizontalSplitPanel split) {
		this.splitPanel=split;
		panel = new VerticalPanel();
		tbl = new FlexTable();
		panel.add(tbl);
		
		tbl.setHTML(1, 1, "Browser Title");
		tbl.setWidget(1, 2, browserTitle);

		tbl.setHTML(2, 1, "Page Title");
		tbl.setWidget(2, 2, pageTitle);
		
		tbl.setHTML(3, 1, "URL name");
		tbl.setWidget(3, 2, urlName);

		tbl.setHTML(4, 1, "Description");
		tbl.setWidget(4, 2, description);

		tbl.setHTML(5, 1, "Publish date");
		tbl.setWidget(5, 2, publishDate);
		
		tbl.setHTML(6, 1, "Content");
		htmlEditor = new TinyMCE(800,35);
		tbl.setWidget(6, 2, htmlEditor);
		
		browserTitle.setWidth("400px");
		pageTitle.setWidth("400px");
		urlName.setWidth("400px");
		description.setWidth("400px");
		
		
		  FileUpload upload = new FileUpload();
		    upload.setName("Importer une image");
		    panel.add(upload);

		    
		    
		    HorizontalPanel importation2 = new HorizontalPanel();
		 // Ajout d'un Widget HTML pour afficher le resultat de l'upload
		    panel.add(importation2);
			final HTML test = new HTML();
			RootPanel.get().add(test);
	 
			// Creation du panel contenant le formulaire
			final FormPanel form = new FormPanel();
			// encodage pour envoi de fichier
			form.setEncoding(FormPanel.ENCODING_MULTIPART);
			// definition de la methode POST pour l'envoi
			form.setMethod(FormPanel.METHOD_POST);
			// Definition de l'action du formulaire (url du servlet)
			String urlAction = GWT.getModuleBaseURL() + "uploadServlet";
			form.setAction(urlAction);
	 
			// Creation du panel contenant tous les element du formulaire
			form.setWidget(panel);
	 
			// Creation d'un champ texte et ajout au panel
			final TextBox tb = new TextBox();
			tb.setName("textBoxFormElement");
			tb.setText("Nom de l'image");
			panel.add(tb);
	 
	
			// Creation du widget d'envoi de fichier (upload) et ajout au panel
			FileUpload uploadtest = new FileUpload();
			upload.setName("uploadFormElement");
			panel.add(upload);		
	 
			// Ajout d'un evenement declenche pendant la soumission du formulaire
			form.addSubmitHandler(new SubmitHandler() {
				public void onSubmit1(SubmitEvent event) {
					test.setHTML("Submitting");
				}

				public void onSubmit(SubmitEvent event) {
					// TODO Auto-generated method stub
					
				}
			});
	 
			// Ajout d'un evenement declenche a la fin de l'upload
			form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
				public void onSubmitComplete(SubmitCompleteEvent event) {
					test.setHTML("Uploaded = " + event.getResults());
				}
			});
	 
			// Ajout d'un bouton de soumission pour le formulaire
			panel.add(new Button("Submit", new ClickHandler() {
				public void onClick(ClickEvent event) {
					form.submit();
				}
			}));
	 
			// Ajout du formulaire sur la page principale (affichage)
			RootPanel.get().add(form);
	 
	
		
		HorizontalPanel panelButton = new HorizontalPanel();
		panel.add(panelButton);
		Button ok = new Button("OK");
		ok.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				pageService.addPage(browserTitle.getText(), pageTitle.getText(),
						urlName.getText(), description.getText(),
						htmlEditor.getText(), new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						splitPanel.remove(splitPanel.getRightWidget());
						splitPanel.setRightWidget(new Label("Ok"));
					}
					
					public void onFailure(Throwable caught) {
						splitPanel.remove(splitPanel.getRightWidget());
						splitPanel.setRightWidget(new Label("Echec"));
					}
				});
				pageService.getAllPages(new AsyncCallback<String[]>() {
					
					@Override
					public void onSuccess(String[] result) {
						splitPanel.remove(splitPanel.getLeftWidget());
						Tree tree=new Tree();
						for(String s : result){
							tree.addItem(s);
						}
						splitPanel.setLeftWidget(tree);
					}
					
					@Override
					public void onFailure(Throwable caught) {
						splitPanel.remove(splitPanel.getLeftWidget());
						splitPanel.setLeftWidget(new Label("TreeUpdateError"));
					}
				});
			}
		});
		
		Button cancel = new Button("Cancel");
		panelButton.add(ok);
		panelButton.add(cancel);
		
		// All composites must call initWidget() in their constructors.
		panel.setWidth("100%");
		initWidget(panel);

	}
	
	public void initEdition() {
		initContent();
	}
	
	private void initContent() {
		htmlEditor = new TinyMCE(800,35);
		htmlEditor.setText("TinyMCE");
		tbl.setWidget(6, 2, htmlEditor);
		panel.add(this);
	}
	
	public TinyMCE getHtmlEditor() {
		return htmlEditor;
	}
}
