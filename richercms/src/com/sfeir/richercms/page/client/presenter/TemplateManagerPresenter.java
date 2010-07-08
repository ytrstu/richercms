package com.sfeir.richercms.page.client.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.sfeir.richercms.client.view.PopUpMessage;
import com.sfeir.richercms.page.client.PopUpTemplateState;
import com.sfeir.richercms.page.client.TagServiceAsync;
import com.sfeir.richercms.page.client.TemplateServiceAsync;
import com.sfeir.richercms.page.client.event.PageEventBus;
import com.sfeir.richercms.page.client.interfaces.ITemplateManager;
import com.sfeir.richercms.page.client.view.TemplateManager;
import com.sfeir.richercms.page.shared.BeanTag;
import com.sfeir.richercms.page.shared.BeanTemplate;

@Presenter( view = TemplateManager.class)
public class TemplateManagerPresenter extends LazyPresenter<ITemplateManager, PageEventBus>{

	private TagServiceAsync rpcTag = null;
	private TemplateServiceAsync rpcTemplate = null;
	private PopUpTemplateState popUpState = PopUpTemplateState.add;
	private boolean mayApplyChange = false;
	
	public void bindView() {
		
		this.view.getBtnAddClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.disableApplyTagBtn();
				mayApplyChange = false;
				view.showPopUpAddTemplate();
				popUpState = PopUpTemplateState.add;
			}
		});
		
		this.view.getBtnDelClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(view.getSelectedTemplateId() != null) {
					eventBus.addWaitLinePopUp("Suppression en cours");
					rpcTemplate.deleteTemplate(view.getSelectedTemplateId(), 
							new AsyncCallback<Void>() {
						
						public void onFailure(Throwable caught) {
							eventBus.addErrorLinePopUp("Suppression impossible");
							eventBus.hideInformationPopUp();
						}
						public void onSuccess(Void result) {
							view.deleteSelectedTemplate();
							fetchTagTable();
							eventBus.addSuccessPopUp("Suppression réussi");
							eventBus.hideInformationPopUp();
						}
					});
				}
			}
		});
		
		this.view.getBtnModifyClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(view.getSelectedTemplateId() != null) {
					view.disableApplyTagBtn();
					mayApplyChange = false;
					view.showPopUpAddTemplate();
					popUpState = PopUpTemplateState.modify;
					rpcTemplate.getTemplate(view.getSelectedTemplateId(), new AsyncCallback<BeanTemplate>() {
						public void onFailure(Throwable caught) {}
						public void onSuccess(BeanTemplate result) {
							view.setPopUpNewTempDesc(result.getDescription());
							view.setPopUpNewTempName(result.getName());
						}
					});
				}
			}
		});
		
		this.view.getPopUpBtnCancel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.hidePopUpAddTemplate();
			}
		});
		
		this.view.getPopUpBtnOk().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(addOrModifyTemplate())//add template if its possible
					view.hidePopUpAddTemplate();
			}
		});
		
		this.view.getPopUpKbEvent().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					if(addOrModifyTemplate())//add template if its possible
						view.hidePopUpAddTemplate();// hide popUp only if template are added
				}else if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE){
					view.hidePopUpAddTemplate();
				}
			}
		});
		
		this.view.getTemplateLstSelection().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				view.disableApplyTagBtn();
				mayApplyChange = false;
				modifySelectedTemplate();
			}
		});
		
		this.view.getBtnApplyTagClick().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				view.disableApplyTagBtn();
				mayApplyChange = false;
				saveNewTag();
			}
		});
		
		this.view.getKeyPressEvent().addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if(mayApplyChange  && event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					view.disableApplyTagBtn();
					mayApplyChange = false;
					saveNewTag();
				}
			}
		});
	}
	
	public void onStartTemplateManager() {
		//fetchTemplateList do : fetch the template List and
		//call the fetchTagTable to synchronize RPC call
		this.fetchTemplateList();
		this.view.disableApplyTagBtn();
		mayApplyChange = false;
		eventBus.displayTemplateManager(this.view);
	}
	
	/**
	 * Take information in the popup and create or modify the template
	 * and add it into the list
	 * @return true if add was possible, false either
	 */
	private boolean addOrModifyTemplate() {
		
		if(this.view.getPopUpNewTempName().length() == 0) {
			PopUpMessage p = new PopUpMessage("Le nom du template ne peut être vide !");
			p.show();
			return false;
		}else {
			BeanTemplate bTP = new BeanTemplate();
			bTP.setName(this.view.getPopUpNewTempName());
			bTP.setDescription(this.view.getPopUpNewTempDesc());
			
			switch(this.popUpState) {
			case modify :
				this.rpcTemplate.updateTemplate(this.view.getSelectedTemplateId(),
						this.view.getPopUpNewTempName(), 
						this.view.getPopUpNewTempDesc(), 
						new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						view.changeSelectedTagName(view.getPopUpNewTempName());
					}
					public void onFailure(Throwable caught) {}
				});
				break;
			case add :
				this.rpcTemplate.addTemplate(bTP, new AsyncCallback<Long>() {
					public void onSuccess(Long result) {
						view.addTemplateInList(view.getPopUpNewTempName(), 
								result.toString());
					}
					public void onFailure(Throwable caught) {
					}
				});
				break;
			}
			return true;
		}
	}
	
	/**
	 * This function clear TemplateList and add
	 * all template into the list (use an rpc call to take all template)
	 */
	private void fetchTemplateList() {
		this.view.clearTemplateList();
		this.rpcTemplate.getAllTemplate(new AsyncCallback<List<BeanTemplate>>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(List<BeanTemplate> result) {
				for(BeanTemplate template : result){
					view.addTemplateInList(template.getName(), 
							template.getId().toString());
				}
				fetchTagTable();
			}
		});
	}
	
	/**
	 * Take the template selected into the list
	 * and call selectGoodTag to check good tag
	 */
	private void modifySelectedTemplate() {
		//test if list contain no template yet
		if(this.view.getSelectedTemplateId() != null)
			this.rpcTemplate.getTemplate(this.view.getSelectedTemplateId(), 
					new AsyncCallback<BeanTemplate>() {
						public void onFailure(Throwable caught) {
						}
						public void onSuccess(BeanTemplate result) {
							selectGoodTag(result.getAssociatedTags());
						}
			});
	}
	
	/**
	 * Select good tag
	 * @param tags : template's selected tags
	 */
	private void selectGoodTag(List<BeanTag> tags){
		this.view.unCheckAllTags();
		for(BeanTag tag : tags){
			this.view.checktag(tag.getId());
		}
	}
	
	/**
	 * Clear the tag table and and all tag in it
	 * (use an RPC call to take all tag)
	 */
	private void fetchTagTable(){
		this.view.clearTagTable();
		this.rpcTag.getAllTags(new AsyncCallback<List<BeanTag>>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(List<BeanTag> result) {
				for(BeanTag tag : result) {
					//add tag and handle event
					//if a checkBox is clicked, we enable applyChange Button
					view.addTagLine(tag.getId().toString(), 
							tag.getTagName(), 
							tag.getShortLib(), 
							tag.getDescription(),
							tag.isTextual()
							).addValueChangeHandler(new ValueChangeHandler<Boolean>() {
								public void onValueChange(ValueChangeEvent<Boolean> event) {
									view.enableApplyTagBtn();
									mayApplyChange = true;
								}
							});
				}
				modifySelectedTemplate();//check tag for template selected by default
			}
		});
	}
	
	/**
	 * Save new selected tag into the template
	 */
	private void saveNewTag() {
		this.rpcTemplate.upDateTagsTemplate(view.getSelectedTemplateId(), 
				view.getSelectedTagsId(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {}
					public void onSuccess(Void result) {}
				});
	}
	
	/**
	 * used by the framework to instantiate rpcTag
	 * @param rpcTag
	 */
	@InjectService
	public void setTagService( TagServiceAsync rpcTag ) {
		this.rpcTag = rpcTag;
	}
	
	/**
	 * used by the framework to instantiate rpcTag
	 * @param rpcTag
	 */
	@InjectService
	public void setTemplateService( TemplateServiceAsync rpcTemplate ) {
		this.rpcTemplate = rpcTemplate;
	}
}
