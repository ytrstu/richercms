package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.UserInfoServiceAsync;
import com.sfeir.richercms.page.client.ArboPageServiceAsync;
import com.sfeir.richercms.page.client.ImageAndId;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.IUserManager;
import com.sfeir.richercms.page.client.view.UserManager;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.shared.BeanUser;


@Presenter( view = UserManager.class)
public class UserManagerPresenter extends LazyPresenter<IUserManager, PageEventBus>{


	private UserInfoServiceAsync rpcUser = null;
	private ArboPageServiceAsync rpcPage = null;
	
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
								fetchUserTable();
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
		this.rpcPage.getAllLockedPages(new AsyncCallback<List<BeanArboPage>>() {
			public void onFailure(Throwable caught) {}
			public void onSuccess(List<BeanArboPage> lokedPage) {
				makeTable(lokedPage);
			}
		});
	}
	
	/**
	 * Function call by fetchUserTable to fetch the table
	 * @param lokedPage
	 */
	private void makeTable(final List<BeanArboPage> lokedPages){
		
		this.rpcUser.getUsers(new AsyncCallback<List<BeanUser>>() {
			public void onSuccess(List<BeanUser> result) {	
				
				// boucle sur les user
				for(BeanUser usr : result){
					String state = "Offline";
					
					if(usr.isLoggedIn())
						state = "OnLine";
					//add new line	
					view.addLine(usr.getEmailAddress(),
								usr.getNickname(), 
								state);
					// add admin RadioButton + handle event
					view.addAdminWidget(usr.isAdmin());
					
					// take all lockedPage by current User
					for(BeanArboPage lokedPage : lokedPages){
						
						if(lokedPage.getIdUserInModif().intValue() == usr.getId().intValue()){
							//add all locked page in the line + handle event
							view.addLockedPage(lokedPage.getId(),lokedPage.getTranslation().get(0).getUrlName()).
									addClickHandler(new ClickHandler() {
										public void onClick(ClickEvent event) {	
											// ici il faudra débloquer la page
											ImageAndId img = (ImageAndId)event.getSource();
											unlockPageRPC(img.getId());
									}});
						}
					}
				}
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	private void unlockPageRPC(Long pageId) {
		this.rpcPage.unlockThisPage(pageId, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				
			}

			public void onSuccess(Void result) {
				fetchUserTable();
			}
		});
	}
	

	/**
	 * used by the framework to instantiate rpcPage 
	 * @param rpcPage
	 */
	@InjectService
	public void setPageService( ArboPageServiceAsync rpcPage ) {
		this.rpcPage = rpcPage;
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
