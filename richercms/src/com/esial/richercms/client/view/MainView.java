package com.esial.richercms.client.view;

import java.util.HashMap;
import java.util.Iterator;

import com.esial.richercms.client.Richercms;
import com.esial.richercms.client.UserInfo;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class MainView {

	private FlowPanel content;
	private TabPanel tabPanel;
	private HashMap<String, FlowPanel> tabsContent;
	private HorizontalPanel loginPanel = new HorizontalPanel();
	private Label loginLabel = new Label(Richercms.getInstance().getCmsConstants().signInto());
	private Anchor signInLink = new Anchor(Richercms.getInstance().getCmsConstants().signIn());
	private Anchor signOutLink = new Anchor(Richercms.getInstance().getCmsConstants().signOut());

	

	//private String email;

	public MainView(UserInfo loginInfo) {
		super();
		if(loginInfo.isLoggedIn()) {
			//Tabs creation and insertion
			tabsContent=createTabs();
			tabPanel=insertTabsInPanel();
			tabPanel.selectTab(0);

			content=new FlowPanel();
			content.add(tabPanel);

			FlowPanel siteDock = tabsContent.get(Richercms.getInstance().getCmsConstants().site());
			siteDock.add(new SiteView());
			FlowPanel adminDock = tabsContent.get(Richercms.getInstance().getCmsConstants().admin());
			adminDock.add(new AdminView());
			signOutLink.setHref(loginInfo.getLogoutUrl());
			Label loginLabelMenu = new Label(loginInfo.getEmailAddress());
			loginLabelMenu.setStylePrimaryName("login-menu");
			loginPanel.add(loginLabelMenu);
			if(loginInfo.isAdmin()) loginPanel.add(new Label(" (admin) "));
			signOutLink.setStylePrimaryName("anchor-menu");
			loginPanel.add(signOutLink);
			
			
			
			Label lblCmsLg = new Label(Richercms.getInstance().getCmsConstants()
					.lblCmslang());
			lblCmsLg.setStylePrimaryName("text-langue");
			final ListBox cmsLg = new ListBox();
			cmsLg.addItem("English");
			cmsLg.addItem("Francais");
			cmsLg.addItem("Deutsch");
			cmsLg.setVisibleItemCount(1);
			String test = myLocale();
			if (test.equalsIgnoreCase("fr")){
				cmsLg.setSelectedIndex(1);	
			}else if (test.equalsIgnoreCase("de")){
				cmsLg.setSelectedIndex(2);			
			}else {cmsLg.setSelectedIndex(0);}
			cmsLg.setStylePrimaryName("menu-langue");

			cmsLg.addChangeHandler(new ChangeHandler() {
				public void onChange(ChangeEvent event) {
					// Get the index of the selected item
					int itemSelected = cmsLg.getSelectedIndex();

					// Get the string value of the item that has been selected
					String itemStringSelected = cmsLg.getValue(itemSelected);

					switch (itemSelected) {
					case 0: {
						myReload("en");
					}break;
					case 1: {
						myReload("fr");
					}break;
					case 2: {
						myReload("de");
					}break;
					default:{
						myReload("en");
					}break;
					}
				}
			});

			loginPanel.add(lblCmsLg);
			loginPanel.add(cmsLg);
		
		


		content.insert(loginPanel, 0);
			
		

	
			
			
			tabPanel.selectTab(0);
		} else {
			content=new FlowPanel();
			signInLink.setHref(loginInfo.getLoginUrl());
			loginLabel.setStylePrimaryName("text-login");
			loginPanel.add(loginLabel);
			signInLink.setStylePrimaryName("anchor-login");
			loginPanel.add(signInLink);
			content.add(loginPanel);
		}
	}

	private TabPanel insertTabsInPanel() {
		TabPanel tPanel=new TabPanel();
		Iterator<String> it=tabsContent.keySet().iterator();
		while (it.hasNext()) {
			String string = (String) it.next();
			tPanel.add(tabsContent.get(string), string);	
		}
		return tPanel;
	}

	private  HashMap<String, FlowPanel> createTabs() {
		HashMap<String, FlowPanel> tContent=new HashMap<String, FlowPanel>();
		tContent.put(Richercms.getInstance().getCmsConstants().site(), new FlowPanel());
		tContent.put(Richercms.getInstance().getCmsConstants().admin(), new FlowPanel());
		return tContent;
	}

	public FlowPanel getContent() {
		return content;
	}
 
	public void setContent(FlowPanel content) {
		this.content = content;
	}
	
	
	
	public void myReload(String countryCode) {
		String url=Window.Location.getHref();
		System.out.println(url);
		String[] splitted=url.split("locale");
		for(String s:splitted){
			System.out.println(s);
		}
		StringBuffer buf=new StringBuffer();
		buf.append(splitted[0]);
		buf.append("locale=");
		buf.append(countryCode);
		Window.Location.replace(buf.toString());
	}
	
	public String myLocale() {
		String url = Window.Location.getHref();
		String[] splitted = url.split("locale=");
		if(splitted.length<2) return "en";
		return splitted[1];
	};
 
}
