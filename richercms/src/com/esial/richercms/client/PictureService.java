package com.esial.richercms.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("pictures")
public interface PictureService extends RemoteService {
	public void addPicture(String fileName, byte[] content) throws NotLoggedInException;
	public void removePicture(String fileName) throws NotLoggedInException;
	public byte[] getPictureData(String fileName) throws NotLoggedInException;
	public ArrayList<byte[]> getAllPictures() throws NotLoggedInException;
}
