package com.sfeir.richercms.main.server.business;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datastore.Text;

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
	private Text content;
	
	
	public TranslationPage()
	{
		this.browserTitle = new String("");
		this.pageTitle = new String("");
		this.urlName = new String("");
		this.description = new String("");
		this.keyWord = new String("");
		this.content = new Text("");
	}
	
	public TranslationPage(String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String content) {
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.content = new Text(content);
	}
	
	public TranslationPage(String key, String browserTitle, String pageTitle, String urlName,
			String description, String keyWord, String content) {
		this.encodedKey = key;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.urlName = urlName;
		this.description = description;
		this.keyWord = keyWord;
		this.content = new Text(content);
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

	public Text getContent() {
		return this.content;
	}

	public void setContent(Text content) {
		this.content = content;
	}
}
