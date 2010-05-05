package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.main.client.ArboPageService;
import com.sfeir.richercms.main.server.business.ArboPage;
import com.sfeir.richercms.main.server.business.RootArbo;
import com.sfeir.richercms.main.server.business.TranslationPage;
import com.sfeir.richercms.main.shared.BeanArboPage;
import com.sfeir.richercms.main.shared.BeanTranslationPage;
import com.sfeir.richercms.server.PMF;
import com.sfeir.richercms.wizard.server.business.Language;

@SuppressWarnings("serial")
public class ServiceArboPageImpl  extends RemoteServiceServlet implements ArboPageService {

	
	private static final PersistenceManagerFactory Pmf = PMF.get();
	private RootArbo root = null;
	private int nbTranslation = 1;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}
	
	public ServiceArboPageImpl(){super();}
	
	public void addArboPage(BeanArboPage newArboPage, String parentKey) {
		
		PersistenceManager pm = getPersistenceManager();
		
    	Transaction tx = pm.currentTransaction();
		 try {
	    			ArboPage nAP = this.BeanToArboPage(newArboPage, pm);
	    			pm.makePersistent(nAP);

		    		if(parentKey.toString().equals(this.root.getEncodedKey())){
		    			tx.begin();
		    				RootArbo parentPage = pm.getObjectById(RootArbo.class, parentKey);
		    				parentPage.getIdChildArboPage().add(nAP.getEncodedKey());
		    			tx.commit();
		    		}else{
		    			tx.begin();
		    				ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
		    				parentPage.getIdChildArboPage().add(nAP.getEncodedKey());
		    			tx.commit();
		    		}
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
			pm.close();
		 }
		
	}

	public void deleteArboPage(String key, String parentKey) {
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {
	    		if(parentKey.toString().equals(this.root.getEncodedKey())){
	    			tx.begin();
	    				RootArbo parentPage = pm.getObjectById(RootArbo.class, parentKey);
	    				parentPage.getIdChildArboPage().remove(key);
	    			tx.commit();
	    		}else{
	    			tx.begin();
	    				ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
	    				parentPage.getIdChildArboPage().remove(key);
	    			tx.commit();
	    		}
	    		
		    	tx.begin();
		    		ArboPage arboPage = pm.getObjectById(ArboPage.class, key);
					pm.deletePersistent(arboPage);
				tx.commit();
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
		 }
	}

	public BeanArboPage getArboPage(String key) {
		ArboPage arboPage = null;
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {
		    	tx.begin();
		    	arboPage = pm.getObjectById(ArboPage.class, key);
			 	tx.commit();
			 }finally {
				if (tx.isActive()) {
				    tx.rollback();
				}
			 }
			 
		 if(arboPage != null)
			 return this.arboPageToBean(arboPage);
		 else
			 return null;
	}

	@Override
	public List<BeanArboPage> getChildPages(String ParentKey, boolean isMain) {
		
		List<String> childKeys;
    	ArrayList<BeanArboPage> lst = new ArrayList<BeanArboPage>();
		PersistenceManager pm = getPersistenceManager();
		 try {
				if(isMain) {
					childKeys = pm.getObjectById(RootArbo.class, ParentKey).getIdChildArboPage();
				}
				else {
					childKeys = pm.getObjectById(ArboPage.class, ParentKey).getIdChildArboPage();
				}
	    		
			 	ArboPage childArboPage;
				for (String key : childKeys){
					childArboPage = pm.getObjectById(ArboPage.class, key);
					if(childArboPage != null)
						lst.add(this.arboPageToBean(childArboPage));
				}
			 
		 }finally {
			 pm.close();
		 }
		 return lst;
	}

	@SuppressWarnings("unchecked")
	public BeanArboPage getMainArboPage() {
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
    	BeanArboPage bean = null;
    	try {
	    	tx.begin();
		        Query q = pm.newQuery(RootArbo.class);
		        List<RootArbo> roots = (List<RootArbo>) q.execute(); 
		        if(roots.size() == 0){
		        	countLanguage();
		        	this.root = new RootArbo();
		        	pm.makePersistent(this.root);
		        	TranslationPage tp = new TranslationPage(); tp.setPageTitle("main");
		        	pm.makePersistent(tp);
		        	this.root.getTranslation().add(tp);
		        	
		        	//add empty Translation into the page
		        	for(int i = 1 ; i<this.nbTranslation ; i++) {
			        	TranslationPage emptyTP = new TranslationPage();
			        	pm.makePersistent(emptyTP);
			        	this.root.getTranslation().add(emptyTP);
		        	}
		        }else {
		        	this.root = roots.get(0);
		        }
		        bean = this.arboRootToBean(this.root);
	        tx.commit();
	        q.closeAll();
        } finally {
			if (tx.isActive()) {
			    tx.rollback();
			}
        	pm.close();
        }
		return bean;
	}
	
	@Override
	public void updateArboPage(BeanArboPage bean) {
		
		PersistenceManager pm = getPersistenceManager();
    	Transaction tx = pm.currentTransaction();
		 try {
	    		if(bean.getEncodedKey().toString().equals(this.root.getEncodedKey())){
	    			tx.begin();
	    				RootArbo parentPage = pm.getObjectById(RootArbo.class, this.root.getEncodedKey());
	    				parentPage.getTranslation().clear();
	    				for(BeanTranslationPage trans : bean.getTranslation()) {
	    					TranslationPage tP = this.BeanToTranslationPage(trans);
	    					pm.makePersistent(tP);
	    					parentPage.getTranslation().add(tP);
	    				}
	    			tx.commit();
	    		}else{
	    			tx.begin();
	    				ArboPage parentPage = pm.getObjectById(ArboPage.class, bean.getEncodedKey());
	    				parentPage.getTranslation().clear();
	    				for(BeanTranslationPage trans : bean.getTranslation()) {
	    					TranslationPage tP = this.BeanToTranslationPage(trans);
	    					pm.makePersistent(tP);
	    					parentPage.getTranslation().add(tP);
	    				}
	    			tx.commit();
	    		}
			 }
		 finally{
			if (tx.isActive()) {
			    tx.rollback();
			}
		 }
	}
	
	public BeanArboPage arboPageToBean(ArboPage ap){
		BeanArboPage bap = new BeanArboPage(ap.getEncodedKey());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(TranslationPage tp : ap.getTranslation()){
			lst.add(translationPageToBean(tp));}
		bap.setTranslation(lst);
		return bap;
	}
	
	public BeanArboPage arboRootToBean(RootArbo ar){
		BeanArboPage bap = new BeanArboPage(ar.getEncodedKey());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(TranslationPage tp : ar.getTranslation()){
			lst.add(translationPageToBean(tp));}
		bap.setTranslation(lst);
		return bap;
	}
	
	public BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getEncodedKey(), tp.getBrowserTitle(),tp.getPageTitle(), tp.getUrlName(),
				 tp.getDescription(), tp.getKeyWord(), tp.getPublicationStart(),
				 tp.getPublicationFinish(), tp.getContent());
	}
	
	public ArboPage BeanToArboPage(BeanArboPage bAP,PersistenceManager pm){
		ArboPage ap = new ArboPage();
		ArrayList<TranslationPage> lst = new ArrayList<TranslationPage>();
		for(BeanTranslationPage bTp : bAP.getTranslation()){
			TranslationPage TP = BeanToTranslationPage(bTp);
			pm.makePersistent(TP);
			lst.add(TP);}
		ap.setTranslation(lst);
		return ap;
	}
	
	public TranslationPage BeanToTranslationPage(BeanTranslationPage bTp){
		return new TranslationPage(bTp.getBrowserTitle(),bTp.getPageTitle(), bTp.getUrlName(),
				bTp.getDescription(), bTp.getKeyWord(), bTp.getPublicationStart(),
				bTp.getPublicationFinish(), bTp.getContent());
	}
	
	
	/**
	 * Store the number of language in the class variable nbTranslation.
	 * With that, the application know how many translation are needed.
	 */
	@SuppressWarnings("unchecked")
	private void countLanguage() {
		PersistenceManager pm = getPersistenceManager();
		try {
	        Query q = pm.newQuery(Language.class);
	        List<Language> languages = (List<Language>) q.execute();
	        this.nbTranslation = languages.size();
		}finally{
			pm.close();
		}
	}

}
