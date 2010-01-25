package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PageServiceAsync {
	public void addPage(String unique_name, AsyncCallback<Void> async);
}
