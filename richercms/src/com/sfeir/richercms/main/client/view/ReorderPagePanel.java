package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;

public class ReorderPagePanel extends ResizeComposite implements IReorderPagePanel {

	private FlexTable pageLst = null;
    private AbsolutePanel lstPanel = null;
    private static final String CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE = "demo-FlexTableRowExample";

	private Button upBtn, upperBtn, downBtn, LowerBtn, apply;
	
	public ReorderPagePanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.upBtn = new Button("up");
		this.upperBtn = new Button("upper");
		this.downBtn = new Button("down");
		this.LowerBtn = new Button("lower");
		this.apply = new Button("apply");
	
		VerticalPanel btnPanel = new VerticalPanel();
		btnPanel.add(this.upperBtn);
		btnPanel.add(this.upBtn);
		btnPanel.add(this.downBtn);
		btnPanel.add(this.LowerBtn);
		
		this.lstPanel = new AbsolutePanel();
		this.pageLst = new FlexTable();
		this.pageLst.setCellSpacing(2);
		this.pageLst.addStyleName("demo-flextable");
		this.pageLst.setWidget(0, 0, new Label("URL Name"));
		this.pageLst.setWidget(0, 1, new Label("Number Of Child"));
		this.pageLst.setWidget(0, 2, new Label("Key"));
		
		this.lstPanel.setPixelSize(this.pageLst.getOffsetWidth(),this.pageLst.getOffsetHeight());
		this.lstPanel.add(this.pageLst,0,0);
		this.lstPanel.addStyleName(CSS_DEMO_FLEX_TABLE_ROW_EXAMPLE);
	    	
		VerticalPanel container = new VerticalPanel();
		container.add(this.lstPanel);
		container.add(this.apply);
		
		ScrollPanel root = new ScrollPanel();
		root.addStyleName("mainButtonPanel");
		root.add(container);
		
		LayoutPanel p = new LayoutPanel();
		p.add(root);
		
		this.initWidget(p);
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public Widget addNewPage(String text,int numberOfChild, String key) {
		Label l = new Label(text);
		this.pageLst.setWidget(this.pageLst.getRowCount(), 0, l);
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 1, new Label(String.valueOf(numberOfChild)));
		this.pageLst.setWidget(this.pageLst.getRowCount()-1, 2, new Label(key));
		this.lstPanel.setPixelSize(this.pageLst.getOffsetWidth(),this.pageLst.getOffsetHeight());	
		return l;
	}
	
	public AbsolutePanel getPageListPanel() {
		return this.lstPanel;
	}
	
	public FlexTable getPageList() {
		return this.pageLst;
	}
}
