package com.esial.richercms.client.view;

import com.esial.richercms.client.PageService;
import com.esial.richercms.client.PageServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SiteView extends FlowPanel {

	private HorizontalSplitPanel splitPanel;
	private VerticalPanel vPanel;
	private final PageServiceAsync pageService = GWT.create(PageService.class);

	public SiteView() {
		super();
		splitPanel = SiteViewService.getInstance().setUpSplitPanel();
		loadPages();
		vPanel = SiteViewService.getInstance().setUpVPanel(splitPanel);
		splitPanel.setRightWidget(vPanel);
		this.add(splitPanel);
	}
	
	private void loadPages(){
		pageService.getAllPages(new AsyncCallback<String[]>() {
			
			@Override
			public void onSuccess(String[] result) {
				Tree tree=new Tree();
				for(String s : result){
					MyPopUpAnchor anchor=new MyPopUpAnchor(s,splitPanel);
					tree.addItem(anchor);
				}
				if(tree.getItemCount()==0) splitPanel.setLeftWidget(new Label("No Elements"));
				else splitPanel.setLeftWidget(tree);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				splitPanel.setLeftWidget(new Label("TreeEchec"));
			}
		});
	}
}
