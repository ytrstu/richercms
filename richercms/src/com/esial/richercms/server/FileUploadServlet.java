package com.esial.richercms.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.esial.richercms.client.NotLoggedInException;
import com.google.appengine.api.datastore.Blob;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 132039883431151234L;

	//	private final PictureServiceAsync pictureService;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
	throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		System.out.println("dans try");
		if (ServletFileUpload.isMultipartContent(req)) {
			System.out.println("dans if isMultipartContent");
			System.out.println("request : "+req.getQueryString());

			ServletFileUpload upload = new ServletFileUpload();
			resp.setContentType("text/plain");

			FileItemIterator iterator;
			try {
				iterator = upload.getItemIterator(req);
				while (iterator.hasNext()) {
					FileItemStream item = iterator.next();
					InputStream in = (InputStream) item.openStream();

					if (item.isFormField()) {
						System.out.println("Got a form field: " + item.getFieldName());
					} else {
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();
						System.out.println("Got a file : fileName : "+fileName+", contentType : "+contentType);
						int len;
						byte[] buffer = new byte[8192];
						while ((len = in.read(buffer, 0, buffer.length)) != -1) {
							resp.getOutputStream().write(buffer, 0, len);
						}
						Blob picture = new Blob(buffer);
/*						try {
							picture.add();
						} catch (NotLoggedInException e) {
							e.printStackTrace();
						}*/
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
