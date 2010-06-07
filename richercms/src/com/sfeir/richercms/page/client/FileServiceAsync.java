package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.shared.BeanFile;


public interface FileServiceAsync {

	public  void getUnlikedFile(AsyncCallback<List<Long>> callback);
	public void getUnLinkedThumbnails(AsyncCallback<List<byte[]>> callback);
	public void getFile(String path, AsyncCallback<List<BeanFile>> callback);
	
	public void deleteFile(Long id, AsyncCallback<Void> callback);
}
