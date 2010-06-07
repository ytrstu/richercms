package com.sfeir.richercms.page.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.sfeir.richercms.page.server.business.MemoryFileItem;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132039883431151234L;
	private String path;

	static {
        ObjectifyService.register(MemoryFileItem.class);
    }


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(req)) {
			MemoryFileItemFactory factory = new MemoryFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			resp.setContentType("text/plain");
			upload.setSizeMax(1024*1024); // 1 MB
			// Parse the request
			List<MemoryFileItem> items;
			
			Objectify ofy = ObjectifyService.begin();
			
			try {
				items = upload.parseRequest(req);
				for(MemoryFileItem item : items) {
					if(item.getFieldName().equals("uploadFormElement")){
						item.commit();
						item.setPath(path);
						ofy.put(item);
					}else {
						this.path = item.getFieldName();
					}
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
			"Request contents type is not supported by the servlet.");
		}
	}
}
