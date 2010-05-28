package com.sfeir.richercms.page.client.tinyMCE;
/**
 * Created on 20/08/2007
 *
 * Wrapper for TinyMCE
 * NOTE: Expects Javascript includes to be in enclosing HTML
 *
 * Author: Aaron Watkins (aaronDOTjDOTwatkinsATgmailDOTcom)
 * Website: http://www.goannatravel.com
 * Home Page for initial release of this widget: http://consult.goannatravel.com/code/gwt/tinymce.php
 *
 * Copyright [Aaron Watkins]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TextArea;
import com.sfeir.richercms.page.client.tinyMCE.FileManager;
import com.sfeir.richercms.page.client.tinyMCE.ResizeTinyMCETextArea;

/**
 * TinyMCE -
 *
 * A wrapper widget for using TinyMCE. It contains a number of JSNI methods that
 * I have found useful during development
 *
 * @author Aaron Watkins
 */
public class TinyMCE extends ResizeComposite {

    private TextArea ta;
    private String id;

    public TinyMCE(String height) {
        super();

        LayoutPanel panel = new LayoutPanel();

        id = HTMLPanel.createUniqueId();
        ta = new ResizeTinyMCETextArea(this);
        ta.setHeight(height);
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
     * @param text
     */
    public void setText(String text) {
    	this.removeMCE(id);
        ta.setText(text);
        this.setTextAreaToTinyMCE(id);
    }

    public String getText() {
        getTextData();
        return ta.getText();
    }
    
    public void disable() {
    	this.removeMCE(id);
    	ta.setEnabled(false);
    	this.setTextAreaToTinyMCE(id);
    }
    
    public void enable() {
    	this.removeMCE(id);
    	ta.setEnabled(true);
    	this.setTextAreaToTinyMCE(id);
    }
    
    public void setReadOnly(boolean readOnly) {
    	ta.setReadOnly(readOnly);
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    protected void onLoad() {
        super.onLoad();

        DeferredCommand.addCommand(new Command() {
            public void execute() {
                setWidth("100%");
        		LayoutPanel parent = (LayoutPanel) ta.getParent();
        		int height = parent.getOffsetHeight() - 13;
        		ta.setHeight(height+"px");
        		
               initTinyMCE();
            }
        });
    }
    
    public void initTinyMCE() {
        setTextAreaToTinyMCE(id);
        focusMCE(id);
		FileManager.init();
    }

    /**
     * focusMCE() -
     *
     * Use this to set the focus to the MCE area
     * @param id - the element's ID
     */
    protected native void focusMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceFocus', true, id);
    }-*/;

    /**
     * resetMCE() -
     *
     * Use this if reusing the same MCE element, but losing focus
     */
    protected native void resetMCE() /*-{
        $wnd.tinyMCE.execCommand('mceResetDesignMode', true);
    }-*/;

    /**
     * unload() -
     *
     * Unload this MCE editor instance from active memory.
     * I use this in the onHide function of the containing widget. This helps
     * to avoid problems, especially when using tabs.
     */
    public void unload() {
        unloadMCE(id);
    }

    /**
     * unloadMCE() -
     *
     * @param id - The element's ID
     * JSNI method to implement unloading the MCE editor instance from memory
     */
    protected native void unloadMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', false, id);
    }-*/;

    /**
     * updateContent() -
     *
     * Update the internal referenced content. Use this if you programatically change
     * the original text area's content (eg. do a clear)
     * @param id - the ID of the text area that contains the content you wish to copy
     */
    protected native void updateContent(String id) /*-{
        $wnd.tinyMCE.selectedInstance = $wnd.tinyMCE.getInstanceById(id);
        $wnd.tinyMCE.setContent($wnd.document.getElementById(id).value);
    }-*/;

    /**
     * getTextArea() -
     *
     */
    protected native void getTextData() /*-{
        $wnd.tinyMCE.triggerSave();
    }-*/;

    /**
     * encodeURIComponent() -
     *
     * Wrapper for the native URL encoding methods
     * @param text - the text to encode
     * @return the encoded text
     */
    protected native String encodeURIComponent(String text) /*-{
        return encodeURIComponent(text);
    }-*/;

    /**
     * setTextAreaToTinyMCE() -
     *
     * Change a text area to a tiny MCE editing field
     * @param id - the text area's ID
     */
    protected native void setTextAreaToTinyMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceAddControl', true, id);
    }-*/;

    /**
     * removeMCE() -
     *
     * Remove a tiny MCE editing field from a text area
     * @param id - the text area's ID
     */
    protected native void removeMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveControl', true, id);
    }-*/;
}