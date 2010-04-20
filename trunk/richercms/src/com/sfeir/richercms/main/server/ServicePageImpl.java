package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.PageService;
import com.sfeir.richercms.main.server.business.Page;
import com.sfeir.richercms.main.server.business.Root;
import com.sfeir.richercms.main.shared.BeanPage;
import com.sfeir.richercms.server.PMF;


/**
 * Implementation of all WebPages Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServicePageImpl extends RemoteServiceServlet implements PageService {
	
	private static final PersistenceManagerFactory Pmf = PMF.get();
	private Root root = null;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}

	@SuppressWarnings("unchecked")
	public List<BeanPage>  getPages() {
		
		//this.addBasesPage();
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
        ArrayList<BeanPage> lst = new ArrayList<BeanPage>();
	    try {
	    	tx.begin();
		        Query q = pm.newQuery(Root.class);
		        List<Root> roots = (List<Root>) q.execute();
		        
		        if(roots.size() == 0){
		        	this.root = new Root();
			    	List<Page> lst1 = new ArrayList<Page>();
			    	lst1.add(new Page());
			    	this.root.setPages(lst1);
		        	pm.makePersistent(this.root);
		        } else {
		        	this.root = roots.get(0);
		        	for (Page page : this.root.getPages())
		        		lst.add(this.pageToBean(page));
		        }
	        tx.commit();

        } finally {
			if (tx.isActive()) {
			    tx.rollback();
			}
        	pm.close();
        }
        
        return lst;
	}
	
	@SuppressWarnings("unchecked")
	public void addPage(BeanPage newPage)
	{
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
	    try {
		    	Page p = this.BeanToPage(newPage);
		    	
		    	tx.begin();
			    	Query q = pm.newQuery(Root.class);
			        List<Root> roots = (List<Root>) q.execute();
			        this.root = roots.get(0);
			        this.root.getPages().add(p);
		    		pm.makePersistent(p);
		    	tx.commit();
		    } finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
				pm.close();
		    }
	}
	
	public void updatePage(BeanPage p)
	{
		PersistenceManager pm = getPersistenceManager();
		 try {
			 	Root r = pm.getObjectById(Root.class, this.root.getEncodedKey()); 	

		 }finally{
			 pm.close();
		 }
	}
	
	public void deletePage(int id) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {
		    	Transaction tx = pm.currentTransaction();
		    	tx.begin();
					Page page = pm.getObjectById(Page.class, this.root.getPages().get(id).getEncodedKey());
					pm.deletePersistent(page);
				tx.commit();
			 }
		 finally{
			 pm.close();
		 }
	}
	
	public BeanPage getPage(String key)
	{
		Page page = null;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {

		    	tx.begin();
			 		page = pm.getObjectById(Page.class, key);
			 	tx.commit();

			 }finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
				 pm.close();
			 }
		 return this.pageToBean(page);
	}
	
	
	private void addBasesPage() {
		
	    PersistenceManager pm = getPersistenceManager();

    	//p.setSubPages(lst);
    	
	   /* try {*/
	    	
	    	//pm.makePersistentAll(lst);
	    	
	    	Transaction tx = pm.currentTransaction();
	    	try {
		        tx.begin();
		        
			    	List<Page> lst = new ArrayList<Page>();
			    	
			    	List<Page> lst1 = new ArrayList<Page>();
			    	lst1.add(new Page("Page 1.1","Page 1.1","1.1","1.1","1.1","1.1","1.1","1.1"));
			    	lst1.add(new Page("Page 1.2","Page 1.2","1.2","1.2","1.2","1.2","1.2","1.2"));
			    	lst1.add(new Page("Page 1.3","Page 1.3","1.3","1.3","1.3","1.3","1.3","1.3"));
	
			    	Page sp1 = new Page("Page 1","Page 1","1","1","1","1","1","1",lst1);
			    	lst.add(sp1);
			    	
			    	List<Page> lst3 = new ArrayList<Page>();
			    	lst3.add(new Page("Page 3.1","Page 3.1","3.1","3.1","3.1","3.1","3.1","3.1"));
			    	lst3.add(new Page("Page 3.2","Page 3.2","3.2","3.2","3.2","3.2","3.2","3.2"));
			    	lst3.add(new Page("Page 3.3","Page 3.3","3.3","3.3","3.3","3.3","3.3","3.3"));
			    	lst3.add(new Page("Page 3.4","Page 3.4","3.4","3.4","3.4","3.4","3.4","3.4"));
			    	
			    	lst.add(new Page("Page 2","Page 2","2","2","2","2","2","2"));
			    	
			    	Page sp3 = new Page("Page 3","Page 3","3","3","3","3","3","3",lst3);
			    	lst.add(sp3);
			    	
			    	lst.add(new Page("Page 4","Page 4","4","4","4","4","4","4"));
			    	
			    	Root mp = new Root();
			    	mp.setPages(lst);
		    		pm.makePersistent(mp);
	            tx.commit();
	    	} finally {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	    	}

		    /*pm.makePersistent(new SubPage("Page 1","Page 1","1","1","1","1","1","1",lst1));
		    pm.makePersistent(new SubPage("Page 2","Page 2","2","2","2","2","2","2"));
		    pm.makePersistent(new SubPage("Page 3","Page 3","3","3","3","3","3","3",lst3));
		    pm.makePersistent(new SubPage("Page 4","Page 4","4","4","4","4","4","4"));*/
	   /* } finally {
	    		pm.close();
	    }*/
	    
	}
	
	
	/**
	 * make a Bean Page with all information necessary to display correctly a Page
	 * @return the corresponding BeanPage
	 */
	public BeanPage pageToBean(Page p) {
		 return new BeanPage(p.getEncodedKey(), p.getBrowserTitle(),p.getPageTitle(), p.getUrlName(),
				 p.getDescription(), p.getKeyWord(), p.getPublicationStart(),
				 p.getPublicationFinish(), p.getContent(),null);
	}
	
	public Page BeanToPage(BeanPage bean) {
		return new Page(bean.getBrowserTitle(),bean.getPageTitle(), bean.getUrlName(),
				bean.getDescription(), bean.getKeyWord(), bean.getPublicationStart(),
				bean.getPublicationFinish(), bean.getContent());

	}
}
