package com.esial.richercms.server;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.esial.richercms.client.NotLoggedInException;
import com.esial.richercms.client.Richercms;
import com.esial.richercms.client.StatService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StatServiceImpl extends RemoteServiceServlet implements
		StatService {

	private static final Logger LOG = Logger.getLogger(StatServiceImpl.class
			.getName());
	
	@Override
	public int getPagesCount() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		int size;
		try {
			Query q = pm.newQuery(Page.class);
			List<Page> pages = (List<Page>) q.execute();
			size=pages.size();
		} finally {
			pm.close();
		}
		return size;
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

}
