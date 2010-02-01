package com.esial.richercms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.esial.richercms.client.NotLoggedInException;
import com.esial.richercms.client.PageService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PageServiceImpl extends RemoteServiceServlet implements
		PageService {

	private static final Logger LOG = Logger.getLogger(PageServiceImpl.class
			.getName());

	@Override
	public void addPage(String browser_title, String page_title,
			String url_name, String description, String content)
			throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Page(browser_title, page_title, url_name,
					description, content));
		} finally {
			pm.close();
		}
		;
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("Not logged in.");
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
	public String[] getAllPages() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query q = pm.newQuery(Page.class);
			q.setOrdering("publish_date");
			List<Page> pages=(List<Page>) q.execute();
			for(Page page : pages){
				symbols.add(page.getPage_title());
			}
		} finally {
			pm.close();
		}
		return (String[]) symbols.toArray(new String[0]);
	}

}
