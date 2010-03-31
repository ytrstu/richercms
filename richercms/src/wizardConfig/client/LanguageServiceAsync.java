package wizardConfig.client;


import java.util.List;

import wizardConfig.shared.BeanLanguageDetails;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Language Services
 * @author homberg.g
 *
 */
public interface LanguageServiceAsync {
	public void getLangues(AsyncCallback<List<BeanLanguageDetails>> callback);
	public void addBasesLanguage(AsyncCallback<Void> callback);
	public void addLanguage(String language,AsyncCallback<Void> callback);
	public void selectLanguage(List<Integer> lstID, AsyncCallback<Void> callback);
	public void deleteLanguage(List<Integer> lstID, AsyncCallback<Void> callback);
}
