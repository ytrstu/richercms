package com.sfeir.richercms.page.client.view.custom.thumb;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasMouseDownHandlers;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.HasMouseOverHandlers;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.sfeir.richercms.page.client.PageConstants;

public class ThumbAndBtn extends LayoutPanel implements HasMouseOverHandlers, HasMouseOutHandlers{

	//gestion des langues
	private PageConstants constants = GWT.create(PageConstants.class);
	private Thumb img;
	private Image btn;
	private int width = 100;
	private int height = 100;
	
	public ThumbAndBtn(){
		super();
		this.img = new Thumb();
		setUp();
	}
	
	public ThumbAndBtn(String URL, Long id){
		super();
		this.img = new Thumb(URL, id);
		setUp();
	}
	
	public ThumbAndBtn(String URL, Long id, String path){
		super();
		this.img = new Thumb(URL, id, path);
		setUp();
	}
	
	/**
	 * 
	 * @param URL : URL needed to display the thumb
	 * @param id : id in datastore of the picutes
	 * @param path : path of the image corresponding to the thumb
	 * @param width : width of the thumb. this constructor add Xpx for display the button
	 * @param height : height of the thumb.
	 */
	public ThumbAndBtn(String URL, Long id, String path, int width, int height){
		super();
		this.img = new Thumb(URL, id, path);
		this.width = width;
		this.height = height;
		setUp();
	}
	
	private void setUp() {
		this.img.setTitle(this.constants.MsgClick2DisplayIRS());
		this.addStyleName("thumb");
		this.btn = new Image("tab_images/delete-Thumb.png");
		this.btn.setTitle(this.constants.MsgClick2DeleteImg());
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
		
		this.img.addLoadHandler(new LoadHandler(){

			public void onLoad(LoadEvent event) {
				int spacingWidth = width - img.getWidth();
				ThumbAndBtn.this.setWidgetLeftWidth(img, spacingWidth/2, Unit.PX, img.getWidth(), Unit.PX);
				int spacingheight = height - img.getHeight();
				ThumbAndBtn.this.setWidgetTopHeight(img, spacingheight/2, Unit.PX, img.getHeight(), Unit.PX);
				
				ThumbAndBtn.this.setWidgetRightWidth(btn, spacingWidth/2-8, Unit.PX, 30, Unit.PX);
				ThumbAndBtn.this.setWidgetTopHeight(btn, spacingheight/2-8, Unit.PX, 30, Unit.PX);
			}
		});
		
		this.add(this.img);
		this.add(this.btn);
		this.setHeight(this.height+"px");
		this.setWidth(this.width+"px");
	}
	
	public HandlerRegistration addMouseOverHandler(MouseOverHandler handler) {
		return addDomHandler(handler, MouseOverEvent.getType());
	}

	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}
	
	public HasClickHandlers btnClickEvent() {
		return this.btn;
	}
	
	public HasMouseDownHandlers imageClickEvent() {
		return this.img;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}