package com.sfeir.richercms.page.client.view;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.sfeir.richercms.page.client.interfaces.INavigationPanel;
import com.sfeir.richercms.page.client.interfaces.custom.IPopUpMenuBar;
import com.sfeir.richercms.page.client.view.custom.PopUpMenuBar;
import com.sfeir.richercms.page.client.view.custom.TreePanel;

/**
 * Panel containing the tree who represent the architectural view of the site.
 * @author homberg.g
 *
 */
public class NavigationPanel extends TreePanel implements INavigationPanel{


	private PopUpMenuBar menuBar = null;
	
	public NavigationPanel() {
		super();
	}

	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
		super.createView();
		this.menuBar = new PopUpMenuBar();
	}

	
	public HasClickHandlers addPageInTree(String name, String key)
	{
		Image b = new Image("tab_images/leftBtn.png");
		HorizontalPanel p = new HorizontalPanel();
		p.add(new Label(name));
		p.add(b);
		b.setVisible(false);
		TreeItem t = new TreeItem();
		t.setUserObject(key);
		t.setWidget(p);
		
		if(this.rootItem == null) {
			this.rootItem = t;
			this.navigationTree.addItem(this.rootItem);
		}
		else
			this.rootItem.addItem(t);
		
		
		return b;
	}
		
	public IPopUpMenuBar getPopUpMenuBar () {
		return this.menuBar;
	}
}
