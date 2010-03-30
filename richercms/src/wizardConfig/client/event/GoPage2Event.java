package wizardConfig.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class GoPage2Event extends GwtEvent<GoPage2EventHandler> {
	
	public static Type<GoPage2EventHandler> TYPE = new Type<GoPage2EventHandler>();

	protected void dispatch(GoPage2EventHandler handler) {
		
		handler.onGoPage2(this);
	}

	public Type<GoPage2EventHandler> getAssociatedType() {
		
		return TYPE;
	}
}
