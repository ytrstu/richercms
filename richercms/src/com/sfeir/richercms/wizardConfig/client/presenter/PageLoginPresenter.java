package com.sfeir.richercms.wizardConfig.client.presenter;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.wizardConfig.client.LanguageServiceAsync;
import com.sfeir.richercms.wizardConfig.client.UserInfoService;
import com.sfeir.richercms.wizardConfig.client.UserInfoServiceAsync;
import com.sfeir.richercms.wizardConfig.client.Interface.IdisplayPageLogin;
import com.sfeir.richercms.wizardConfig.client.event.WizardConfigEventBus;
import com.sfeir.richercms.wizardConfig.client.view.PageLoginView;
import com.sfeir.richercms.wizardConfig.shared.BeanUserInfo;

/**
 * Presenter of the home page
 * @author homberg.g
 */
@Presenter( view = PageLoginView.class)
public class PageLoginPresenter extends LazyPresenter<IdisplayPageLogin, WizardConfigEventBus> {
	
	// Initialized by the mvp4g framework
	private UserInfoServiceAsync rpcLoginService = null;
	
	/**
	 * Bind the various evt
	 * It's Mvp4g framework who call this function
	 */ 
	public void bindView() {
	
	}
	
	public void onLogin() {
		
		rpcLoginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<BeanUserInfo>() {
			public void onFailure(Throwable error) {
				Window.alert("Connection for login failed");
			}

			public void onSuccess(BeanUserInfo result) {
				if(result.isLoggedIn()) {
					eventBus.startWizard();
				}
				else {
					view.getSignInLink().setHref(result.getLoginUrl());
					eventBus.changeBody(view.asWidget());
				}
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

}
