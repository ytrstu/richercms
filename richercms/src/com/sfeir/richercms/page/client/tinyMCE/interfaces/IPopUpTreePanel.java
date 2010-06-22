package com.sfeir.richercms.page.client.tinyMCE.interfaces;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;
import com.sfeir.richercms.page.client.interfaces.custom.ITreePanel;

/**
 * Panel displays a tree structure, 
 * easy to browse the page as a file system who containing the pictures
 * @author homberg.g
 *
 */
public interface IPopUpTreePanel extends LazyView, ITreePanel{

	Widget asWidget();
}
