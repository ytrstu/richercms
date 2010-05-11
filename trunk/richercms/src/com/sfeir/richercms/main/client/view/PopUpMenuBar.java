package com.sfeir.richercms.main.client.view;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.sfeir.richercms.main.client.interfaces.IPopUpMenuBar;

/**
 * 
 * @author homberg.g
 *
 * MenuBar, using to make modification on Page (add, delete, modify, ...)
 */
public class PopUpMenuBar extends PopupPanel implements IPopUpMenuBar{

	private MenuBar popupMenuBar = new MenuBar(true);
	private MenuItem modifyPage = null;
	private MenuItem addPage = null;
	private MenuItem delPage = null;
	private MenuItem upPage = null;
	private MenuItem downPage = null;

	public PopUpMenuBar() {
		super(true);
		
		// instantiate with empty command
		this.modifyPage = new MenuItem("modify page", true, new Command(){public void execute() {;}});
		this.addPage = new MenuItem("Add page", true, new Command(){public void execute() {;}});
		this.delPage = new MenuItem("Delete page", true, new Command(){public void execute() {;}});
		this.upPage = new MenuItem("UP", true, new Command(){public void execute() {;}});
		this.downPage = new MenuItem("DOWN", true, new Command(){public void execute() {;}});
		
		this.popupMenuBar.addItem(this.modifyPage);
		this.popupMenuBar.addItem(this.addPage);
		this.popupMenuBar.addItem(this.delPage);
		this.popupMenuBar.addItem(this.upPage);
		this.popupMenuBar.addItem(this.downPage);
		  
		this.popupMenuBar.setVisible(true);
		this.popupMenuBar.setAnimationEnabled(true);
		this.setAnimationEnabled(true);
		this.add(this.popupMenuBar);
	}
	
	public void setPopupPosition(int left, int top)
	{
		super.setPopupPosition(left, top);
	}
	
	public void setAddPageCommand(Command cmd)
	{
		this.addPage.setCommand(cmd);
	}
	
	public void setDelPageCommand(Command cmd)
	{
		this.delPage.setCommand(cmd);
	}
	
	public void setModifyPageCommand(Command cmd)
	{
		this.modifyPage.setCommand(cmd);
	}
	
	public void setUpPageCommand(Command cmd)
	{
		this.upPage.setCommand(cmd);
	}
	
	public void setDownPageCommand(Command cmd)
	{
		this.downPage.setCommand(cmd);
	}

}
