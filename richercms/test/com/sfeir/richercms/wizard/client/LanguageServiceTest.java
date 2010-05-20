package com.sfeir.richercms.wizard.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.wizard.shared.BeanLanguageDetails;

public class LanguageServiceTest extends GWTTestCase {

	private LanguageServiceAsync rpcLanguage = null;
	
	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}

	/**
	 * Initialize rpc connection
	 */
	@Before
	public void gwtSetUp()
	{
		this.rpcLanguage = GWT.create(LanguageService.class);
	}
	
	
	/**
	 * Delete all Language
	 */
	@Test
	public void testDeleteAllService() {
		
	  this.rpcLanguage.deleteAllLanguages(new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {
	    		assertTrue(true); // pas beau
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * Add 3 Languages
	 */
	@Test
	public void testBasesLanguage() {
		
		this.rpcLanguage.addBasesLanguage(new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {
	    		assertTrue(true); // pas beau
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * get the 3 languages added
	 */
	@Test
	public void testgetLangues() {
		
		this.rpcLanguage.getLangues(new AsyncCallback<List<BeanLanguageDetails>>(){
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		assertEquals(result.size(), 3);
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * add a language and test the numbe on language in the datastore
	 */
	@Test
	public void testAddLanguage() {
		
		this.rpcLanguage.addLanguage("Russe", "ru",new AsyncCallback<Void>() {
	    	public void onSuccess(Void result) {assertTrue(true);} // pas beau
			public void onFailure(Throwable caught){fail();}
			});
		
		this.rpcLanguage.getLangues(new AsyncCallback<List<BeanLanguageDetails>>(){
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		assertEquals(result.size(), 4); // one language added
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * delete the 4th element added above
	 */
	@Test
	public void testDeleteLanguage() {
		
		//delete the 4th (0 is the first id) element added above
		this.rpcLanguage.deleteLanguage(3, new AsyncCallback<Void>(){
	    	public void onSuccess(Void result) {assertTrue(true);} // pas beau
			public void onFailure(Throwable caught){fail();}
			});
		
		this.rpcLanguage.getLangues(new AsyncCallback<List<BeanLanguageDetails>>(){
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		assertEquals(result.size(), 3); // one language deleted
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * get a language thanks to his id (position)
	 */
	@Test
	public void testGetLanguage() {
		
		this.rpcLanguage.getLangue(0, new AsyncCallback<BeanLanguageDetails>(){
	    	public void onSuccess(BeanLanguageDetails result) {
	    		assertEquals(result.getLangue(), "Français"); // language added in with the bases language
	    	}
			public void onFailure(Throwable caught){fail();}
		});
		
		// the 4th language was deleted
		this.rpcLanguage.getLangue(4, new AsyncCallback<BeanLanguageDetails>(){
	    	public void onSuccess(BeanLanguageDetails result) {
	    		assertNull(result);
	    	}
			public void onFailure(Throwable caught){fail();}
		});
	}
	
	/**
	 * Select a list of default language -_-
	 */
	@Test
	public void testSelectLanguages()
	{
		/*List<Integer> lstID = new ArrayList<Integer>();
		lstID.add(0); lstID.add(1);
		
		this.rpcLanguage.selectLanguages(lstID, new AsyncCallback<Void>(){
	    	public void onSuccess(Void result) {assertTrue(true);} // pas beau
			public void onFailure(Throwable caught){fail();}
			});
		
		this.rpcLanguage.getLangues(new AsyncCallback<List<BeanLanguageDetails>>(){
	    	public void onSuccess(List<BeanLanguageDetails> result) {
	    		assertTrue(result.get(0).getSelectionner()); // test if this language is selected
	    		assertTrue(result.get(1).getSelectionner()); // test if this language is selected
	    	}
			public void onFailure(Throwable caught){fail();}
			});*/
	}
	
	/**
	 * Select the default language
	 */
	@Test
	public void testSelectLanguage()
	{
		this.rpcLanguage.selectLanguage(3, new AsyncCallback<Void>(){
	    	public void onSuccess(Void result) {assertTrue(true);} // pas beau
			public void onFailure(Throwable caught){fail();}
			});
		
		this.rpcLanguage.getLangue(3, new AsyncCallback<BeanLanguageDetails>(){
	    	public void onSuccess(BeanLanguageDetails result) {
	    		assertTrue(result.getSelectionner()); // test if this language is selected
	    	}
			public void onFailure(Throwable caught){fail();}
			});
	}
	
	/**
	 * Delete latest language
	 */
	@After
	public void testdeleteLanguages() {
		
		List<Integer> lstID = new ArrayList<Integer>();
		lstID.add(2); lstID.add(3);
		
		this.rpcLanguage.deleteLanguages( lstID, new AsyncCallback<Void>() {
		    public void onSuccess(Void result) {
		    	assertTrue(true); // pas beau
			}
			public void onFailure(Throwable caught){fail();}
			});
		this.rpcLanguage.getLangues(new AsyncCallback<List<BeanLanguageDetails>>(){
			public void onSuccess(List<BeanLanguageDetails> result) {
				assertEquals(result.size(), 0); // no language in the datastore
			}
			public void onFailure(Throwable caught){fail();}
			});
	}
}
