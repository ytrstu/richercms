package com.sfeir.richercms.main.server.business;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ArboPage {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	@Persistent
	List<TranslationPage> translation;
	List<String> idChildArboPage;
	//TODO List<Long> idTag;
	
	public ArboPage() {
		super();
		this.translation = new ArrayList<TranslationPage>();
		this.idChildArboPage = new ArrayList<String>();
	}
	
	public ArboPage(List<TranslationPage> translation) {
		super();
		this.idChildArboPage = new ArrayList<String>();
		this.translation = translation;
	}
	
	public ArboPage(List<TranslationPage> translation, List<String> idChildArboPage) {
		super();
		this.idChildArboPage = idChildArboPage;
		this.translation = translation;
	}

	public String getEncodedKey() {
		return this.encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public List<TranslationPage> getTranslation() {
		return this.translation;
	}

	public void setTranslation(List<TranslationPage> translation) {
		this.translation = translation;
	}

	public List<String> getIdChildArboPage() {
		return this.idChildArboPage;
	}

	public void setIdChildArboPage(List<String> idChildArboPage) {
		this.idChildArboPage = idChildArboPage;
	}

}
