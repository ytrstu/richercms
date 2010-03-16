package com.esial.richercms.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PictureServiceAsync {

	void addPicture(String fileName, byte[] content,
			AsyncCallback<Void> callback);

	void removePicture(String fileName, AsyncCallback<Void> callback);

	void getPictureData(String fileName, AsyncCallback<byte[]> callback);

	void getAllPictures(AsyncCallback<ArrayList<byte[]>> callback);

}
