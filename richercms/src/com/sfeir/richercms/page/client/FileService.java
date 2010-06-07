package com.sfeir.richercms.page.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.page.shared.BeanFile;


/**
 * All services needed to handle File stored in Data store
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("fileService")
public interface FileService  extends RemoteService {

	public List<Long> getUnlikedFile();
	
	public List<byte[]> getUnLinkedThumbnails();
	
	public List<BeanFile> getFile(String path);
	
	public void deleteFile(Long id);
}
