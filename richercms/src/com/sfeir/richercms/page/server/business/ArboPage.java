package com.sfeir.richercms.page.server.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.PostLoad;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity(name="ArboPage")
@Unindexed
public class ArboPage {
	
    @Id
    private Long id;

    @Indexed 
	private List<Key<TranslationPage>> translation;
	
	@Indexed 
	private List<Long> idChildArboPage;
	
	private Date publicationStart;
	
	private Date publicationFinish;
	
	private Date creationDate;
	
	@Indexed
	private String urlName;
	
	// One page Use One Template
	@Indexed 
	private Long templateId;

	// One page Use Many tags and witch
	// may be include in the specific Template
	@Indexed 
	private List<Long> tagsId;
	
	@Indexed 
	private Long idUserInModif;
	
	@Indexed
	private Long parentId; // Parent id
	
	public ArboPage() {
		super();
		this.translation = new ArrayList<Key<TranslationPage>>();
		this.idChildArboPage = new ArrayList<Long>();
		this.publicationStart = new Date();
		this.publicationFinish = new Date();
		this.creationDate = new Date();
		this.idUserInModif = new Long(-1);
		this.templateId = new Long(-1);
		this.tagsId = new ArrayList<Long>();
		this.parentId = null;
		this.urlName = "";
	}
	
	public ArboPage(Long id, String urlName, Date publicationStart,Date publicationFinish,
			Date creationDate,Long templateId,List<Long> tagsId, Long parentId) {
		super();
		this.id = id;
		this.translation = new ArrayList<Key<TranslationPage>>();
		this.idChildArboPage = new ArrayList<Long>();
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = creationDate;
		this.idUserInModif = new Long(-1);
		this.templateId = templateId;
		this.tagsId = tagsId;
		this.parentId = parentId;
		this.urlName = urlName;
	}

	public ArboPage(List<Key<TranslationPage>> translation,
			List<Long> idChildArboPage, String urlName, Date publicationStart,
			Date publicationFinish, Date creationDate, Long templateId,
			List<Long> tagsId, Long idUserInModif, Long parentId) {
		super();
		this.translation = translation;
		this.idChildArboPage = idChildArboPage;
		this.publicationStart = publicationStart;
		this.publicationFinish = publicationFinish;
		this.creationDate = creationDate;
		this.templateId = templateId;
		this.tagsId = tagsId;
		this.idUserInModif = idUserInModif;
		this.parentId = parentId;
		this.urlName = urlName;
	}

	@PostLoad
	public void trackPostLoad() { 
		if (this.idChildArboPage == null) {
			this.idChildArboPage = new ArrayList<Long>();
		}
		if (this.tagsId==null) {
			this.tagsId = new ArrayList<Long>();
		}
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

	public Long getIdUserInModif() {
		return idUserInModif;
	}

	public void setIdUserInModif(Long idUserInModif) {
		this.idUserInModif = idUserInModif;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public List<Long> getTagsId() {
		return this.tagsId;
	}

	public void setTagsId(List<Long> tagsId) {
		this.tagsId = tagsId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
}
