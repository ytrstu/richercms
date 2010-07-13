package com.sfeir.richercms.wizard.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import com.sfeir.richercms.wizard.client.LanguageService;
import com.sfeir.richercms.wizard.server.business.Language;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Implementation of all language Services.
 * @author homberg.g
 */
@SuppressWarnings("serial")
public class ServiceLangueImpl extends RemoteServiceServlet implements LanguageService{

	private List<Language> languages = null;
	
	static {
        ObjectifyService.register(Language.class);
    }
	
	/**
	 * Renvoi les langues disponibles
	 */
	public List<BeanLanguageDetails> getLangues() {
		
		Objectify ofy = ObjectifyService.begin();
		
		ArrayList<BeanLanguageDetails> lst = new ArrayList<BeanLanguageDetails>();
		ArrayList<Language> lgList = new ArrayList<Language>();

    	Query<Language> lgs = ofy.query(Language.class);

        for (Language language : lgs) {
        	lgList.add(language);
            lst.add(new BeanLanguageDetails(language.getId(), language.getLangue(),language.getTag(),language.isSelected(), language.getTranslationID()));
        }
        this.languages = lgList;
		return lst;
	}
	

	public BeanLanguageDetails getLangue(int id) {
		
		Objectify ofy = ObjectifyService.begin();
		
		Language lg = ofy.get(Language.class, this.languages.get(id).getId());
		 
		if(lg != null)
			return new BeanLanguageDetails(lg.getId(), lg.getLangue(), lg.getTag(), lg.isSelected(), lg.getTranslationID());
		else
			return null;
	}
	
	public void addBasesLanguage() {
		
	  /*  PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Language("Français","fr", false));
	      pm.makePersistent(new Language("Anglais","en", false));
	      pm.makePersistent(new Language("Allemand", "de", false));
	      pm.makePersistent(new Language("Italien", "it", false));
	    } finally {
	      pm.close();
	    }*/
	}
	
	public boolean addLanguage(String language, String tag) {
		
		Objectify ofy = ObjectifyService.begin();
		Language lg = new Language(language, tag);
		
		if(!this.existingLanguage(tag)){
			ofy.put(lg);
			return true;
		}
		return false;
	}
	
	public void selectLanguage(int id) {
	
		Objectify ofy = ObjectifyService.begin();
		if(id != -1 && this.languages.size() != 0)
		{

			//On d�-selectionne toute les langues
			for (Language objLanguage : this.languages) {
				Language lg = ofy.get(Language.class, objLanguage.getId());
				lg.setSelected(false);
				ofy.put(lg);
			}
			
			Language lg = ofy.get(Language.class, this.languages.get(id).getId());
			lg.setSelected(true);
			ofy.put(lg);
			
			 //when the default language is selected, we can save the index for each language
			 setAllTranslationID();
		}
	}
	
	public void deleteLanguages(List<Integer> lstID) {

		Objectify ofy = ObjectifyService.begin();
		for (Integer id : lstID) {
			Language lg = ofy.get(Language.class, this.languages.get(id).getId());
			ofy.delete(lg);
		}
	}
	
	public void deleteAllLanguages()
	{
		Objectify ofy = ObjectifyService.begin();
		Query<Language> lgs = ofy.query(Language.class);

		 for (Language lg : lgs) {
			 ofy.delete(lg);
		 }

	}
	
	public void deleteLanguage(int id) {
		Objectify ofy = ObjectifyService.begin();
		Language lg = ofy.get(Language.class, this.languages.get(id).getId());
		ofy.delete(lg);
	}
	
	private boolean existingLanguage(String tag) {
		
		 for (Language language : this.languages) {
			 if(tag.equals(language.getTag()))
				 return true;
		 }
		 return false;
	}
	
	private void setAllTranslationID() {
		Objectify ofy = ObjectifyService.begin();
		Query<Language> lgs = ofy.query(Language.class);   
	        
        int i = 0;
        for (Language language : lgs) {
            if(language.isSelected()){
            	language.setTranslationID(0);
            }else {
            	i = i+1;
            	language.setTranslationID(i);
            }
            ofy.put(language);
         }
	}
}
