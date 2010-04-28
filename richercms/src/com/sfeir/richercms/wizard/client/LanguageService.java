package com.sfeir.richercms.wizard.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;


/**
 * All services needed to handle languages
 * @author homberg.g
 *
 */
@RemoteServiceRelativePath("languageService")
public interface LanguageService extends RemoteService
{
	/**
	 * Return the list containing the detail of the language to display
	 * @return the list containing the detail of the language
	 */
	public List<BeanLanguageDetails> getLangues();
	
	/**
	 * 
	 * @param id : the position in the DisplayTable of a specific language
	 * @return detail of this language
	 */
	public BeanLanguageDetails getLangue(int id);
	
	/**
	 * Add Bases Languages in the datastore
	 */
	public void addBasesLanguage();
	
	/**
	 * Add a new language in the datastore
	 * @param language ,new language to add
	 * @param tag, the tag of the associated language
	 */
	public void addLanguage(String language, String tag);
	
	/**
	 * Select different Language available for the website
	 * @param lstID : the position in the DisplayTable of Language available for the website
	 */
	public void selectLanguages(List<Integer> lstID);
	
	/**
	 * Select a different default Language available for the website
	 * @param id : the position in the DisplayTable of the new default language available for the website
	 */
	public void selectLanguage(int id);
	
	/**
	 * Delete some Languages
	 * @param lstID : the position in the DisplayTable of different languages available to delete
	 */
	public void deleteLanguages(List<Integer> lstID);
	
	/**
	 * Delete all Languages
	 */
	public void deleteAllLanguages();

	/**
	 * Delete a language
	 * @param id : the position in the DisplayTable of the language available to delete
	 */
	public void deleteLanguage(int id);
	
	/**
	 * When a translation of the website is setting, in the current language, we need to set the key of the translation
	 * @param languagekey : key of the language in the database
	 * @param translationKey : key of the translation in the database
	 */
	public void setTranslationKey(String languagekey, String translationKey);
	
	/**
	 * Return the key of a translation. if any translation exist, this function retrun null
	 * @param languagekey : the key of the language who need a translation
	 * @return the key of the translation in the database or null either
	 */
	public String isAlreadyTranslated(String languagekey);
}
