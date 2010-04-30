package com.sfeir.richercms.main.server.business;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class TranslationPage {
	
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
	
	
	public TranslationPage()
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
	
	public TranslationPage(String browserTitle, String pageTitle, String urlName,
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
	
	public TranslationPage(String key, String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String publicationStart,
			String publicationFinish, String content) {
		this.encodedKey = key;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
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

	public String getPublicationStart() {
		return this.publicationStart;
	}

	public void setPublicationStart(String publicationStart) {
		this.publicationStart = publicationStart;
	}

	public String getPublicationFinish() {
		return this.publicationFinish;
	}

	public void setPublicationFinish(String publicationFinish) {
		this.publicationFinish = publicationFinish;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
