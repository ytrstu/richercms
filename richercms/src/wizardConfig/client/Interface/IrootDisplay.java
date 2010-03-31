package wizardConfig.client.Interface;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Allows the Root presenter to communicate with the Root view
 * @author homberg.g
 */
public interface IrootDisplay 
{
	/**
	 * Return the rootLayoutPanel, where is displayed differents view
	 * @return the rootLayoutPanel
	 */
	public Panel getBody();
	
	/**
	 * Return the Main Composite widget where the rootLayoutPanel is attached. 
	 * @return a Composite where the rootLayoutPanel is attached
	 */
	public Widget getViewWidget();
}
