package com.sfeir.richercms.page.client.view;

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
import com.sfeir.richercms.page.client.MainConstants;
import com.sfeir.richercms.page.client.interfaces.IReorderPagePanel;
import com.sfeir.richercms.page.client.view.custom.FlexTableUtil;

public class ReorderPagePanel extends ResizeComposite implements IReorderPagePanel {

	//gestion des langues
	private MainConstants constants = GWT.create(MainConstants.class);
	private FlexTable pageLst = null;
	private FlexTable tableTitle = null;
    private AbsolutePanel lstPanel = null;
    private LayoutPanel finalContainer = null;
    private static final String CSS_FLEX_TABLE_ROW = "flexTableRow";
    private static final String CSS_PAGE_TABLE = "pageTable";
	private Button initPosition, cancel, apply;
	//same configuration for the table and his title
	private int tabCellSpacing = 2;
	
	public ReorderPagePanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		
		
		this.initPosition = new Button(this.constants.BtnResetPos());
		this.cancel = new Button(this.constants.BtnCancel());
		this.apply = new Button(this.constants.BtnApply());
		
		HorizontalPanel btnPanel = new HorizontalPanel();
		btnPanel.add(this.apply);
		btnPanel.add(this.cancel);
		btnPanel.add(this.initPosition);
		
		this.lstPanel = new AbsolutePanel();
		this.pageLst = new FlexTable();
		this.pageLst.addStyleName(CSS_PAGE_TABLE);
		this.pageLst.setCellSpacing(this.tabCellSpacing);
		this.pageLst.addStyleName("flextable");
		
		this.lstPanel.setPixelSize(this.pageLst.getOffsetWidth(),this.pageLst.getOffsetHeight());
		this.lstPanel.add(this.pageLst,0,0);
		this.lstPanel.addStyleName(CSS_FLEX_TABLE_ROW);
	    
		this.tableTitle = new FlexTable();
		this.addTableTitle();
		
		VerticalPanel container = new VerticalPanel();
		container.add(this.tableTitle);
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
		this.tableTitle.addStyleName(CSS_PAGE_TABLE);
		this.tableTitle.setCellSpacing(this.tabCellSpacing);
		this.tableTitle.setWidget(0, 0, new HTML("<nobr>"+this.constants.PageTitle()+"</nobr>"));
		this.tableTitle.setWidget(0, 1, new HTML("<nobr>"+this.constants.InitPosition()+"</nobr>"));
		this.tableTitle.setWidget(0, 2, new HTML("<nobr>"+this.constants.CreationDate()+"</nobr>"));
		CellFormatter cellFormater = this.tableTitle.getCellFormatter();
		cellFormater.setStyleName(0, 0, "pageTableHeader");
		cellFormater.setStyleName(0, 1, "pageTableHeader");
		cellFormater.setStyleName(0, 2, "pageTableHeader");
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public Widget addNewPage(String text, Date date) {
		HTML handle = new HTML("<nobr>"+text+"</nobr>");
		this.pageLst.setWidget(this.pageLst.getRowCount(), 0, handle);
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 1, new HTML("<nobr>"+String.valueOf(this.pageLst.getRowCount())+"</nobr>"));
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 2, new HTML("<nobr>"+date.toString()+"</nobr>"));
		
		if(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 0).getOffsetWidth() < this.tableTitle.getWidget(0, 0).getOffsetWidth()) {
			this.pageLst.getWidget(this.pageLst.getRowCount()-1, 0).setWidth(this.tableTitle.getWidget(0, 0).getOffsetWidth()+"px");
		}else{
			this.tableTitle.getWidget(0, 0).setWidth(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 0).getOffsetWidth()+"px");
		}
		if(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 1).getOffsetWidth() < this.tableTitle.getWidget(0, 1).getOffsetWidth()){
			this.pageLst.getWidget(this.pageLst.getRowCount()-1, 1).setWidth(this.tableTitle.getWidget(0, 1).getOffsetWidth()+"px");
		}else{
			this.tableTitle.getWidget(0, 1).setWidth(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 1).getOffsetWidth()+"px");
		}
		if(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 2).getOffsetWidth() < this.tableTitle.getWidget(0, 2).getOffsetWidth()){
			this.pageLst.getWidget(this.pageLst.getRowCount()-1, 2).setWidth(this.tableTitle.getWidget(0, 2).getOffsetWidth()+"px");
		}else{
			this.tableTitle.getWidget(0, 2).setWidth(this.pageLst.getWidget(this.pageLst.getRowCount()-1, 2).getOffsetWidth()+"px");
		}
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
		
		for(int i=0; i<this.pageLst.getRowCount(); i++){
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
			while(i != pos.intValue()-1) {
				FlexTableUtil.moveRow(this.pageLst, this.pageLst, i, pos.intValue()-1);
				pos = new Integer(this.pageLst.getText(i, 1));
			}
		}
	}
}
