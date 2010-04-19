package com.sfeir.richercms.main.server.business;

import java.util.ArrayList;
import java.util.List;


import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Contain a page.
 * @author homberg.g
 */

@PersistenceCapable
public class Page{

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
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
	@Persistent
	private List<Page> subPages;

	
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
		this.subPages = new ArrayList<Page>();
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
		this.subPages = new ArrayList<Page>();
	}
	
	public Page(String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content, List<Page> subPages) {
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.content = content;
		this.subPages = subPages;
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

	public List<Page> getSubPages() {
		return subPages;
	}

	public void setSubPages(List<Page> subPages) {
		this.subPages = subPages;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	
}
