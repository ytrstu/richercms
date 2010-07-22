package com.sfeir.richercms.page.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.server.business.MemoryFileItem;

public class ThumbNailServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static {
        ObjectifyService.register(MemoryFileItem.class);
    }
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		Image thumb;
		String id = req.getParameter("path");
		int width = new Integer(req.getParameter("width")).intValue();
		int height = new Integer(req.getParameter("height")).intValue();
		MemoryFileItem mfi = getMemoryFileItem(id);
		if(mfi != null) {
			
			thumb= ImagesServiceFactory.makeImage(mfi.get());
	
			//if img is to big
			if((thumb.getHeight() > height) || (thumb.getWidth() > width))
				thumb = resizeImg(ImagesServiceFactory.makeImage(mfi.get()), height, width);
			
			resp.setContentType(mfi.getContentType());
			ServletOutputStream outStream = resp.getOutputStream();
			outStream.write(thumb.getImageData());
			outStream.flush();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	
	private MemoryFileItem getMemoryFileItem(String path) {
		int lastSlash = path.lastIndexOf("/");
		String fileName = path.substring(lastSlash+1);
		String PagePath = path.substring(0,lastSlash+1);
		
		Objectify ofy = ObjectifyService.begin();
		Query<MemoryFileItem> files  = ofy.query(MemoryFileItem.class).filter("path =", PagePath);
		
		if(files.countAll() != 0) {
			for(MemoryFileItem file : files){
				if(file.getFileName().equals(fileName))
					return file;
			}
		}
		return null;
	}
	

	
	private Image resizeImg(Image oldImage, int height, int width) {
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Transform resize = ImagesServiceFactory.makeResize(width, height);
		return imagesService.applyTransform(resize, oldImage);
	}

}
