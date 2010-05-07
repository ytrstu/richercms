package com.sfeir.richercms.main.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BeanArboPage implements Serializable{
	
    private String encodedKey;
	List<BeanTranslationPage> translation;
	private Date publicationStart;
	private Date publicationFinish;
	
	public BeanArboPage() {
		super();
		this.encodedKey = null;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
	}
	
	public BeanArboPage(String encodedKey) {
		super();
		this.encodedKey = encodedKey;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
	}
	
	public BeanArboPage(String encodedKey, Date publicationStart, Date publicationFinish) {
		super();
		this.encodedKey = encodedKey;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = publicationFinish;
		this.publicationStart = publicationStart;
	}
	
	public BeanArboPage(List<BeanTranslationPage> translation) {
		super();
		this.encodedKey = null;
		this.translation = translation;
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
	}
	
	public BeanArboPage(String encodedKey, List<BeanTranslationPage> translation) {
		super();
		this.encodedKey = encodedKey;
		this.translation = translation;
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
	}
	
	public BeanArboPage(String encodedKey, List<BeanTranslationPage> translation,
			Date publicationStart, Date publicationFinish) {
		super();
		this.encodedKey = encodedKey;
		this.translation = translation;
		this.publicationFinish = publicationFinish;
		this.publicationStart = publicationStart;
	}
	
	public String getEncodedKey() {
		return encodedKey;
	}
	
	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	
	public List<BeanTranslationPage> getTranslation() {
		return translation;
	}
	
	public void setTranslation(List<BeanTranslationPage> translation) {
		this.translation = translation;
	}

	public Date getPublicationStart() {
		return publicationStart;
	}

	public void setPublicationStart(Date publicationStart) {
		this.publicationStart = publicationStart;
	}

	public Date getPublicationFinish() {
		return publicationFinish;
	}

	public void setPublicationFinish(Date publicationFinish) {
		this.publicationFinish = publicationFinish;
	}
	
	
	
	
}
