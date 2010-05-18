package com.sfeir.richercms.main.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.CellFormatter;
import com.sfeir.richercms.main.client.MainConstants;
import com.sfeir.richercms.main.client.event.FlexTableUtil;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;

public class ReorderPagePanel extends ResizeComposite implements IReorderPagePanel {

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private FlexTable pageLst = null;
    private AbsolutePanel lstPanel = null;
    private LayoutPanel finalContainer = null;
    private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE = "demo-FlexTableRowExample";

	private Button initPosition, cancel, apply;
	
	public ReorderPagePanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.initPosition = new Button("Reset position");
		this.cancel = new Button("Cancel");
		this.apply = new Button("apply");
		
		HorizontalPanel btnPanel = new HorizontalPanel();
		btnPanel.add(this.apply);
		btnPanel.add(this.cancel);
		btnPanel.add(this.initPosition);
		
		this.lstPanel = new AbsolutePanel();
		this.pageLst = new FlexTable();
		this.pageLst.addStyleName("pageTable");
		this.pageLst.setCellSpacing(2);
		this.pageLst.addStyleName("demo-flextable");
		this.addTableTitle();
		
		this.lstPanel.setPixelSize(this.pageLst.getOffsetWidth(),this.pageLst.getOffsetHeight());
		this.lstPanel.add(this.pageLst,0,0);
		this.lstPanel.addStyleName(CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE);
	    	
		VerticalPanel container = new VerticalPanel();
		container.add(this.lstPanel);
		container.add(btnPanel);
		
		ScrollPanel root = new ScrollPanel();
		root.addStyleName("mainButtonPanel");
		root.add(container);
		
		this.finalContainer = new LayoutPanel();
		this.finalContainer.add(root);
		
		this.initWidget(this.finalContainer);
	}
	
	/**
	 * add the title Line into the FlexTable
	 */
	private void addTableTitle() {
		this.pageLst.setWidget(0, 0, new HTML("<nobr>URL Name</nobr>"));
		this.pageLst.setWidget(0, 1, new HTML("<nobr>Initial position</nobr>"));
		this.pageLst.setWidget(0, 2, new HTML("<nobr>Creation Date</nobr>"));
		CellFormatter cellFormater = this.pageLst.getCellFormatter();
		cellFormater.setStyleName(0, 0, "pageTableHeader");
		cellFormater.setStyleName(0, 1, "pageTableHeader");
		cellFormater.setStyleName(0, 2, "pageTableHeader");
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public Widget addNewPage(String text, Date date) {
		HTML handle = new HTML(text);
		this.pageLst.setWidget(this.pageLst.getRowCount(), 0, handle);
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 1, new HTML("<nobr>"+String.valueOf(this.pageLst.getRowCount()-1)+"</nobr>"));
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 2, new HTML("<nobr>08-05-10</nobr>"));
		this.lstPanel.setPixelSize(this.pageLst.getOffsetWidth(),this.pageLst.getOffsetHeight());	
		return handle;
	}
	
	public AbsolutePanel getPageListPanel() {
		return this.lstPanel;
	}
	
	public FlexTable getPageList() {
		return this.pageLst;
	}
	
	public List<Integer> getNewOrder() {
		ArrayList<Integer> lst = new ArrayList<Integer>();
		
		for(int i=1; i<this.pageLst.getRowCount(); i++){
			lst.add(new Integer(this.pageLst.getText(i, 1)));
		}
		return lst;
	}
	
	public void clearList() {
		this.pageLst.clear(true);
		this.pageLst.removeAllRows();
		this.addTableTitle();
	}
	
	public HasClickHandlers  getApplyClick() {
		return this.apply;
	}
	
	public HasClickHandlers  getCancelClick() {
		return this.cancel;
	}
	
	public HasClickHandlers  getResetClick() {
		return this.initPosition;
	}
	
	public MainConstants getConstants() {
		return this.constants;
	}
	
	public void resetPosition() {
		Integer pos;
		for(int i = this.pageLst.getRowCount()-1; i>0 ; i--){
			pos = new Integer(this.pageLst.getText(i, 1));
			//pas le bon chiffre à la bonne position
			while(i != pos.intValue()) {
				FlexTableUtil.moveRow(this.pageLst, this.pageLst, i, pos.intValue());
				pos = new Integer(this.pageLst.getText(i, 1));
			}
		}
	}
}
