package com.sfeir.richercms.main.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.PageService;
import com.sfeir.richercms.main.shared.Page;
import com.sfeir.richercms.wizard.server.business.Language;


/**
 * Implementation of all WebPages Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServicePageImpl extends RemoteServiceServlet implements PageService {
	
	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private List<Page> pages = null;
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	@SuppressWarnings("unchecked")
	public List<Page>  getPages() {
		//addBasesPage();
		PersistenceManager pm = getPersistenceManager();

	    try {
	        Query q = pm.newQuery(Page.class);
	        this.pages = (List<Page>) q.execute();
	        
        } finally {
        	pm.close();
        }
        return this.pages;
	}
	
	public void addPage(Page newPage)
	{
		PersistenceManager pm = getPersistenceManager();
	    try {
	    		pm.makePersistent(newPage);
		    } finally {
		      pm.close();
		    }
	}
	
	public void updatePage(Page p)
	{
		PersistenceManager pm = getPersistenceManager();
		 try {
			 {
				 //Page page = pm.getObjectById(Page.class, p.getId());
				 //pm.deletePersistent(page);
			 }

		 }finally{
			 pm.close();
		 }
	}
	
	public void deletePage(int id) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {
			 {
				 Page page = pm.getObjectById(Page.class, this.pages.get(id).getId());
				 pm.deletePersistent(page);
			 }

		 }finally{
			 pm.close();
		 }
	}
	
	public Page getPage(int id)
	{
		Page page = null;
		PersistenceManager pm = getPersistenceManager();
		 try {
			 	page = pm.getObjectById(Page.class, this.pages.get(id).getId());

			 }finally{
				 pm.close();
			 }
		 return page;
	}
/*	
	private void addBasesPage() {
		
	    PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Page("Page 1","Page 1","1","1","1","1","1","1"));
	      pm.makePersistent(new Page("Page 2","Page 2","2","2","2","2","2","2"));
	      pm.makePersistent(new Page("Page 3","Page 3","3","3","3","3","3","3"));
	      pm.makePersistent(new Page("Page 4","Page 4","4","4","4","4","4","4"));
	    } finally {
	      pm.close();
	    }
	}*/
}
