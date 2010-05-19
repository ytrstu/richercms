package com.sfeir.richercms.wizard.server.business;


import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;


/**
 * Represent the configuration of the site
 * without the language.
 * @author homberg.g
 */
@Entity(name="Configuration")
@Unindexed// or @Unindexed sets default for fields in class; if neither specified, assume @Indexed
public class Configuration {

	@Id
	private Long id;

	private boolean isConfigured;
	
	public Configuration() {
		super();
		this.isConfigured = false;
	}
	
	public Configuration(boolean isConfigured) {
		super();
		this.isConfigured = isConfigured;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isConfigured() {
		return isConfigured;
	}

	public void setConfigured(boolean isConfigured) {
		this.isConfigured = isConfigured;
	}
	
}
