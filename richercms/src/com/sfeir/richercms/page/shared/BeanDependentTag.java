package com.sfeir.richercms.page.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanDependentTag implements Serializable{

    private Long id;
    private Long pageId;
    private Long dependentTagId;
    private String customName;
    
	public BeanDependentTag() {
		super();
		this.dependentTagId = null;
		this.customName = "";
	}
	
	public BeanDependentTag(Long dependentTagId, String customName, Long pageId) {
		super();
		this.dependentTagId = dependentTagId;
		this.customName = customName;
		this.pageId = pageId;
	}
    
	public BeanDependentTag(Long id, Long dependentTagId, String customName, Long pageId) {
		super();
		this.id = id;
		this.dependentTagId = dependentTagId;
		this.customName = customName;
		this.pageId = pageId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDependentTagId() {
		return dependentTagId;
	}

	public void setDependentTagId(Long dependentTagId) {
		this.dependentTagId = dependentTagId;
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
