package wizardConfig.client;

import java.util.List;

import wizardConfig.shared.BeanLanguageDetails;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


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
	 * Add Bases Languages in the datastore
	 */
	public void addBasesLanguage();
	
	/**
	 * Add a new language in the datastore
	 * @param language ,new language to add
	 */
	public void addLanguage(String language);
	
	/**
	 * Select different Language available for the website
	 * @param lstID : the position in the DisplayTable of Language available for the website
	 */
	public void selectLanguage(List<Integer> lstID);
	
	/**
	 * Delete some Languages
	 * @param lstID : the position in the DisplayTable of different languages available to delete
	 */
	public void deleteLanguage(List<Integer> lstID);
}
