package com.sfeir.richercms.page.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BeanTemplate implements Serializable {


    private Long id;
	private List<BeanTag> associatedTags;
	private String name;
	private String description;
	
	public BeanTemplate() {
		super();
		this.associatedTags = new ArrayList<BeanTag>();
		this.name = "";
		this.description = "";
	}
	
	public BeanTemplate(List<BeanTag> associatedTags, String name,
			String description) {
		super();
		this.associatedTags = associatedTags;
		this.name = name;
		this.description = description;
	}
	
	
	public BeanTemplate(Long id, List<BeanTag> associatedTags, String name,
			String description) {
		super();
		this.id = id;
		this.associatedTags = associatedTags;
		this.name = name;
		this.description = description;
	}
	
	public BeanTemplate(Long id, String name,
			String description) {
		super();
		this.id = id;
		this.associatedTags = new ArrayList<BeanTag>();
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<BeanTag> getAssociatedTags() {
		return associatedTags;
	}

	public void setAssociatedTags(List<BeanTag> associatedTags) {
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
