package com.sfeir.richercms.image.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface FileServiceAsync {

	public  void getUnlikedFile(AsyncCallback<List<Long>> callback);
	public void getUnLinkedThumbnails(AsyncCallback<List<byte[]>> callback);
	public void getFile(String path, AsyncCallback<List<String>> callback);
}
