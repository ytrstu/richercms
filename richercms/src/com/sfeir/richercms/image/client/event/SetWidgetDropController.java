package com.sfeir.richercms.image.client.event;

import com.google.gwt.user.client.ui.SimplePanel;
import com.sfeir.richercms.page.client.view.custom.thumb.Thumb;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;

/**
 * DropController which allows a widget to be dropped on a SimplePanel drop target when the drop
 * target does not yet have a child widget.
 */
public class SetWidgetDropController extends SimpleDropController {

  private final SimplePanel dropTarget;
  private ImageEventBus eventBus;

  public SetWidgetDropController(SimplePanel dropTarget, ImageEventBus eventBus) {
    super(dropTarget);
    this.eventBus = eventBus;
    this.dropTarget = dropTarget;
  }
  
  public SetWidgetDropController(SimplePanel dropTarget, String path) {
	    super(dropTarget);
	    this.dropTarget = dropTarget;
	  }

  public void onDrop(DragContext context) {
	  Thumb img =(Thumb)context.draggable;
	  img.setPath("test");
	dropTarget.setWidget(context.draggable);
	this.eventBus.linkAndAddSlot();

	super.onDrop(context);

  }

  public void onPreviewDrop(DragContext context) throws VetoDragException {
    if (dropTarget.getWidget() != null) {
      throw new VetoDragException();
    }
    super.onPreviewDrop(context);
  }

}
