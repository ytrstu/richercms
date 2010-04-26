package com.sfeir.richercms.main.client.interfaces;

import com.mvp4g.client.view.LazyView;

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
	 * Enable the tinyEditor
	 */
	void enableEditor();
	
	/**
	 * Disable the tinyEditor
	 */
	void disableEditor();
}
