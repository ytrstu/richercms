package com.esial.richercms.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StatServiceAsync {

	public void getPagesCount(AsyncCallback<Integer> callback);

}
