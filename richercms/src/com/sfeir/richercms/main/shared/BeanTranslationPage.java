package com.sfeir.richercms.main.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanTranslationPage implements Serializable {
	
    private String encodedKey;
	private String browserTitle;
	private String pageTitle;
	private String urlName;
	private String description;
	private String keyWord;
	private String content;
	
	public BeanTranslationPage() {
		super();
		this.encodedKey = null;
		this.browserTitle = "";
		this.pageTitle = "";
		this.urlName = "";
		this.description = "";
		this.keyWord = "";
		this.content = "";
	}
	
	public BeanTranslationPage(String browserTitle,
			String pageTitle, String urlName, String description,
			String keyWord, String content) {
		super();
		this.encodedKey = null;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.content = content;
	}
	
	public BeanTranslationPage(String encodedKey, String browserTitle,
			String pageTitle, String urlName, String description,
			String keyWord, String content) {
		super();
		this.encodedKey = encodedKey;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.content = content;
	}

	public String getEncodedKey() {
		return this.encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getBrowserTitle() {
		return this.browserTitle;
	}

	public void setBrowserTitle(String browserTitle) {
		this.browserTitle = browserTitle;
	}

	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}
