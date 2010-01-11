package com.esial.richercms.server;

import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.esial.richercms.client.MyHTMLPageService;
import com.esial.richercms.client.NotLoggedInException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MyHTMLPageServiceImpl extends RemoteServiceServlet implements
		MyHTMLPageService {
	
	private static final Logger LOG=Logger.getLogger(MyHTMLPageServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public void addPage(String content) throws NotLoggedInException {
		PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new MyHTMLPage());
	    } finally {
	      pm.close();
	    }
	}

	@Override
	public void deletePage(String qualifiedName) throws NotLoggedInException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] getPages() throws NotLoggedInException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private PersistenceManager getPersistenceManager() {
	    return PMF.getPersistenceManager();
	  }

}
