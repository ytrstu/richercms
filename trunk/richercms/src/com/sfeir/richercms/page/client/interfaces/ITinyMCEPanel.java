package com.sfeir.richercms.page.client.interfaces;

import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.event.PageEventBus;

/**
 * Allows the presenter to communicate with the view
 * TinyMCEPanel <=> TinyMCEPanelPresenter
 * @author homberg.g
 *
 */
public interface ITinyMCEPanel extends LazyView{

	/**
	 * display the editor in the Panel
	 */
	void ShowEditor();
	
	/**
	 * Hide the editor in the Panel
	 */
	void HideEditor();
	
	/**
	 * Get the content of a webPage
	 * @return the content of the tinyEditor
	 */
	String getContent();
	
	/**
	 * Set the content of a webPage
	 * @param Content to display in tinyEditor
	 */
	void setContent(String content);
	
	/**
	 * display the tinyEditor in the panel
	 * @param html : the html String to display
	 */
	void displayEditor(String html);
	
	/**
	 * display a HtmlPanel to view a specific html String
	 * @param html : the html String to display
	 */
	void displayViewer(String html);
	
	/**
	 * Set EventBus in tiny for inject event throught
	 * the javascript wraper
	 * @param eventBus
	 */
	void addEventBusInTiny(PageEventBus eventBus);
}
