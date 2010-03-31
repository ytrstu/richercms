package wizardConfig.client.presenter;

import wizardConfig.client.Interface.IrootDisplay;
import wizardConfig.client.event.WizardConfigEventBus;
import wizardConfig.client.view.RootView;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;


/**
 * Presenter of the Main display, using to switch view
 * Replace appControler in the MVP model
 * @author homberg.g
 */
@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IrootDisplay, WizardConfigEventBus> {

	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}
	
	/**
	 * empty, wait to display a view
	 */
	public void onStartWizard() {}

}
