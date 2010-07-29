package com.sfeir.richercms.page.client.tinyMCE;

import com.sfeir.richercms.page.client.event.PageEventBus;

/**
 * 
 * @author homberg.g
 * Wrapper for running a gwt popUp into Javascript
 * link tinyMCe command with a GWT PopUp
 */
public class FileManager {

	// PageEventBus, it's necessary to pass it to call the FileBoxManager
	public static PageEventBus evtBus = null;
	
	/**
	 * Méthode statique d'ouverture de la popup
	 * @return Pour les tests retourne une chaine affiché dans le champ url de TinyMCE
	 */
	public static String loadFileName(final String type) {
		if(type.compareTo("image") == 0)
			FileManager.evtBus.loadFileManager();
		else if(type.compareTo("file") == 0)
			FileManager.evtBus.loadLinkManager();
		return "";
	}

	/**
	 * Mise en place du Javascript permettant d'ouvrir le FileManager GWT
	 */
	public static native void init() /*-{
		$wnd.tinyMCE.org_richercms_call = {};
	    $wnd.tinyMCE.org_richercms_call.loadFileName = function(field_name, url, type, win) {
	    	//alert('Dans GWT');
	    	$wnd.tinyMCE.org_richercms_call.win = win;
	    	$wnd.tinyMCE.org_richercms_call.field_name = field_name;
	    	win.document.forms[0].elements[field_name].value = @com.sfeir.richercms.page.client.tinyMCE.FileManager::loadFileName(Ljava/lang/String;)(type);
	    }
	    $wnd.tinyMCE.org_richercms_call.setFileName = function(url) {
		}
	}-*/;

	/**
	 * Insère le nom de l'image dans tinyMCE.
	 * @param url : adresse de l'image
	 */
	public static native void setTinyMceUrl(String url) /*-{
		var fieldName = $wnd.tinyMCE.org_richercms_call.field_name;
		$wnd.tinyMCE.org_richercms_call.win.document.forms[0].elements[fieldName].value = url;
	}-*/;
}
