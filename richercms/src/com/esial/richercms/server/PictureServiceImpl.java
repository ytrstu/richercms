package com.esial.richercms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.esial.richercms.client.NotLoggedInException;
import com.esial.richercms.client.PictureService;
import com.esial.richercms.client.Richercms;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PictureServiceImpl extends RemoteServiceServlet implements PictureService {

	private static final Logger LOG = Logger.getLogger(PictureServiceImpl.class
			.getName());
	
	@Override
	public void addPicture(String fileName, byte[] content)
			throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Picture(fileName, content));
		} finally {
			pm.close();
		}
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException(Richercms.getInstance()
					.getCmsConstants().notLogged());
		}
	}
	
	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	private PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
	
	@Override
	public byte[] getPictureData(String fileName) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Picture.class);
		query.setFilter("fileName == fileName_param");
		query.declareParameters("String fileName_param");
		byte[] content;
		try {
			List<Picture> pictures = (List<Picture>) query.execute(fileName);
			Picture picture = pictures.get(0);
			content = picture.getContent();
		} finally {
			pm.close(); 
		}
		return content;
	}

	@Override
	public void removePicture(String fileName) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Picture.class);
		query.setFilter("fileName == fileName_param");
		query.declareParameters("String fileName_param");
		try {
			List<Picture> pictures = (List<Picture>) query.execute(fileName);
			pm.deletePersistent(pictures.get(0));
		} finally {
			
		}
	}

	@Override
	public ArrayList<byte[]> getAllPictures() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		//ArrayList<byte[]> list = new Arraylist<byte>
		return null;
	}
	

}
