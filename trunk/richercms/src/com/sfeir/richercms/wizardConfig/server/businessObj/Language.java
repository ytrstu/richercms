package com.sfeir.richercms.wizardConfig.server.businessObj;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


/**
 * Represent a language
 * @author homberg.g
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Language {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String langue;
	@Persistent
	private boolean selected;
	
	
	public Language() {
		this.langue = "";
		this.selected = false;
	}

	public Language(String langue) {
		this.langue = langue;
		this.selected = false;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLangue() {
		return this.langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
