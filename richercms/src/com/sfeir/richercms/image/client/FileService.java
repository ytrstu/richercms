package com.sfeir.richercms.image.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * All services needed to handle File stored in Data store
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("fileService")
public interface FileService  extends RemoteService {

	public List<Long> getUnlikedFile();
	
	public List<byte[]> getUnLinkedThumbnails();
}
