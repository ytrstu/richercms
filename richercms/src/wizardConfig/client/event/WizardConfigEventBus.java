package wizardConfig.client.event;

import wizardConfig.client.presenter.Page1Presenter;
import wizardConfig.client.presenter.Page2Presenter;
import wizardConfig.client.presenter.RootPresenter;
import wizardConfig.client.view.RootView;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;


/**
 * EventBus Interface :
 * Contains the prototype of each event,
 * and annotations necessary to map events and 
 * classes that implement the function to launch
 * 
 * @author homberg.g
 */
@Events(startView = RootView.class)
public interface WizardConfigEventBus extends EventBus{

	/**
	 * Change view : Page1 => Page2
	 */
	@Event(handlers=Page2Presenter.class)
	public void GoToSecondPage();
	
	/**
	 * Display the new view in the rootLayout
	 * @param widget : the new view
	 */
	@Event( handlers = RootPresenter.class )
	public void changeBody( Widget widget );
	
	
	/**
	 * Start the rootLayout and display the first page.
	 * 2 presenter are started : RootPresenter and Page1Presenter
	 */
	@Start
	@Event( handlers = {RootPresenter.class, Page1Presenter.class} )
	public void startWizard();
}
