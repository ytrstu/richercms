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
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.page.client.event.PageEventBus;

/**
 * TinyMCE -
 *
 * A wrapper widget for using TinyMCE. It contains a number of JSNI methods that
 * I have found useful during development
 *
 * @author Aaron Watkins
 */
public class TinyMCE extends ResizeComposite {

	private boolean initialized = false; 
	
    private TextArea ta;
    private String id;
    private String text = "";

    public TinyMCE() {
        super();
        //System.out.println("TinyMCE create");
        LayoutPanel panel = new LayoutPanel();

        id = HTMLPanel.createUniqueId();
        ta = new TextArea();
        DOM.setElementAttribute(ta.getElement(), "id", id);
        DOM.setStyleAttribute(ta.getElement(), "width", "100%");
        ta.setStyleName("tinymce");
        panel.add(ta);

        initWidget(panel);
    }
    
	@Override
	public void onResize() {
		Widget parent = getParent();
		if (parent!=null && ta !=null) {
			ta.setHeight((parent.getOffsetHeight()-14)+"px");
			//System.out.println("TinyMCE 1 : " + parent.getOffsetHeight());
	        if (!initialized) {
	        	initialized = true;
	        	init(getID());
	        } else {
	        	text = getText(getID());
				hide();
				init(getID());
			}
			
	        DeferredCommand.addCommand(new Command() {
				
				@Override
				public void execute() {
					setText(getID(), text);
					FileManager.init();
				}
			});
	        
		}
	}
	
	public native void init(String id)/*-{
	    $wnd.$(function() {
	    	$wnd.$('textarea.tinymce').tinymce({
	    	// Location of TinyMCE script
		    	script_url : 'tiny_mce_jquery_3.3.9.1/tiny_mce.js',
		    	
		        mode : "textareas",
		        theme : "advanced",
		        plugins : "table,advhr,advimage,advlink,emotions,insertdatetime,preview,searchreplace,"+
		                  "print,contextmenu,inlinepopups,fullscreen",
		        file_browser_callback : "window.parent.tinyMCE_org_richercms_call.loadFileName",
		        theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,styleselect,formatselect,fontselect,fontsizeselect",
		        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
		        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,fullscreen",
		        theme_advanced_toolbar_location : "top",
		        theme_advanced_toolbar_align : "left",
		        theme_advanced_path_location : "bottom",
		        theme_advanced_resizing : false,
		        content_css : "/RicherCMS.css",
		        plugin_insertdate_dateFormat : "%Y/%m/%d",
		        plugin_insertdate_timeFormat : "%H:%M:%S",
		        extended_valid_elements : "a[name|href|target|title|onclick],img[class|style|src|border=0|alt|title|hspace"+
		                                  "|vspace|width|height|align|onmouseover|onmouseout|name],hr[class|width|size|"+
		                                  "noshade],font[face|size|color|style],span[class|align|style]",
		        fullscreen_new_window : false,
		        fullscreen_settings : {
		        	theme_advanced_path_location : "top"
		        }
	    	});
	   	});
    }-*/;
	
	public native void show(String id)/*-{
	$wnd.$('#'+id).tinymce().show();
    }-*/;

	public native void hide(String id)/*-{
	$wnd.$('#'+id).tinymce().hide();
	}-*/;
	 
	public native String getText(String id)/*-{
	return $wnd.$('#'+id).html();
	}-*/;
	
	public native void setText(String id, String content)/*-{
	$wnd.$('#'+id).html(content);
	}-*/;
	
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
    	this.text = text;
    }

    public String getText() {
       return getText(getID());
    }
    
    public void show() {
    	show(getID());
    }
    
    public void hide() {
    	hide(getID());
    }
    
    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    protected void onLoad() {
        super.onLoad();

        DeferredCommand.addCommand(new Command() {
			
			@Override
			public void execute() {
            	onResize();	
			}
		});
    }
    
	public void setEventBus(PageEventBus eventBus){
    	FileManager.evtBus = eventBus;
    }

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

}