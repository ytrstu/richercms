package com.sfeir.richercms.page.shared;

import java.io.Serializable;

public class BeanFile implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String path;
	private String fileName;
	private String contentType;
	
	public BeanFile() {
		super();
		this.id = null;
		this.path = "";
		this.fileName = "";
		this.contentType = "";
	}
	
	public BeanFile(Long id, String path) {
		super();
		this.id = id;
		this.path = path;
		this.fileName = "";
		this.contentType = "";
	}
	
	public BeanFile(Long id, String path, String fileName, String contentType) {
		super();
		this.id = id;
		this.path = path;
		this.fileName = fileName;
		this.contentType = contentType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	
	
}
