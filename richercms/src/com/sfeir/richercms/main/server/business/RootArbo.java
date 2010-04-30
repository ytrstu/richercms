package com.sfeir.richercms.main.server.business;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class RootArbo {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
	@Persistent
	List<TranslationPage> translation;
	List<Long> idChildArboPage;
	//TODO List<Long> idTag;
	
	public RootArbo() {
		super();
		this.translation = new ArrayList<TranslationPage>();
		this.idChildArboPage = new ArrayList<Long>();
	}
	
	public RootArbo(List<TranslationPage> translation) {
		super();
		this.idChildArboPage = new ArrayList<Long>();
		this.translation = translation;
	}
	
	public RootArbo(List<TranslationPage> translation, List<Long> idChildArboPage) {
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

	public List<Long> getIdChildArboPage() {
		return this.idChildArboPage;
	}

	public void setIdChildArboPage(List<Long> idChildArboPage) {
		this.idChildArboPage = idChildArboPage;
	}
}
