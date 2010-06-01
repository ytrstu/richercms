package com.sfeir.richercms.image.client.event;

import com.mvp4g.client.Mvp4gModule;
import com.sfeir.richercms.page.client.event.PageModule;

/**
 * Interface needed for the mvp4g multi-module system
 * @author homberg.g
 *
 */
public interface ImageMobule extends Mvp4gModule{
	public void setParentModule( PageModule parentModule);
}
