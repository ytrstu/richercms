package com.sfeir.richercms.image.server;

import java.io.IOException;
import java.util.List;

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
import com.sfeir.richercms.image.server.business.MemoryFileItem;
import com.sfeir.richercms.image.server.business.UnlinkedFile;

public class ThumbNailServlet extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static {
        ObjectifyService.register(MemoryFileItem.class);
        ObjectifyService.register(UnlinkedFile.class);
    }
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		String id = req.getParameter("id");
		MemoryFileItem mfi = getMemoryFileItem(new Long(id));
		if(mfi != null) {
			Image thumb = resizeImg(ImagesServiceFactory.makeImage(mfi.get()), 200, 200);
			
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
	
	
	
	private List<Long> getUnlikedFile() {
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
	
	private MemoryFileItem getMemoryFileItem(Long id) {
		List<Long> lst = this.getUnlikedFile();
		if(lst.size() != 0) {
			Objectify ofy = ObjectifyService.begin();
			return ofy.get(MemoryFileItem.class, id);
		}
		return null;
	}
	
	private Image resizeImg(Image oldImage, int height, int width) {
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Transform resize = ImagesServiceFactory.makeResize(width, height);
		return imagesService.applyTransform(resize, oldImage);
	}

}
