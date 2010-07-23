package com.sfeir.richercms.page.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanTranslationPage implements Serializable {
	
    private Long id;
	private String browserTitle;
	private String pageTitle;
	private String description;
	private String keyWord;
	private String content;
	
	public BeanTranslationPage() {
		super();
		this.id = null;
		this.browserTitle = "";
		this.pageTitle = "";
		this.description = "";
		this.keyWord = "";
		this.content = "";
	}
	
	public BeanTranslationPage(String browserTitle,
			String pageTitle, String description,
			String keyWord, String content) {
		super();
		this.id = null;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.description = description;
		this.keyWord = keyWord;
		this.content = content;
	}
	
	public BeanTranslationPage(Long id, String browserTitle,
			String pageTitle, String description,
			String keyWord, String content) {
		super();
		this.id = id;
		this.browserTitle = browserTitle;
		this.pageTitle = pageTitle;
		this.description = description;
		this.keyWord = keyWord;
		this.content = content;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
