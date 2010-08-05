package com.sfeir.richercms.page.server.business;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity(name="DependentTag")
@Unindexed
public class DependentTag {

    @Id
    private Long id;
    @Indexed
    private Long pageId;
    @Indexed
    private Long correspTagId;
    private String customName;
    
	public DependentTag() {
		super();
		this.correspTagId = null;
		this.customName = "";
		this.pageId = null;
	}
	
	public DependentTag(Long pageId, Long tagId, String customName) {
		super();
		this.correspTagId = tagId;
		this.customName = customName;
		this.pageId = pageId;
	}
    
	public DependentTag(Long id, Long pageId, Long tagId, String customName) {
		super();
		this.id = id;
		this.correspTagId = tagId;
		this.customName = customName;
		this.pageId = pageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCorrespTagId() {
		return this.correspTagId;
	}

	public void setCorrespTagId(Long tagId) {
		this.correspTagId = tagId;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
}
