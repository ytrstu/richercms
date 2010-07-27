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
import com.sfeir.richercms.shared.BeanUser;
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
					// on test si l'utilisateur est bien inscrit dans la base de donnée ou non
					connection(result);
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
	 * Verify if user are authorized to connect in administration interface
	 * @param usrInfo : user info
	 */
	private void connection(final BeanUserInfo usrInfo) {
		rpcLoginService.isAutorized(usrInfo, new AsyncCallback<BeanUser>() {
			public void onFailure(Throwable caught) {

			}
			public void onSuccess(BeanUser result) {
				// si non null alors tout est ok pour lancer l'application
				if(result!= null)
					startRicherCMS(result);
				else{
					view.notAuthorized(usrInfo.getEmailAddress(),usrInfo.getLogoutUrl());
					eventBus.changeBody(view.asWidget());
				}
			}
		});
	}
	
	/**
	 * Start wizard module or main module 
	 * depend of configuation value
	 * @param usr : user
	 */
	private void startRicherCMS(final BeanUser usr) {
		
		//on test pour voir si il faut lancer le wizard ou alord directement le main
		rpcConfigurationService.SiteIsConfigured(new AsyncCallback<Boolean>() {
			public void onFailure(Throwable error) {
				PopUpMessage p = new PopUpMessage("Connection test confguration failed");
				p.show();
			}

			public void onSuccess(Boolean result) {
				eventBus.setUsr(usr);
				if(result.booleanValue())		
					eventBus.startMain(usr);
				else
					eventBus.startWizard();
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
