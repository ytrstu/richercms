package com.sfeir.richercms.wizard.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sfeir.richercms.wizard.client.LanguageService;
import com.sfeir.richercms.wizard.server.business.Language;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Implementation of all language Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServiceLangueImpl extends RemoteServiceServlet implements LanguageService{

	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	private List<Language> languages = null;

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
	            lst.add(new BeanLanguageDetails(language.getLangue(),language.getTag(),language.isSelected()));
	          }
        } finally {
        	pm.close();
        }
		return lst;
	}
	
	public void addBasesLanguage() {
		
	    PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Language("Français"));
	      pm.makePersistent(new Language("Anglais"));
	      pm.makePersistent(new Language("Allemand"));
	      pm.makePersistent(new Language("Italien"));
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
			//On d�-selectionne toute les langues
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
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
	
}
