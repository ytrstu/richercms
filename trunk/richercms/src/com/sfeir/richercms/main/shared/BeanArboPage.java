package com.sfeir.richercms.main.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class BeanArboPage implements Serializable{
	
    private String encodedKey;
	List<BeanTranslationPage> translation;
	
	
	public BeanArboPage() {
		super();
		this.encodedKey = null;
		this.translation = new ArrayList<BeanTranslationPage>();
	}
	
	public BeanArboPage(String encodedKey) {
		super();
		this.encodedKey = encodedKey;
		this.translation = new ArrayList<BeanTranslationPage>();
	}
	
	public BeanArboPage(List<BeanTranslationPage> translation) {
		super();
		this.encodedKey = null;
		this.translation = translation;
	}
	
	public BeanArboPage(String encodedKey, List<BeanTranslationPage> translation) {
		super();
		this.encodedKey = encodedKey;
		this.translation = translation;
	}
	
	public String getEncodedKey() {
		return encodedKey;
	}
	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}
	public List<BeanTranslationPage> getTranslation() {
		return translation;
	}
	public void setTranslation(List<BeanTranslationPage> translation) {
		this.translation = translation;
	}
	
	
}
