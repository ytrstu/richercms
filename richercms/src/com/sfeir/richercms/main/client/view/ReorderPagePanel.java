package com.sfeir.richercms.main.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.main.client.interfaces.IReorderPagePanel;

public class ReorderPagePanel extends ResizeComposite implements IReorderPagePanel {

	private ListBox pageLst = null;
	private Button upBtn, upperBtn, downBtn, LowerBtn, apply;
	
	public ReorderPagePanel() {
		super();
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		this.pageLst = new ListBox(false);
		this.pageLst.setVisibleItemCount(10);
		
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
		
		VerticalPanel lstPanel = new VerticalPanel();
		lstPanel.add(this.pageLst);
		lstPanel.add(this.apply);
		
		FlowPanel root = new FlowPanel();
		//root.
		root.addStyleName("mainButtonPanel");
		root.add(lstPanel);
		root.add(btnPanel);
		
		LayoutPanel p = new LayoutPanel();
		p.add(root);
		
		this.initWidget(p);
	}
	
	public Widget asWidget() {
		return this;
	}
}
