package com.sfeir.richercms.page.server.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cached;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Cached
@Entity(name="Template")
@Unindexed
public class Template {

	@Id
    private Long id;
	@Indexed
	private List<Key<Tag>> associatedTags;
	private String name;
	private String description;
	
	public Template() {
		super();
		this.associatedTags = new ArrayList<Key<Tag>>();;
		this.name = "";
		this.description = "";
	}
	
	public Template(List<Key<Tag>> associatedTags, String name,
			String description) {
		super();
		this.associatedTags = associatedTags;
		this.name = name;
		this.description = description;
	}
	
	
	public Template(Long id, List<Key<Tag>> associatedTags, String name,
			String description) {
		super();
		this.id = id;
		this.associatedTags = associatedTags;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Key<Tag>> getAssociatedTags() {
		return associatedTags;
	}

	public void setAssociatedTags(List<Key<Tag>> associatedTags) {
		this.associatedTags = associatedTags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
