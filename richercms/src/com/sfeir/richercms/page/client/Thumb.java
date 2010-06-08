package com.sfeir.richercms.page.client;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.sfeir.richercms.page.client.view.custom.HorizontalEventPanel;

public class Thumb extends HorizontalEventPanel {

	private Long id;
	private String path;
	private Image img;
	private Button btn;
	
	public Thumb(){
		super();
		this.id = null;
		this.path = new String("");
		setUp("");
	}
	
	public Thumb(String URL, Long id){
		super();
		this.id = id;
		this.path = new String("");
		
		setUp(URL);
	}
	
	public Thumb(String URL, Long id, String path){
		super();
		this.id = id;
		this.path = path;
		
		setUp(URL);
	}
	
	private void setUp(String URL) {

		this.addStyleName("thumb");
		this.img = new Image(URL);	
		this.btn = new Button("-");
		this.btn.setVisible(false);
		
		this.addMouseOverHandler(new MouseOverHandler(){
			public void onMouseOver(MouseOverEvent event) {
				if(!btn.isVisible())
					btn.setVisible(true);
			}
		});
		this.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				if(btn.isVisible())
					btn.setVisible(false);
			}
		});
		
		this.add(this.img);
		this.add(this.btn);
		
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
		return true;
	}
	
	public HasClickHandlers btnClickEvent() {
		return this.btn;
	}
	
	public HasClickHandlers imageClickEvent() {
		return this.img;
	}
	
}
