package wizardConfig.client;


import java.util.List;

import wizardConfig.shared.DetailsLangue;


import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Language Services
 * @author homberg.g
 *
 */
public interface ServiceLangueAsync 
{
	public void getLangues(AsyncCallback<List<DetailsLangue>> callback);
	public void ajoutLangueDeBase(AsyncCallback<Void> callback);
	public void addLanguage(String language,AsyncCallback<Void> callback);
	public void selectionneLangue(List<Integer> lstID, AsyncCallback<Void> callback);
	public void deleteLanguage(List<Integer> lstID, AsyncCallback<Void> callback);
}
