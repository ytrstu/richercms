package com.sfeir.richercms.wizard.client.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * NE FONCTIONNE PAS, A VOIR.
 * @author homberg.g
 *
 */
public class Page2ViewTest extends GWTTestCase {

	private Page2View view;
	
	public String getModuleName() {
		return "com.sfeir.richercms.RicherCMS";
	}
	
	/**
	 * Initialize the view
	 */
	@Before
	public void gwtSetUp() {
		view = new Page2View();
		view.createView();
	}
	
	@Test
	public void testAddLanguage() {
		view.addLanguage("French", "fr", false);
		assertEquals("First language added : id=0",view.getCurrentNumRow(), 0);
	}
	
	@Test
	public void testGetSelectedLanguage() {
		assertEquals("Any selected Language",view.getSelectedLanguage(), -1);
		view.addLanguage("English", "en", true);
		assertEquals("One language selected id : 1",view.getSelectedLanguage(), 1);
		
	}
	
	@Test
	public void  testClearTableLanguage() {
		view.clearTableLanguage();
		assertEquals("Juste the title in the table",view.getCurrentNumRow(), -1);
	}
	
	@Test
	public void  testSetSelectedLanguage() {
		view.addLanguage("French", "fr", false);
		view.addLanguage("English", "en", false);
		view.addLanguage("Deutsch", "DE", false);
		assertEquals("3 Language in the table",view.getCurrentNumRow(), 2);
		assertEquals("Any languages selected",view.getSelectedLanguage(), -1);
		view.setSelectedLanguage(2);
		assertEquals("The Third language is set selected",view.getSelectedLanguage(), 3);
	}
}
