package com.sfeir.richercms.main.server.business;


import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity(name="RootArbo")
public class RootArbo {
	
	@Id
    private Long id;

	private Long idOfRootArboPage;
	
	public RootArbo() {
		this.idOfRootArboPage = null;
	}
	
	public RootArbo(Long idOfRootArboPage) {
		this.idOfRootArboPage = idOfRootArboPage;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdOfRootArboPage() {
		return idOfRootArboPage;
	}

	public void setIdOfRootArboPage(Long idOfRootArboPage) {
		this.idOfRootArboPage = idOfRootArboPage;
	}
}
