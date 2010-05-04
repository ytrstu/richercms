package com.sfeir.richercms.wizard.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.server.PMF;
import com.sfeir.richercms.wizard.client.LanguageService;
import com.sfeir.richercms.wizard.server.business.Language;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Implementation of all language Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServiceLangueImpl extends RemoteServiceServlet implements LanguageService{

	private static final PersistenceManagerFactory Pmf = PMF.get();
	private List<Language> languages = null;

	private PersistenceManager getPersistenceManager() {
		return Pmf.getPersistenceManager();
	}
	
	/**
	 * Renvoi les langues disponibles
	 */
	@SuppressWarnings("unchecked")
	public List<BeanLanguageDetails> getLangues() {
		
		PersistenceManager pm = getPersistenceManager();
		ArrayList<BeanLanguageDetails> lst = new ArrayList<BeanLanguageDetails>();
	    try {
	        Query q = pm.newQuery(Language.class);
	        languages = (List<Language>) q.execute();
	      
	        for (Language language : languages) {
	            lst.add(new BeanLanguageDetails(language.getId(), language.getLangue(),language.getTag(),language.isSelected(), language.getTranslationID()));
	          }
        } finally {
        	pm.close();
        }
		return lst;
	}
	

	public BeanLanguageDetails getLangue(int id) {
		
		PersistenceManager pm = getPersistenceManager();
		
		 try
		 {
			 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getId());
			 
			 if(lg != null)
				 return new BeanLanguageDetails(lg.getId(), lg.getLangue(), lg.getTag(), lg.isSelected(), lg.getTranslationID());
			 else
				 return null;
		 }finally{
			 pm.close();
		 }
	}
	
	public void addBasesLanguage() {
		
	    PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Language("Français","fr", false));
	      pm.makePersistent(new Language("Anglais","en", false));
	      pm.makePersistent(new Language("Allemand", "de", false));
	      pm.makePersistent(new Language("Italien", "it", false));
	    } finally {
	      pm.close();
	    }
	}
	
	public void addLanguage(String language, String tag) {
		
		Language lg = new Language(language, tag);
		PersistenceManager pm = getPersistenceManager();
	    try {
	    	// on ajoute dans le dataStore uniquement s'il n'existe pas d�j�
	    	if(!this.existingLanguage(language)){
	    		pm.makePersistent(lg);
	    	}
	    } finally {
	      pm.close();
	    }
	}
	
	public void selectLanguage(int id) {
		
		if(id != -1 && this.languages.size() != 0)
		{
			PersistenceManager pm = getPersistenceManager();
			
			 try
			 {
				//On d�-selectionne toute les langues
				 for (Language objLanguage : this.languages) {
					Language lg = pm.getObjectById(Language.class, objLanguage.getId());
					lg.setSelected(false);
				 }
		
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getId());
				 lg.setSelected(true);
	
				 
			 }finally{
				 pm.close();
			 }
		}
	}
	
	public void selectLanguages(List<Integer> lstID) {
		
		PersistenceManager pm = getPersistenceManager();
		
		 try
		 {
			//On dé-selectionne toute les langues
			 for (Language objLanguage : this.languages) {
				Language lg = pm.getObjectById(Language.class, objLanguage.getId());
				lg.setSelected(false);
			 }
	
			 
			 // On selectionne uniquement les bonnes langues
			 for (Integer IDlangue : lstID)
			 {	
				 Language lg = pm.getObjectById(Language.class, this.languages.get(IDlangue.intValue()).getId());
				 lg.setSelected(true);
			 }
			 
		 }finally{
			 pm.close();
		 }
	}
	
	public void deleteLanguages(List<Integer> lstID) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {
			 
			 for (Integer id : lstID) {
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getId());
				 pm.deletePersistent(lg);
			 }

		 }finally{
			 pm.close();
		 }
	}
	
	@SuppressWarnings("unchecked")
	public void deleteAllLanguages()
	{
		PersistenceManager pm = getPersistenceManager();
		 try {
		        Query q = pm.newQuery(Language.class);
		        languages = (List<Language>) q.execute();

				 for (Language lg : this.languages) {
					 pm.deletePersistent(lg);
				 }
				 
		 }finally{
			 pm.close();
		 }
	}
	
	public void deleteLanguage(int id) {
		
		PersistenceManager pm = getPersistenceManager();
		 try {
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getId());
				 pm.deletePersistent(lg);

		 }finally{
			 pm.close();
		 }
	}
	
	private boolean existingLanguage(String lg) {
		
		//ArrayList<String> lst = getLangues();
		 for (Language language : this.languages) {
			 if(lg.equals(language.getLangue()))
				 return true;
		 }
		 return false;
	}
	
	public void setTranslationKey(Long id, int translationID){
		
		PersistenceManager pm = getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
				Language lg = pm.getObjectById(Language.class, id);
				lg.setTranslationID(translationID);
			tx.commit();
		}finally{
			if (tx.isActive()) {
			    tx.rollback();}
			pm.close();
		}
	}
	
	public Integer isAlreadyTranslated(Long id) {
		PersistenceManager pm = getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		int translationID;
		try {
			tx.begin();
				Language lg = pm.getObjectById(Language.class, id);
				translationID = lg.getTranslationID();
			tx.commit();
		}finally{
			if (tx.isActive()) {
			    tx.rollback();}
			pm.close();
		}
		
		return new Integer(translationID);
	}
	
	@SuppressWarnings("unchecked")
	public void setAllTranslationID() {
		PersistenceManager pm = getPersistenceManager();
	    try {
	        Query q = pm.newQuery(Language.class);
	        languages = (List<Language>) q.execute();   
	        
	        int i = 0;
	        for (Language language : languages) {
	            if(language.isSelected()){
	            	language.setTranslationID(0);
	            }else {
	            	i = i+1;
	            	language.setTranslationID(i);
	            }
	         }
        } finally {
        	pm.close();
        }
	}
}
