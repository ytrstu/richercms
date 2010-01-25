package com.esial.richercms.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Page {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String browser_title;
	@Persistent
	private String page_title;
	@Persistent
	private String url_name;
	@Persistent
	private String description;
	@Persistent
	private String content;
	@Persistent
	private Date publish_date;
	
	public long getId() {
		return id;
	}
	
	public Page(){
		this.publish_date=new Date();
	}

	public Page(String browser_title,String page_title, String url_name,String 
			description, String content){
		this();
		this.browser_title=browser_title;
		this.page_title=page_title;
		this.url_name=url_name;
		this.description=description;
		this.content=content;
	}

	public String getBrowser_title() {
		return browser_title;
	}

	public void setBrowser_title(String browserTitle) {
		browser_title = browserTitle;
	}

	public String getPage_title() {
		return page_title;
	}

	public void setPage_title(String pageTitle) {
		page_title = pageTitle;
	}

	public String getUrl_name() {
		return url_name;
	}

	public void setUrl_name(String urlName) {
		url_name = urlName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(Date publishDate) {
		publish_date = publishDate;
	}

}
