package com.sfeir.richercms.main.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class BeanPage implements Serializable {
	
	private String key;
	private String browserTitle;
	private String pageTitle;
	private String urlName;
	private String description;
	private String keyWord;
	private String publicationStart;
	private String publicationFinish;
	private String content;
	private List<BeanPage> subPages;
	
	public BeanPage() {
		super();
		this.key = "";
		this.browserTitle = "";
		this.pageTitle = "";
		this.urlName = "";
		this.description = "";
		this.keyWord = "";
		this.publicationStart = "";
		this.publicationFinish = "";
		this.content = "";
		this.subPages = new ArrayList<BeanPage>();
	}

	public BeanPage(String key, String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content, List<BeanPage> subPages) {
		super();
		this.key = key;
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
	
	public BeanPage(String key, String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content) {
		super();
		this.key = key;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.content = content;
		this.subPages = new ArrayList<BeanPage>();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public List<BeanPage> getSubPages() {
		return subPages;
	}

	public void setSubPages(List<BeanPage> subPages) {
		this.subPages = subPages;
	}
}
