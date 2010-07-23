package com.sfeir.richercms.page.server.business;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity(name="TranslationPage")
@Unindexed
public class TranslationPage {
	
	@Id
    private Long id;

	private String browserTitle;

	private String pageTitle;

	private String description;

	private String keyWord;

	private Text content;
	
	
	public TranslationPage()
	{
		this.browserTitle = new String("");
		this.pageTitle = new String("");
		this.description = new String("");
		this.keyWord = new String("");
		this.content = new Text("");
	}
	
	public TranslationPage(String browserTitle, String pageTitle,
			String description, String keyWord, String content) {
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.description = description;
		this.keyWord = keyWord;
		this.content = new Text(content);
	}
	
	public TranslationPage(Long id, String browserTitle, String pageTitle,
			String description, String keyWord, String content) {
		this.id = id;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.description = description;
		this.keyWord = keyWord;
		this.content = new Text(content);
	}

	public Long getId() {
		return this.id;
	}

	public void setEncodedKey(Long id) {
		this.id = id;
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
