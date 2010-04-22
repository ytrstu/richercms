package com.sfeir.richercms.client.presenter;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.RootEventBus;
import com.sfeir.richercms.client.UserInfoServiceAsync;
import com.sfeir.richercms.client.interfaces.IdisplayPageLogin;
import com.sfeir.richercms.client.view.PageLoginView;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.shared.BeanUserInfo;
import com.sfeir.richercms.wizard.client.ConfigurationServiceAsync;

/**
 * Presenter of the home page
 * @author homberg.g
 */
@Presenter( view = PageLoginView.class)
public class PageLoginPresenter extends LazyPresenter<IdisplayPageLogin, RootEventBus> {
	
	// Initialized by the mvp4g framework
	private UserInfoServiceAsync rpcLoginService = null;
	private ConfigurationServiceAsync rpcConfigurationService = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
	
	}
	
	public void onLogin() {
		this.view.showPopUpWait();
		this.rpcLoginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<BeanUserInfo>() {
			public void onFailure(Throwable error) {
				PopUpMessage p = new PopUpMessage("Connection for login failed");
				p.show();
			}

			public void onSuccess(BeanUserInfo result) {
				//on test si il est loggé ou s'il faut le faire passer par la page de login
				if(result.isLoggedIn()) {
					//on test pour voir si il faut lancer le wizard ou alord directement le main
					rpcConfigurationService.SiteIsConfigured(new AsyncCallback<Boolean>() {
						public void onFailure(Throwable error) {
							PopUpMessage p = new PopUpMessage("Connection test confguration failed");
							p.show();
						}

						public void onSuccess(Boolean result) {
							if(result.booleanValue())
								eventBus.startMain();
							else
								eventBus.startWizard();
						}
					});	
				}
				else {
					view.getSignInLink().setHref(result.getLoginUrl());
					eventBus.changeBody(view.asWidget());
				}
				view.hidePopUpWait();
			}
		});
		
	}
	
	/**
	 * used by the framework to instantiate rpcLoginService 
	 * @param rpcLoginService
	 */
	@InjectService
	public void setUserService( UserInfoServiceAsync rpcLoginService ) {
		this.rpcLoginService = rpcLoginService;
	}
	
	@InjectService
	public void setConfigurationService( ConfigurationServiceAsync rpcConfigurationService ) {
		this.rpcConfigurationService = rpcConfigurationService;
	}

}
