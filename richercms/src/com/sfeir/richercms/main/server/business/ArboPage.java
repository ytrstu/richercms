package com.sfeir.richercms.main.server.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.IdentityType;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ArboPage {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    
	@Persistent
	@Element(dependent = "true")
	private List<TranslationPage> translation;
	
	@Persistent
	private List<String> idChildArboPage;
	
	@Persistent
	private Date publicationStart;
	
	@Persistent
	private Date publicationFinish;
	
	@Persistent
	private Date creationDate;
	
	//TODO List<Long> idTag;
	
	public ArboPage() {
		super();
		this.translation = new ArrayList<TranslationPage>();
		this.idChildArboPage = new ArrayList<String>();
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<TranslationPage> translation) {
		super();
		this.idChildArboPage = new ArrayList<String>();
		this.translation = translation;
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<TranslationPage> translation, Date publicationStart, Date publicationFinish) {
		super();
		this.idChildArboPage = new ArrayList<String>();
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = new Date();
	}
	
	public ArboPage(List<TranslationPage> translation, List<String> idChildArboPage) {
		super();
		this.idChildArboPage = idChildArboPage;
		this.translation = translation;
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<TranslationPage> translation, List<String> idChildArboPage,
			Date publicationStart, Date publicationFinish) {
		super();
		this.idChildArboPage = idChildArboPage;
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = new Date();
	}

	public String getEncodedKey() {
		return this.encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public List<TranslationPage> getTranslation() {
		return this.translation;
	}

	public void setTranslation(List<TranslationPage> translation) {
		this.translation = translation;
	}

	public List<String> getIdChildArboPage() {
		return this.idChildArboPage;
	}

	public void setIdChildArboPage(List<String> idChildArboPage) {
		this.idChildArboPage = idChildArboPage;
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
