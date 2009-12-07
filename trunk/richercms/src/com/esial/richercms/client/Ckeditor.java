package com.esial.richercms.client;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Ckeditor extends Composite {

	private TextArea ta;
	private String id;

	public Ckeditor(int width, int height) {
		super();

		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");

		id = HTMLPanel.createUniqueId();
		ta = new TextArea();
		ta.setCharacterWidth(width);
		ta.setVisibleLines(height);
		DOM.setElementAttribute(ta.getElement(), "id", id);
		DOM.setStyleAttribute(ta.getElement(), "width", "100%");
		panel.add(ta);

		initWidget(panel);
	}

	/**
	 * getID() -
	 * 
	 * @return the MCE element's ID
	 */
	public String getID() {
		return id;
	}

	/**
	 * setText() -
	 * 
	 * NOTE:
	 * 
	 * @param text
	 */
	public void setText(String text) {
		ta.setText(text);
	}

	public String getText() {
		getTextData();
		return ta.getText();
	}

	/**
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	protected void onLoad() {
		super.onLoad();

		DeferredCommand.addCommand(new Command() {
			public void execute() {
				setWidth("100%");
				setTextAreaToCkeditor(id);
				focusCkeditor(id);
			}
		});
	}

	/**
	 * focusCkeditor() -
	 * 
	 * Use this to set the focus to the Ckeditor area
	 * 
	 * @param id
	 *            - the element's ID
	 */
	protected native void focusCkeditor(String id) /*-{
		$wnd.ckeditor.execCommand('ckeditorFocus', true, id);
	}-*/;

	/**
	 * resetCkeditor() -
	 * 
	 * Use this if reusing the same Ckeditor element, but losing focus
	 */
	protected native void resetCkeditor() /*-{
		$wnd.ckeditor.execCommand('ckeditorResetDesignMode', true);
	}-*/;

	/**
	 * unload() -
	 * 
	 * Unload this Ckeditor instance from active memory. I use this in the
	 * onHide function of the containing widget. This helps to avoid problems,
	 * especially when using tabs.
	 */
	public void unload() {
		unloadCkeditor(id);
	}

	/**
	 * unloadCkeditor() -
	 * 
	 * @param id
	 *            - The element's ID JSNI method to implement unloading the 
	 *              Ckeditor instance from memory
	 */
	protected native void unloadCkeditor(String id) /*-{
		$wnd.ckeditor.execCommand('ckeditorRemoveControl', false, id);
	}-*/;

	/**
	 * updateContent() -
	 * 
	 * Update the internal referenced content. Use this if you programatically
	 * change the original text area's content (eg. do a clear)
	 * 
	 * @param id
	 *            - the ID of the text area that contains the content you wish
	 *            to copy
	 */
	protected native void updateContent(String id) /*-{
		$wnd.ckeditor.selectedInstance = $wnd.ckeditor.getInstanceById(id);
		$wnd.ckeditor.setContent($wnd.document.getElementById(id).value);
	}-*/;

	/**
	 * getTextArea() -
	 * 
	 */
	protected native void getTextData() /*-{
		$wnd.ckeditor.triggerSave();
	}-*/;

	/**
	 * encodeURIComponent() -
	 * 
	 * Wrapper for the native URL encoding methods
	 * 
	 * @param text
	 *            - the text to encode
	 * @return the encoded text
	 */
	protected native String encodeURIComponent(String text) /*-{
		return encodeURIComponent(text);
	}-*/;

	/**
	 * setTextAreaToCkeditor() -
	 * 
	 * Change a text area to a tiny Ckeditor editing field
	 * 
	 * @param id
	 *            - the text area's ID
	 */
	protected native void setTextAreaToCkeditor(String id) /*-{
		$wnd.ckeditor.execCommand('ckeditorAddControl', true, id);
	}-*/;

	/**
	 * removeCkeditor() -
	 * 
	 * Remove a Ckeditor editing field from a text area
	 * 
	 * @param id
	 *            - the text area's ID
	 */
	protected native void removeCkeditor(String id) /*-{
		$wnd.ckeditor.execCommand('ckeditorRemoveControl', true, id);
	}-*/;
}
