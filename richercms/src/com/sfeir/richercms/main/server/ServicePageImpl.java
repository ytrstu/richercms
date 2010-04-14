package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.PageService;
import com.sfeir.richercms.main.server.business.Page;
import com.sfeir.richercms.main.shared.BeanPage;
import com.sfeir.richercms.server.PMF;


/**
 * Implementation of all WebPages Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServicePageImpl extends RemoteServiceServlet implements PageService {
	
	private static final PersistenceManagerFactory Pmf = PMF.get();
	private List<Page> pages = null;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}

	@SuppressWarnings("unchecked")
	public List<BeanPage>  getPages() {
		PersistenceManager pm = getPersistenceManager();
        ArrayList<BeanPage> lst = new ArrayList<BeanPage>();

	    try {
	        Query q = pm.newQuery(Page.class);
	        this.pages = (List<Page>) q.execute();
	        
	        for (Page page : this.pages)
	        	lst.add(this.pageToBean(page));
	        
        } finally {
        	pm.close();
        }
        
        return lst;
	}
	
	public void addPage(BeanPage newPage)
	{
		PersistenceManager pm = getPersistenceManager();
	    try {
		    	Page p = this.BeanToPage(newPage);
	    		pm.makePersistent(p);
		    } finally {
		      pm.close();
		    }
	}
	
	public void updatePage(BeanPage p)
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
	
	public BeanPage getPage(int id)
	{
		Page page = null;
		PersistenceManager pm = getPersistenceManager();
		 try {
			 	page = pm.getObjectById(Page.class, this.pages.get(id).getId());

			 }finally{
				 pm.close();
			 }
		 return this.pageToBean(page);
	}
	
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
	}
	
	
	/**
	 * make a Bean Page with all information necessary to display correctly a Page
	 * @return the corresponding BeanPage
	 */
	public BeanPage pageToBean(Page p) {
		 return new BeanPage(p.getBrowserTitle(),p.getPageTitle(), p.getUrlName(),
				 p.getDescription(), p.getKeyWord(), p.getPublicationStart(),
				 p.getPublicationFinish(), p.getContent());
	}
	
	public Page BeanToPage(BeanPage bean) {
		return new Page(bean.getBrowserTitle(),bean.getPageTitle(), bean.getUrlName(),
				bean.getDescription(), bean.getKeyWord(), bean.getPublicationStart(),
				bean.getPublicationFinish(), bean.getContent());

	}
}
