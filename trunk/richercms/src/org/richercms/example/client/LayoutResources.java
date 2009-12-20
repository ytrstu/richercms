package org.richercms.example.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;

public interface LayoutResources extends ClientBundle {
	public static final LayoutResources INSTANCE = GWT.create(LayoutResources.class);

	@Source("TestLayoutInline.css")
	public LayoutCss css();

}
