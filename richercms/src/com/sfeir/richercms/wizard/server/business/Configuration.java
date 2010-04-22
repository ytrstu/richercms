package com.sfeir.richercms.wizard.server.business;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Represent the configuration of the site
 * without the language.
 * @author homberg.g
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Configuration {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
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
