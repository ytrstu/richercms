package com.sfeir.richercms.main.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

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
	private String keyRoot = null;
	private int nbTranslation = 1;
	
	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}
	
	public ServiceArboPageImpl(){super();}
	
	public void addArboPage(BeanArboPage newArboPage, String parentKey) {
		
		PersistenceManager pm = getPersistenceManager();
		
		try {
			ArboPage nAP = this.BeanToArboPage(newArboPage);
			pm.makePersistent(nAP);
			
			ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
			parentPage.getIdChildArboPage().add(nAP.getEncodedKey());
			pm.makePersistent(parentPage);
	
		}
		finally {
			pm.close();
		}	
	}

	public void deleteArboPage(String key, String parentKey) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {

    			ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
    			parentPage.getIdChildArboPage().remove(key);
    			pm.makePersistent(parentPage);
	    		
		    	ArboPage arboPage = pm.getObjectById(ArboPage.class, key);
				pm.deletePersistent(arboPage);

			 }
		 finally{
			 pm.close();
		 }
	}

	public BeanArboPage getArboPage(String key) {
		PersistenceManager pm = getPersistenceManager();
    	BeanArboPage bean;
    	
		 try {
		    	ArboPage page = pm.getObjectById(ArboPage.class, key);
	    		bean = this.arboPageToBean(page);

		 }finally {
			pm.close();
		 }

		 if(bean != null)
			 return bean;
		 else
			 return null;
	}

	
	public List<BeanArboPage> getChildPages(String ParentKey, boolean isMain) {
		
		List<String> childKeys;
    	ArrayList<BeanArboPage> lst = new ArrayList<BeanArboPage>();
		PersistenceManager pm = getPersistenceManager();
		 try {
				childKeys = pm.getObjectById(ArboPage.class, ParentKey).getIdChildArboPage();

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
    	BeanArboPage bean = null;
    	try {
	        Query q = pm.newQuery(RootArbo.class);
	        List<RootArbo> roots = (List<RootArbo>) q.execute(); 
	        if(roots.size() == 0){
	        	countLanguage();
	        	RootArbo root = new RootArbo();
	        	ArboPage rootPage = new ArboPage();
	        	TranslationPage tp = new TranslationPage(); tp.setUrlName("main");
	        	rootPage.getTranslation().add(tp);
	        	
	        	//add empty Translation into the page
	        	for(int i = 1 ; i<this.nbTranslation ; i++) {
		        	TranslationPage emptyTP = new TranslationPage();
		        	rootPage.getTranslation().add(emptyTP);
	        	}
	   
	        	pm.makePersistent(rootPage);
	        	root.setKeyOfRootArboPage(rootPage.getEncodedKey());
	        	pm.makePersistent(root);
	       
	        	this.keyRoot = rootPage.getEncodedKey();
	        	bean = this.arboPageToBean(rootPage);
	        }else {
	        	this.keyRoot = roots.get(0).getKeyOfRootArboPage();
	        	bean = this.arboPageToBean(pm.getObjectById(ArboPage.class, this.keyRoot));
	        }
        } finally {
        	pm.close();
        }
		return bean;
	}
	
	public void updateArboPage(BeanArboPage bean) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {
				ArboPage parentPage = pm.getObjectById(ArboPage.class, bean.getEncodedKey());
				pm.deletePersistentAll(parentPage.getTranslation());
				parentPage.getTranslation().clear();
				for(BeanTranslationPage trans : bean.getTranslation()) {
					TranslationPage tP = this.BeanToTranslationPage(trans);
					parentPage.getTranslation().add(tP);
				}
				pm.makePersistent(parentPage);

			 }
		 finally{
			pm.close();
		 }
	}
	
	public BeanArboPage getLastChildAdded(String parentKey){
		PersistenceManager pm = getPersistenceManager();
		BeanArboPage bean = null;
		try {
			 ArboPage parentPage = pm.getObjectById(ArboPage.class, parentKey);
			 
			 if(parentPage.getIdChildArboPage().size() != 0) {
				 ArboPage LastPageAdded = pm.getObjectById(ArboPage.class, 
					 parentPage.getIdChildArboPage().get(parentPage.getIdChildArboPage().size() -1));

			 	bean = this.arboPageToBean(LastPageAdded);
			 }
		 }finally{
			pm.close();
		 }
		return bean;
	}
	
	public BeanArboPage arboPageToBean(ArboPage ap){
		BeanArboPage bap = new BeanArboPage(ap.getEncodedKey(),ap.getPublicationStart(), ap.getPublicationFinish());
		ArrayList<BeanTranslationPage> lst = new ArrayList<BeanTranslationPage>();
		for(TranslationPage tp : ap.getTranslation()){
			lst.add(translationPageToBean(tp));}
		bap.setTranslation(lst);
		return bap;
	}
	
	public BeanTranslationPage translationPageToBean(TranslationPage tp) {
		return new BeanTranslationPage(tp.getEncodedKey(), tp.getBrowserTitle(),tp.getPageTitle(), tp.getUrlName(),
				 tp.getDescription(), tp.getKeyWord(), tp.getContent().getValue());
	}
	
	public ArboPage BeanToArboPage(BeanArboPage bAP){
		ArboPage ap = new ArboPage();
		ap.setPublicationStart(bAP.getPublicationStart());
		ap.setPublicationFinish(bAP.getPublicationFinish());
		ArrayList<TranslationPage> lst = new ArrayList<TranslationPage>();
		for(BeanTranslationPage bTp : bAP.getTranslation()){
			TranslationPage TP = BeanToTranslationPage(bTp);
			lst.add(TP);}
		ap.setTranslation(lst);
		return ap;
	}
	
	public TranslationPage BeanToTranslationPage(BeanTranslationPage bTp){
		return new TranslationPage(bTp.getBrowserTitle(),bTp.getPageTitle(), bTp.getUrlName(),
				bTp.getDescription(), bTp.getKeyWord(), bTp.getContent());
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
