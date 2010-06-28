package com.sfeir.richercms.page.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.image.server.business.UnlinkedFile;
import com.sfeir.richercms.page.client.FileService;
import com.sfeir.richercms.page.server.business.MemoryFileItem;
import com.sfeir.richercms.page.shared.BeanFile;

@SuppressWarnings("serial")
public class ServiceFileImpl   extends RemoteServiceServlet implements FileService {

	static {
        ObjectifyService.register(UnlinkedFile.class);
        ObjectifyService.register(MemoryFileItem.class);
	}
	
	
	public List<Long> getUnlikedFile() {
		Objectify ofy = ObjectifyService.begin();
		
		Query<UnlinkedFile> unlinkedFile = ofy.query(UnlinkedFile.class);
		
		if(unlinkedFile.countAll() == 0) {
			UnlinkedFile uFile = new UnlinkedFile();
			ofy.put(uFile);
			return uFile.getIdUnlinkedImg();
		}else {
			return unlinkedFile.get().getIdUnlinkedImg();
		}
	}
	
	public List<byte[]> getUnLinkedThumbnails() {
		ArrayList<byte[]> thumbNails = new ArrayList<byte[]>();
		List<Long> lst = this.getUnlikedFile();
		Map<Long, MemoryFileItem> imgs;
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Transform resize = ImagesServiceFactory.makeResize(100, 100);
		
		Objectify ofy = ObjectifyService.begin();
		imgs = ofy.get(MemoryFileItem.class, lst);
		
		for(Long id: lst) {
			MemoryFileItem img = imgs.get(id);
			Image oldImage = ImagesServiceFactory.makeImage(img.get());
			
			
			thumbNails.add(imagesService.applyTransform(resize, oldImage).getImageData());
		}
		return thumbNails;
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