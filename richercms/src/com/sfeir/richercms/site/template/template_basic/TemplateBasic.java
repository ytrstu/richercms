package com.sfeir.richercms.site.template.template_basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;
import com.sfeir.richercms.page.server.business.ArboPage;
import com.sfeir.richercms.page.shared.BeanArboPage;
import com.sfeir.richercms.page.shared.BeanDependentTag;
import com.sfeir.richercms.page.shared.BeanTemplate;
import com.sfeir.richercms.site.TemplateTools;

public class TemplateBasic {
	
	private BeanArboPage page;
	private int translation;
	private BeanArboPage root;
	private List<LinkPage> linkPath;
	
	static {
        ObjectifyService.register(ArboPage.class);
	}
	
	public TemplateBasic(BeanArboPage page, int translationIndex){
		this.page = page;
		this.translation = translationIndex;
	}
	
	public List<LinkPage> getAllCategory() {
		return getAllPageByTag("Category");
	}
	
	public boolean isVisible(){
		return TemplateTools.isVisible(this.page);
	}
	
	public List<LinkPage> getAllNews(){
		return getAllPageByTag("News");
	}
	
	public List<LinkPage> getAllImportant(){
		return getAllPageByTag("Important");
	}
	
	public List<LinkPage> getChildArticle(){
		return getChildByTag("Article");
	}
	
	public List<LinkPage> getChildCategory(){
		return getChildByTag("Category");
	}
	
	public LinkPage getRootPage(){
		
		if(this.root == null)
			this.root = TemplateTools.getRootPage();
		
		return new LinkPage(this.root.getTranslation().get(this.translation)
							.getPageTitle(),
							"/"+this.root.getUrlName());
	}
	
	public List<LinkPage> getLinkPagePath(){
		if(this.linkPath == null)
			this.linkPath = TemplateTools.getLinkPagePath(this.page.getId(),
															this.translation);
		return this.linkPath;
	}
	
	public List<LinkPage> getLinkSistersPage(){
		return TemplateTools.getLinkSistersPage(this.page.getId(),this.translation);
	}
	
	
	public String getPath(){
		return TemplateTools.getPagePath(page.getId(), this.translation);
	}
	
	public String getBrowserTitle() {
		return page.getTranslation().get(this.translation).getBrowserTitle();
	}
	
	public String getPageTitle() {
		return page.getTranslation().get(this.translation).getPageTitle();
	}
	
	
	public String getUrlName() {
		return page.getUrlName();
	}
	
	
	public String getDescription() {
		return page.getTranslation().get(this.translation).getDescription();
	}
	
	
	public String getKeyWord() {
		return page.getTranslation().get(this.translation).getKeyWord().replaceAll(" ", ",");
	}
	
	
	public Date getPublicationStart() {
		return page.getPublicationStart();
	}
	

	public Date getPublicationFinish() {
		return page.getPublicationFinish();
	}

	
	public String getContent() {
		return page.getTranslation().get(this.translation).getContent();
	}

	public int getTranslation() {
		return translation;
	}

	public void setTranslation(int translation) {
		this.translation = translation;
	}
	
	/**
	 * Delete the null value at the end of
	 * request and add right urlName
	 * @param path : return by request.getInfoPath()
	 * @return right url
	 */
	public String getRegularPath(String path){
		return path.replace("null?", this.page.getUrlName()+"?");
	}
	
	public List<LinkPage> getAllPageByTag(String tagName) {
		
		Objectify ofy = ObjectifyService.begin();
		ArrayList<LinkPage> lnkPage = new ArrayList<LinkPage>();
		BeanTemplate template = TemplateTools.getTemplatebyName("basic");
		Query<ArboPage> pages = ofy.query(ArboPage.class).filter("templateId =", template.getId());
		
		for(ArboPage page : pages){
			
			for(BeanDependentTag tag : TemplateTools.getTag(page.getId())){
				if(tag.getDependentTag().getTagName().equals(tagName)){
					lnkPage.add(new LinkPage(ofy.get(page.getTranslation().get(this.translation))
									.getPageTitle(),
							TemplateTools.getPagePath(page.getId(), this.translation)));
					break;
				}
			}		
		}
		return lnkPage;
	}
	
	private List<LinkPage> getChildByTag(String tagName){
		List<BeanArboPage> childs = TemplateTools.getChildPage(this.page.getId());
		ArrayList<LinkPage> lnkPage = new ArrayList<LinkPage>();

		for(BeanArboPage child : childs){
			for(BeanDependentTag tag : TemplateTools.getTag(child.getId())){
				if(tag.getDependentTag().getTagName().equals(tagName)){
					lnkPage.add(new LinkPage(child.getTranslation().get(this.translation)
									.getPageTitle(),
							TemplateTools.getPagePath(child.getId(), this.translation)));
					break;
				}
			}
		}
		return lnkPage;
	}
}
