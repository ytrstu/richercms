package com.sfeir.richercms.main.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.sfeir.richercms.main.client.interfaces.IPopUpTree;

public class PopUpTree extends PopupPanel implements IPopUpTree{
	
	private Button btnAddPage = new Button("Ajouter une page");
	private Button btnDelPage = new Button("supprimer la page");
	
	public PopUpTree() {
		super();
		this.setAutoHideEnabled(true);
		this.setAnimationEnabled(true);
	}
	
	public void show(int type) {
		
		switch(type) {
		case 1 : addPage();//addPage	
		}
		this.setAnimationEnabled(true);
		super.show();
		this.setAnimationEnabled(false);
	}
	
	private void addPage() {
		this.clear();
		this.setTitle("Action :");
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.add(this.btnAddPage);
		hPanel.add(this.btnDelPage);
		hPanel.setSpacing(5);
		
		this.add(hPanel);
	}
	
	public HasClickHandlers getClickBtnAddPage()
	{
		return this.btnAddPage;
	}
	
	public HasClickHandlers getClickBtnDelPage()
	{
		return this.btnDelPage;
	}
	
	public void setPopupPosition(int left, int top)
	{
		super.setPopupPosition(left+10, top+10);
	}
}
