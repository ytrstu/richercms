package org.richercms.example.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.TextArea;

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
		// It is not beautiful !!
		// If TinyMCE is the only field that grow vertically, 
		// we can calculate the height of the component.
		int height = Window.getClientHeight()-50;
		setHeight(height+"px");
		
		tinyMce.unload();
		tinyMce.initTinyMCE();
	}

}
