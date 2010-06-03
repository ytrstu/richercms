package com.sfeir.richercms.image.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sfeir.richercms.image.client.Thumb;
import com.sfeir.richercms.image.client.interfaces.ILinkPanel;

public class LinkPanel  extends ResizeComposite implements ILinkPanel {

	private FlexTable unLinkImgPanel = null;
	private FlexTable leftLinkImgPanel = null;
	private static final String thumbnailUrl = GWT.getModuleBaseURL() + "thumbnail";
	private static final int IMAGE_HEIGHT = 100;
	private static final int IMAGE_WIDTH = 100;
	private final int NB_THUMB_PER_LINE = 4;
	private int nbCurrentRow = -1;
	private AbsolutePanel boundaryPanel = null;
	
	public Widget asWidget() {	
		return this;
	}
	
	/**
	 * Create the widget and attached all component
	 */
	public void createView() {
	    // use the boundary panel as this composite's widget
	    this.boundaryPanel = new AbsolutePanel();
	    this.boundaryPanel.setPixelSize(800,700);

		this.unLinkImgPanel = new FlexTable();
		this.unLinkImgPanel.addStyleName("unlinkThumb-table");
		ScrollPanel scrollUnLinkPanel = new ScrollPanel();
		
		this.leftLinkImgPanel = new FlexTable();
		this.leftLinkImgPanel.addStyleName("linkThumb-table");
		ScrollPanel scrollLeftLinkPanel = new ScrollPanel();
		
		scrollUnLinkPanel.add(this.unLinkImgPanel);
		scrollLeftLinkPanel.add(this.leftLinkImgPanel);
		
		boundaryPanel.add(scrollLeftLinkPanel,0,0);
		boundaryPanel.add(scrollUnLinkPanel,0,500);
		
		LayoutPanel p = new LayoutPanel();
		p.add(this.boundaryPanel);
		this.initWidget(p);
		this.addStyleName("image-zone");

	}
	
	@SuppressWarnings("static-access")
	public SimplePanel addUnlinkThumbnail(Long id){
		int col = 0;
		// create the thumb with the servlet
		Thumb thumb = new Thumb(this.thumbnailUrl+"?id="+id.toString(),id);
		
        // create a simple panel drop target for the current cell
        SimplePanel thumbcontainer = new SimplePanel();
        thumbcontainer.setPixelSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        thumbcontainer.setWidget(thumb);
        
        if(this.unLinkImgPanel.getRowCount() == 0)
        	col = 0;
        else
        	col = this.unLinkImgPanel.getCellCount(0);
        
        this.unLinkImgPanel.setWidget(0,col,thumbcontainer);
        this.unLinkImgPanel.getCellFormatter().setStyleName(0,col,"unlinkThumb-cell");
        
        return thumbcontainer;
	}

	@SuppressWarnings("static-access")
	public SimplePanel addThumbnail(Long id) {
		int line = 0;
		
		if(this.nbCurrentRow != -1 )
			line = this.leftLinkImgPanel.getCellCount(0);
		
		if((this.nbCurrentRow == -1) || (this.nbCurrentRow == this.NB_THUMB_PER_LINE))
			this.nbCurrentRow = 0;
	
			
        SimplePanel thumbcontainer = new SimplePanel();
        thumbcontainer.setPixelSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        
        if(id!=null){
        	// create the thumb with the servlet
        	Thumb thumb = new Thumb(this.thumbnailUrl+"?id="+id.toString(),id);
            thumbcontainer.setWidget(thumb);
        }
        
        this.leftLinkImgPanel.setWidget(line,this.nbCurrentRow,thumbcontainer);
        this.leftLinkImgPanel.getCellFormatter().setStyleName(line,this.nbCurrentRow,"linkThumb-cell");
                
        this.nbCurrentRow ++;
        
       return thumbcontainer;
	}
	
	public AbsolutePanel getBoundaryPanel() {
		return this.boundaryPanel;
	}

	public void clearElement(){
		this.leftLinkImgPanel.clear();
		this.leftLinkImgPanel.removeAllRows();
		this.unLinkImgPanel.clear();
		this.unLinkImgPanel.removeAllRows();
		this.nbCurrentRow =-1;
	}
}
