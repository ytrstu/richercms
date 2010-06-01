package com.sfeir.richercms.image.server;

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
import com.googlecode.objectify.Query;
import com.sfeir.richercms.image.server.business.MemoryFileItem;
import com.sfeir.richercms.image.server.business.UnlinkedFile;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132039883431151234L;

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
			Query<UnlinkedFile> unlinkedFile = ofy.query(UnlinkedFile.class);
			
			try {
				items = upload.parseRequest(req);
				for(MemoryFileItem item : items) {
					item.commit();
					ofy.put(item);
					UnlinkedFile uFile = unlinkedFile.get();
					uFile.getIdUnlinkedImg().add(item.getId());
					ofy.put(uFile);	
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
