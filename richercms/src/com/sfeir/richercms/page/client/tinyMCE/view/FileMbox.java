package com.sfeir.richercms.page.client.tinyMCE.view;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IFileMBox;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IImageTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.view.custom.TreePanel;

public class FileMbox extends DialogBox implements IFileMBox{

	SimplePanel treePanel = null;
	SimplePanel thumbPanel = null;
	VerticalPanel mainContainer = null;
	HorizontalPanel treeAndThumbs = null;
	
	public Widget asWidget() {	
		return this;
	}
	
	public void createView() {
		
        // Enable animation.
        setAnimationEnabled(true);
        // Enable glass background.
        setGlassEnabled(true);
        setStyleName("fileManagerPopup");
        setGlassStyleName("fileManagerGlassPopup");
		setSize("500px", "500px");
		
		this.treePanel = new SimplePanel();
		this.treePanel.setHeight("400px");
		this.treePanel.setWidth("200px");
		this.thumbPanel = new SimplePanel();
		this.thumbPanel.setHeight("400px");
		this.thumbPanel.setWidth("300px");
		this.treeAndThumbs = new HorizontalPanel();
		this.treeAndThumbs.add(this.treePanel);
		this.treeAndThumbs.add(this.thumbPanel);
		
        Button ok = new Button("upload");
        ok.addClickHandler(new ClickHandler() {
        	public void onClick(ClickEvent event) {
        		//FileManager.setTinyMceUrl(upload.getFilename());
        		hide();
        	}
        });
        this.mainContainer = new VerticalPanel();
        this.mainContainer.add(this.treeAndThumbs);
        this.mainContainer.add(ok);

        setWidget(this.mainContainer);
        
        //place the popUp
        //this.popUpPosition();

        // creation de la verrue
        this.verrue();
	}
	
	private void popUpPosition(){
        int top =  Window.getClientHeight()/2-150;
        int left = Window.getClientWidth()/2-150;
        if (top<0) {
        	top = 0;
        }
        if (left<0) {
        	left = 0;
        }
        setPopupPosition(left, top);
	}
	
	private void verrue() {
		// Verrue pour que la popup soit au dessus de tinyMce
        final Element elt = this.getStyleElement();
        DeferredCommand.addCommand(new Command() {
			
        	// Cette exécution différée est obligatoire car le div qui grise le fonds 
        	// associé à la popup du FileManager n'est pas encore crée par GWT.
			@Override
			public void execute() {
				// Récupération de l'id de la popup de tinymce pour ensuite retrouver son z-index
		        Element eltBody = RootPanel.getBodyElement();
		        com.google.gwt.dom.client.Element fils = eltBody.getFirstChildElement();
		        String id = null;
		        while (fils!=null) {
		        	String idTmp = fils.getId();
		        	if (idTmp!=null && idTmp.startsWith("mce_")) {
		        		id = idTmp;
		        		break;
		        	} 
		        	fils = fils.getNextSiblingElement();
		        }
		        
		        // Récupération de fond grisé de la popup du FileManager
		        com.google.gwt.dom.client.Element filsGlassPopup = eltBody.getFirstChildElement();
		        String name = null;
		        com.google.gwt.dom.client.Element eltGlassPopup = null;
		        while (filsGlassPopup!=null) {
		        	String nameTmp = filsGlassPopup.getClassName();
		        	if (nameTmp!=null && nameTmp.equals("fileManagerGlassPopup")) {
		        		name = nameTmp;
		        		eltGlassPopup = filsGlassPopup;
		        		break;
		        	}
		        	filsGlassPopup = filsGlassPopup.getNextSiblingElement();
		        }
		        
		        // Calcul et changement du zindex sur
		        // - la popup FileManager
		        // - le fond grisé sous la popup FileManager
		        if (id !=null) {
		        	
		        	Element eltTinyMce = RootPanel.get(id).getElement();
		        	String zindexStr = eltTinyMce.getStyle().getZIndex();
		        	Integer zindex = 600000;
		        	try {
						zindex = Integer.valueOf(zindexStr) + 1;
					} catch (NumberFormatException e) {
						// On garde la valeur par défaut en espérant que la valeur
						// soit assez grande.
						// Idéalement, il faudrait afficher un message d'erreur et recharger l'application
					}
			        Style style = elt.getStyle();
			        style.setZIndex(zindex+1);
			        
			        if (name!=null) {
						//Window.alert(id + ": zindex = " + zindexStr + " -> " + zindex + " # " + eltGlassPopup.getStyle().getZIndex());
						eltGlassPopup.getStyle().setZIndex(zindex);
			        }
		        }
				
			}
		});
        // Fin de la verrue
	}
	
	public void displayLeftTree(IImageTreePanel p) {
		this.treePanel.clear();
		this.treePanel.add((TreePanel)p);
	}
	
	public void displayThumbs(IThumbsPanel p) {
		this.thumbPanel.clear();
		this.thumbPanel.add((ThumbsPanel)p);
	}
	
	public void center(){
		this.verrue();
		super.center();
	}

}
