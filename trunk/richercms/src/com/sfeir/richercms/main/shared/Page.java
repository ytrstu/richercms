package com.sfeir.richercms.main.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Contain the displayable elements of a page.
 * @author homberg.g
 */
@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Page implements Serializable{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String browserTitle;
	@Persistent
	private String pageTitle;
	@Persistent
	private String urlName;
	@Persistent
	private String description;
	@Persistent
	private String keyWord;
	@Persistent
	private String publicationStart;
	@Persistent
	private String publicationFinish;
	@Persistent
	private String content;

	
	public Page()
	{
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.browserTitle = new String("");
		this.content = new String("");
	}

	public Page(String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content) {
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrowserTitle() {
		return browserTitle;
	}

	public void setBrowserTitle(String browserTitle) {
		this.browserTitle = browserTitle;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getPublicationStart() {
		return publicationStart;
	}

	public void setPublicationStart(String publicationStart) {
		this.publicationStart = publicationStart;
	}

	public String getPublicationFinish() {
		return publicationFinish;
	}

	public void setPublicationFinish(String publicationFinish) {
		this.publicationFinish = publicationFinish;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
