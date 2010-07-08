package com.sfeir.richercms.page.server.business;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
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
    private Key<Tag> dependentTag;
    private String customName;
    
	public DependentTag() {
		super();
		this.dependentTag = null;
		this.customName = "";
	}
	
	public DependentTag(Key<Tag> dependentTag, String customName) {
		super();
		this.dependentTag = dependentTag;
		this.customName = customName;
	}
    
	public DependentTag(Long id, Key<Tag> dependentTag, String customName) {
		super();
		this.id = id;
		this.dependentTag = dependentTag;
		this.customName = customName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Key<Tag> getDependentTag() {
		return dependentTag;
	}

	public void setDependentTag(Key<Tag> dependentTag) {
		this.dependentTag = dependentTag;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
}
