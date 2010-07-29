package com.sfeir.richercms.page.client.tinyMCE.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.PageConstants;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IFileMBox;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPopUpTreePanel;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IPageViewer;
import com.sfeir.richercms.page.client.tinyMCE.interfaces.IThumbsPanel;
import com.sfeir.richercms.page.client.view.custom.TreePanel;

/**
 * 
 * @author homberg.g
 * PopUp which contains a tree, 
 * a area where you can display thumbs or a Html Viewer
 */
public class FileMbox extends DialogBox implements IFileMBox {

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private SimplePanel treePanel = null;
	private SimplePanel thumbOrViewerPanel = null;
	private VerticalPanel mainContainer = null;
	private HorizontalPanel treeAndThumbs = null;
	private Label title = null;
	private Button ok  = null;
	private Button cancel = null;
	
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
		setSize("650px", "500px");
		
		// the treePanel at left
		this.treePanel = new SimplePanel();
		this.treePanel.setHeight("400px");
		this.treePanel.setWidth("200px");
		
		// the thumbPanel or PageViewer at right
		this.thumbOrViewerPanel = new SimplePanel();
		this.thumbOrViewerPanel.setHeight("400px");
		this.thumbOrViewerPanel.setWidth("450px");
		
		//top of the popUp
		this.treeAndThumbs = new HorizontalPanel();
		this.treeAndThumbs.addStyleName("thumbsPanel");
		this.treeAndThumbs.setBorderWidth(5);
		this.treePanel.addStyleName("paddingPanel");
		this.treeAndThumbs.add(this.treePanel);
		this.thumbOrViewerPanel.addStyleName("paddingPanel");
		this.treeAndThumbs.add(this.thumbOrViewerPanel);
		
		//bottom of the popUp
        this.ok = new Button(this.constants.BtnOk());
        this.cancel = new Button(this.constants.BtnCancel());
        
        //button Panel
        FlowPanel btnPanel = new FlowPanel();
        btnPanel.addStyleName("fileMBoxBtnPanel");
        btnPanel.add(this.ok);
        btnPanel.add(this.cancel);
        
        this.title = new Label();
        this.title.setStyleName("informationTitle");
        
        //main container
        this.mainContainer = new VerticalPanel();
        this.mainContainer.add(this.title);
        this.mainContainer.add(this.treeAndThumbs);
        this.mainContainer.add(btnPanel);
        this.mainContainer.setCellHorizontalAlignment(ok, HasHorizontalAlignment.ALIGN_CENTER);

        setWidget(this.mainContainer);

        // creation de la verrue
        this.verrue();
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
	
	public void displayLeftTree(IPopUpTreePanel p) {
		this.treePanel.clear();
		this.treePanel.add((TreePanel)p);
	}
	
	public void displayThumbs(IThumbsPanel p) {
		this.thumbOrViewerPanel.clear();
		this.thumbOrViewerPanel.add((ThumbsPanel)p);
	}
	
	public void displayViewer(IPageViewer p) {
		this.thumbOrViewerPanel.clear();
		this.thumbOrViewerPanel.add((PageViewer)p);
		
	}
	
	public HasClickHandlers onOkClick() {
		return this.ok;
	}
	
	public HasClickHandlers onCancelClick() {
		return this.cancel;
	}
	
	public void center(){
		this.verrue();
		super.center();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public void setImgDefaultTitle() {
		this.title.setText(this.constants.PopUpImgTitle());
	}
	
	public void setLinkDefaultTitle() {
		this.title.setText(this.constants.PopUpLinkTitle());
	}
}
