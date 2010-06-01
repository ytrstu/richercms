package com.sfeir.richercms.image.server.business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Unindexed;

@Entity(name="UnlinkedFile")
@Unindexed
public class UnlinkedFile {
    @Id
    private Long id;
    
    private List<Long> idUnlinkedImg;

	public UnlinkedFile() {
		super();
		this.id = null;
		this.idUnlinkedImg = new ArrayList<Long>();
	}
    
	public UnlinkedFile(List<Long> idUnlinkedImg) {
		super();
		this.idUnlinkedImg = idUnlinkedImg;
	}
	
	public UnlinkedFile(Long id, List<Long> idUnlinkedImg) {
		super();
		this.id = id;
		this.idUnlinkedImg = idUnlinkedImg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getIdUnlinkedImg() {
		return idUnlinkedImg;
	}

	public void setIdUnlinkedImg(List<Long> idUnlinkedImg) {
		this.idUnlinkedImg = idUnlinkedImg;
	}
}
