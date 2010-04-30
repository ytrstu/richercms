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
	            lst.add(new BeanLanguageDetails(language.getEncodedKey(), language.getLangue(),language.getTag(),language.isSelected(), language.getTranslationKey()));
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
			 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getEncodedKey());
			 
			 if(lg != null)
				 return new BeanLanguageDetails(lg.getEncodedKey(), lg.getLangue(), lg.getTag(), lg.isSelected(), lg.getTranslationKey());
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
					Language lg = pm.getObjectById(Language.class, objLanguage.getEncodedKey());
					lg.setSelected(false);
				 }
		
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getEncodedKey());
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
				Language lg = pm.getObjectById(Language.class, objLanguage.getEncodedKey());
				lg.setSelected(false);
			 }
	
			 
			 // On selectionne uniquement les bonnes langues
			 for (Integer IDlangue : lstID)
			 {	
				 Language lg = pm.getObjectById(Language.class, this.languages.get(IDlangue.intValue()).getEncodedKey());
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
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getEncodedKey());
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
				 Language lg = pm.getObjectById(Language.class, this.languages.get(id).getEncodedKey());
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
	
	public void setTranslationKey(String languagekey, String translationKey){
		
		PersistenceManager pm = getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
				Language lg = pm.getObjectById(Language.class, languagekey);
				lg.setTranslationKey(translationKey);
			tx.commit();
		}finally{
			if (tx.isActive()) {
			    tx.rollback();}
			//pm.close();
		}
	}
	
	public String isAlreadyTranslated(String languagekey) {
		PersistenceManager pm = getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		String translationKey;
		try {
			tx.begin();
				Language lg = pm.getObjectById(Language.class, languagekey);
				translationKey = lg.getTranslationKey();
			tx.commit();
		}finally{
			if (tx.isActive()) {
			    tx.rollback();}
			pm.close();
		}
		
		return translationKey;
	}
}
