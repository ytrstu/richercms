package com.sfeir.richercms.wizard.client;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

/**
 * Language Services
 * @author homberg.g
 *
 */
public interface LanguageServiceAsync {
	public void getLangues(AsyncCallback<List<BeanLanguageDetails>> callback);
	public void getLangue(int id, AsyncCallback<BeanLanguageDetails> callback);
	public void addBasesLanguage(AsyncCallback<Void> callback);
	public void addLanguage(String language, String tag,AsyncCallback<Void> callback);
	public void selectLanguages(List<Integer> lstID, AsyncCallback<Void> callback);
	public void selectLanguage(int id, AsyncCallback<Void> callback);
	public void deleteLanguages(List<Integer> lstID, AsyncCallback<Void> callback);
	public void deleteAllLanguages(AsyncCallback<Void> callback);
	public void deleteLanguage(int id, AsyncCallback<Void> callback);
	public void setTranslationKey(String languagekey, String translationKey, AsyncCallback<Void> callback);
	public void isAlreadyTranslated(String languagekey, AsyncCallback<String> callback);
}
