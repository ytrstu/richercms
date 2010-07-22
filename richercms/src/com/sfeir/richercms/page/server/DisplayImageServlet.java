package com.sfeir.richercms.page.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.server.business.MemoryFileItem;

public class DisplayImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;



	static {
        ObjectifyService.register(MemoryFileItem.class);
    }
	

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		//you can use directly the path
		String id = req.getPathInfo();
		if( id == null)//or add a parameter
			id = req.getParameter("path");
		else{
			// delete the first "/"
			id = id.substring(1, id.length());
			// replace all "%20" to a space char
			id = id.replaceAll("%20", " ");
		}
		MemoryFileItem mfi = getMemoryFileItem(id);
		if(mfi != null) {
			Image thumb = ImagesServiceFactory.makeImage(mfi.get());
			
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

}
