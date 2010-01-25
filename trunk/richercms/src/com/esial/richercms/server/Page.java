package com.esial.richercms.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Page {
	
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String unique_name;
	
	public long getId() {
		return id;
	}

	public String getUnique_name() {
		return unique_name;
	}

	public void setUnique_name(String uniqueName) {
		unique_name = uniqueName;
	}

	public Page(String unique_name){
		this.unique_name=unique_name;
	}

}
