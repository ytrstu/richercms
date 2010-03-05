package com.esial.richercms.client.view;

import com.esial.richercms.client.Richercms;
import com.esial.richercms.client.UserInfo;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AdminView extends FlowPanel {
	private HorizontalPanel panel;
	private VerticalPanel adminPanel;
	private VerticalPanel languagePanel;
	private VerticalPanel addPanel;
	private VerticalPanel editPanel;
	private Label test;

	public AdminView() {
		super();
		panel = new HorizontalPanel();
		adminPanel = new VerticalPanel();
		adminPanel.setSize("500px", "400px");

		/* To delete after testing */
		test = new Label();
		panel.add(test);
		/* */

		Button addUser = new Button(Richercms.getInstance().getCmsConstants()
				.bpaddu());
		addUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.remove(adminPanel);
				panel.remove(languagePanel);
				addPanel = new VerticalPanel();
				addPanel.setSize("500px", "400px");

				Label lbllogin = new Label(Richercms.getInstance()
						.getCmsConstants().login());
				TextBox login = new TextBox();
				addPanel.add(lbllogin);
				addPanel.add(login);

				Button bpAddUser = new Button(Richercms.getInstance()
						.getCmsConstants().bpaddu());

				Button cancelAction = new Button(Richercms.getInstance()
						.getCmsConstants().bpcancel());
				cancelAction.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						panel.remove(addPanel);
						panel.add(adminPanel);
						panel.add(languagePanel);
					}
				});
				addPanel.add(bpAddUser);
				addPanel.add(cancelAction);
				panel.add(addPanel);
			}
		});

		Button bpEditUser = new Button(Richercms.getInstance()
				.getCmsConstants().bpeditu());
		bpEditUser.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				panel.remove(adminPanel);
				panel.remove(languagePanel);

				editPanel = new VerticalPanel();
				editPanel.setSize("500px", "400px");

				Label title = new Label(Richercms.getInstance()
						.getCmsConstants().bpeditu());

				// Create a grid
				Grid grid = new Grid(2, 3);

				/*
				 * int numRows = grid.getRowCount(); int numColumns =
				 * grid.getColumnCount();
				 */
				UserInfo u = Richercms.getInstance().getLoginInfo();

				grid.setHTML(0, 0, Richercms.getInstance().getCmsConstants()
						.nickname());

				grid.setHTML(0, 1, Richercms.getInstance().getCmsConstants()
						.email());

				grid.setHTML(0, 2, Richercms.getInstance().getCmsConstants()
						.isadmin());

				grid.setHTML(1, 0, u.getNickname());
				grid.setHTML(1, 1, u.getEmailAddress());
				grid.setHTML(1, 2, "" + u.isAdmin());

				Button cancelAction = new Button(Richercms.getInstance()
						.getCmsConstants().bpcancel());
				cancelAction.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						panel.remove(editPanel);
						panel.add(adminPanel);
						panel.add(languagePanel);
					}
				});

				editPanel.add(title);
				editPanel.add(grid);
				editPanel.add(cancelAction);
				panel.add(editPanel);
			}
		});

		adminPanel.add(addUser);
		adminPanel.add(bpEditUser);

		// Language Settings part
		languagePanel = new VerticalPanel();
		languagePanel.setSize("500px", "400px");

		/* Choice of the CMS language */
		Label lblCmsLg = new Label(Richercms.getInstance().getCmsConstants()
				.lblCmslang());
		final ListBox cmsLg = new ListBox();
		cmsLg.addItem("English");
		cmsLg.addItem("Francais");
		cmsLg.addItem("Deutsch");
		cmsLg.setVisibleItemCount(1);
		String test = myLocale();
		if (test.equalsIgnoreCase("fr")) {
			cmsLg.setSelectedIndex(1);
		} else if (test.equalsIgnoreCase("de")) {
			cmsLg.setSelectedIndex(2);
		} else {
			cmsLg.setSelectedIndex(0);
		}

		cmsLg.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				// Get the index of the selected item
				int itemSelected = cmsLg.getSelectedIndex();

				// Get the string value of the item that has been selected
				// String itemStringSelected = cmsLg.getValue(itemSelected);

				switch (itemSelected) {
				case 0: {
					myReload("en");
				}
					break;
				case 1: {
					myReload("fr");
				}
					break;
				case 2: {
					myReload("de");
				}
					break;
				default: {
					myReload("en");
				}
					break;
				}
			}
		});

		languagePanel.add(lblCmsLg);
		languagePanel.add(cmsLg);

		/* Choice of the generated website language */
		/*
		 * Label lblSiteLg = new Label(Richercms.getInstance().getCmsConstants()
		 * .lblSitelang()); ListBox siteLg = new ListBox();
		 * siteLg.addItem("English"); siteLg.addItem("Francais");
		 * siteLg.addItem("Deutsch"); siteLg.setVisibleItemCount(1);
		 * languagePanel.add(lblSiteLg); languagePanel.add(siteLg);
		 */
		panel.add(adminPanel);
		panel.add(languagePanel);
		this.add(panel);
	}

	public void myReload(String countryCode) {
		String url = Window.Location.getHref();
		System.out.println(url);
		StringBuffer buf = new StringBuffer();
		if (url.contains("locale")) {
			String[] splitted = url.split("locale");
			buf.append(splitted[0]);
			buf.append("locale=");
			buf.append(countryCode);
			System.out.println("Avec locale : "+buf);
		} else {
			buf.append(url);
			buf.append("?locale=");
			buf.append(countryCode);
			System.out.println("Sans locale : "+buf);
		}

		Window.Location.replace(buf.toString());
	};

	public String myLocale() {
		String url = Window.Location.getHref();
		if (url.contains("locale")) {
			String[] splitted = url.split("locale=");
			if (splitted.length < 2)
				return "en";
			return splitted[1];
		}
		return "en";
	};
}