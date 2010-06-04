package com.sfeir.richercms.image.client;

import com.google.gwt.user.client.ui.Image;

public class Thumb extends Image{

	private Long id;
	private String path;
	
	public Thumb(){
		super();
		this.id = null;
		this.path = new String("");
	}
	
	public Thumb(String URL, Long id){
		super(URL);
		this.id = id;
		this.path = new String("");
	}
	
	public Thumb(String URL, Long id, String path){
		super(URL);
		this.id = id;
		this.path = path;
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

	public boolean isLinked() {
		if(this.path == null)
			return true;
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		//result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Thumb other = (Thumb) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		/*if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;*/
		return true;
	}
	
	
}
