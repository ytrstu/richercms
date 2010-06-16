package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.UserInfoServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.view.UserManager;
import com.sfeir.richercms.shared.BeanUser;


@Presenter( view = UserManager.class)
public class UserManagerPresenter extends LazyPresenter<IUserManager, PageEventBus>{


	private UserInfoServiceAsync rpcUser = null;
	
	public void bindView() {
		
		this.view.onAddNewUserClick().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.showInformationPopUp();
				if(view.getNewEmail().length() == 0){
					eventBus.addErrorLinePopUp("Attention le champs email est vide");
					eventBus.hideInformationPopUp();
				}else {
					eventBus.addWaitLinePopUp("Ajout de l'utilisateur ...");
					rpcUser.addUser(view.getNewEmail(), new AsyncCallback<Long>() {
						public void onFailure(Throwable caught) {
							eventBus.addErrorLinePopUp("erreur lié à l'ajout");
							eventBus.hideInformationPopUp();
						}
						public void onSuccess(Long result) {
							if(result == null){
								eventBus.addErrorLinePopUp("l'utilisateur est déjà enregistrer");
							}else {
								eventBus.addSuccessPopUp("Ajout Réussi!");
								view.addLine(view.getNewEmail(), view.getNewEmail(), "OffLine", false);
								view.clearAddUserTextBox();
							}
							eventBus.hideInformationPopUp();
						}
					});
				}
			}
		});
	}
	
	public void onStartUserManager() {
		this.eventBus.displayUserManager(this.view);
		fetchUserTable();
	}
	
	private void fetchUserTable() {
		view.clearUserTable();
		this.rpcUser.getUsers(new AsyncCallback<List<BeanUser>>() {
			public void onSuccess(List<BeanUser> result) {	
				for(BeanUser usr : result){
					String state = "Offline";
					
					if(usr.isLoggedIn())
						state = "OnLine";
						
					view.addLine(usr.getEmailAddress(),
								usr.getNickname(), 
								state, 
								usr.isAdmin());
				}
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	/**
	 * used by the framework to instantiate rpcLanguage
	 * @param rpcUser
	 */
	@InjectService
	public void setUserInfoService( UserInfoServiceAsync rpcUser ) {
		this.rpcUser = rpcUser;
	}
}
