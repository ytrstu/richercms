package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.client.FileService;
import com.sfeir.richercms.page.server.business.MemoryFileItem;
import com.sfeir.richercms.page.shared.BeanFile;

@SuppressWarnings("serial")
public class ServiceFileImpl   extends RemoteServiceServlet implements FileService {

	static {
        ObjectifyService.register(MemoryFileItem.class);
	}

	
	public List<BeanFile> getFile(String path) {
		BeanFile bean;
		ArrayList<BeanFile> pathLst = new ArrayList<BeanFile>();
		
		Objectify ofy = ObjectifyService.begin();
		Query<MemoryFileItem> files  = ofy.query(MemoryFileItem.class).filter("path =", path);
		
		for(MemoryFileItem file: files){
			bean = new BeanFile(file.getId(), file.getPath(),file.getFileName(),"");
			pathLst.add(bean);
		}
		
		return pathLst;
	}
	
	public void deleteFile(Long id){
		Objectify ofy = ObjectifyService.begin();
		MemoryFileItem mfi = ofy.get(MemoryFileItem.class, id);
		if(mfi!= null)
			ofy.delete(mfi);
	}
}
