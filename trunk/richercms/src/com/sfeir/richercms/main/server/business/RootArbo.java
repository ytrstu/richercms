package com.sfeir.richercms.main.server.business;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class RootArbo {
	
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
    private String encodedKey;
    
	@Persistent
	private String keyOfRootArboPage;
	
	//TODO List<Long> idTag;
	
	public RootArbo() {
		this.keyOfRootArboPage = null;
	}
	
	public RootArbo(String keyOfRootArboPage) {
		this.keyOfRootArboPage = keyOfRootArboPage;
	}

	public String getEncodedKey() {
		return this.encodedKey;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getKeyOfRootArboPage() {
		return keyOfRootArboPage;
	}

	public void setKeyOfRootArboPage(String keyOfRootArboPage) {
		this.keyOfRootArboPage = keyOfRootArboPage;
	}
}
