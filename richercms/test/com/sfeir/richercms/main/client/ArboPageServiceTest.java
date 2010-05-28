package com.sfeir.richercms.main.client;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sfeir.richercms.page.client.ArboPageService;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.shared.BeanArboPage;

public class ArboPageServiceTest  extends GWTTestCase {
	
	private ArboPageServiceAsync rpcArboPage = null;
	
	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}
	
	/**
	 * Initialize rpc connection
	 */
	@Before
	public void gwtSetUp() {
		this.rpcArboPage = GWT.create(ArboPageService.class);
	}
	
	@Test
	public void testGetMain(){
		Timer timer = new Timer() {
			public void run() {
				rpcArboPage.getMainArboPage(new AsyncCallback<BeanArboPage>(){
			    	public void onSuccess(BeanArboPage result) {
			    		assertEquals("add a main page in the default language",result.getTranslation().get(0).getPageTitle(), "main"); // test if the main page are added in default
			    		finishTest();
			    	}
					public void onFailure(Throwable caught){
						fail();
						finishTest();}
				});
			}
		};
		delayTestFinish(1000);
		timer.schedule(100);
	}
	
	@Test
	public void testAddArboPage() {
/*		final BeanArboPage newPage = new BeanArboPage();
		BeanTranslationPage defaultTranslation = new BeanTranslationPage("page1", "page1", "page1", "page1", "page1", "page1", "page1", "page1");
		newPage.getTranslation().add(defaultTranslation);
		
		this.rpcArboPage.getMainArboPage(new AsyncCallback<BeanArboPage>(){
	    	public void onSuccess(BeanArboPage result) {
	    		
	    		rpcArboPage.addArboPage(newPage, result.getEncodedKey(),new AsyncCallback<Void>(){
	    	    	public void onSuccess(Void result) {
	    	    		assertTrue(true);
	    	    	}
	    			public void onFailure(Throwable caught){fail();}
	    		});
	    	}
			public void onFailure(Throwable caught){fail();}
		});*/
	}
	
	@Test
	public void testGetChildPages() {
		this.rpcArboPage.getMainArboPage(new AsyncCallback<BeanArboPage>(){
	    	public void onSuccess(BeanArboPage result) {
	    		
	    		/*rpcArboPage.getChildPages(result.getEncodedKey(),new AsyncCallback<List<BeanArboPage>>(){
	    	    	public void onSuccess(List<BeanArboPage> result) {
	    	    		assertEquals("One child added in the previous test",result.size(),1);
	    	    		assertEquals("Test if the translation of each child is goodly load",result.get(0).getTranslation().get(0).getPageTitle(), "page1");
	    	    	}
	    			public void onFailure(Throwable caught){fail();}
	    		});*/
	    	}
			public void onFailure(Throwable caught){fail();}
		});
	}
	
	@Test
	public void testGetArboPage() {
		//take the main arboPage
		/*this.rpcArboPage.getMainArboPage(new AsyncCallback<BeanArboPage>(){
	    	public void onSuccess(BeanArboPage result) {
	    		//take his only child
	    		rpcArboPage.getChildPages(result.getEncodedKey(),new AsyncCallback<List<BeanArboPage>>(){
	    	    	public void onSuccess(List<BeanArboPage> result) {
	    	    		//take this child with its key
	    	    		rpcArboPage.getArboPage(result.get(0).getEncodedKey(),new AsyncCallback<BeanArboPage>(){
	    	    	    	public void onSuccess(BeanArboPage result) {
	    	    	    		assertEquals(result.getTranslation().get(0).getPageTitle(), "page1");
	    	    	    	}
	    	    			public void onFailure(Throwable caught){fail();}
	    	    		});
	    	    	}
	    			public void onFailure(Throwable caught){fail();}
	    		});
	    	}
			public void onFailure(Throwable caught){fail();}
		});*/
	}
	
	@Test 
	public void testDeleteArboPage() {
		//take the mainPage
		/*this.rpcArboPage.getMainArboPage(new AsyncCallback<BeanArboPage>(){
	    	public void onSuccess(BeanArboPage result) {
	    		//Take all child of the mainPage
	    		rpcArboPage.getChildPages(result.getEncodedKey(),new AsyncCallback<List<BeanArboPage>>(){
	    	    	public void onSuccess(List<BeanArboPage> result) {
	    	    		final String childKey = result.get(0).getEncodedKey();
	    	    		//Destroy the only child of the main
	    	    		rpcArboPage.deleteArboPage(childKey, new AsyncCallback<Void>(){
	    	    	    	public void onSuccess(Void result) {
	    	    	    		assertTrue(true);
	    	    	    		rpcArboPage.getArboPage(childKey,new AsyncCallback<BeanArboPage>(){
	    	    	    	    	public void onSuccess(BeanArboPage result) {
	    	    	    	    		assertNull(result);
	    	    	    	    	}
	    	    	    			public void onFailure(Throwable caught){fail();}
	    	    	    		});
	    	    	    	}
	    	    			public void onFailure(Throwable caught){fail();}
	    	    		});
	    	    	}
	    			public void onFailure(Throwable caught){fail();}
	    		});
	    	}
			public void onFailure(Throwable caught){fail();}
		});*/
	}
	
	
	
	/**
	 * Delete latest ArboPage
	 */
	@After
	public void testdeleteMain() {
	}

}
