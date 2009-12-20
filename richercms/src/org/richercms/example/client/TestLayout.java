package org.richercms.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class TestLayout implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	    // Create a three-item tab panel, with the tab area 1.5em tall.
		if (LayoutResources.INSTANCE.css().ensureInjected()) {
		    TabLayoutPanel p = new TabLayoutPanel(25, Unit.PX);

		    LayoutPanel layoutPanel1 = new LayoutPanel();
		    layoutPanel1.setStyleName("tab-content");
		    HTML h1 = new HTML("TODO...");
		    layoutPanel1.add(h1);
		    p.add(layoutPanel1, "Persistence example");

		    LayoutPanel layoutPanel2 = new LayoutPanel();
		    layoutPanel2.setStyleName("tab-content");
			int height = Window.getClientHeight()-50;
		    TinyMCE tmce = new TinyMCE(height+"px");
		    layoutPanel2.add(tmce);
		    p.add(layoutPanel2, "TinyMce with resize");
		    
		    Label other = new Label("Example of new CssResources");
		    CheckBox cBox = new CheckBox("New css test");
		    cBox.addStyleName(LayoutResources.INSTANCE.css().testLabel());
		    LayoutPanel layoutPanel = new LayoutPanel();
		    layoutPanel.setStyleName("tab-content");
		    layoutPanel.add(cBox);
		    p.add(layoutPanel, other);
		    // Attach the LayoutPanel to the RootLayoutPanel. The latter will listen for
		    // resize events on the window to ensure that its children are informed of
		    // possible size changes.
		    RootLayoutPanel rp = RootLayoutPanel.get();
		    rp.setStyleName("testBody");
		    
		    rp.add(p);
		}
		
	}

}
