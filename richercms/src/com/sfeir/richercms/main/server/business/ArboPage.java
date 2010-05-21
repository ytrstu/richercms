package com.sfeir.richercms.main.server.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity(name="ArboPage")
@Unindexed
public class ArboPage {
	
    @Id
    private Long id;

	private List<Key<TranslationPage>> translation;
	
	private List<Long> idChildArboPage;
	
	private Date publicationStart;
	
	private Date publicationFinish;
	
	private Date creationDate;
	
	//TODO List<Long> idTag;
	
	public ArboPage() {
		super();
		this.translation = new ArrayList<Key<TranslationPage>>();
		this.idChildArboPage = new ArrayList<Long>();
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<Key<TranslationPage>> translation) {
		super();
		this.idChildArboPage = new ArrayList<Long>();
		this.translation = translation;
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<Key<TranslationPage>> translation, Date publicationStart, Date publicationFinish) {
		super();
		this.idChildArboPage = new ArrayList<Long>();
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = new Date();
	}
	
	public ArboPage(List<Key<TranslationPage>> translation, List<Long> idChildArboPage) {
		super();
		this.idChildArboPage = idChildArboPage;
		this.translation = translation;
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
	}
	
	public ArboPage(List<Key<TranslationPage>> translation, List<Long> idChildArboPage,
			Date publicationStart, Date publicationFinish) {
		super();
		this.idChildArboPage = idChildArboPage;
		this.translation = translation;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = new Date();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Key<TranslationPage>> getTranslation() {
		return this.translation;
	}

	public void setTranslation(List<Key<TranslationPage>> translation) {
		this.translation = translation;
	}

	public List<Long> getIdChildArboPage() {
		return this.idChildArboPage;
	}

	public void setIdChildArboPage(List<Long> idChildArboPage) {
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
