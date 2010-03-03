package com.esial.richercms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.esial.richercms.client.NotLoggedInException;
import com.esial.richercms.client.PageService;
import com.esial.richercms.client.Richercms;
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
	public String[] getAllPages() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query q = pm.newQuery(Page.class);
			q.setOrdering("publish_date");
			List<Page> pages = (List<Page>) q.execute();
			for (Page page : pages) {
				symbols.add(page.getPage_title());
			}
		} finally {
			pm.close();
		}
		return (String[]) symbols.toArray(new String[0]);
	}

	@Override
	public void removePage(String pageTitle) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Page.class);
		query.setFilter("page_title == page_title_param");
		query.declareParameters("String page_title_param");
		try {
			List<Page> pages = (List<Page>) query.execute(pageTitle);
			pm.deletePersistent(pages.get(0));
		} finally {
			pm.close();
		}
	}

	@Override
	public String[] getPageData(String pageTitle) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Page.class);
		query.setFilter("page_title == page_title_param");
		query.declareParameters("String page_title_param");
		ArrayList<String> aList = new ArrayList<String>();
		try {
			List<Page> pages = (List<Page>) query.execute(pageTitle);
			Page page = pages.get(0);
			aList.add(page.getBrowser_title());
			aList.add(page.getPage_title());
			aList.add(page.getUrl_name());
			aList.add(page.getDescription());
			aList.add(page.getPublish_date().toString());
			aList.add(page.getContent());
		} finally {
			pm.close();
		}
		return aList.toArray(new String[6]);
	}

	@Override
	public void editPage(String browserTitle, String pageTitle, String urlName,
			String description, String content, boolean hasTitleChanged)
			throws NotLoggedInException {
		if (hasTitleChanged) {
			//FIXME have to remove old page first
			//have to get old page name
			removePage(pageTitle);
			addPage(browserTitle, pageTitle, urlName, description, content);
		} else {
			checkLoggedIn();
			PersistenceManager pm = getPersistenceManager();
			Query query = pm.newQuery(Page.class);
			query.setFilter("page_title == page_title_param");
			query.declareParameters("String page_title_param");
			ArrayList<String> aList = new ArrayList<String>();
			try {
				List<Page> pages = (List<Page>) query.execute(pageTitle);
				Page page = pages.get(0);
				page.setBrowser_title(browserTitle);
				page.setUrl_name(urlName);
				page.setDescription(description);
				page.setContent(content);
			} finally {
				pm.close();
			}
		}
	}

}
