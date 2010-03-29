package wizardConfig.client;

import java.util.List;

import wizardConfig.shared.DetailsLangue;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("serviceLangue")
public interface ServiceLangue extends RemoteService
{
	/**
	 * Return the list containing the detail of the language to display
	 * @return the list containing the detail of the language
	 */
	public List<DetailsLangue> getLangues();
	
	/**
	 * Add Bases Languages in the datastore
	 */
	public void ajoutLangueDeBase();
	
	/**
	 * Add a new language in the datastore
	 * @param language ,new language to add
	 */
	public void addLanguage(String language);
	
	/**
	 * Select different Language available for the website
	 * @param lstID : the position in the DisplayTable of Language available for the website
	 */
	public void selectionneLangue(List<Integer> lstID);
	
	/**
	 * Delete some Languages
	 * @param lstID : the position in the DisplayTable of different languages available to delete
	 */
	public void deleteLanguage(List<Integer> lstID);
}
