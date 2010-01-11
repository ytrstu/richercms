package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyHTMLPageServiceAsync {

	void addPage(String content, AsyncCallback<Void> callback);

	void deletePage(String qualified_name, AsyncCallback<Void> callback);

	void getPages(AsyncCallback<String[]> callback);

}
