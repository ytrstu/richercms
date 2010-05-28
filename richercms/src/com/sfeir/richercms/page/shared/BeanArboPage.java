package com.sfeir.richercms.page.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class BeanArboPage implements Serializable{
	
    private Long id;
	List<BeanTranslationPage> translation;
	private Date publicationStart;
	private Date publicationFinish;
	private Date creationDate;
	
	public BeanArboPage() {
		super();
		this.id = null;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
	}
	
	public BeanArboPage(Long id) {
		super();
		this.id = id;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
	}
	
	public BeanArboPage(Long id, Date publicationStart, Date publicationFinish, Date creationDate) {
		super();
		this.id = id;
		this.translation = new ArrayList<BeanTranslationPage>();
		this.publicationFinish = publicationFinish;
		this.publicationStart = publicationStart;
		this.creationDate = creationDate;
	}
	
	public BeanArboPage(List<BeanTranslationPage> translation) {
		super();
		this.id = null;
		this.translation = translation;
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
	}
	
	public BeanArboPage(Long id, List<BeanTranslationPage> translation) {
		super();
		this.id = id;
		this.translation = translation;
		this.publicationFinish = new Date();
		this.publicationStart = new Date();
		this.creationDate = new Date();
	}
	
	public BeanArboPage(Long id, List<BeanTranslationPage> translation,
			Date publicationStart, Date publicationFinish) {
		super();
		this.id = id;
		this.translation = translation;
		this.publicationFinish = publicationFinish;
		this.publicationStart = publicationStart;
		this.creationDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
