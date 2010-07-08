package com.sfeir.richercms.page.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BeanDependentTag implements Serializable{

    private Long id;
    private BeanTag dependentTag;
    private String customName;
    
	public BeanDependentTag() {
		super();
		this.dependentTag = null;
		this.customName = "";
	}
	
	public BeanDependentTag(BeanTag dependentTag, String customName) {
		super();
		this.dependentTag = dependentTag;
		this.customName = customName;
	}
    
	public BeanDependentTag(Long id, BeanTag dependentTag, String customName) {
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

	public BeanTag getDependentTag() {
		return dependentTag;
	}

	public void setDependentTag(BeanTag dependentTag) {
		this.dependentTag = dependentTag;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
}
