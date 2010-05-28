package com.sfeir.richercms.page.client.tinyMCE;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TextArea;
import com.sfeir.richercms.page.client.tinyMCE.TinyMCE;

public class ResizeTinyMCETextArea extends TextArea implements RequiresResize {

	public TinyMCE tinyMce;
	
	public ResizeTinyMCETextArea(TinyMCE tinyMce) {
		super();
		this.tinyMce = tinyMce;
	}

	public ResizeTinyMCETextArea(Element element) {
		super(element);
	}

	@Override
	public void onResize() {
		LayoutPanel parent = (LayoutPanel) getParent();
		int height = parent.getOffsetHeight() - 13;
		setHeight(height+"px");
		
		tinyMce.unload();
		tinyMce.initTinyMCE();
	}

}
